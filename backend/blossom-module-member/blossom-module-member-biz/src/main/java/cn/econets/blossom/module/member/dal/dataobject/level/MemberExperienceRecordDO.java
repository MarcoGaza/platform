package cn.econets.blossom.module.member.dal.dataobject.level;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Member Experience Record DO
 *
 *
 */
@TableName("member_experience_record")
@KeySequence("member_experience_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberExperienceRecordDO extends BaseDO {

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
     * Business Type
     * <p>
     * Enumeration {@link MemberExperienceBizTypeEnum}
     */
    private Integer bizType;
    /**
     * Business Number
     */
    private String bizId;
    /**
     * Title
     */
    private String title;
    /**
     * Description
     */
    private String description;
    /**
     * Experience
     */
    private Integer experience;
    /**
     * Experience after change
     */
    private Integer totalExperience;

}
