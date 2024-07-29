package cn.econets.blossom.module.promotion.dal.dataobject.discount;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Limited time discount items DO
 *
 */
@TableName(value = "promotion_discount_product", autoResultMap = true)
@KeySequence("promotion_discount_product_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscountProductDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;

    /**
     * Limited time discount event number
     *
     * Relationship {@link DiscountActivityDO#getId()}
     */
    private Long activityId;

    /**
     * Products SPU Number
     *
     * Relationship ProductSpuDO of id Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     *
     * Relationship ProductSkuDO of id Number
     */
    private Long skuId;

    /**
     * Discount type
     *
     * Enumeration {@link PromotionDiscountTypeEnum}
     */
    private Integer discountType;
    /**
     * Discount Percentage
     *
     * For example，80% for 80
     */
    private Integer discountPercent;
    /**
     * Discount amount，Unit：Points
     *
     * When {@link #discountType} for {@link PromotionDiscountTypeEnum#PRICE} Effective
     */
    private Integer discountPrice;

    /**
     * Activity Status
     *
     * Association {@link DiscountActivityDO#getStatus()}
     */
    private Integer activityStatus;
    /**
     * Activity start time
     *
     * Redundant {@link DiscountActivityDO#getStartTime()}
     */
    private LocalDateTime activityStartTime;
    /**
     * Event end time
     *
     * Redundant {@link DiscountActivityDO#getEndTime()}
     */
    private LocalDateTime activityEndTime;

}
