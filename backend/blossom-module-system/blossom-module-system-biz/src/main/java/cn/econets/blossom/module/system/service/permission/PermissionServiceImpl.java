package cn.econets.blossom.module.system.service.permission;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.module.system.api.permission.dto.DeptDataPermissionRespDTO;
import cn.econets.blossom.module.system.dal.mysql.permission.RoleMenuMapper;
import cn.econets.blossom.module.system.dal.mysql.permission.UserRoleMapper;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleMenuDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.UserRoleDO;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import cn.econets.blossom.module.system.enums.permission.DataScopeEnum;
import cn.econets.blossom.module.system.service.dept.DeptService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Supplier;

/**
 * Permissions Service Implementation class
 *
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private DeptService deptService;
    @Resource
    private AdminUserService userService;

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // If empty，It means that the permission is already there
        if (ArrayUtil.isEmpty(permissions)) {
            return true;
        }

        // Get the currently logged in role。If empty，Indicates no permission
        List<RoleDO> roles = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollUtil.isEmpty(roles)) {
            return false;
        }

        // Situation 1：Traverse and determine each permission，If there is one satisfaction，Indicates that there is authority
        for (String permission : permissions) {
            if (hasAnyPermission(roles, permission)) {
                return true;
            }
        }

        // Case 2：If it is a super administrator，It also means that there is authority
        return roleService.hasAnySuperAdmin(CollectionUtils.convertSet(roles, RoleDO::getId));
    }

    /**
     * Judge the specified role，Do you have this? permission Permissions
     *
     * @param roles Specify role array
     * @param permission Permission flag
     * @return Do you have it?
     */
    private boolean hasAnyPermission(List<RoleDO> roles, String permission) {
        List<Long> menuIds = menuService.getMenuIdListByPermissionFromCache(permission);
        // Use strict mode，If the permission cannot find the corresponding one Menu Words，Also think there is no permission
        if (CollUtil.isEmpty(menuIds)) {
            return false;
        }

        // Judge whether you have permission
        Set<Long> roleIds = CollectionUtils.convertSet(roles, RoleDO::getId);
        for (Long menuId : menuIds) {
            // Get the character ID set that owns this menu
            Set<Long> menuRoleIds = getSelf().getMenuRoleIdListByMenuIdFromCache(menuId);
            // If there is an intersection，Indicates that there is permission
            if (CollUtil.containsAny(menuRoleIds, roleIds)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // If empty，It means that the permission is already there
        if (ArrayUtil.isEmpty(roles)) {
            return true;
        }

        // Get the currently logged in role。If empty，Indicates no permission
        List<RoleDO> roleList = getEnableUserRoleListByUserIdFromCache(userId);
        if (CollUtil.isEmpty(roleList)) {
            return false;
        }

        // Judge whether there is a role
        Set<String> userRoles = CollectionUtils.convertSet(roleList, RoleDO::getCode);
        return CollUtil.containsAny(userRoles, Sets.newHashSet(roles));
    }

    // ========== Role-Menu related methods  ==========

    @Override
    @DSTransactional // Multiple data sources，Use @DSTransactional Ensure local transactions，And switching of data source
    @CacheEvict(value = RedisKeyConstants.MENU_ROLE_ID_LIST,
            allEntries = true) // allEntries Clear all caches，The main update involves menuIds More，Batch will be faster
    public void assignRoleMenu(Long roleId, Set<Long> menuIds) {
        // Get the menu number of the character
        Set<Long> dbMenuIds = CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleId), RoleMenuDO::getMenuId);
        // Calculate the numbers of newly added and deleted menus
        Set<Long> menuIdList = CollUtil.emptyIfNull(menuIds);
        Collection<Long> createMenuIds = CollUtil.subtract(menuIdList, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIdList);
        // Perform addition and deletion。For authorized menus，No need to do anything
        if (CollUtil.isNotEmpty(createMenuIds)) {
            roleMenuMapper.insertBatch(CollectionUtils.convertList(createMenuIds, menuId -> {
                RoleMenuDO entity = new RoleMenuDO();
                entity.setRoleId(roleId);
                entity.setMenuId(menuId);
                return entity;
            }));
        }
        if (CollUtil.isNotEmpty(deleteMenuIds)) {
            roleMenuMapper.deleteListByRoleIdAndMenuIds(roleId, deleteMenuIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = RedisKeyConstants.MENU_ROLE_ID_LIST,
                    allEntries = true), // allEntries Clear all caches，Not easily accessible here roleId Corresponding menu Caches
            @CacheEvict(value = RedisKeyConstants.USER_ROLE_ID_LIST,
                    allEntries = true) // allEntries Clear all caches，Not easily accessible here roleId Corresponding user Caches
    })
    public void processRoleDeleted(Long roleId) {
        // Mark for deletion UserRole
        userRoleMapper.deleteListByRoleId(roleId);
        // Mark for deletion RoleMenu
        roleMenuMapper.deleteListByRoleId(roleId);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.MENU_ROLE_ID_LIST, key = "#menuId")
    public void processMenuDeleted(Long menuId) {
        roleMenuMapper.deleteListByMenuId(menuId);
    }

    @Override
    public Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }

        // If you are an administrator，Get all menu numbers
        if (roleService.hasAnySuperAdmin(roleIds)) {
            return CollectionUtils.convertSet(menuService.getMenuList(), MenuDO::getId);
        }
        // If you are not an administrator，Get the menu number you own
        return CollectionUtils.convertSet(roleMenuMapper.selectListByRoleId(roleIds), RoleMenuDO::getMenuId);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.MENU_ROLE_ID_LIST, key = "#menuId")
    public Set<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId) {
        return CollectionUtils.convertSet(roleMenuMapper.selectListByMenuId(menuId), RoleMenuDO::getRoleId);
    }

    // ========== User-Role related methods  ==========

    @Override
    @DSTransactional // Multiple data sources，Use @DSTransactional Ensure local transactions，And switching of data source
    @CacheEvict(value = RedisKeyConstants.USER_ROLE_ID_LIST, key = "#userId")
    public void assignUserRole(Long userId, Set<Long> roleIds) {
        // Get the character's character number
        Set<Long> dbRoleIds = CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId),
                UserRoleDO::getRoleId);
        // Calculate the number of new and deleted roles
        Set<Long> roleIdList = CollUtil.emptyIfNull(roleIds);
        Collection<Long> createRoleIds = CollUtil.subtract(roleIdList, dbRoleIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbRoleIds, roleIdList);
        // Perform addition and deletion。For authorized roles，No need to do anything
        if (!CollectionUtil.isEmpty(createRoleIds)) {
            userRoleMapper.insertBatch(CollectionUtils.convertList(createRoleIds, roleId -> {
                UserRoleDO entity = new UserRoleDO();
                entity.setUserId(userId);
                entity.setRoleId(roleId);
                return entity;
            }));
        }
        if (!CollectionUtil.isEmpty(deleteMenuIds)) {
            userRoleMapper.deleteListByUserIdAndRoleIdIds(userId, deleteMenuIds);
        }
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.USER_ROLE_ID_LIST, key = "#userId")
    public void processUserDeleted(Long userId) {
        userRoleMapper.deleteListByUserId(userId);
    }

    @Override
    public Set<Long> getUserRoleIdListByUserId(Long userId) {
        return CollectionUtils.convertSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.USER_ROLE_ID_LIST, key = "#userId")
    public Set<Long> getUserRoleIdListByUserIdFromCache(Long userId) {
        return getUserRoleIdListByUserId(userId);
    }

    @Override
    public Set<Long> getUserRoleIdListByRoleId(Collection<Long> roleIds) {
        return CollectionUtils.convertSet(userRoleMapper.selectListByRoleIds(roleIds), UserRoleDO::getUserId);
    }

    /**
     * Get the roles owned by the user，And these roles are enabled
     *
     * @param userId User Number
     * @return Roles owned by the user
     */
    @VisibleForTesting
    List<RoleDO> getEnableUserRoleListByUserIdFromCache(Long userId) {
        // Get the role number owned by the user
        Set<Long> roleIds = getSelf().getUserRoleIdListByUserIdFromCache(userId);
        // Get role array，And remove the disabled ones
        List<RoleDO> roles = roleService.getRoleListFromCache(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus()));
        return roles;
    }

    // ========== User-Department-related methods  ==========

    @Override
    public void assignRoleDataScope(Long roleId, Integer dataScope, Set<Long> dataScopeDeptIds) {
        roleService.updateRoleDataScope(roleId, dataScope, dataScopeDeptIds);
    }

    @Override
    @DataPermission(enable = false) // Close data permissions，Otherwise, there will be problems with recursive access to data permissions
    public DeptDataPermissionRespDTO getDeptDataPermission(Long userId) {
        // Get the user's role
        List<RoleDO> roles = getEnableUserRoleListByUserIdFromCache(userId);

        // If the role is empty，You can only view yourself
        DeptDataPermissionRespDTO result = new DeptDataPermissionRespDTO();
        if (CollUtil.isEmpty(roles)) {
            result.setSelf(true);
            return result;
        }

        // Get the user's department number cache，Passed Guava of Suppliers Lazy evaluation，There is only the first launch DB Query
        Supplier<Long> userDeptId = Suppliers.memoize(() -> userService.getUser(userId).getDeptId());
        // Traverse each character，Calculation
        for (RoleDO role : roles) {
            // When empty，Skip
            if (role.getDataScope() == null) {
                continue;
            }
            // Situation 1，ALL
            if (Objects.equals(role.getDataScope(), DataScopeEnum.ALL.getScope())) {
                result.setAll(true);
                continue;
            }
            // Case 2，DEPT_CUSTOM
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_CUSTOM.getScope())) {
                CollUtil.addAll(result.getDeptIds(), role.getDataScopeDeptIds());
                // When customizing visible departments，Guaranteed to see the department you are in。Otherwise，There may be problems in some scenarios。
                // For example，When logging in，Based on t_user of username The query may be dept_id Filter out
                CollUtil.addAll(result.getDeptIds(), userDeptId.get());
                continue;
            }
            // Case 3，DEPT_ONLY
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_ONLY.getScope())) {
                CollectionUtils.addIfNotNull(result.getDeptIds(), userDeptId.get());
                continue;
            }
            // Case 4，DEPT_DEPT_AND_CHILD
            if (Objects.equals(role.getDataScope(), DataScopeEnum.DEPT_AND_CHILD.getScope())) {
                CollUtil.addAll(result.getDeptIds(), deptService.getChildDeptIdListFromCache(userDeptId.get()));
                // Add your own department number
                CollUtil.addAll(result.getDeptIds(), userDeptId.get());
                continue;
            }
            // Situation Five，SELF
            if (Objects.equals(role.getDataScope(), DataScopeEnum.SELF.getScope())) {
                result.setSelf(true);
                continue;
            }
            // Unknown situation，error log That's it
            log.error("[getDeptDataPermission][LoginUser({}) role({}) Unable to process]", userId, JsonUtils.toJsonString(result));
        }
        return result;
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private PermissionServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
