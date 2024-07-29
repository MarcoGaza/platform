package cn.econets.blossom.module.mp.service.tag;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagPageReqVO;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.MpTagUpdateReqVO;
import cn.econets.blossom.module.mp.convert.tag.MpTagConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.tag.MpTagDO;
import cn.econets.blossom.module.mp.dal.mysql.tag.MpTagMapper;
import cn.econets.blossom.module.mp.framework.mp.core.MpServiceFactory;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.module.mp.enums.ErrorCodeConstants.*;

/**
 * Public account tag Service Implementation class
 *
 *
 */
@Slf4j
@Service
@Validated
public class MpTagServiceImpl implements MpTagService {

    @Resource
    private MpTagMapper mpTagMapper;

    @Resource
    private MpAccountService mpAccountService;

    @Resource
    @Lazy // Delayed loading，To solve the problem of delayed loading
    private MpServiceFactory mpServiceFactory;

    @Override
    public Long createTag(MpTagCreateReqVO createReqVO) {
        // Get the public account
        MpAccountDO account = mpAccountService.getRequiredAccount(createReqVO.getAccountId());

        // First step，Add tags to the public account platform。Unique tag name，Submit to the official account platform
        WxMpService mpService = mpServiceFactory.getRequiredMpService(createReqVO.getAccountId());
        WxUserTag wxTag;
        try {
            wxTag = mpService.getUserTagService().tagCreate(createReqVO.getName());
        } catch (WxErrorException e) {
            throw exception(TAG_CREATE_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Add tags to the database
        MpTagDO tag = MpTagConvert.INSTANCE.convert(wxTag, account);
        mpTagMapper.insert(tag);
        return tag.getId();
    }

    @Override
    public void updateTag(MpTagUpdateReqVO updateReqVO) {
        // Verify tag exists
        MpTagDO tag = validateTagExists(updateReqVO.getId());

        // First step，Update tags to the public account platform。Unique tag name，Submit to the official account platform
        WxMpService mpService = mpServiceFactory.getRequiredMpService(tag.getAccountId());
        try {
            mpService.getUserTagService().tagUpdate(tag.getTagId(), updateReqVO.getName());
        } catch (WxErrorException e) {
            throw exception(TAG_UPDATE_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Update tags to database
        mpTagMapper.updateById(new MpTagDO().setId(tag.getId()).setName(updateReqVO.getName()));
    }

    @Override
    public void deleteTag(Long id) {
        // Verify tag exists
        MpTagDO tag = validateTagExists(id);

        // First step，Delete tags to the public account platform。
        WxMpService mpService = mpServiceFactory.getRequiredMpService(tag.getAccountId());
        try {
            mpService.getUserTagService().tagDelete(tag.getTagId());
        } catch (WxErrorException e) {
            throw exception(TAG_DELETE_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Delete tags to database
        mpTagMapper.deleteById(tag.getId());
    }

    private MpTagDO validateTagExists(Long id) {
        MpTagDO tag = mpTagMapper.selectById(id);
        if (tag == null) {
            throw exception(TAG_NOT_EXISTS);
        }
        return tag;
    }

    @Override
    public PageResult<MpTagDO> getTagPage(MpTagPageReqVO pageReqVO) {
        return mpTagMapper.selectPage(pageReqVO);
    }

    @Override
    public MpTagDO get(Long id) {
        return mpTagMapper.selectById(id);
    }

    @Override
    public List<MpTagDO> getTagList() {
        return mpTagMapper.selectList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncTag(Long accountId) {
        MpAccountDO account = mpAccountService.getRequiredAccount(accountId);

        // First step，Get the latest tag list from the official account platform
        WxMpService mpService = mpServiceFactory.getRequiredMpService(accountId);
        List<WxUserTag> wxTags;
        try {
            wxTags = mpService.getUserTagService().tagGet();
        } catch (WxErrorException e) {
            throw exception(TAG_GET_FAIL, e.getError().getErrorMsg());
        }

        // Step 2，Merge and update back to your own database；Because the tag only has 100 pcs，So direct for Loop operation
        Map<Long, MpTagDO> tagMap = convertMap(mpTagMapper.selectListByAccountId(accountId),
                MpTagDO::getTagId);
        wxTags.forEach(wxTag -> {
            MpTagDO tag = tagMap.remove(wxTag.getId());
            // Situation 1，Does not exist，Newly added
            if (tag == null) {
                tag = MpTagConvert.INSTANCE.convert(wxTag, account);
                mpTagMapper.insert(tag);
                return;
            }
            // Situation 2，Exists，Update
            mpTagMapper.updateById(new MpTagDO().setId(tag.getId())
                    .setName(wxTag.getName()).setCount(wxTag.getCount()));
        });
        // Situation 3，Some tags no longer exist，Delete
        if (CollUtil.isNotEmpty(tagMap)) {
            mpTagMapper.deleteBatchIds(convertList(tagMap.values(), MpTagDO::getId));
        }
    }

}
