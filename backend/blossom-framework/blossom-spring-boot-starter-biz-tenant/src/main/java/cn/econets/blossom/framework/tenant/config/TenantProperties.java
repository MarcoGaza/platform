package cn.econets.blossom.framework.tenant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * Multi-tenant configuration
 *
 */
@ConfigurationProperties(prefix = "application.tenant")
@Data
public class TenantProperties {

    /**
     * Is the tenant enabled?
     */
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * Is it enabled?
     */
    private Boolean enable = ENABLE_DEFAULT;

    /**
     * Need to ignore multi-tenant requests
     *
     * By default，Each request needs to bring tenant-id Request header。But，Some requests do not need to be included，For example, SMS callback、Payment callback, etc. Open API！
     */
    private Set<String> ignoreUrls = Collections.emptySet();

    /**
     * Multi-tenant tables need to be ignored
     *
     * By default, all tables have multi-tenant functionality enabled，So remember to add the corresponding tenant_id Field
     */
    private Set<String> ignoreTables = Collections.emptySet();

}
