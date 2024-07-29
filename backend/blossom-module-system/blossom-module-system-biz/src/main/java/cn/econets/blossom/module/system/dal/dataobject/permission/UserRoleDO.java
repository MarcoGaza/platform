package cn.econets.blossom.module.system.dal.dataobject.permission;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User and role association
 *
 */
@TableName("system_user_role")
@KeySequence("system_user_role_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * User ID
     */
    private Long userId;
    /**
     * Role ID
     */
    private Long roleId;

}
