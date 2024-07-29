package cn.econets.blossom.module.system.dal.dataobject.social;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Social user binding
 * That is {@link SocialUserDO} with UserDO Relationship table
 *
 */
@TableName(value = "system_social_user_bind", autoResultMap = true)
@KeySequence("system_social_user_bind_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserBindDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Associated user ID
     *
     * Relationship UserDO Number
     */
    private Long userId;
    /**
     * User Type
     *
     * Enumeration {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * User ID of social platform
     *
     * Relationship {@link SocialUserDO#getId()}
     */
    private Long socialUserId;
    /**
     * Type of social platform
     *
     * Redundant {@link SocialUserDO#getType()}
     */
    private Integer socialType;

}
