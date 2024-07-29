package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Trading order quantity Response VO")
@Data
public class TradeOrderCountRespVO {

    @Schema(description = "Waiting for delivery", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long undelivered;

    @Schema(description = "To be cancelled", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long pickUp;

    @Schema(description = "Refund in progress", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long afterSaleApply;

    @Schema(description = "Withdrawal pending review", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long auditingWithdraw;

}
