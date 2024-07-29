package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.getSumValue;
import static java.util.Collections.singletonList;

/**
 * {@link TradePriceCalculator} Tools
 *
 * Mainly implement {@link TradePriceCalculateRespBO} Operation of calculation results
 *
 */
public class TradePriceCalculatorHelper {

    public static TradePriceCalculateRespBO buildCalculateResp(TradePriceCalculateReqBO param,
                                                               List<ProductSpuRespDTO> spuList, List<ProductSkuRespDTO> skuList) {
        // Create PriceCalculateRespDTO Object
        TradePriceCalculateRespBO result = new TradePriceCalculateRespBO();
        result.setType(getOrderType(param));
        result.setPromotions(new ArrayList<>());

        // Create it OrderItem Properties
        result.setItems(new ArrayList<>(param.getItems().size()));
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spuList, ProductSpuRespDTO::getId);
        Map<Long, ProductSkuRespDTO> skuMap = convertMap(skuList, ProductSkuRespDTO::getId);
        param.getItems().forEach(item -> {
            ProductSkuRespDTO sku = skuMap.get(item.getSkuId());
            if (sku == null) {
                return;
            }
            ProductSpuRespDTO spu = spuMap.get(sku.getSpuId());
            if (spu == null) {
                return;
            }
            // Product Item
            TradePriceCalculateRespBO.OrderItem orderItem = new TradePriceCalculateRespBO.OrderItem();
            result.getItems().add(orderItem);
            orderItem.setSpuId(sku.getSpuId()).setSkuId(sku.getId())
                    .setCount(item.getCount()).setCartId(item.getCartId()).setSelected(item.getSelected());
            // sku Price
            orderItem.setPrice(sku.getPrice()).setPayPrice(sku.getPrice() * item.getCount())
                    .setDiscountPrice(0).setDeliveryPrice(0).setCouponPrice(0).setPointPrice(0).setVipPrice(0);
            // sku Information
            orderItem.setPicUrl(sku.getPicUrl()).setProperties(sku.getProperties())
                    .setWeight(sku.getWeight()).setVolume(sku.getVolume());
            // spu Information
            orderItem.setSpuName(spu.getName()).setCategoryId(spu.getCategoryId())
                    .setDeliveryTemplateId(spu.getDeliveryTemplateId())
                    .setGivePoint(spu.getGiveIntegral()).setUsePoint(0);
            if (orderItem.getPicUrl() == null) {
                orderItem.setPicUrl(spu.getPicUrl());
            }
        });

