package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Visa officer at the flash sale activity base
 * Second-sale event Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class SeckillActivityBaseVO {

    @Schema(description = "Second-sale products id", requiredMode = Schema.RequiredMode.REQUIRED, example = "[121,1212]")
    @NotNull(message = "Second sale items cannot be empty")
    private Long spuId;

    @Schema(description = "Name of flash sale event", requiredMode = Schema.RequiredMode.REQUIRED, example = "618Big Sale")
    @NotNull(message = "The name of the flash sale event cannot be empty")
    private String name;

    @Schema(description = "Remarks", example = "Clearance sale, cutting leeks")
    private String remark;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The activity start time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The activity end time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

    @Schema(description = "Second sale period id", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,2,3]")
    @NotNull(message = "Second sale period cannot be empty")
    private List<Long> configIds;

    @Schema(description = "Total purchase limit quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "12877")
    private Integer totalLimitCount;

    @Schema(description = "Limited quantity per time", requiredMode = Schema.RequiredMode.REQUIRED, example = "31683")
    private Integer singleLimitCount;

}
