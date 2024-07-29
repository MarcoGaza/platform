package cn.econets.blossom.framework.tenant.core.service;

import java.util.List;

/**
 * Tenant Framework Service Interface，Define how to get tenant information
 *
 */
public interface TenantFrameworkService {

    /**
     * Get all tenants
     *
     * @return Tenant ID array
     */
    List<Long> getTenantIds();

    /**
     * Check whether the tenant is legal
     *
     * @param id Tenant Number
     */
    void validTenant(Long id);

}
