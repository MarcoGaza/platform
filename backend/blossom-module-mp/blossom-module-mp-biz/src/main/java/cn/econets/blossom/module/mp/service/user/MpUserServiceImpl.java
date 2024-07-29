package cn.econets.blossom.module.mp.service.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.mp.controller.admin.user.vo.MpUserPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.user.vo.MpUserUpdateReqVO;
import cn.econets.blossom.module.mp.convert.user.MpUserConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.user.MpUserDO;
import cn.econets.blossom.module.mp.dal.mysql.user.MpUserMapper;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.USER_NOT_EXISTS;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.USER_UPDATE_TAG_FAIL;

/**
 * WeChat public account fans Service Implementation class
 *
 *
 */
@Service
@Validated
@Slf4j
public class MpUserServiceImpl implements MpUserService {

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpAccountService mpAccountService;

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpServiceFactory mpServiceFactory;

    @Resource
    private MpUserMapper mpUserMapper;

    @Override
    public MpUserDO getUser(Long id) {
        return mpUserMapper.selectById(id);
    }

    @Override
    public MpUserDO getUser(String appId, String openId) {
        return mpUserMapper.selectByAppIdAndOpenid(appId, openId);
    }

    @Override
    public List<MpUserDO> getUserList(Collection<Long> ids) {
        return mpUserMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MpUserDO> getUserPage(MpUserPageReqVO pageReqVO) {
        return mpUserMapper.selectPage(pageReqVO);
    }

    @Override
    public MpUserDO saveUser(String appId, WxMpUser wxMpUser) {
        // Build saved MpUserDO Object
        MpAccountDO account = mpAccountService.getAccountFromCache(appId);
        MpUserDO user = MpUserConvert.INSTANCE.convert(account, wxMpUser);

        // Depending on the situation，Insert or update
        MpUserDO dbUser = mpUserMapper.selectByAppIdAndOpenid(appId, wxMpUser.getOpenId());
        if (dbUser == null) {
            mpUserMapper.insert(user);
        } else {
            user.setId(dbUser.getId());
            mpUserMapper.updateById(user);
        }
        return user;
    }

    @Override
    @Async
    public void syncUser(Long accountId) {
        MpAccountDO account = mpAccountService.getRequiredAccount(accountId);
        // for Loop，Avoid unexpected problems with recursion，Causes an infinite loop
        String nextOpenid = null;
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            log.info("[syncUser][No.({}) Loading the public account fans list，nextOpenid({})]", i, nextOpenid);
            try {
                nextOpenid = syncUser0(account, nextOpenid);
            } catch (WxErrorException e) {
                log.error("[syncUser][No.({}) Secondary synchronization fans abnormality]", i, e);
                break;
            }
            // If nextOpenid Empty，Synchronization has been completed
            if (StrUtil.isEmpty(nextOpenid)) {
                break;
            }
        }
    }

    private String syncUser0(MpAccountDO account, String nextOpenid) throws WxErrorException {
        // First step，Load fans from public account streaming
        WxMpService mpService = mpServiceFactory.getRequiredMpService(account.getId());
        WxMpUserList wxUserList = mpService.getUserService().userList(nextOpenid);
        if (CollUtil.isEmpty(wxUserList.getOpenids())) {
            return null;
        }

        // Step 2，Load fan information in batches
        List<List<String>> openidsList = CollUtil.split(wxUserList.getOpenids(), 100);
        for (List<String> openids : openidsList) {
            log.info("[syncUser][Batch load fan information，openids({})]", openids);
            List<WxMpUser> wxUsers = mpService.getUserService().userInfoList(openids);
            batchSaveUser(account, wxUsers);
        }

        // Return to next time nextOpenId
        return wxUserList.getNextOpenid();
    }

    private void batchSaveUser(MpAccountDO account, List<WxMpUser> wxUsers) {
        if (CollUtil.isEmpty(wxUsers)) {
            return;
        }
        // 1. Get the fan list saved in the database
        List<MpUserDO> dbUsers = mpUserMapper.selectListByAppIdAndOpenid(account.getAppId(),
                CollectionUtils.convertList(wxUsers, WxMpUser::getOpenId));
        Map<String, MpUserDO> openId2Users = CollectionUtils.convertMap(dbUsers, MpUserDO::getOpenid);

        // 2.1 Depending on the situation，Insert or update
        List<MpUserDO> users = MpUserConvert.INSTANCE.convertList(account, wxUsers);
        List<MpUserDO> newUsers = new ArrayList<>();
        for (MpUserDO user : users) {
            MpUserDO dbUser = openId2Users.get(user.getOpenid());
            if (dbUser == null) { // Newly added：Batch insert later
                newUsers.add(user);
            } else { // Update：Execute update directly
                user.setId(dbUser.getId());
                mpUserMapper.updateById(user);
            }
        }
        // 2.2 Batch insert
        if (CollUtil.isNotEmpty(newUsers)) {
            mpUserMapper.insertBatch(newUsers);
        }
    }

    @Override
    public void updateUserUnsubscribe(String appId, String openid) {
        MpUserDO dbUser = mpUserMapper.selectByAppIdAndOpenid(appId, openid);
        if (dbUser == null) {
            log.error("[updateUserUnsubscribe][WeChat public account fans appId({}) openid({}) Does not exist]", appId, openid);
            return;
        }
        mpUserMapper.updateById(new MpUserDO().setId(dbUser.getId()).setSubscribeStatus(CommonStatusEnum.DISABLE.getStatus())
                .setUnsubscribeTime(LocalDateTime.now()));
    }

    @Override
    public void updateUser(MpUserUpdateReqVO updateReqVO) {
        // Check existence
        MpUserDO user = validateUserExists(updateReqVO.getId());

        // First step，Update tags to public account
        updateUserTag(user.getAppId(), user.getOpenid(), updateReqVO.getTagIds());

        // Step 2，Update basic information to the database
        MpUserDO updateObj = MpUserConvert.INSTANCE.convert(updateReqVO).setId(user.getId());
        mpUserMapper.updateById(updateObj);
    }

    private MpUserDO validateUserExists(Long id) {
        MpUserDO user = mpUserMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    private void updateUserTag(String appId, String openid, List<Long> tagIds) {
        WxMpService mpService = mpServiceFactory.getRequiredMpService(appId);
        try {
            // First step，Cancel the original label first
            List<Long> oldTagIds = mpService.getUserTagService().userTagList(openid);
            for (Long tagId : oldTagIds) {
                mpService.getUserTagService().batchUntagging(tagId, new String[]{openid});
            }
            // Step 2，Set a new label
            if (CollUtil.isEmpty(tagIds)) {
                return;
            }
            for (Long tagId: tagIds) {
                mpService.getUserTagService().batchTagging(tagId, new String[]{openid});
            }
        } catch (WxErrorException e) {
            throw exception(USER_UPDATE_TAG_FAIL, e.getError().getErrorMsg());
        }
    }

}
