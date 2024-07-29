package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.combination.CombinationRecordApi;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// TODO Single test can be supplemented later

/**
 * Group buying activity {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_COMBINATION_ACTIVITY)
public class TradeCombinationActivityPriceCalculator implements TradePriceCalculator {

    @Resource
    private CombinationRecordApi combinationRecordApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 1. Determine the order type and whether it has a group purchase activity number
        if (param.getCombinationActivityId() == null) {
            return;
        }
        Assert.isTrue(param.getItems().size() == 1, "When buying in a group，Only one product can be selected");
        // 2. Check whether you can participate in the group purchase
        TradePriceCalculateRespBO.OrderItem orderItem = result.getItems().get(0);
        CombinationValidateJoinRespDTO combinationActivity = combinationRecordApi.validateJoinCombination(
                param.getUserId(), param.getCombinationActivityId(), param.getCombinationHeadId(),
                orderItem.getSkuId(), orderItem.getCount());

        // 3.1 Record discount details
        Integer discountPrice = orderItem.getPayPrice() - combinationActivity.getCombinationPrice() * orderItem.getCount();
        TradePriceCalculatorHelper.addPromotion(result, orderItem,
                param.getCombinationActivityId(), combinationActivity.getName(), PromotionTypeEnum.COMBINATION_ACTIVITY.getType(),
                StrUtil.format("Group buying activity：Province {} Yuan", TradePriceCalculatorHelper.formatPrice(discountPrice)),
                discountPrice);
        // 3.2 Update SKU Discount amount
        orderItem.setDiscountPrice(orderItem.getDiscountPrice() + discountPrice);
        TradePriceCalculatorHelper.recountPayPrice(orderItem);
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

}
