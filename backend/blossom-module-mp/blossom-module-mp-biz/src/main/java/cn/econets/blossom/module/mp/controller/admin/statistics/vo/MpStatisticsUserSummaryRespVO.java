package cn.econets.blossom.module.mp.controller.admin.statistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Fans increase and decrease data for a certain day Response VO")
@Data
public class MpStatisticsUserSummaryRespVO {

    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime refDate;

    @Schema(description = "Source of fans", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer userSource;

    @Schema(description = "Number of new followers", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer newUser;

    @Schema(description = "Number of fans who unfollowed", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer cancelUser;

}
