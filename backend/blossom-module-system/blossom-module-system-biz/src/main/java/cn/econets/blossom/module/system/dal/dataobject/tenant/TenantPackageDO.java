package cn.econets.blossom.module.system.dal.dataobject.tenant;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.JsonLongSetTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Set;

/**
 * Tenant Package DO
 *
 */
@TableName(value = "system_tenant_package", autoResultMap = true)
@KeySequence("system_tenant_package_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantPackageDO extends BaseDO {

    /**
     * Package number，Self-increment
     */
    private Long id;
    /**
     * Package Name，Only
     */
    private String name;
    /**
     * Tenant package status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;

}
