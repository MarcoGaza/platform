package cn.econets.blossom.module.system.service.tenant.handler;


import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantDO;

/**
 * Tenant information processing
 * Purpose：Minimize the coupling of tenant logic into the system
 *
 */
public interface TenantInfoHandler {

    /**
     * Based on the tenant information passed in，Execute related logic
     * For example，When creating a user，Exceeded maximum account quota
     *
     * @param tenant Tenant Information
     */
    void handle(TenantDO tenant);

}
