package cn.econets.blossom.module.member.dal.dataobject.level;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Member Level DO
 *
 * Configure the points required for each level
 *
 * 
 */
@TableName("member_level")
@KeySequence("member_level_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLevelDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Level Name
     */
    private String name;
    /**
     * Level
     */
    private Integer level;
    /**
     * Upgrade Experience
     */
    private Integer experience;
    /**
     * Enjoy discount
     */
    private Integer discountPercent;

    /**
     * Level Icon
     */
    private String icon;
    /**
     * Level background image
     */
    private String backgroundUrl;
    /**
     * Status
     * <p>
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
