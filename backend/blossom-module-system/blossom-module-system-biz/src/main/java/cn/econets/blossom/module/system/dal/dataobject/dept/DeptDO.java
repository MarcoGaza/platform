package cn.econets.blossom.module.system.dal.dataobject.dept;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Department Table
 *
 */
@TableName("system_dept")
@KeySequence("system_dept_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDO extends TenantBaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * DepartmentID
     */
    @TableId
    private Long id;
    /**
     * Department Name
     */
    private String name;
    /**
     * Parent DepartmentID
     *
     * Relationship {@link #id}
     */
    private Long parentId;
    /**
     * Display order
     */
    private Integer sort;
    /**
     * Person in charge
     *
     * Relationship {@link AdminUserDO#getId()}
     */
    private Long leaderUserId;
    /**
     * Contact number
     */
    private String phone;
    /**
     * Mailbox
     */
    private String email;
    /**
     * Department Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
