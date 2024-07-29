package cn.econets.blossom.module.system.dal.dataobject.social;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Social（Three parties）User
 *
 */
@TableName(value = "system_social_user", autoResultMap = true)
@KeySequence("system_social_user_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * Type of social platform
     *
     * Enumeration {@link SocialTypeEnum}
     */
    private Integer type;

    /**
     * Social openid
     */
    private String openid;
    /**
     * Social token
     */
    private String token;
    /**
     * Original Token Data，Generally JSON Format
     */
    private String rawTokenInfo;

    /**
     * User Nickname
     */
    private String nickname;
    /**
     * User avatar
     */
    private String avatar;
    /**
     * Original user data，Generally JSON Format
     */
    private String rawUserInfo;

    /**
     * The last authentication code
     */
    private String code;
    /**
     * The last authentication state
     */
    private String state;

}


