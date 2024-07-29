package cn.econets.blossom.module.mp.controller.admin.statistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Message sending overview data for a certain day Response VO")
@Data
public class MpStatisticsUserCumulateRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime refDate;

    @Schema(description = "Total fans", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer cumulateUser;

}
