package cn.econets.blossom.module.system.dal.dataobject.dept;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User and position association
 *
 */
@TableName("system_user_post")
@KeySequence("system_user_post_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPostDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * User ID
     *
     * Relationship {@link AdminUserDO#getId()}
     */
    private Long userId;
    /**
     * Role ID
     *
     * Relationship {@link PostDO#getId()}
     */
    private Long postId;

}
