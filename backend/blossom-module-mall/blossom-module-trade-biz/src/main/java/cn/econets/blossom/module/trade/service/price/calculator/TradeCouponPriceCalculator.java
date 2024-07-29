package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.coupon.CouponApi;
import cn.econets.blossom.module.promotion.api.coupon.dto.CouponRespDTO;
import cn.econets.blossom.module.promotion.api.coupon.dto.CouponValidReqDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.filterList;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.COUPON_NO_MATCH_MIN_PRICE;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.COUPON_NO_MATCH_SPU;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.PRICE_CALCULATE_COUPON_NOT_MATCH_NORMAL_ORDER;

/**
 * Coupon {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_COUPON)
public class TradeCouponPriceCalculator implements TradePriceCalculator {

    @Resource
    private CouponApi couponApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 1.1 Check coupons
        if (param.getCouponId() == null) {
            return;
        }
        CouponRespDTO coupon = couponApi.validateCoupon(new CouponValidReqDTO()
                .setId(param.getCouponId()).setUserId(param.getUserId()));
        Assert.notNull(coupon, "Coupons that have passed verification({})，Cannot be empty", param.getCouponId());
        // 1.2 Only【Normal】Order，Coupons are allowed to be used
        if (ObjectUtil.notEqual(result.getType(), TradeOrderTypeEnum.NORMAL.getType())) {
            throw exception(PRICE_CALCULATE_COUPON_NOT_MATCH_NORMAL_ORDER);
        }

        // 2.1 Get matching products SKU Array
        List<TradePriceCalculateRespBO.OrderItem> orderItems = filterMatchCouponOrderItems(result, coupon);
        if (CollUtil.isEmpty(orderItems)) {
            throw exception(COUPON_NO_MATCH_SPU);
        }
        // 2.2 Calculate whether the coupon usage amount is met
        Integer totalPayPrice = TradePriceCalculatorHelper.calculateTotalPayPrice(orderItems);
        if (totalPayPrice < coupon.getUsePrice()) {
            throw exception(COUPON_NO_MATCH_MIN_PRICE);
        }

        // 3.1 Calculate the amount of discount available
        Integer couponPrice = getCouponPrice(coupon, totalPayPrice);
        Assert.isTrue(couponPrice < totalPayPrice,
                "Coupon({}) Discount amount({})，Cannot be greater than the total order amount({})", coupon.getId(), couponPrice, totalPayPrice);
        // 3.2 Calculate the allocated discount amount
        List<Integer> divideCouponPrices = TradePriceCalculatorHelper.dividePrice(orderItems, couponPrice);

        // 4.1 Record the coupons used
        result.setCouponId(param.getCouponId());
        // 4.2 Record discount details
        TradePriceCalculatorHelper.addPromotion(result, orderItems,
                param.getCouponId(), coupon.getName(), PromotionTypeEnum.COUPON.getType(),
                StrUtil.format("Coupon：Province {} Yuan", TradePriceCalculatorHelper.formatPrice(couponPrice)),
                divideCouponPrices);
        // 4.3 Update SKU Discount amount
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            orderItem.setCouponPrice(divideCouponPrices.get(i));
            TradePriceCalculatorHelper.recountPayPrice(orderItem);
        }
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    private Integer getCouponPrice(CouponRespDTO coupon, Integer totalPayPrice) {
        if (PromotionDiscountTypeEnum.PRICE.getType().equals(coupon.getDiscountType())) { // Price reduction
            return coupon.getDiscountPrice();
        } else if (PromotionDiscountTypeEnum.PERCENT.getType().equals(coupon.getDiscountType())) { // Discount
            int couponPrice = totalPayPrice * coupon.getDiscountPercent() / 100;
            return coupon.getDiscountLimitPrice() == null ? couponPrice
                    : Math.min(couponPrice, coupon.getDiscountLimitPrice()); // Discount limit
        }
        throw new IllegalArgumentException(String.format("Coupon(%s) The offer type is incorrect", coupon));
    }

    /**
     * Get the order items that can be used for the coupon（Products）List
     *
     * @param result Calculation results
     * @param coupon Coupon
     * @return Order Item（Products）List
     */
    private List<TradePriceCalculateRespBO.OrderItem> filterMatchCouponOrderItems(TradePriceCalculateRespBO result,
                                                                                  CouponRespDTO coupon) {
        Predicate<TradePriceCalculateRespBO.OrderItem> matchPredicate = TradePriceCalculateRespBO.OrderItem::getSelected;
        if (PromotionProductScopeEnum.SPU.getScope().equals(coupon.getProductScope())) {
            matchPredicate = matchPredicate // Add the following additional conditions
                    .and(orderItem -> coupon.getProductScopeValues().contains(orderItem.getSpuId()));
        } else if (PromotionProductScopeEnum.CATEGORY.getScope().equals(coupon.getProductScope())) {
            matchPredicate = matchPredicate // Add the following additional conditions
                    .and(orderItem -> coupon.getProductScopeValues().contains(orderItem.getCategoryId()));
        }
        return filterList(result.getItems(), matchPredicate);
    }

}
