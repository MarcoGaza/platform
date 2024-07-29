package cn.econets.blossom.module.promotion.api.coupon.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Use coupons Request DTO
 *
 */
@Data
public class CouponValidReqDTO {

    /**
     * Coupon number
     */
    @NotNull(message = "Coupon number cannot be empty")
    private Long id;

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

}
