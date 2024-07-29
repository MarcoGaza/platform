package cn.econets.blossom.framework.tenant.core.db;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Expanding multi-tenancy BaseDO Base class
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TenantBaseDO extends BaseDO {

    /**
     * Multi-tenant number
     */
    private Long tenantId;

}
