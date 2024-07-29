package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Group buying activity Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class CombinationActivityBaseVO {

    @Schema(description = "Group name", requiredMode = Schema.RequiredMode.REQUIRED, example = "The more you work, the more money you save")
    @NotNull(message = "The group name cannot be empty")
    private String name;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Group buy products cannot be empty")
    private Long spuId;

    @Schema(description = "Total purchase limit quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "16218")
    @NotNull(message = "The total purchase limit quantity cannot be empty")
    private Integer totalLimitCount;

    @Schema(description = "Single purchase limit quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "28265")
    @NotNull(message = "The single purchase limit quantity cannot be empty")
    private Integer singleLimitCount;

    @Schema(description = "Activity time", requiredMode = Schema.RequiredMode.REQUIRED, example = "[2022-07-01 23:59:59]")
    @NotNull(message = "Activity time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "Activity time", requiredMode = Schema.RequiredMode.REQUIRED, example = "[2022-07-01 23:59:59]")
    @NotNull(message = "Activity time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Number of people in the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "25222")
    @NotNull(message = "The number of people in a group cannot be empty")
    private Integer userSize;

    @Schema(description = "Virtual group formation", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "The virtual group cannot be empty")
    private Boolean virtualGroup;

    @Schema(description = "Limit duration（Hours）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "The limit time cannot be empty")
    private Integer limitDuration;

}
