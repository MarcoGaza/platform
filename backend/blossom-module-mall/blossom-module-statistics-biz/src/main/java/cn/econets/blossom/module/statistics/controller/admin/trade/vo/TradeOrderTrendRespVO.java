package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Order volume trend statistics Response VO")
@Data
public class TradeOrderTrendRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String date;

    @Schema(description = "Order Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderPayCount;

    @Schema(description = "Order payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer orderPayPrice;

}
