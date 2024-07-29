package cn.econets.blossom.module.trade.controller.app.order.vo.item;

import cn.econets.blossom.module.trade.controller.app.base.property.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Order Transaction Item Response VO")
@Data
public class AppTradeOrderItemRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long orderId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long spuId;
    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    private String spuName;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long skuId;

    /**
     * Attribute array
     */
    private List<AppProductPropertyValueDetailRespVO> properties;

    @Schema(description = "Product images", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

    @Schema(description = "Purchase quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer count;

    @Schema(description = "Do you want to comment?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean commentStatus;

    // ========== Price + Basic payment information ==========

    @Schema(description = "Original price of product（Single）", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "Amount payable（Total），Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "50")
    private Integer payPrice;

    // ========== Basic marketing information ==========

    // TODO Finding it out

    // ========== Basic after-sales information ==========

    @Schema(description = "After-sales number", example = "1024")
    private Long afterSaleId;

    @Schema(description = "After-sales status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer afterSaleStatus;

}
