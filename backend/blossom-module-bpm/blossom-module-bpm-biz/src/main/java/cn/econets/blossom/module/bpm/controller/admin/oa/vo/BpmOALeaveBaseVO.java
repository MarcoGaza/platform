package cn.econets.blossom.module.bpm.controller.admin.oa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* Leave application Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class BpmOALeaveBaseVO {

    @Schema(description = "Leave start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Start time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;
    @Schema(description = "End time of leave", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "End time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Leave Type-See also bpm_oa_type Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Reason", requiredMode = Schema.RequiredMode.REQUIRED, example = "Read source code")
    private String reason;

}
