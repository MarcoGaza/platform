package cn.econets.blossom.framework.tenant.core.context;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * Multi-tenant context Holder
 *
 */
public class TenantContextHolder {

    /**
     * Current tenant number
     */
    private static final ThreadLocal<Long> TENANT_ID = new TransmittableThreadLocal<>();

    /**
     * Whether to ignore tenants
     */
    private static final ThreadLocal<Boolean> IGNORE = new TransmittableThreadLocal<>();

    /**
     * Get tenant number。
     *
     * @return Tenant Number
     */
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * Get tenant number String
     *
     * @return Tenant Number
     */
    public static String getTenantIdStr() {
        Long tenantId = getTenantId();
        return StrUtil.toStringOrNull(tenantId);
    }

    /**
     * Get tenant number。If it does not exist，Throw NullPointerException Abnormal
     *
     * @return Tenant Number
     */
    public static Long getRequiredTenantId() {
        Long tenantId = getTenantId();
        if (tenantId == null) {
            throw new NullPointerException("TenantContextHolder Tenant number does not exist");
        }
        return tenantId;
    }

    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static void setIgnore(Boolean ignore) {
        IGNORE.set(ignore);
    }

    /**
     * Whether to ignore tenants currently
     *
     * @return Whether to ignore
     */
    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE.get());
    }

    public static void clear() {
        TENANT_ID.remove();
        IGNORE.remove();
    }

}
