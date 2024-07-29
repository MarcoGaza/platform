package cn.econets.blossom.module.member.dal.dataobject.signin;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Sign-in rules DO
 *
 * 
 */
@TableName("member_sign_in_config")
@KeySequence("member_sign_in_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignInConfigDO extends BaseDO {

    /**
     * Rule auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * Sign in x Sky
     */
    private Integer day;
    /**
     * Reward points
     */
    private Integer point;
    /**
     * Reward experience
     */
    private Integer experience;

    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
