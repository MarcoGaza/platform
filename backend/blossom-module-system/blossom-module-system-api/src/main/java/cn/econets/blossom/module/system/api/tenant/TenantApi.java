package cn.econets.blossom.module.system.api.tenant;

import java.util.List;

/**
 * Multi-tenant API Interface
 *
 */
public interface TenantApi {

    /**
     * Get all tenants
     *
     * @return Tenant ID array
     */
    List<Long> getTenantIdList();

    /**
     * Check whether the tenant is legal
     *
     * @param id Tenant Number
     */
    void validateTenant(Long id);

}
