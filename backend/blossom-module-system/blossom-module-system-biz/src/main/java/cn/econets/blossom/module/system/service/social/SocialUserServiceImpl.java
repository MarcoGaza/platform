package cn.econets.blossom.module.system.service.social;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserRespDTO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserPageReqVO;
import cn.econets.blossom.module.system.dal.mysql.social.SocialUserBindMapper;
import cn.econets.blossom.module.system.dal.mysql.social.SocialUserMapper;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialUserBindDO;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialUserDO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.xingyuv.jushauth.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.AUTH_THIRD_LOGIN_NOT_BIND;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.SOCIAL_USER_NOT_FOUND;

/**
 * Social user Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class SocialUserServiceImpl implements SocialUserService {

    @Resource
    private SocialUserBindMapper socialUserBindMapper;
    @Resource
    private SocialUserMapper socialUserMapper;

    @Resource
    private SocialClientService socialClientService;

    @Override
    public List<SocialUserDO> getSocialUserList(Long userId, Integer userType) {
        // Get Binding
        List<SocialUserBindDO> socialUserBinds = socialUserBindMapper.selectListByUserIdAndUserType(userId, userType);
        if (CollUtil.isEmpty(socialUserBinds)) {
            return Collections.emptyList();
        }
        // Get social users
        return socialUserMapper.selectBatchIds(CollectionUtils.convertSet(socialUserBinds, SocialUserBindDO::getSocialUserId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String bindSocialUser(SocialUserBindReqDTO reqDTO) {
        // Get social users
        SocialUserDO socialUser = authSocialUser(reqDTO.getSocialType(), reqDTO.getUserType(),
                reqDTO.getCode(), reqDTO.getState());
        Assert.notNull(socialUser, "Social user cannot be empty");

        // The social user may have previously bound to another user，Need to unbind
        socialUserBindMapper.deleteByUserTypeAndSocialUserId(reqDTO.getUserType(), socialUser.getId());

        // The user may have bound this social type before，Need to unbind
        socialUserBindMapper.deleteByUserTypeAndUserIdAndSocialType(reqDTO.getUserType(), reqDTO.getUserId(),
                socialUser.getType());

        // Bind the currently logged in social user
        SocialUserBindDO socialUserBind = SocialUserBindDO.builder()
                .userId(reqDTO.getUserId()).userType(reqDTO.getUserType())
                .socialUserId(socialUser.getId()).socialType(socialUser.getType()).build();
        socialUserBindMapper.insert(socialUserBind);
        return socialUser.getOpenid();
    }

    @Override
    public void unbindSocialUser(Long userId, Integer userType, Integer socialType, String openid) {
        // Get openid Corresponding SocialUserDO Social user
        SocialUserDO socialUser = socialUserMapper.selectByTypeAndOpenid(socialType, openid);
        if (socialUser == null) {
            throw exception(SOCIAL_USER_NOT_FOUND);
        }

        // Get the corresponding social binding relationship
        socialUserBindMapper.deleteByUserTypeAndUserIdAndSocialType(userType, userId, socialUser.getType());
    }

    @Override
    public SocialUserRespDTO getSocialUserByUserId(Integer userType, Long userId, Integer socialType) {
        // Get bound user
        SocialUserBindDO socialUserBind = socialUserBindMapper.selectByUserIdAndUserTypeAndSocialType(userId, userType, socialType);
        if (socialUserBind == null) {
            return null;
        }
        // Get social users
        SocialUserDO socialUser = socialUserMapper.selectById(socialUserBind.getSocialUserId());
        Assert.notNull(socialUser, "Social user cannot be empty");
        return new SocialUserRespDTO(socialUser.getOpenid(), socialUser.getNickname(), socialUser.getAvatar(),
                socialUserBind.getUserId());
    }

    @Override
    public SocialUserRespDTO getSocialUserByCode(Integer userType, Integer socialType, String code, String state) {
        // Get social users
        SocialUserDO socialUser = authSocialUser(socialType, userType, code, state);
        Assert.notNull(socialUser, "Social user cannot be empty");

        // Get bound user
        SocialUserBindDO socialUserBind = socialUserBindMapper.selectByUserTypeAndSocialUserId(userType,
                socialUser.getId());
        return new SocialUserRespDTO(socialUser.getOpenid(), socialUser.getNickname(), socialUser.getAvatar(),
                socialUserBind != null ? socialUserBind.getUserId() : null);
    }

    /**
     * Authorize to obtain the corresponding social user
     * If authorization fails，will throw {@link ServiceException} Abnormal
     *
     * @param socialType Type of social platform {@link SocialTypeEnum}
     * @param userType User Type
     * @param code     Authorization code
     * @param state    state
     * @return Authorized User
     */
    @NotNull
    public SocialUserDO authSocialUser(Integer socialType, Integer userType, String code, String state) {
        // Prioritize from DB Get，Because code Yes and can be used once。
        // When logging in via social media，When not bound User Time，Need to bind login，Needed at this time code Used twice
        SocialUserDO socialUser = socialUserMapper.selectByTypeAndCodeAnState(socialType, code, state);
        if (socialUser != null) {
            return socialUser;
        }

        // Request to obtain
        AuthUser authUser = socialClientService.getAuthUser(socialType, userType, code, state);
        Assert.notNull(authUser, "The third-party user cannot be empty");

        // Save to DB Medium
        socialUser = socialUserMapper.selectByTypeAndOpenid(socialType, authUser.getUuid());
        if (socialUser == null) {
            socialUser = new SocialUserDO();
        }
        socialUser.setType(socialType).setCode(code).setState(state) // Need to save code + state Field，Guaranteed to be available for subsequent inquiry
                .setOpenid(authUser.getUuid()).setToken(authUser.getToken().getAccessToken()).setRawTokenInfo((JsonUtils.toJsonString(authUser.getToken())))
                .setNickname(authUser.getNickname()).setAvatar(authUser.getAvatar()).setRawUserInfo(JsonUtils.toJsonString(authUser.getRawUserInfo()));
        if (socialUser.getId() == null) {
            socialUserMapper.insert(socialUser);
        } else {
            socialUserMapper.updateById(socialUser);
        }
        return socialUser;
    }

    // ==================== Social user CRUD ====================

    @Override
    public SocialUserDO getSocialUser(Long id) {
        return socialUserMapper.selectById(id);
    }

    @Override
    public PageResult<SocialUserDO> getSocialUserPage(SocialUserPageReqVO pageReqVO) {
        return socialUserMapper.selectPage(pageReqVO);
    }

}
