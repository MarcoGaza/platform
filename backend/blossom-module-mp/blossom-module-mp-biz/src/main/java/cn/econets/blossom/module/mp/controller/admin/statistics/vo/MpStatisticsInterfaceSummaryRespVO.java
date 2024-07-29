package cn.econets.blossom.module.mp.controller.admin.statistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Interface analysis data for a certain day Response VO")
@Data
public class MpStatisticsInterfaceSummaryRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime refDate;

    @Schema(description = "After getting the message through the server configuration address，Number of passive replies to fan messages", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer callbackCount;

    @Schema(description = "Number of failures of the above action", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer failCount;

    @Schema(description = "Total time，divide by callback_count The average time consumed", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private Integer totalTimeCost;

    @Schema(description = "Maximum time", requiredMode = Schema.RequiredMode.REQUIRED, example = "40")
    private Integer maxTimeCost;

}
