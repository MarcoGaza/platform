package cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo02;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Example Category DO
 *
 */
@TableName("infra_demo02_category")
@KeySequence("infra_demo02_category_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Demo02CategoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

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
     * Parent number
     */
    private Long parentId;

}
