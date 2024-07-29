package cn.econets.blossom.framework.tenant.core.util;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Multi-tenancy Util
 *
 */
public class TenantUtils {

    /**
     * Use a specified tenant，Execute the corresponding logic
     *
     * Attention，If the tenant is currently ignored，Will be forced to not ignore tenants
     * Of course，After execution is completed，It will still be restored
     *
     * @param tenantId Tenant Number
     * @param runnable Logic
     */
    public static void execute(Long tenantId, Runnable runnable) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false);
            // Execution logic
            runnable.run();
        } finally {
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * Use a specified tenant，Execute the corresponding logic
     *
     * Attention，If the tenant is currently ignored，Will be forced to not ignore tenants
     * Of course，After execution is completed，It will still be restored
     *
     * @param tenantId Tenant Number
     * @param callable Logic
     */
    public static <V> V execute(Long tenantId, Callable<V> callable) {
        Long oldTenantId = TenantContextHolder.getTenantId();
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setTenantId(tenantId);
            TenantContextHolder.setIgnore(false);
            // Execution logic
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            TenantContextHolder.setTenantId(oldTenantId);
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * Ignore tenants，Execute the corresponding logic
     *
     * @param runnable Logic
     */
    public static void executeIgnore(Runnable runnable) {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // Execution logic
            runnable.run();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

    /**
     * Number the multiple tenants，Add to header Medium
     *
     * @param headers HTTP Request headers
     * @param tenantId Tenant Number
     */
    public static void addTenantHeader(Map<String, String> headers, Long tenantId) {
        if (tenantId != null) {
            headers.put(WebFrameworkUtils.HEADER_TENANT_ID, tenantId.toString());
        }
    }

}
