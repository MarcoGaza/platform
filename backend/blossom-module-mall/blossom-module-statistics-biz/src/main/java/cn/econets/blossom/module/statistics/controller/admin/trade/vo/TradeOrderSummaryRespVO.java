package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Transaction Order Statistics Response VO")
@Data
public class TradeOrderSummaryRespVO {

    @Schema(description = "Number of items in paid order", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderPayCount;

    @Schema(description = "Total payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderPayPrice;

}
