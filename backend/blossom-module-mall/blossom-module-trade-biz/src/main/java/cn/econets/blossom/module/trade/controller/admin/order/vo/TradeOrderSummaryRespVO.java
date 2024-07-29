package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Transaction Order Statistics Response VO")
@Data
public class TradeOrderSummaryRespVO {

    @Schema(description = "Order Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long orderCount;

    @Schema(description = "Order amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long orderPayPrice;

    @Schema(description = "Number of refunds", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long afterSaleCount;

    @Schema(description = "Refund amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long afterSalePrice;

}
