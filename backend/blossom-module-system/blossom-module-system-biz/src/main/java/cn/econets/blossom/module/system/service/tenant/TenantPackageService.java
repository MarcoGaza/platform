package cn.econets.blossom.module.system.service.tenant;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackagePageReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackageSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantPackageDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Tenant Package Service Interface
 *
 *
 */
public interface TenantPackageService {

    /**
     * Create tenant package
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createTenantPackage(@Valid TenantPackageSaveReqVO createReqVO);

    /**
     * Update tenant package
     *
     * @param updateReqVO Update information
     */
    void updateTenantPackage(@Valid TenantPackageSaveReqVO updateReqVO);

    /**
     * Delete tenant package
     *
     * @param id Number
     */
    void deleteTenantPackage(Long id);

    /**
     * Get tenant package
     *
     * @param id Number
     * @return Tenant Package
     */
    TenantPackageDO getTenantPackage(Long id);

    /**
     * Get tenant package page
     *
     * @param pageReqVO Paged query
     * @return Tenant Package Paging
     */
    PageResult<TenantPackageDO> getTenantPackagePage(TenantPackagePageReqVO pageReqVO);

    /**
     * Verify tenant package
     *
     * @param id Number
     * @return Tenant Package
     */
    TenantPackageDO validTenantPackage(Long id);

    /**
     * Get the tenant package list of the specified status
     *
     * @param status Status
     * @return Tenant Package
     */
    List<TenantPackageDO> getTenantPackageListByStatus(Integer status);

    /**
     * Get tenant menuids
     *
     * @param id Number
     * @return Menuids
     */
    Set<Long> getMenuIdsByTenantPackageId(Long id);

}
