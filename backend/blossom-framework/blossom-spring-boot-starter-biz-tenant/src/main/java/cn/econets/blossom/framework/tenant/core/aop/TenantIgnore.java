package cn.econets.blossom.framework.tenant.core.aop;

import java.lang.annotation.*;

/**
 * Ignore tenants，Mark the specified method to not automatically filter tenants
 *
 * Attention，Only DB The scene will be filtered，Other scenes are not filtered for the time being：
 * 1、Redis Scene：Because it is based on Key Achieve multi-tenancy capabilities，So ignoring it is meaningless，Unlike DB is a column Implemented
 * 2、MQ Scene：It's a bit hard to decide，Currently available Consumer Manual consumption method，Add @TenantIgnore Ignore
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TenantIgnore {
}
