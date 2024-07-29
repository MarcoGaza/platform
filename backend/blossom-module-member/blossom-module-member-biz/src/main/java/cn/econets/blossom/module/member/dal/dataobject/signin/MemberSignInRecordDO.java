package cn.econets.blossom.module.member.dal.dataobject.signin;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Sign-in record DO
 *
 * 
 */
@TableName("member_sign_in_record")
@KeySequence("member_sign_in_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignInRecordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Sign in user
     */
    private Long userId;
    /**
     * Sign in on which day
     */
    private Integer day;
    /**
     * Sign-in points
     */
    private Integer point;
    /**
     * Sign-in experience
     */
    private Integer experience;

}
