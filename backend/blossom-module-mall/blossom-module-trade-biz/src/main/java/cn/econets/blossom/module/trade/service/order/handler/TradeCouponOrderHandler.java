package cn.econets.blossom.module.trade.service.order.handler;

import cn.econets.blossom.module.promotion.api.coupon.CouponApi;
import cn.econets.blossom.module.promotion.api.coupon.dto.CouponUseReqDTO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Coupon {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeCouponOrderHandler implements TradeOrderHandler {

    @Resource
    private CouponApi couponApi;

    @Override
    public void afterOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (order.getCouponId() == null || order.getCouponId() <= 0) {
            return;
        }
        // Reasons for not making a pre-deduction，Because the coupon needs to record the order number used
        couponApi.useCoupon(new CouponUseReqDTO().setId(order.getCouponId()).setUserId(order.getUserId())
                .setOrderId(order.getId()));
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (order.getCouponId() == null || order.getCouponId() <= 0) {
            return;
        }
        // Return coupon
        couponApi.returnUsedCoupon(order.getCouponId());
    }

}
