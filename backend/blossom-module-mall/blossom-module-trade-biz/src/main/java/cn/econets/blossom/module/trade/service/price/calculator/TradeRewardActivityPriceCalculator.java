package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.reward.RewardActivityApi;
import cn.econets.blossom.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionConditionTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.filterList;
import static cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculatorHelper.formatPrice;

/**
 * Save a lot of money for free {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_REWARD_ACTIVITY)
public class TradeRewardActivityPriceCalculator implements TradePriceCalculator {

    @Resource
    private RewardActivityApi rewardActivityApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 0. Only【Normal】Order，The discount will be calculated later
        if (ObjectUtil.notEqual(result.getType(), TradeOrderTypeEnum.NORMAL.getType())) {
            return;
        }
        // Get SKU Corresponding discounts and free gifts
        List<RewardActivityMatchRespDTO> rewardActivities = rewardActivityApi.getMatchRewardActivityList(
                convertSet(result.getItems(), TradePriceCalculateRespBO.OrderItem::getSpuId));
        if (CollUtil.isEmpty(rewardActivities)) {
            return;
        }

        // Process each full discount activity
        rewardActivities.forEach(rewardActivity -> calculate(param, result, rewardActivity));
    }

    private void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result,
                           RewardActivityMatchRespDTO rewardActivity) {
        // 1.1 Get the order items with full discounts and deliveries（Products）List
        List<TradePriceCalculateRespBO.OrderItem> orderItems = filterMatchCouponOrderItems(result, rewardActivity);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        // 1.2 Get the rules for the maximum matching full discount activity
        RewardActivityMatchRespDTO.Rule rule = getMaxMatchRewardActivityRule(rewardActivity, orderItems);
        if (rule == null) {
            TradePriceCalculatorHelper.addNotMatchPromotion(result, orderItems,
                    rewardActivity.getId(), rewardActivity.getName(), PromotionTypeEnum.REWARD_ACTIVITY.getType(),
                    getRewardActivityNotMeetTip(rewardActivity));
            return;
        }

        // 2.1 Calculate the amount of discount available
        Integer newDiscountPrice = rule.getDiscountPrice();
        // 2.2 Calculate the allocated discount amount
        List<Integer> divideDiscountPrices = TradePriceCalculatorHelper.dividePrice(orderItems, newDiscountPrice);

        // 3.1 Record the coupons used
        result.setCouponId(param.getCouponId());
        // 3.2 Record discount details
        TradePriceCalculatorHelper.addPromotion(result, orderItems,
                rewardActivity.getId(), rewardActivity.getName(), PromotionTypeEnum.REWARD_ACTIVITY.getType(),
                StrUtil.format("Get a discount if you spend more than 100 yuan：Province {} Yuan", formatPrice(rule.getDiscountPrice())),
                divideDiscountPrices);
        // 3.3 Update SKU Discount amount
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            orderItem.setDiscountPrice(orderItem.getDiscountPrice() + divideDiscountPrices.get(i));
            TradePriceCalculatorHelper.recountPayPrice(orderItem);
        }
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    /**
     * Get the order items with full discounts and deliveries（Products）List
     *
     * @param result Calculation results
     * @param rewardActivity Save a lot of money and get a free gift
     * @return Order Item（Products）List
     */
    private List<TradePriceCalculateRespBO.OrderItem> filterMatchCouponOrderItems(TradePriceCalculateRespBO result,
                                                                                  RewardActivityMatchRespDTO rewardActivity) {
        return filterList(result.getItems(),
                orderItem -> CollUtil.contains(rewardActivity.getSpuIds(), orderItem.getSpuId()));
    }

    /**
     * Get the rules for the maximum matching full discount activity
     *
     * @param rewardActivity Save a lot of money and get a free gift
     * @param orderItems Product Item
     * @return Matching activity rules
     */
    private RewardActivityMatchRespDTO.Rule getMaxMatchRewardActivityRule(RewardActivityMatchRespDTO rewardActivity,
                                                                          List<TradePriceCalculateRespBO.OrderItem> orderItems) {
        // 1. Calculate quantity and price
        Integer count = TradePriceCalculatorHelper.calculateTotalCount(orderItems);
        Integer price = TradePriceCalculatorHelper.calculateTotalPayPrice(orderItems);
        assert count != null && price != null;

        // 2. Find the rule with the biggest discount in reverse order
        for (int i = rewardActivity.getRules().size() - 1; i >= 0; i--) {
            RewardActivityMatchRespDTO.Rule rule = rewardActivity.getRules().get(i);
            if (PromotionConditionTypeEnum.PRICE.getType().equals(rewardActivity.getConditionType())
                    && price >= rule.getLimit()) {
                return rule;
            }
            if (PromotionConditionTypeEnum.COUNT.getType().equals(rewardActivity.getConditionType())
                    && count >= rule.getLimit()) {
                return rule;
            }
        }
        return null;
    }

    /**
     * Prompt for matching when getting full discount activity
     *
     * @param rewardActivity Save a lot of money and get a free gift
     * @return Prompt
     */
    private String getRewardActivityNotMeetTip(RewardActivityMatchRespDTO rewardActivity) {
        // TODO Think about it later；Should find the first rule，Just calculate how much is left。
        return "TODO";
    }

}
