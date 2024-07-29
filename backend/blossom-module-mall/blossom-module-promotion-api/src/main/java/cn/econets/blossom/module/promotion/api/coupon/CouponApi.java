package cn.econets.blossom.module.promotion.api.coupon;

import cn.econets.blossom.module.promotion.api.coupon.dto.CouponRespDTO;
import cn.econets.blossom.module.promotion.api.coupon.dto.CouponUseReqDTO;
import cn.econets.blossom.module.promotion.api.coupon.dto.CouponValidReqDTO;

import javax.validation.Valid;

/**
 * Coupon API Interface
 *
 */
public interface CouponApi {

    /**
     * Use coupon
     *
     * @param useReqDTO Use request
     */
    void useCoupon(@Valid CouponUseReqDTO useReqDTO);

    /**
     * Refund used coupons
     *
     * @param id Coupon number
     */
    void returnUsedCoupon(Long id);

    /**
     * Check coupon
     *
     * @param validReqDTO Verification request
     * @return Coupon
     */
    CouponRespDTO validateCoupon(@Valid CouponValidReqDTO validReqDTO);

}
