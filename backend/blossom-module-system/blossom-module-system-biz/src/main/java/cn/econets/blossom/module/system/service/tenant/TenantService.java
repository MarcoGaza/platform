package cn.econets.blossom.module.system.service.tenant;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant.TenantSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantDO;
import cn.econets.blossom.module.system.service.tenant.handler.TenantInfoHandler;
import cn.econets.blossom.module.system.service.tenant.handler.TenantMenuHandler;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Tenant Service Interface
 *
 */
public interface TenantService {

    /**
     * Create tenant
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createTenant(@Valid TenantSaveReqVO createReqVO);

    /**
     * Update tenant
     *
     * @param updateReqVO Update information
     */
    void updateTenant(@Valid TenantSaveReqVO updateReqVO);

    /**
     * Update tenant role menu
     *
     * @param tenantId Tenant Number
     * @param menuIds Menu number array
     */
    void updateTenantRoleMenu(Long tenantId, Set<Long> menuIds);

    /**
     * Delete tenant
     *
     * @param id Number
     */
    void deleteTenant(Long id);

    /**
     * Get tenants
     *
     * @param id Number
     * @return Tenant
     */
    TenantDO getTenant(Long id);

    /**
     * Get tenant paging
     *
     * @param pageReqVO Paged query
     * @return Tenant Paging
     */
    PageResult<TenantDO> getTenantPage(TenantPageReqVO pageReqVO);

    /**
     * Get the tenant corresponding to the name
     *
     * @param name Tenant Name
     * @return Tenant
     */
    TenantDO getTenantByName(String name);

    /**
     * Get the tenant corresponding to the domain name
     *
     * @param website Domain Name
     * @return Tenant
     */
    TenantDO getTenantByWebsite(String website);

    /**
     * Get the number of tenants using the specified package
     *
     * @param packageId Tenant package number
     * @return Number of tenants
     */
    Long getTenantCountByPackageId(Long packageId);

    /**
     * Get the array of tenants using the specified package
     *
     * @param packageId Tenant package number
     * @return Tenant array
     */
    List<TenantDO> getTenantListByPackageId(Long packageId);

    /**
     * Perform tenant information processing logic
     * Among them，Tenant ID from {@link TenantContextHolder} Get from context
     *
     * @param handler Processor
     */
    void handleTenantInfo(TenantInfoHandler handler);

    /**
     * Perform tenant menu processing logic
     * Among them，Tenant number from {@link TenantContextHolder} Get from context
     *
     * @param handler Processor
     */
    void handleTenantMenu(TenantMenuHandler handler);

    /**
     * Get all tenants
     *
     * @return Tenant ID array
     */
    List<Long> getTenantIdList();

    /**
     * Check if the tenant is legal
     *
     * @param id Tenant Number
     */
    void validTenant(Long id);

}
