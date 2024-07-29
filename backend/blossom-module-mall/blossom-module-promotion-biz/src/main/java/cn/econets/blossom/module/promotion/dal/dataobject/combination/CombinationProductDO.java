package cn.econets.blossom.module.promotion.dal.dataobject.combination;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Group buy product DO
 *
 */
@TableName("promotion_combination_product")
@KeySequence("promotion_combination_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombinationProductDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Group buying activity number
     */
    private Long activityId;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     */
    private Long skuId;
    /**
     * Group buying price，Unit points
     */
    private Integer combinationPrice;

    /**
     * Group buy product status
     *
     * Relationship {@link CombinationActivityDO#getStatus()}
     */
    private Integer activityStatus;
    /**
     * Activity start time
     *
     * Redundant {@link CombinationActivityDO#getStartTime()}
     */
    private LocalDateTime activityStartTime;
    /**
     * Event end time
     *
     * Redundant {@link CombinationActivityDO#getEndTime()}
     */
    private LocalDateTime activityEndTime;

}
