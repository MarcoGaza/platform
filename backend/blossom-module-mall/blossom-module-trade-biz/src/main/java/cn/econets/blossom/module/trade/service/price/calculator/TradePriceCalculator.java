package cn.econets.blossom.module.trade.service.price.calculator;

import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;

/**
 * Calculator interface for price calculation
 *
 * Discount calculation order：
 * 1. <a href="https://help.youzan.com/displaylist/detail_4_4-1-53316">Points redeemable for cash、Member Price、Coupon、Fans exclusive price、Which one should be given priority in calculating the full reduction?？</>
 *
 */
public interface TradePriceCalculator {

    int ORDER_MEMBER_LEVEL = 5;

    int ORDER_SECKILL_ACTIVITY = 8;
    int ORDER_BARGAIN_ACTIVITY = 8;
    int ORDER_COMBINATION_ACTIVITY = 8;

    int ORDER_DISCOUNT_ACTIVITY = 10;
    int ORDER_REWARD_ACTIVITY = 20;
    int ORDER_COUPON = 30;
    int ORDER_POINT_USE = 40;
    /**
     * Calculation of express delivery charges
     *
     * Put it in various marketing activities、Behind the coupon
     */
    int ORDER_DELIVERY = 50;
    /**
     * Send points，Put it last
     *
     * Put it in {@link #ORDER_DELIVERY} The reason behind，Shipping costs will also be incurred，Need to give corresponding points
     */
    int ORDER_POINT_GIVE = 999;

    void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result);

}
