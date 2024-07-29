package cn.econets.blossom.module.promotion.dal.dataobject.coupon;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTemplateValidityTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Coupon Template DO
 *
 * When the user receives it，Will be generated {@link CouponDO} Coupon
 *
 */
@TableName(value = "promotion_coupon_template", autoResultMap = true)
@KeySequence("promotion_coupon_template_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponTemplateDO extends BaseDO {

    // ========== Basic Information BEGIN ==========
    /**
     * Template number，Self-increment unique
     */
    @TableId
    private Long id;
    /**
     * Coupon Name
     */
    private String name;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

    // ========== Basic Information END ==========

    // ========== Rule for receiving BEGIN ==========
    /**
     * Number of issued items
     *
     * -1 - It means there is no limit on the number of distributions
     */
    private Integer totalCount;
    /**
     * Limited number of items per person
     *
     * -1 - It means no restriction
     */
    private Integer takeLimitCount;
    /**
     * How to receive
     *
     * Enumeration {@link CouponTakeTypeEnum}
     */
    private Integer takeType;
    // ========== Rules for receiving END ==========

    // ========== Rules of Use BEGIN ==========
    /**
     * Whether to set the maximum amount of available funds，Unit：Points
     *
     * 0 - No restrictions
     * Greater than 0 - How much money is available
     */
    private Integer usePrice;
    /**
     * Product Range
     *
     * Enumeration {@link PromotionProductScopeEnum}
     */
    private Integer productScope;
    /**
     * Array of product range numbers
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> productScopeValues;
    /**
     * Effective Date Type
     *
     * Enumeration {@link CouponTemplateValidityTypeEnum}
     */
    private Integer validityType;
    /**
     * Fixed date - Effective start time
     *
     * When {@link #validityType} for {@link CouponTemplateValidityTypeEnum#DATE}
     */
    private LocalDateTime validStartTime;
    /**
     * Fixed date - Effective end time
     *
     * When {@link #validityType} for {@link CouponTemplateValidityTypeEnum#DATE}
     */
    private LocalDateTime validEndTime;
    /**
     * Date of collection - Starting days
     *
     * When {@link #validityType} for {@link CouponTemplateValidityTypeEnum#TERM}
     */
    private Integer fixedStartTerm;
    /**
     * Date of collection - Ending days
     *
     * When {@link #validityType} for {@link CouponTemplateValidityTypeEnum#TERM}
     */
    private Integer fixedEndTerm;
    // ========== Rules of Use END ==========

    // ========== Effect of use BEGIN ==========
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
     * Discount limit，Only in {@link #discountType} equal to {@link PromotionDiscountTypeEnum#PERCENT} Effective when
     *
     * For example，The upper limit of discount is 20 Yuan，When using 8 Discount coupon，The order amount is 1000 Yuan Shi，Maximum discount available 20 Yuan，Rather than 80  Yuan。
     */
    private Integer discountLimitPrice;
    // ========== Effect of use END ==========

    // ========== Statistics BEGIN ==========
    /**
     * Number of coupons received
     */
    private Integer takeCount;
    /**
     * Number of times the coupon was used
     */
    private Integer useCount;
    // ========== Statistics END ==========

}
