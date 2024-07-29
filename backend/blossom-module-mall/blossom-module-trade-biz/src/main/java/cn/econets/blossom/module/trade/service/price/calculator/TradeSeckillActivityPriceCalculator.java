package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.seckill.SeckillActivityApi;
import cn.econets.blossom.module.promotion.api.seckill.dto.SeckillValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.service.order.TradeOrderQueryService;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.PRICE_CALCULATE_SECKILL_TOTAL_LIMIT_COUNT;

// TODO Single test needs to be supplemented
/**
 * Second-sale event {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_SECKILL_ACTIVITY)
public class TradeSeckillActivityPriceCalculator implements TradePriceCalculator {

    @Resource
    private SeckillActivityApi seckillActivityApi;

    @Resource
    private TradeOrderQueryService tradeOrderQueryService;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 1. Determine the order type and whether it has a flash sale activity number
        if (param.getSeckillActivityId() == null) {
            return;
        }
        Assert.isTrue(param.getItems().size() == 1, "Second kill，Only one product can be selected");
        // 2. Check whether you can participate in the flash sale
        TradePriceCalculateRespBO.OrderItem orderItem = result.getItems().get(0);
        SeckillValidateJoinRespDTO seckillActivity = validateJoinSeckill(
                param.getUserId(), param.getSeckillActivityId(),
                orderItem.getSkuId(), orderItem.getCount());

        // 3.1 Record discount details
        Integer discountPrice = orderItem.getPayPrice() - seckillActivity.getSeckillPrice() * orderItem.getCount();
        TradePriceCalculatorHelper.addPromotion(result, orderItem,
                param.getSeckillActivityId(), seckillActivity.getName(), PromotionTypeEnum.SECKILL_ACTIVITY.getType(),
                StrUtil.format("Second-sale event：Province {} Yuan", TradePriceCalculatorHelper.formatPrice(discountPrice)),
                discountPrice);
        // 3.2 Update SKU Discount amount
        orderItem.setDiscountPrice(orderItem.getDiscountPrice() + discountPrice);
        TradePriceCalculatorHelper.recountPayPrice(orderItem);
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    private SeckillValidateJoinRespDTO validateJoinSeckill(Long userId, Long activityId, Long skuId, Integer count) {
        // 1. Check whether you can participate in the flash sale
        SeckillValidateJoinRespDTO seckillActivity = seckillActivityApi.validateJoinSeckill(activityId, skuId, count);
        // 2. Check total purchase limit quantity，Currently only trade There are specific order data，Need to be handed over trade Price calculation use
        int seckillProductCount = tradeOrderQueryService.getSeckillProductCount(userId, activityId);
        if (seckillProductCount + count > seckillActivity.getTotalLimitCount()) {
            throw exception(PRICE_CALCULATE_SECKILL_TOTAL_LIMIT_COUNT);
        }
        return seckillActivity;
    }

}
