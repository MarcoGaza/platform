package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.bargain.BargainRecordApi;
import cn.econets.blossom.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// TODO Single test needs to be supplemented
/**
 * Bargaining activity {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_BARGAIN_ACTIVITY)
public class TradeBargainActivityPriceCalculator implements TradePriceCalculator {

    @Resource
    private BargainRecordApi bargainRecordApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 1. Judge the order type and whether it has a group purchase record number
        if (ObjectUtil.notEqual(result.getType(), TradeOrderTypeEnum.BARGAIN.getType())) {
            return;
        }
        Assert.isTrue(param.getItems().size() == 1, "Bargaining，Only one product can be selected");
        Assert.isTrue(param.getItems().get(0).getCount() == 1, "Bargaining，Only one product can be selected");
        // 2. Check whether you can participate in bargaining
        TradePriceCalculateRespBO.OrderItem orderItem = result.getItems().get(0);
        BargainValidateJoinRespDTO bargainActivity = bargainRecordApi.validateJoinBargain(
                param.getUserId(), param.getBargainRecordId(), orderItem.getSkuId());

        // 3.1 Record discount details
        Integer discountPrice = orderItem.getPayPrice() - bargainActivity.getBargainPrice() * orderItem.getCount();
        // TODO Extreme Situation，The discount amount is negative，Needs processing
        TradePriceCalculatorHelper.addPromotion(result, orderItem,
                param.getSeckillActivityId(), bargainActivity.getName(), PromotionTypeEnum.BARGAIN_ACTIVITY.getType(),
                StrUtil.format("Bargaining activity：Province {} Yuan", TradePriceCalculatorHelper.formatPrice(discountPrice)),
                discountPrice);
        // 3.2 Update SKU Discount amount
        orderItem.setDiscountPrice(orderItem.getDiscountPrice() + discountPrice);
        TradePriceCalculatorHelper.recountPayPrice(orderItem);
        TradePriceCalculatorHelper.recountAllPrice(result);
        // 4. Special：Set the corresponding bargaining activity number
        result.setBargainActivityId(bargainActivity.getActivityId());
    }

}
