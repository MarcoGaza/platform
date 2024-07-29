package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.module.member.api.level.MemberLevelApi;
import cn.econets.blossom.module.member.api.level.dto.MemberLevelRespDTO;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculatorHelper.formatPrice;

/**
 * Member VIP Discounted {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_MEMBER_LEVEL)
public class TradeMemberLevelPriceCalculator implements TradePriceCalculator {

    @Resource
    private MemberLevelApi memberLevelApi;
    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 0. Only【Normal】Order，The discount will be calculated later
        if (ObjectUtil.notEqual(result.getType(), TradeOrderTypeEnum.NORMAL.getType())) {
            return;
        }
        // 1. Get the user's membership level
        MemberUserRespDTO user = memberUserApi.getUser(param.getUserId());
        if (user.getLevelId() == null || user.getLevelId() <= 0) {
            return;
        }
        MemberLevelRespDTO level = memberLevelApi.getMemberLevel(user.getLevelId());
        if (level == null || level.getDiscountPercent() == null) {
            return;
        }

        // 2. Calculate each SKU Discount amount
        result.getItems().forEach(orderItem -> {
            // 2.1 Calculate discount amount
            Integer vipPrice = calculateVipPrice(orderItem.getPayPrice(), level.getDiscountPercent());
            if (vipPrice <= 0) {
                return;
            }

            // 2.2 Record discount details
            if (orderItem.getSelected()) {
                // Attention，Only when selected，Only then will the discount details be recorded。Otherwise just update SKU Discount amount，For display
                TradePriceCalculatorHelper.addPromotion(result, orderItem,
                        level.getId(), level.getName(), PromotionTypeEnum.MEMBER_LEVEL.getType(),
                        String.format("Member Level Discount：Province %s Yuan", formatPrice(vipPrice)),
                        vipPrice);
            }

            // 2.3 Update SKU Discount amount
            orderItem.setVipPrice(vipPrice);
            TradePriceCalculatorHelper.recountPayPrice(orderItem);
        });
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    /**
     * Calculate Membership VIP Preferential price
     *
     * @param price Original price
     * @param discountPercent Discount
     * @return Preferential price
     */
    public Integer calculateVipPrice(Integer price, Integer discountPercent) {
        if (discountPercent == null) {
            return 0;
        }
        Integer newPrice = price * discountPercent / 100;
        return price - newPrice;
    }

}
