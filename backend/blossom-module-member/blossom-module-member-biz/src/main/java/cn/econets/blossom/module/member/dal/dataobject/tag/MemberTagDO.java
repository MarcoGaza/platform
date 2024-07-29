package cn.econets.blossom.module.member.dal.dataobject.tag;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Member Tag DO
 *
 *
 */
@TableName("member_tag")
@KeySequence("member_tag_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberTagDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Tag name
     */
    private String name;

}
