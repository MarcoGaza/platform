package cn.econets.blossom.module.member.dal.dataobject.level;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Member level record DO
 *
 * Every time a user's level changes，Record a log
 *
 * 
 */
@TableName("member_level_record")
@KeySequence("member_level_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLevelRecordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship {@link MemberUserDO#getId()} Field
     */
    private Long userId;
    /**
     * Level Number
     *
     * Relationship {@link MemberLevelDO#getId()} Field
     */
    private Long levelId;
    /**
     * Member Level
     *
     * Redundant {@link MemberLevelDO#getLevel()} Field
     */
    private Integer level;
    /**
     * Enjoy discount
     */
    private Integer discountPercent;
    /**
     * Upgrade Experience
     */
    private Integer experience;
    /**
     * Member's current experience
     */
    private Integer userExperience;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Description
     */
    private String description;

}
