package cn.econets.blossom.module.system.api.social;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserRespDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserUnbindReqDTO;

import javax.validation.Valid;

/**
 * Social user API Interface
 *
 */
public interface SocialUserApi {

    /**
     * Bind social user
     *
     * @param reqDTO Bind information
     * @return Social User openid
     */
    String bindSocialUser(@Valid SocialUserBindReqDTO reqDTO);

    /**
     * Unbind social user
     *
     * @param reqDTO Unbind
     */
    void unbindSocialUser(@Valid SocialUserUnbindReqDTO reqDTO);

    /**
     * Get social users，Based on userId
     *
     * @param userType User Type
     * @param userId User Number
     * @param socialType Type of social platform
     * @return Social user
     */
    SocialUserRespDTO getSocialUserByUserId(Integer userType, Long userId, Integer socialType);

    /**
     * Get social users
     *
     * In the case of incorrect authentication information，will also throw {@link ServiceException} Business exception
     *
     * @param userType User Type
     * @param socialType Type of social platform
     * @param code Authorization code
     * @param state state
     * @return Social user
     */
    SocialUserRespDTO getSocialUserByCode(Integer userType, Integer socialType, String code, String state);
}
