package cn.econets.blossom.module.system.dal.dataobject.tenant;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Tenant DO
 *
 */
@TableName(value = "system_tenant", autoResultMap = true)
@KeySequence("system_tenant_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantDO extends BaseDO {

    /**
     * Package number - System
     */
    public static final Long PACKAGE_ID_SYSTEM = 0L;

    /**
     * Tenant Number，Self-increment
     */
    private Long id;
    /**
     * Tenant Name，Only
     */
    private String name;
    /**
     * Contact's user ID
     *
     * Relationship {@link AdminUserDO#getId()}
     */
    private Long contactUserId;
    /**
     * Contact Person
     */
    private String contactName;
    /**
     * Contact phone number
     */
    private String contactMobile;
    /**
     * Tenant Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Bind domain name
     */
    private String website;
    /**
     * Tenant package number
     *
     * Relationship {@link TenantPackageDO#getId()}
     * Special Logic：System built-in tenants，Not using package，Temporary use {@link #PACKAGE_ID_SYSTEM} Logo
     */
    private Long packageId;
    /**
     * Expiration time
     */
    private LocalDateTime expireTime;
    /**
     * Number of accounts
     */
    private Integer accountCount;

}
