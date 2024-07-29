package cn.econets.blossom.module.system.dal.dataobject.tenant;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Tenant packages and menu associations
 *
 */
@TableName("system_tenant_package_menu")
@KeySequence("system_tenant_package_menu_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantPackageMenuDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * Tenant PackageID
     */
    private Long tenantPackageId;
    /**
     * MenuID
     */
    private Long menuId;

}
