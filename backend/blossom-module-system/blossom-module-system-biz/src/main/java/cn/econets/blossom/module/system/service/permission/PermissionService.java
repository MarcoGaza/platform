package cn.econets.blossom.module.system.service.permission;

import cn.econets.blossom.module.system.api.permission.dto.DeptDataPermissionRespDTO;

import java.util.Collection;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * Permissions Service Interface
 * <p>
 * Provide user-Role、Role-Menu、Role-Department-related authority processing
 *
 */
public interface PermissionService {

    /**
     * Judge whether you have permission，Any one is fine
     *
     * @param userId      User Number
     * @param permissions Permissions
     * @return Yes
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * Judge whether there is a role，Any one is fine
     *
     * @param roles Role array
     * @return Yes
     */
    boolean hasAnyRoles(Long userId, String... roles);

    // ========== Role-Menu related methods  ==========

    /**
     * Set character menu
     *
     * @param roleId  Role number
     * @param menuIds Menu number set
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * When processing role deletion，Delete associated authorization data
     *
     * @param roleId Character number
     */
    void processRoleDeleted(Long roleId);

    /**
     * When processing menu deletion，Delete associated authorization data
     *
     * @param menuId Menu number
     */
    void processMenuDeleted(Long menuId);

    /**
     * Get the menu number set owned by the character
     *
     * @param roleId Character number
     * @return Menu number set
     */
    default Set<Long> getRoleMenuListByRoleId(Long roleId) {
        return getRoleMenuListByRoleId(singleton(roleId));
    }

    /**
     * Get the menu number set owned by the characters
     *
     * @param roleIds Role number array
     * @return Menu number set
     */
    Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds);

    /**
     * Get the character ID array that has the specified menu，Get from cache
     *
     * @param menuId Menu number
     * @return Role number array
     */
    Set<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId);

    // ========== User-Role related methods  ==========

    /**
     * Set user role
     *
     * @param userId  Role number
     * @param roleIds Role number collection
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

    /**
     * When processing user deletion，Delete associated authorization data
     *
     * @param userId User Number
     */
    void processUserDeleted(Long userId);

    /**
     * Get the set of user IDs with multiple roles
     *
     * @param roleIds Role number collection
     * @return User ID collection
     */
    Set<Long> getUserRoleIdListByRoleId(Collection<Long> roleIds);

    /**
     * Get the role ID set owned by the user
     *
     * @param userId User Number
     * @return Role number collection
     */
    Set<Long> getUserRoleIdListByUserId(Long userId);

    /**
     * Get the role ID set owned by the user，Get from cache
     *
     * @param userId User Number
     * @return Role number collection
     */
    Set<Long> getUserRoleIdListByUserIdFromCache(Long userId);

    // ========== User-Department-related methods  ==========

    /**
     * Set the role's data permissions
     *
     * @param roleId           Character number
     * @param dataScope        Data Range
     * @param dataScopeDeptIds Department number array
     */
    void assignRoleDataScope(Long roleId, Integer dataScope, Set<Long> dataScopeDeptIds);

    /**
     * Get the department data permissions of the logged-in user
     *
     * @param userId User Number
     * @return Department data permissions
     */
    DeptDataPermissionRespDTO getDeptDataPermission(Long userId);

}
