package cn.econets.blossom.module.member.dal.dataobject.point;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * User points record DO
 *
 *
 */
@TableName("member_point_record")
@KeySequence("member_point_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointRecordDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Corresponding MemberUserDO of id Properties
     */
    private Long userId;

    /**
     * Business Code
     */
    private String bizId;
    /**
     * Business Type
     *
     * Enumeration {@link MemberPointBizTypeEnum}
     */
    private Integer bizType;

    /**
     * Points Title
     */
    private String title;
    /**
     * Points description
     */
    private String description;

    /**
     * Change Points
     *
     * 1、A positive number indicates points gained
     * 2、Negative numbers indicate points consumption
     */
    private Integer point;
    /**
     * Changed points
     */
    private Integer totalPoint;

}
