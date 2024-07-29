package cn.econets.blossom.module.system.dal.dataobject.permission;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.type.JsonLongSetTypeHandler;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.system.enums.permission.DataScopeEnum;
import cn.econets.blossom.module.system.enums.permission.RoleTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Role DO
 *
 */
@TableName(value = "system_role", autoResultMap = true)
@KeySequence("system_role_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends TenantBaseDO {

    /**
     * RoleID
     */
    @TableId
    private Long id;
    /**
     * Role name
     */
    private String name;
    /**
     * Role ID
     *
     * Enumeration
     */
    private String code;
    /**
     * Role sorting
     */
    private Integer sort;
    /**
     * Character Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Role type
     *
     * Enumeration {@link RoleTypeEnum}
     */
    private Integer type;
    /**
     * Remarks
     */
    private String remark;

    /**
     * Data Range
     *
     * Enumeration {@link DataScopeEnum}
     */
    private Integer dataScope;
    /**
     * Data Range(Specify department array)
     *
     * Applicable to {@link #dataScope} The value of is {@link DataScopeEnum#DEPT_CUSTOM} Time
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> dataScopeDeptIds;

}
