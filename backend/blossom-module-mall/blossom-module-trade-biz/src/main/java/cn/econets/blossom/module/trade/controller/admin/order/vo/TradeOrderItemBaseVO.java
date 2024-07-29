package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Transaction order item Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class TradeOrderItemBaseVO {

    // ========== Basic information of order items ==========

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long userId;

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long orderId;

    // ========== Basic information of the product ==========

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long spuId;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    private String spuName;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long skuId;

    @Schema(description = "Product image", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

    @Schema(description = "Purchase quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer count;

    // ========== Price + Basic payment information ==========

    @Schema(description = "Original price of product（Single）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "Product Discounts（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer discountPrice;

    @Schema(description = "Amount paid for the product（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer payPrice;

    @Schema(description = "Sub-order allocation amount（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer orderPartPrice;

    @Schema(description = "Actual payment amount of sub-orders after allocation（Total）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer orderDividePrice;

    // ========== Basic marketing information ==========

    // TODO Finding it out

    // ========== Basic after-sales information ==========

    @Schema(description = "After-sales status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer afterSaleStatus;

}
