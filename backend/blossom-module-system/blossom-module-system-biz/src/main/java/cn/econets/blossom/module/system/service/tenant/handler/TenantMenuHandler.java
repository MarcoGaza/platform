package cn.econets.blossom.module.system.service.tenant.handler;

import java.util.Set;

/**
 * Tenant menu processing
 * Purpose：Minimize the coupling of tenant logic into the system
 *
 *
 */
public interface TenantMenuHandler {

    /**
     * Based on the tenant menu passed in【All】List，Execute related logic
     * For example，When returning to the assignable menu，Can remove redundant ones
     *
     * @param menuIds Menu List
     */
    void handle(Set<Long> menuIds);

}
