package cn.econets.blossom.module.trade.controller.app.cart.vo;

import cn.econets.blossom.module.trade.controller.app.base.sku.AppProductSkuBaseRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - User's shopping cart details Response VO")
@Data
public class AppCartDetailRespVO {

    /**
     * Product grouping array
     */
    private List<ItemGroup> itemGroups;

    /**
     * Cost
     */
    private Order order;

    @Schema(description = "Product Grouping") // Multiple products，Participate in the same event，Thus forming a group
    @Data
    public static class ItemGroup {

        /**
         * Product array
         */
        private List<Sku> items;
        /**
         * Marketing Activities，Order Level
         */
        private Promotion promotion;

    }

    @Schema(description = "Products SKU")
    @Data
    public static class Sku extends AppProductSkuBaseRespVO {

        /**
         * SPU Information
         */
        private AppProductSkuBaseRespVO spu;

        // ========== Shopping cart related fields ==========

        @Schema(description = "Quantity of goods", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer count;
        @Schema(description = "Is it selected?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        private Boolean selected;

        // ========== Price related fields，Corresponding PriceCalculateRespDTO.OrderItem Properties ==========

        // TODO Some useless fields can be removed later

        @Schema(description = "Original price of product（Single）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer originalPrice;
        @Schema(description = "Original price of product（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
        private Integer totalOriginalPrice;
        @Schema(description = "Product-level discount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "300")
        private Integer totalPromotionPrice;
        @Schema(description = "Final purchase amount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "400")
        private Integer totalPresentPrice;
        @Schema(description = "Final purchase amount（Single）", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
        private Integer presentPrice;
        @Schema(description = "Amount payable（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "600")
        private Integer totalPayPrice;

        // ========== Marketing related fields ==========
        /**
         * Marketing Activities，Product Level
         */
        private Promotion promotion;

    }

    @Schema(description = "Order") // Corresponding PriceCalculateRespDTO.Order Class，For expenses（Total）
    @Data
    public static class Order {

        // TODO Some useless fields can be removed later

        @Schema(description = "Original price of product（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer skuOriginalPrice;
        @Schema(description = "Product discounts（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
        private Integer skuPromotionPrice;
        @Schema(description = "Order discount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "300")
        private Integer orderPromotionPrice;
        @Schema(description = "Shipping amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "400")
        private Integer deliveryPrice;
        @Schema(description = "Amount payable（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
        private Integer payPrice;

    }

    @Schema(description = "Marketing Activities") // Corresponding PriceCalculateRespDTO.Promotion Class properties
    @Data
    public static class Promotion {

        @Schema(description = "Marketing Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024") // Marketing campaign number、Coupon number
        private Long id;
        @Schema(description = "Marketing name", requiredMode = Schema.RequiredMode.REQUIRED, example = "xx Activities")
        private String name;
        @Schema(description = "Marketing Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer type;

        // ========== Matching situation ==========
        @Schema(description = "Whether the preferential conditions are met", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        private Boolean meet;
        @Schema(description = "Prompts that meet the conditions", requiredMode = Schema.RequiredMode.REQUIRED, example = "Christmas Price:Province 150.00 Yuan")
        private String meetTip;

    }

}
