package cn.econets.blossom.module.infrastructure.controller.admin.job.vo.log;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Scheduled task log Response VO")
@Data
@ExcelIgnoreUnannotated
public class JobLogRespVO {

    @Schema(description = "Log number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Log number")
    private Long id;

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Task number")
    private Long jobId;

    @Schema(description = "Processor name", requiredMode = Schema.RequiredMode.REQUIRED, example = "sysUserSessionTimeoutJob")
    @ExcelProperty("Processor name")
    private String handlerName;

    @Schema(description = "Processor parameters", example = "test")
    @ExcelProperty("Processor parameters")
    private String handlerParam;

    @Schema(description = "Number of executions", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("Number of executions")
    private Integer executeIndex;

    @Schema(description = "Start execution time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Start execution time")
    private LocalDateTime beginTime;

    @Schema(description = "End execution time")
    @ExcelProperty("End execution time")
    private LocalDateTime endTime;

    @Schema(description = "Execution duration", example = "123")
    @ExcelProperty("Execution duration")
    private Integer duration;

    @Schema(description = "Task Status，See JobLogStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Task Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.JOB_STATUS)
    private Integer status;

    @Schema(description = "Result data", example = "Execution successful")
    @ExcelProperty("Result data")
    private String result;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
