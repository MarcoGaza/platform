package cn.econets.blossom.module.statistics.controller.admin.trade.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.statistics.enums.TimeRangeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Trading order volume trend statistics Request VO")
@Data
public class TradeOrderTrendReqVO {

    @Schema(description = "Date range type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Date range type cannot be empty")
    @InEnum(value = TimeRangeTypeEnum.class, message = "Date range type，Must be {value}")
    private Integer type;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Starting time")
    private LocalDateTime beginTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Deadline")
    private LocalDateTime endTime;

}
