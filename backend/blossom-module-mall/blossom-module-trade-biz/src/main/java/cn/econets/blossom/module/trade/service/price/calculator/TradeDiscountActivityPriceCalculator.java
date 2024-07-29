package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.promotion.api.discount.DiscountActivityApi;
import cn.econets.blossom.module.promotion.api.discount.dto.DiscountProductRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculatorHelper.formatPrice;

/**
 * Limited time discount {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_DISCOUNT_ACTIVITY)
public class TradeDiscountActivityPriceCalculator implements TradePriceCalculator {

    @Resource
    private DiscountActivityApi discountActivityApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 0. Only【Normal】Order，The discount will be calculated later
        if (ObjectUtil.notEqual(result.getType(), TradeOrderTypeEnum.NORMAL.getType())) {
            return;
        }
        // Get SKU Corresponding limited-time discount activities
        List<DiscountProductRespDTO> discountProducts = discountActivityApi.getMatchDiscountProductList(
                convertSet(result.getItems(), TradePriceCalculateRespBO.OrderItem::getSkuId));
        if (CollUtil.isEmpty(discountProducts)) {
            return;
        }
        Map<Long, DiscountProductRespDTO> discountProductMap = convertMap(discountProducts, DiscountProductRespDTO::getSkuId);

        // Process each SKU Limited time discount
        result.getItems().forEach(orderItem -> {
            // 1. Get this SKU Promotional information
            DiscountProductRespDTO discountProduct = discountProductMap.get(orderItem.getSkuId());
            if (discountProduct == null) {
                return;
            }
            // 2. Calculate discount amount
            Integer newPayPrice = calculatePayPrice(discountProduct, orderItem);
            Integer newDiscountPrice = orderItem.getPayPrice() - newPayPrice;

            // 3.1 Record discount details
            if (orderItem.getSelected()) {
                // Attention，Only when selected，Only then will the discount details be recorded。Otherwise just update SKU Discount amount，For display
                TradePriceCalculatorHelper.addPromotion(result, orderItem,
                        discountProduct.getActivityId(), discountProduct.getActivityName(), PromotionTypeEnum.DISCOUNT_ACTIVITY.getType(),
                        StrUtil.format("Limited time discount：Province {} Yuan", formatPrice(newDiscountPrice)),
                        newDiscountPrice);
            }
            // 3.2 Update SKU Discount amount
            orderItem.setDiscountPrice(orderItem.getDiscountPrice() + newDiscountPrice);
            TradePriceCalculatorHelper.recountPayPrice(orderItem);
        });
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    private Integer calculatePayPrice(DiscountProductRespDTO discountProduct,
                                      TradePriceCalculateRespBO.OrderItem orderItem) {
        Integer price = orderItem.getPayPrice();
        if (PromotionDiscountTypeEnum.PRICE.getType().equals(discountProduct.getDiscountType())) { // Price reduction
            price -= discountProduct.getDiscountPrice() * orderItem.getCount();
        } else if (PromotionDiscountTypeEnum.PERCENT.getType().equals(discountProduct.getDiscountType())) { // Discount
            price = price * discountProduct.getDiscountPercent() / 100;
        } else {
            throw new IllegalArgumentException(String.format("Products on sale(%s) The offer type is incorrect", discountProduct));
        }
        return price;
    }

}
