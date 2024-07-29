package cn.econets.blossom.module.system.dal.dataobject.permission;

import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Role and menu association
 *
 */
@TableName("system_role_menu")
@KeySequence("system_role_menu_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenuDO extends TenantBaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * RoleID
     */
    private Long roleId;
    /**
     * MenuID
     */
    private Long menuId;

}
