package cn.econets.blossom.module.member.dal.dataobject.group;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * User Grouping DO
 *
 * 
 */
@TableName("member_group")
@KeySequence("member_group_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberGroupDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Name
     */
    private String name;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Status
     * <p>
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
