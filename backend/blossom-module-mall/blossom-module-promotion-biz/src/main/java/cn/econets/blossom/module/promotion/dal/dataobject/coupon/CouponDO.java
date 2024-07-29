package cn.econets.blossom.module.promotion.dal.dataobject.coupon;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponStatusEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Coupon DO
 *
 */
@TableName(value = "promotion_coupon", autoResultMap = true)
@KeySequence("promotion_coupon_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponDO extends BaseDO {

    // ========== Basic Information BEGIN ==========
    /**
     * Coupon number
     */
    private Long id;
    /**
     * Coupon template number
     *
     * Relationship {@link CouponTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * Coupon Name
     *
     * Redundant {@link CouponTemplateDO#getName()}
     */
    private String name;
    /**
     * Promotion code status
     *
     * Enumeration {@link CouponStatusEnum}
     */
    private Integer status;

    // ========== Basic Information END ==========

    // ========== Collection Status BEGIN ==========
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Field
     */
    private Long userId;
    /**
     * Collection Type
     *
     * Enumeration {@link CouponTakeTypeEnum}
     */
    private Integer takeType;
    // ========== Collection Status END ==========

    // ========== Rules of Use BEGIN ==========
    /**
     * Whether to set the maximum amount of available funds，Unit：Points
     *
     * Redundant {@link CouponTemplateDO#getUsePrice()}
     */
    private Integer usePrice;
    /**
     * Effective start time
     */
    private LocalDateTime validStartTime;
    /**
     * Effective end time
     */
    private LocalDateTime validEndTime;
    /**
     * Product Range
     *
     * Enumeration {@link PromotionProductScopeEnum}
     */
    private Integer productScope;
    /**
     * Array of product range numbers
     *
     * Redundant {@link CouponTemplateDO#getProductScopeValues()}
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> productScopeValues;
    // ========== Rules of Use END ==========

    // ========== Effect of use BEGIN ==========
    /**
     * Discount type
     *
     * Redundant {@link CouponTemplateDO#getDiscountType()}
     */
    private Integer discountType;
    /**
     * Discount Percentage
     *
     * Redundant {@link CouponTemplateDO#getDiscountPercent()}
     */
    private Integer discountPercent;
    /**
     * Discount amount，Unit：Points
     *
     * Redundant {@link CouponTemplateDO#getDiscountPrice()}
     */
    private Integer discountPrice;
    /**
     * Discount limit，Only in {@link #discountType} equal to {@link PromotionDiscountTypeEnum#PERCENT} Effective when
     *
     * Redundant {@link CouponTemplateDO#getDiscountLimitPrice()}
     */
    private Integer discountLimitPrice;
    // ========== Effect of use END ==========

    // ========== Usage BEGIN ==========
    /**
     * Use order number
     */
    private Long useOrderId;
    /**
     * Usage time
     */
    private LocalDateTime useTime;

    // ========== Usage END ==========

}
