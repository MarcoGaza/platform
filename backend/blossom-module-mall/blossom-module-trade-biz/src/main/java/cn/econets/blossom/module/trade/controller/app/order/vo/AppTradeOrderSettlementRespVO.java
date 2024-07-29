package cn.econets.blossom.module.trade.controller.app.order.vo;

import cn.econets.blossom.module.trade.controller.app.base.property.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "User App - Transaction order settlement information Response VO")
@Data
public class AppTradeOrderSettlementRespVO {

    @Schema(description = "Transaction Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1") // Corresponding TradeOrderTypeEnum Enumeration
    private Integer type;

    @Schema(description = "Shopping item array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Item> items;

    @Schema(description = "Cost", requiredMode = Schema.RequiredMode.REQUIRED)
    private Price price;

    @Schema(description = "Receiving address", requiredMode = Schema.RequiredMode.REQUIRED)
    private Address address;

    @Schema(description = "Points used", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer usedPoint;

    @Schema(description = "Total points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer totalPoint;

    @Schema(description = "Shopping Items")
    @Data
    public static class Item {

        // ========== SPU Information ==========

        @Schema(description = "Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
        private Long categoryId;
        @Schema(description = "SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
        private Long spuId;
        @Schema(description = "SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Apple iPhone 12")
        private String spuName;

        // ========== SKU Information ==========

        @Schema(description = "SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Integer skuId;
        @Schema(description = "Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer price;
        @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
        private String picUrl;

        @Schema(description = "Attribute array", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private List<AppProductPropertyValueDetailRespVO> properties;

        // ========== Shopping cart information ==========

        @Schema(description = "Shopping cart number", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Long cartId;

        @Schema(description = "Purchase quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer count;

    }

    @Schema(description = "Cost（Total）")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Price {

        @Schema(description = "Original price of product（Total），Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
        private Integer totalPrice;

        @Schema(description = "Order discount（Total），Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "66")
        private Integer discountPrice;

        @Schema(description = "Shipping amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
        private Integer deliveryPrice;

        @Schema(description = "Coupon reduction amount，Unit：points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer couponPrice;

        @Schema(description = "Amount of points deduction，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
        private Integer pointPrice;

        @Schema(description = "VIP Amount of reduction，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
        private Integer vipPrice;

        @Schema(description = "Actual payment amount（Total），Unit：points", requiredMode = Schema.RequiredMode.REQUIRED, example = "450")
        private Integer payPrice;

    }

    @Schema(description = "Address information")
    @Data
    public static class Address {

        @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;

        @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Xiao Wang")
        private String name;

        @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
        private String mobile;

        @Schema(description = "Region Code", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Region code cannot be empty")
        private Long areaId;
        @Schema(description = "Region name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Putuo District, Shanghai")
        private String areaName;

        @Schema(description = "Detailed address", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wangjing Youlehui A Seat")
        private String detailAddress;

        @Schema(description = "Whether to use the default recipient address", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        private Boolean defaultStatus;

    }

}
