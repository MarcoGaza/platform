package cn.econets.blossom.module.mp.controller.admin.statistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Fans increase and decrease data for a certain day Response VO")
@Data
public class MpStatisticsUpstreamMessageRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime refDate;

    @Schema(description = "Uplink sent（Sent to the public account）Number of fans of the message", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer messageUser;

    @Schema(description = "Total number of messages sent upstream", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer messageCount;

}
