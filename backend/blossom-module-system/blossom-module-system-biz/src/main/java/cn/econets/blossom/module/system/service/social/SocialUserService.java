package cn.econets.blossom.module.system.service.social;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserRespDTO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialUserDO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;

import javax.validation.Valid;
import java.util.List;

/**
 * Social user Service Interface，For example, authorized login on a social platform
 *
 */
public interface SocialUserService {

    /**
     * Get the social user list of the specified user
     *
     * @param userId   User Number
     * @param userType User Type
     * @return Social user list
     */
    List<SocialUserDO> getSocialUserList(Long userId, Integer userType);

    /**
     * Bind social user
     *
     * @param reqDTO Bind information
     * @return Social user openid
     */
    String bindSocialUser(@Valid SocialUserBindReqDTO reqDTO);

    /**
     * Unbind social user
     *
     * @param userId User Number
     * @param userType Global User Type
     * @param socialType Type of social platform {@link SocialTypeEnum}
     * @param openid Social platform openid
     */
    void unbindSocialUser(Long userId, Integer userType, Integer socialType, String openid);

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

    // ==================== Social user CRUD ====================

    /**
     * Get social users
     *
     * @param id Number
     * @return Social user
     */
    SocialUserDO getSocialUser(Long id);

    /**
     * Get social user paging
     *
     * @param pageReqVO Paged query
     * @return Social user paging
     */
    PageResult<SocialUserDO> getSocialUserPage(SocialUserPageReqVO pageReqVO);
}
