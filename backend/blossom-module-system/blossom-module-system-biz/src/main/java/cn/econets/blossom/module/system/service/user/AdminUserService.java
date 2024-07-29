package cn.econets.blossom.module.system.service.user;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserImportExcelVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserImportRespVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserPageReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.user.UserSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.hutool.core.collection.CollUtil;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend User Service Interface
 *
 */
public interface AdminUserService {

    /**
     * Create User
     *
     * @param createReqVO User Information
     * @return User Number
     */
    Long createUser(@Valid UserSaveReqVO createReqVO);

    /**
     * Modify user
     *
     * @param updateReqVO User Information
     */
    void updateUser(@Valid UserSaveReqVO updateReqVO);

    /**
     * Update the user's last login information
     *
     * @param id User Number
     * @param loginIp Login IP
     */
    void updateUserLogin(Long id, String loginIp);

    /**
     * Modify user personal information
     *
     * @param id User Number
     * @param reqVO User personal information
     */
    void updateUserProfile(Long id, @Valid UserProfileUpdateReqVO reqVO);

    /**
     * Modify user's personal password
     *
     * @param id User Number
     * @param reqVO Update user's personal password
     */
    void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordReqVO reqVO);

    /**
     * Update user avatar
     *
     * @param id         User id
     * @param avatarFile Avatar file
     */
    String updateUserAvatar(Long id, InputStream avatarFile) throws Exception;

    /**
     * Change password
     *
     * @param id       User Number
     * @param password Password
     */
    void updateUserPassword(Long id, String password);

    /**
     * Modify status
     *
     * @param id     User Number
     * @param status Status
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * Delete user
     *
     * @param id User Number
     */
    void deleteUser(Long id);

    /**
     * Query users by username
     *
     * @param username Username
     * @return User object information
     */
    AdminUserDO getUserByUsername(String username);

    /**
     * Get users by mobile phone number
     *
     * @param mobile Mobile phone number
     * @return User object information
     */
    AdminUserDO getUserByMobile(String mobile);

    /**
     * Get user pagination list
     *
     * @param reqVO Pagination conditions
     * @return Paged list
     */
    PageResult<AdminUserDO> getUserPage(UserPageReqVO reqVO);

    /**
     * By user ID Query user
     *
     * @param id UserID
     * @return User object information
     */
    AdminUserDO getUser(Long id);

    /**
     * Get the user array of the specified department
     *
     * @param deptIds Department Array
     * @return User array
     */
    List<AdminUserDO> getUserListByDeptIds(Collection<Long> deptIds);

    /**
     * Get the user array of the specified position
     *
     * @param postIds Position array
     * @return User Array
     */
    List<AdminUserDO> getUserListByPostIds(Collection<Long> postIds);

    /**
     * Get user list
     *
     * @param ids User ID array
     * @return User List
     */
    List<AdminUserDO> getUserList(Collection<Long> ids);

    /**
     * Check if users are valid。As follows，Deemed invalid：
     * 1. User number does not exist
     * 2. User is disabled
     *
     * @param ids User ID array
     */
    void validateUserList(Collection<Long> ids);

    /**
     * Get user Map
     *
     * @param ids User ID array
     * @return User Map
     */
    default Map<Long, AdminUserDO> getUserMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new HashMap<>();
        }
        return CollectionUtils.convertMap(getUserList(ids), AdminUserDO::getId);
    }

    /**
     * Get user list，Fuzzy matching based on nickname
     *
     * @param nickname Nickname
     * @return User List
     */
    List<AdminUserDO> getUserListByNickname(String nickname);

    /**
     * Batch import users
     *
     * @param importUsers     Import user list
     * @param isUpdateSupport Whether to support updates
     * @return Import results
     */
    UserImportRespVO importUserList(List<UserImportExcelVO> importUsers, boolean isUpdateSupport);

    /**
     * Get users with specified status
     *
     * @param status Status
     * @return Users
     */
    List<AdminUserDO> getUserListByStatus(Integer status);

    /**
     * Judge whether the password matches
     *
     * @param rawPassword Unencrypted password
     * @param encodedPassword Encrypted password
     * @return Whether it matches
     */
    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
