package cn.econets.blossom.module.system.api.permission;

import cn.econets.blossom.module.system.api.permission.dto.DeptDataPermissionRespDTO;

import java.util.Collection;
import java.util.Set;

/**
 * Permissions API Interface
 *
 */
public interface PermissionApi {

    /**
     * Get the set of user IDs with multiple roles
     *
     * @param roleIds Role number collection
     * @return User ID collection
     */
    Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds);

    /**
     * Judge whether there is permission，Any one is fine
     *
     * @param userId User Number
     * @param permissions Permissions
     * @return Yes
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * Judge whether there is a role，Any one is fine
     *
     * @param userId User Number
     * @param roles Role array
     * @return Yes
     */
    boolean hasAnyRoles(Long userId, String... roles);

    /**
     * Get the department data permissions of the logged-in user
     *
     * @param userId User Number
     * @return Department data permissions
     */
    DeptDataPermissionRespDTO getDeptDataPermission(Long userId);

}
