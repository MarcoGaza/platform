package cn.econets.blossom.module.promotion.dal.dataobject.bargain;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Bargaining assistance DO
 *
 */
@TableName("promotion_bargain_help")
@KeySequence("promotion_bargain_help_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainHelpDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Bargaining activity number
     *
     * Relationship {@link BargainActivityDO#getId()} Field
     */
    private Long activityId;
    /**
     * Bargaining record number
     *
     * Relationship {@link BargainRecordDO#getId()} Field
     */
    private Long recordId;

    /**
     * User Number
     */
    private Long userId;
    /**
     * Reduce price，Unit：Points
     */
    private Integer reducePrice;

}
