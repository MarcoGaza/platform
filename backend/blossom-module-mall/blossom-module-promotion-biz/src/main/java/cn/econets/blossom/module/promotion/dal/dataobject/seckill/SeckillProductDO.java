package cn.econets.blossom.module.promotion.dal.dataobject.seckill;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Second sale participating products DO
 *
 */
@TableName("promotion_seckill_product")
@KeySequence("promotion_seckill_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillProductDO extends BaseDO {

    /**
     * Second sale product number
     */
    @TableId
    private Long id;
    /**
     * Second-sale event id
     *
     * Relationship {@link SeckillActivityDO#getId()}
     */
    private Long activityId;
    /**
     * Second sale period id
     *
     * Relationship {@link SeckillConfigDO#getId()}
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> configIds;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     */
    private Long skuId;
    /**
     * Second kill amount，Unit：Points
     */
    private Integer seckillPrice;
    /**
     * Second kill inventory
     */
    private Integer stock;

    /**
     * Second-selling product status
     *
     * Enumeration {@link CommonStatusEnum Corresponding class}
     */
    private Integer activityStatus;
    /**
     * Activity start time
     */
    private LocalDateTime activityStartTime;
    /**
     * Event end time
     */
    private LocalDateTime activityEndTime;

}
