package cn.econets.blossom.module.infrastructure.controller.admin.job.vo.log;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Scheduled task log paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JobLogPageReqVO extends PageParam {

    @Schema(description = "Task number", example = "10")
    private Long jobId;

    @Schema(description = "Processor name，Fuzzy matching")
    private String handlerName;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Start execution time")
    private LocalDateTime beginTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "End execution time")
    private LocalDateTime endTime;

    @Schema(description = "Task Status，See JobLogStatusEnum Enumeration")
    private Integer status;

}
