package cn.econets.blossom.module.mp.controller.admin.statistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Get statistics Request VO")
@Data
public class MpStatisticsGetReqVO {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "Query time range", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotNull(message = "The query time range cannot be empty")
    private LocalDateTime[] date;

}
