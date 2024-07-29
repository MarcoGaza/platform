package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Transaction Statistics Response VO")
@Data
public class TradeSummaryRespVO {

    @Schema(description = "Number of orders yesterday", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer yesterdayOrderCount;
    @Schema(description = "Yesterday's payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer yesterdayPayPrice;

    @Schema(description = "Number of orders this month", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer monthOrderCount;
    @Schema(description = "This month's payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer monthPayPrice;

}
