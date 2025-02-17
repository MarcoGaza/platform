package cn.econets.blossom.module.system.service.tenant;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.tenant.config.TenantProperties;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.system.controller.admin.permission.vo.role.RoleSaveReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant.TenantSaveReqVO;
import cn.econets.blossom.module.system.convert.tenant.TenantConvert;
import cn.econets.blossom.module.system.dal.mysql.tenant.TenantMapper;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantDO;
import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantPackageDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.permission.RoleCodeEnum;
import cn.econets.blossom.module.system.enums.permission.RoleTypeEnum;
import cn.econets.blossom.module.system.service.permission.MenuService;
import cn.econets.blossom.module.system.service.permission.PermissionService;
import cn.econets.blossom.module.system.service.permission.RoleService;
import cn.econets.blossom.module.system.service.tenant.handler.TenantInfoHandler;
import cn.econets.blossom.module.system.service.tenant.handler.TenantMenuHandler;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * Tenant Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class TenantServiceImpl implements TenantService {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired(required = false) // Because application.tenant.enable Configuration item，You can disable the multi-tenancy feature，So we can only not force injection here
    private TenantProperties tenantProperties;

    @Resource
    private TenantMapper tenantMapper;

    @Resource
    private TenantPackageService tenantPackageService;
    @Resource
    @Lazy // Delay，Avoid circular dependency errors
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;

    @Override
    public List<Long> getTenantIdList() {
        List<TenantDO> tenants = tenantMapper.selectList();
        return CollectionUtils.convertList(tenants, TenantDO::getId);
    }

    @Override
    public void validTenant(Long id) {
        TenantDO tenant = getTenant(id);
        if (tenant == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_EXPIRE, tenant.getName());
        }
    }

    @Override
    @DSTransactional // Multiple data sources，Use @DSTransactional Ensure local transactions，And switching of data source
    public Long createTenant(TenantSaveReqVO createReqVO) {
        // Check if tenant name is repeated
        validTenantNameDuplicate(createReqVO.getName(), null);
        // Check whether the tenant domain name is repeated
        validTenantWebsiteDuplicate(createReqVO.getWebsite(), null);
        // Verification package is disabled
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(createReqVO.getPackageId());

        // Create tenant
        TenantDO tenant = BeanUtils.toBean(createReqVO, TenantDO.class);
        tenantMapper.insert(tenant);
        // Create a tenant administrator
        TenantUtils.execute(tenant.getId(), () -> {
            // Create a character
            Long roleId = createRole(tenantPackage);
            // Create user，And assign roles
            Long userId = createUser(roleId, createReqVO);
            // Modify the tenant's administrator
            TenantDO tenantDO = new TenantDO();
            tenantDO.setId(tenant.getId());
            tenantDO.setContactUserId(userId);
            tenantMapper.updateById(tenantDO);
        });
        return tenant.getId();
    }

    private Long createUser(Long roleId, TenantSaveReqVO createReqVO) {
        // Create user
        Long userId = userService.createUser(TenantConvert.INSTANCE.convert02(createReqVO));
        // Assign roles
        permissionService.assignUserRole(userId, singleton(roleId));
        return userId;
    }

    private Long createRole(TenantPackageDO tenantPackage) {
        // Create a character
        RoleSaveReqVO reqVO = new RoleSaveReqVO();
        reqVO.setName(RoleCodeEnum.TENANT_ADMIN.getName());
        reqVO.setCode(RoleCodeEnum.TENANT_ADMIN.getCode());
        reqVO.setSort(0);
        reqVO.setRemark("Automatically generated by the system");
        Long roleId = roleService.createRole(reqVO, RoleTypeEnum.SYSTEM.getType());
        // Assign permissions
        Set<Long> menuIds = tenantPackageService.getMenuIdsByTenantPackageId(tenantPackage.getId());
        permissionService.assignRoleMenu(roleId, menuIds);
        return roleId;
    }

    @Override
    @DSTransactional // Multiple data sources，Use @DSTransactional Ensure local transactions，And switching of data source
    public void updateTenant(TenantSaveReqVO updateReqVO) {
        // Check existence
        TenantDO tenant = validateUpdateTenant(updateReqVO.getId());
        // Check if tenant name is repeated
        validTenantNameDuplicate(updateReqVO.getName(), updateReqVO.getId());
        // Check whether the tenant domain name is repeated
        validTenantWebsiteDuplicate(updateReqVO.getWebsite(), updateReqVO.getId());
        // Verification package is disabled
        TenantPackageDO tenantPackage = tenantPackageService.validTenantPackage(updateReqVO.getPackageId());

        // Update tenant
        TenantDO updateObj = BeanUtils.toBean(updateReqVO, TenantDO.class);
        tenantMapper.updateById(updateObj);
        // If the package changes，Modify the permissions of its role
        if (ObjectUtil.notEqual(tenant.getPackageId(), updateReqVO.getPackageId())) {
            Set<Long> menuIds = tenantPackageService.getMenuIdsByTenantPackageId(tenantPackage.getId());
            updateTenantRoleMenu(tenant.getId(), menuIds);
        }
    }

    private void validTenantNameDuplicate(String name, Long id) {
        TenantDO tenant = tenantMapper.selectByName(name);
        if (tenant == null) {
            return;
        }
        // If id Empty，It means that there is no need to compare whether the tenants have the same name
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_NAME_DUPLICATE, name);
        }
        if (!tenant.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_NAME_DUPLICATE, name);
        }
    }

    private void validTenantWebsiteDuplicate(String website, Long id) {
        if (StrUtil.isEmpty(website)) {
            return;
        }
        TenantDO tenant = tenantMapper.selectByWebsite(website);
        if (tenant == null) {
            return;
        }
        // If id Empty，It means that there is no need to compare whether the tenants have the same name
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_WEBSITE_DUPLICATE, website);
        }
        if (!tenant.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_WEBSITE_DUPLICATE, website);
        }
    }

    @Override
    @DSTransactional
    public void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds) {
        TenantUtils.execute(tenantId, () -> {
            // Get all characters
            List<RoleDO> roles = roleService.getRoleList();
            roles.forEach(role -> Assert.isTrue(tenantId.equals(role.getTenantId()), "Role({}/{}) Tenant does not match",
                    role.getId(), role.getTenantId(), tenantId)); // Bottom check
            // Reassign permissions for each role
            roles.forEach(role -> {
                // If it is a tenant administrator，Reassign its permissions to the tenant package permissions
                if (Objects.equals(role.getCode(), RoleCodeEnum.TENANT_ADMIN.getCode())) {
                    permissionService.assignRoleMenu(role.getId(), menuIds);
                    log.info("[updateTenantRoleMenu][Tenant Administrator({}/{}) The permissions are changed to({})]", role.getId(), role.getTenantId(), menuIds);
                    return;
                }
                // If it is another character，Remove permissions that exceed the package
                Set<Long> roleMenuIds = permissionService.getRoleMenuListByRoleId(role.getId());
                roleMenuIds = CollUtil.intersectionDistinct(roleMenuIds, menuIds);
                permissionService.assignRoleMenu(role.getId(), roleMenuIds);
                log.info("[updateTenantRoleMenu][Role({}/{}) The permissions are changed to({})]", role.getId(), role.getTenantId(), roleMenuIds);
            });
        });
    }

    @Override
    public void deleteTenant(Long id) {
        // Verify existence
        validateUpdateTenant(id);
        // Delete
        tenantMapper.deleteById(id);
    }

    private TenantDO validateUpdateTenant(Long id) {
        TenantDO tenant = tenantMapper.selectById(id);
        if (tenant == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_NOT_EXISTS);
        }
        // Built-in tenants，Deletion is not allowed
        if (isSystemTenant(tenant)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.TENANT_CAN_NOT_UPDATE_SYSTEM);
        }
        return tenant;
    }

    @Override
    public TenantDO getTenant(Long id) {
        return tenantMapper.selectById(id);
    }

    @Override
    public PageResult<TenantDO> getTenantPage(TenantPageReqVO pageReqVO) {
        return tenantMapper.selectPage(pageReqVO);
    }

    @Override
    public TenantDO getTenantByName(String name) {
        return tenantMapper.selectByName(name);
    }

    @Override
    public TenantDO getTenantByWebsite(String website) {
        return tenantMapper.selectByWebsite(website);
    }

    @Override
    public Long getTenantCountByPackageId(Long packageId) {
        return tenantMapper.selectCountByPackageId(packageId);
    }

    @Override
    public List<TenantDO> getTenantListByPackageId(Long packageId) {
        return tenantMapper.selectListByPackageId(packageId);
    }

    @Override
    public void handleTenantInfo(TenantInfoHandler handler) {
        // If disabled，The logic will not be executed
        if (isTenantDisable()) {
            return;
        }
        // Get tenants
        TenantDO tenant = getTenant(TenantContextHolder.getRequiredTenantId());
        // Execution Processor
        handler.handle(tenant);
    }

    @Override
    public void handleTenantMenu(TenantMenuHandler handler) {
        // If disabled，The logic will not be executed
        if (isTenantDisable()) {
            return;
        }
        // Get tenants，Then get the menu
        TenantDO tenant = getTenant(TenantContextHolder.getRequiredTenantId());
        Set<Long> menuIds;
        if (isSystemTenant(tenant)) { // System tenant，The menu is full
            menuIds = CollectionUtils.convertSet(menuService.getMenuList(), MenuDO::getId);
        } else {
//            menuIds = tenantPackageService.getTenantPackage(tenant.getPackageId()).getMenuIds();
            menuIds = tenantPackageService.getMenuIdsByTenantPackageId(tenant.getPackageId());
        }
        // Execution Processor
        handler.handle(menuIds);
    }

    private static boolean isSystemTenant(TenantDO tenant) {
        return Objects.equals(tenant.getPackageId(), TenantDO.PACKAGE_ID_SYSTEM);
    }

    private boolean isTenantDisable() {
        return tenantProperties == null || Boolean.FALSE.equals(tenantProperties.getEnable());
    }

}
