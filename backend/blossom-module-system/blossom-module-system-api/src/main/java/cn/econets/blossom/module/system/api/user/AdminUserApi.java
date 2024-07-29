package cn.econets.blossom.module.system.api.user;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Admin User API Interface
 *
 *
 */
public interface AdminUserApi {

    /**
     * By user ID Query user
     *
     * @param id UserID
     * @return User object information
     */
    AdminUserRespDTO getUser(Long id);

    /**
     * By user ID Query user's subordinates
     *
     * @param userId User Number
     * @return User's subordinate user list
     */
    List<AdminUserRespDTO> getUserListBySubordinate(Long userId);

    /**
     * By user ID Query users
     *
     * @param ids User ID We
     * @return User object information
     */
    List<AdminUserRespDTO> getUserList(Collection<Long> ids);

    /**
     * Get the user array of the specified department
     *
     * @param deptIds Department Array
     * @return User array
     */
    List<AdminUserRespDTO> getUserListByDeptIds(Collection<Long> deptIds);

    /**
     * Get the user array of the specified position
     *
     * @param postIds Position array
     * @return User array
     */
    List<AdminUserRespDTO> getUserListByPostIds(Collection<Long> postIds);

    /**
     * Get user Map
     *
     * @param ids User ID array
     * @return User Map
     */
    default Map<Long, AdminUserRespDTO> getUserMap(Collection<Long> ids) {
        List<AdminUserRespDTO> users = getUserList(ids);
        return CollectionUtils.convertMap(users, AdminUserRespDTO::getId);
    }

    /**
     * Check if users are valid。As follows，Deemed invalid：
     * 1. User number does not exist
     * 2. User is disabled
     *
     * @param ids User ID array
     */
    void validateUserList(Collection<Long> ids);

}
