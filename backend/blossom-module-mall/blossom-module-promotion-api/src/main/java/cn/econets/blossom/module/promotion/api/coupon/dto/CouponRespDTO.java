package cn.econets.blossom.module.promotion.api.coupon.dto;

import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponStatusEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Coupon Response DTO
 *
 */
@Data
public class CouponRespDTO {

    // ========== Basic Information BEGIN ==========
    /**
     * Coupon number
     */
    private Long id;
    /**
     * Coupon template number
     */
    private Integer templateId;
    /**
     * Coupon Name
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
     * User ID
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
     */
    private Integer productScope;
    /**
     * Array of product range numbers
     */
    private List<Long> productScopeValues;
    // ========== Rules of Use END ==========

    // ========== Effect of use BEGIN ==========
    /**
     * Discount type
     */
    private Integer discountType;
    /**
     * Discount Percentage
     */
    private Integer discountPercent;
    /**
     * Discount amount，Unit：Points
     */
    private Integer discountPrice;
    /**
     * Discount limit，Only in {@link #discountType} equal to {@link PromotionDiscountTypeEnum#PERCENT} Effective when
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
