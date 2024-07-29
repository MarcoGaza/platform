package cn.econets.blossom.module.promotion.api.discount.dto;

import lombok.Data;

/**
 * Limited time discount items Response DTO
 *
 */
@Data
public class DiscountProductRespDTO {

    /**
     * Number，Primary key auto-increment
     */
    private Long id;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     */
    private Long skuId;
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

    // ========== Active Field ==========
    /**
     * Limited time discount event number
     */
    private Long activityId;
    /**
     * Activity Title
     */
    private String activityName;

}
