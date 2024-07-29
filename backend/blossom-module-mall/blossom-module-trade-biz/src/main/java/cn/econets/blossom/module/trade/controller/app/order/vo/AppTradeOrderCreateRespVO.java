package cn.econets.blossom.module.trade.controller.app.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Trading order creation Response VO")
@Data
public class AppTradeOrderCreateRespVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long payOrderId;

}
