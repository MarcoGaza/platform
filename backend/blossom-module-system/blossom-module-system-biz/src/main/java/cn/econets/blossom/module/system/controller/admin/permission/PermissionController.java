package cn.econets.blossom.module.system.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleDataScopeReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.permission.PermissionAssignRoleMenuReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.permission.PermissionAssignUserRoleReqVO;
import cn.econets.blossom.module.system.service.permission.PermissionService;
import cn.econets.blossom.module.system.service.tenant.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

/**
 * Permissions Controller，Provide to users、Role permissions API Interface
 *
 */
@Tag(name = "Management Backend - Permissions")
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Resource
    private TenantService tenantService;

    @Operation(summary = "Get the menu number owned by the character")
    @Parameter(name = "roleId", description = "Role number", required = true)
    @GetMapping("/list-role-menus")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Set<Long>> getRoleMenuList(Long roleId) {
        return success(permissionService.getRoleMenuListByRoleId(roleId));
    }

    @PostMapping("/assign-role-menu")
    @Operation(summary = "Give character menu")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-menu')")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAssignRoleMenuReqVO reqVO) {
        // When multi-tenancy is enabled，Need to filter out unopened menus
        tenantService.handleTenantMenu(menuIds -> reqVO.getMenuIds().removeIf(menuId -> !CollUtil.contains(menuIds, menuId)));

        // Assignment of execution menu
        permissionService.assignRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @PostMapping("/assign-role-data-scope")
    @Operation(summary = "Give data permissions to the role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-role-data-scope')")
    public CommonResult<Boolean> assignRoleDataScope(@Valid @RequestBody PermissionAssignRoleDataScopeReqVO reqVO) {
        permissionService.assignRoleDataScope(reqVO.getRoleId(), reqVO.getDataScope(), reqVO.getDataScopeDeptIds());
        return success(true);
    }

    @Operation(summary = "Get the list of role numbers owned by the administrator")
    @Parameter(name = "userId", description = "User Number", required = true)
    @GetMapping("/list-user-roles")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Set<Long>> listAdminRoles(@RequestParam("userId") Long userId) {
        return success(permissionService.getUserRoleIdListByUserId(userId));
    }

    @Operation(summary = "Give user roles")
    @PostMapping("/assign-user-role")
    @PreAuthorize("@ss.hasPermission('system:permission:assign-user-role')")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAssignUserRoleReqVO reqVO) {
        permissionService.assignUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

}