        // Create it Price Properties
        result.setPrice(new TradePriceCalculateRespBO.Price());
        recountAllPrice(result);
        recountAllGivePoint(result);
        return result;
    }

    /**
     * Calculate order type
     *
     * @param param Calculation parameters
     * @return Order Type
     */
    private static Integer getOrderType(TradePriceCalculateReqBO param) {
        if (param.getSeckillActivityId() != null) {
            return TradeOrderTypeEnum.SECKILL.getType();
        }
        if (param.getCombinationActivityId() != null) {
            return TradeOrderTypeEnum.COMBINATION.getType();
        }
        if (param.getBargainRecordId() != null) {
            return TradeOrderTypeEnum.BARGAIN.getType();
        }
        return TradeOrderTypeEnum.NORMAL.getType();
    }

    /**
     * Based on order items，Recalculate price Total Price
     *
     * @param result Calculation results
     */
    public static void recountAllPrice(TradePriceCalculateRespBO result) {
        // Reset first
        TradePriceCalculateRespBO.Price price = result.getPrice();
        price.setTotalPrice(0).setDiscountPrice(0).setDeliveryPrice(0)
                .setCouponPrice(0).setPointPrice(0).setVipPrice(0).setPayPrice(0);
        // Additional calculation item
        result.getItems().forEach(item -> {
            if (!item.getSelected()) {
                return;
            }
            price.setTotalPrice(price.getTotalPrice() + item.getPrice() * item.getCount());
            price.setDiscountPrice(price.getDiscountPrice() + item.getDiscountPrice());
            price.setDeliveryPrice(price.getDeliveryPrice() + item.getDeliveryPrice());
            price.setCouponPrice(price.getCouponPrice() + item.getCouponPrice());
            price.setPointPrice(price.getPointPrice() + item.getPointPrice());
            price.setVipPrice(price.getVipPrice() + item.getVipPrice());
            price.setPayPrice(price.getPayPrice() + item.getPayPrice());
        });
    }

    /**
     * Based on order items，Recalculate the gift points
     *
     * @param result Calculation results
     */
    public static void recountAllGivePoint(TradePriceCalculateRespBO result) {
        result.setGivePoint(getSumValue(result.getItems(), item -> item.getSelected() ? item.getGivePoint() : 0, Integer::sum));
    }

    /**
     * Recalculate the payment amount for a single order item
     *
     * @param orderItem Order Item
     */
    public static void recountPayPrice(TradePriceCalculateRespBO.OrderItem orderItem) {
        orderItem.setPayPrice(orderItem.getPrice() * orderItem.getCount()
                - orderItem.getDiscountPrice()
                + orderItem.getDeliveryPrice()
                - orderItem.getCouponPrice()
                - orderItem.getPointPrice()
                - orderItem.getVipPrice()
        );
    }

    /**
     * Recalculate the payment amount for each order item
     *
     * 【Currently mainly used for single testing】
     *
     * @param orderItems Order item array
     */
    public static void recountPayPrice(List<TradePriceCalculateRespBO.OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            if (orderItem.getDiscountPrice() == null) {
                orderItem.setDiscountPrice(0);
            }
            if (orderItem.getDeliveryPrice() == null) {
                orderItem.setDeliveryPrice(0);
            }
            if (orderItem.getCouponPrice() == null) {
                orderItem.setCouponPrice(0);
            }
            if (orderItem.getPointPrice() == null) {
                orderItem.setPointPrice(0);
            }
            if (orderItem.getUsePoint() == null) {
                orderItem.setUsePoint(0);
            }
            if (orderItem.getGivePoint() == null) {
                orderItem.setGivePoint(0);
            }
            if (orderItem.getVipPrice() == null) {
                orderItem.setVipPrice(0);
            }
            recountPayPrice(orderItem);
        });
    }

    /**
     * Calculate selected order items，Total payment amount
     *
     * @param orderItems Order item array
     * @return Total payment amount
     */
    public static Integer calculateTotalPayPrice(List<TradePriceCalculateRespBO.OrderItem> orderItems) {
        return getSumValue(orderItems,
                orderItem -> orderItem.getSelected() ? orderItem.getPayPrice() : 0, // Unchecked，Do not calculate payment amount
                Integer::sum);
    }

    /**
     * Calculate selected order items，Total number of products
     *
     * @param orderItems Order item array
     * @return Total number of products
     */
    public static Integer calculateTotalCount(List<TradePriceCalculateRespBO.OrderItem> orderItems) {
        return getSumValue(orderItems,
                orderItem -> orderItem.getSelected() ? orderItem.getCount() : 0, // Unchecked，Do not calculate quantity
                Integer::sum);
    }

    /**
     * According to the payment amount，Returns an array of allocated amounts for each order item
     *
     * In fact price Not only the amount can be transferred，It can also be points。Because of its implementation logic，Based on payPrice Just sharing the cost
     *
     * @param orderItems Order item array
     * @param price      Amount
     * @return Allocated amount array，and incoming orderItems One-to-one correspondence
     */
    public static List<Integer> dividePrice(List<TradePriceCalculateRespBO.OrderItem> orderItems, Integer price) {
        Integer total = calculateTotalPayPrice(orderItems);
        assert total != null;
        // Traverse each one，Apportion
        List<Integer> prices = new ArrayList<>(orderItems.size());
        int remainPrice = price;
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            // 1. If it is not selected，Then the allocation is 0
            if (!orderItem.getSelected()) {
                prices.add(0);
                continue;
            }
            // 2. If selected，According to the percentage，Apportion
            int partPrice;
            if (i < orderItems.size() - 1) { // Reason for reducing one，Because of the split，If according to the proportion，May appear.So the last one，Use anti-subtraction
                partPrice = (int) (price * (1.0D * orderItem.getPayPrice() / total));
                remainPrice -= partPrice;
            } else {
                partPrice = remainPrice;
            }
            Assert.isTrue(partPrice >= 0, "The amount of the allocation must be greater than or equal to 0");
            prices.add(partPrice);
        }
        return prices;
    }

    /**
     * Calculate order price adjustment allocation
     *
     * Japanese {@link #dividePrice(List, Integer)} Logical consistency，Just passing in TradeOrderItemDO Object
     *
     * @param items         Order Item
     * @param price Order payment amount
     * @return Allocated amount array，and incoming orderItems One-to-one correspondence
     */
    public static List<Integer> dividePrice2(List<TradeOrderItemDO> items, Integer price) {
        Integer total = getSumValue(items, TradeOrderItemDO::getPrice, Integer::sum);
        assert total != null;
        // Traverse each one，Apportion
        List<Integer> prices = new ArrayList<>(items.size());
        int remainPrice = price;
        for (int i = 0; i < items.size(); i++) {
            TradeOrderItemDO orderItem = items.get(i);
            int partPrice;
            if (i < items.size() - 1) { // Reason for reducing one，Because of the split，If according to the proportion，May appear.So the last one，Use anti-subtraction
                partPrice = (int) (price * (1.0D * orderItem.getPayPrice() / total));
                remainPrice -= partPrice;
            } else {
                partPrice = remainPrice;
            }
            Assert.isTrue(partPrice >= 0, "The amount of the allocation must be greater than or equal to 0");
            prices.add(partPrice);
        }
        return prices;
    }

    /**
     * Add【Match】Single OrderItem Marketing details
     *
     * @param result        Price calculation results
     * @param orderItem     Single order item SKU
     * @param id            Marketing Number
     * @param name          Marketing name
     * @param description   Prompts that meet the conditions
     * @param type          Marketing Type
     * @param discountPrice Single order item SKU Preferential price（Total）
     */
    public static void addPromotion(TradePriceCalculateRespBO result, TradePriceCalculateRespBO.OrderItem orderItem,
                                    Long id, String name, Integer type, String description, Integer discountPrice) {
        addPromotion(result, singletonList(orderItem), id, name, type, description, singletonList(discountPrice));
    }

    /**
     * Add【Match】Multiple OrderItem Marketing details
     *
     * @param result         Price calculation results
     * @param orderItems     Multiple order items SKU
     * @param id             Marketing Number
     * @param name           Marketing name
     * @param description    Prompts that meet the conditions
     * @param type           Marketing Type
     * @param discountPrices Multiple order items SKU Preferential price（Total），Japanese orderItems One-to-one correspondence
     */
    public static void addPromotion(TradePriceCalculateRespBO result, List<TradePriceCalculateRespBO.OrderItem> orderItems,
                                    Long id, String name, Integer type, String description, List<Integer> discountPrices) {
        // Create marketing details Item
        List<TradePriceCalculateRespBO.PromotionItem> promotionItems = new ArrayList<>(discountPrices.size());
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            promotionItems.add(new TradePriceCalculateRespBO.PromotionItem().setSkuId(orderItem.getSkuId())
                    .setTotalPrice(orderItem.getPayPrice()).setDiscountPrice(discountPrices.get(i)));
        }
        // Create marketing details
        TradePriceCalculateRespBO.Promotion promotion = new TradePriceCalculateRespBO.Promotion()
                .setId(id).setName(name).setType(type)
                .setTotalPrice(calculateTotalPayPrice(orderItems))
                .setDiscountPrice(getSumValue(discountPrices, value -> value, Integer::sum))
                .setItems(promotionItems).setMatch(true).setDescription(description);
        result.getPromotions().add(promotion);
    }

    /**
     * Add【No match】Multiple OrderItem Marketing details
     *
     * @param result      Price calculation results
     * @param orderItems  Multiple order items SKU
     * @param id          Marketing Number
     * @param name        Marketing name
     * @param description Prompts that meet the conditions
     * @param type        Marketing Type
     */
    public static void addNotMatchPromotion(TradePriceCalculateRespBO result, List<TradePriceCalculateRespBO.OrderItem> orderItems,
                                            Long id, String name, Integer type, String description) {
        // Create marketing details Item
        List<TradePriceCalculateRespBO.PromotionItem> promotionItems = CollectionUtils.convertList(orderItems,
                orderItem -> new TradePriceCalculateRespBO.PromotionItem().setSkuId(orderItem.getSkuId())
                        .setTotalPrice(orderItem.getPayPrice()).setDiscountPrice(0));
        // Create marketing details
        TradePriceCalculateRespBO.Promotion promotion = new TradePriceCalculateRespBO.Promotion()
                .setId(id).setName(name).setType(type)
                .setTotalPrice(calculateTotalPayPrice(orderItems))
                .setDiscountPrice(0)
                .setItems(promotionItems).setMatch(false).setDescription(description);
        result.getPromotions().add(promotion);
    }

    public static String formatPrice(Integer price) {
        return String.format("%.2f", price / 100d);
    }

}
