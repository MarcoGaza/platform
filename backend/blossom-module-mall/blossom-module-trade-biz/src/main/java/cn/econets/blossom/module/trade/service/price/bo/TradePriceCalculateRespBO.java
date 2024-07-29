package cn.econets.blossom.module.trade.service.price.bo;

import cn.econets.blossom.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * Price calculation Response BO
 *
 * Overall design，Reference taobao Technical documentation：
 * 1. <a href="https://developer.alibaba.com/docs/doc.htm?treeId=1&articleId=1029&docType=1">Order Management</a>
 * 2. <a href="https://open.taobao.com/docV3.htm?docId=108471&docType=1">Common order amount description</a>
 *
 */
@Data
public class TradePriceCalculateRespBO {

    /**
     * Order Type
     *
     * Enumeration {@link TradeOrderTypeEnum}
     */
    private Integer type;

    /**
     * Order price
     */
    private Price price;

    /**
     * Order item array
     */
    private List<OrderItem> items;

    /**
     * Marketing campaign array
     *
     * Only for {@link Price#items} Product matching activities
     */
    private List<Promotion> promotions;

    /**
     * Coupon number
     */
    private Long couponId;

    /**
     * Points used
     */
    private Integer usePoint;

    /**
     * Points used
     */
    private Integer givePoint;

    /**
     * Bargaining activity number
     */
    private Long bargainActivityId;

    /**
     * Order Price
     */
    @Data
    public static class Price {

        /**
         * Original price of product（Total），Unit：Points
         *
         * Based on {@link OrderItem#getPrice()} * {@link OrderItem#getCount()} Summation
         *
         * Corresponding taobao of trade.total_fee Field
         */
        private Integer totalPrice;
        /**
         * Order discount（Total），Unit：Points
         *
         * Corresponding taobao of order.discount_fee Field
         */
        private Integer discountPrice;
        /**
         * Shipping amount，Unit：Points
         */
        private Integer deliveryPrice;
        /**
         * Coupon reduction amount（Total），Unit：Points
         *
         * Corresponding taobao of trade.coupon_fee Field
         */
        private Integer couponPrice;
        /**
         * Amount of points deduction，Unit：Points
         *
         * Corresponding taobao of trade.point_fee Field
         */
        private Integer pointPrice;
        /**
         * VIP Reduction amount，Unit：Points
         */
        private Integer vipPrice;
        /**
         * Final purchase amount（Total），Unit：Points
         *
         * = {@link #totalPrice}
         * - {@link #couponPrice}
         * - {@link #pointPrice}
         * - {@link #discountPrice}
         * + {@link #deliveryPrice}
         * - {@link #vipPrice}
         */
        private Integer payPrice;

    }

    /**
     * Order Items SKU
     */
    @Data
    public static class OrderItem {

        /**
         * SPU Number
         */
        private Long spuId;
        /**
         * SKU Number
         */
        private Long skuId;
        /**
         * Purchase quantity
         */
        private Integer count;
        /**
         * The shopping cart item number
         */
        private Long cartId;
        /**
         * Is it selected?
         */
        private Boolean selected;

        /**
         * Original price of product（Single），Unit：Points
         *
         * Corresponding ProductSkuDO of price Field
         * Corresponding taobao of order.price Field
         */
        private Integer price;
        /**
         * Discount amount（Total），Unit：Points
         *
         * Corresponding taobao of order.discount_fee Field
         */
        private Integer discountPrice;
        /**
         * Shipping amount（Total），Unit：Points
         */
        private Integer deliveryPrice;
        /**
         * Coupon reduction amount，Unit：Points
         *
         * Corresponding taobao of trade.coupon_fee Field
         */
        private Integer couponPrice;
        /**
         * Amount of points deduction，Unit：Points
         *
         * Corresponding taobao of trade.point_fee Field
         */
        private Integer pointPrice;
        /**
         * Points used
         */
        private Integer usePoint;
        /**
         * VIP Reduction amount，Unit：Points
         */
        private Integer vipPrice;
        /**
         * Amount payable（Total），Unit：Points
         *
         * = {@link #price} * {@link #count}
         * - {@link #couponPrice}
         * - {@link #pointPrice}
         * - {@link #discountPrice}
         * + {@link #deliveryPrice}
         * - {@link #vipPrice}
         */
        private Integer payPrice;

        // ========== Products SPU Information ==========
        /**
         * Product Name
         */
        private String spuName;
        /**
         * Product images
         *
         * Priority：SKU.picUrl > SPU.picUrl
         */
        private String picUrl;
        /**
         * Classification number
         */
        private Long categoryId;

        /**
         * Shipping template Id
         */
        private Long deliveryTemplateId;

        // ========== Products SKU Information ==========
        /**
         * Product weight，Unit：kg kilogram
         */
        private Double weight;
        /**
         * Product volume，Unit：m^3 square meters
         */
        private Double volume;

        /**
         * Product attribute array
         */
        private List<ProductPropertyValueDetailRespDTO> properties;

        /**
         * Points used
         */
        private Integer givePoint;

    }

    /**
     * Marketing Details
     */
    @Data
    public static class Promotion {

        /**
         * Marketing Number
         *
         * For example：Marketing campaign number、Coupon number
         */
        private Long id;
        /**
         * Marketing Name
         */
        private String name;
        /**
         * Marketing Type
         *
         * Enumeration {@link PromotionTypeEnum}
         */
        private Integer type;
        /**
         * Original price during calculation（Total），Unit：Points
         */
        private Integer totalPrice;
        /**
         * Discount during calculation（Total），Unit：Points
         */
        private Integer discountPrice;
        /**
         * Matching products SKU Array
         */
        private List<PromotionItem> items;

        // ========== Matching situation ==========

        /**
         * Whether the preferential conditions are met
         */
        private Boolean match;
        /**
         * Prompts that meet the conditions
         *
         * If {@link #match} = true Satisfied，Then prompt“Christmas Price:Province 150.00 Yuan”
         * If {@link #match} = false Not satisfied，Then prompt“Sold out 85 Yuan，Can be reduced 40 Yuan”
         */
        private String description;

    }

    /**
     * Marketing matching products SKU
     */
    @Data
    public static class PromotionItem {

        /**
         * Products SKU Number
         */
        private Long skuId;
        /**
         * Original price during calculation（Total），Unit：Points
         */
        private Integer totalPrice;
        /**
         * Discount during calculation（Total），Unit：Points
         */
        private Integer discountPrice;

    }

}
