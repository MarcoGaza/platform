package cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Scheduled tasks Response VO")
@Data
@ExcelIgnoreUnannotated
public class JobRespVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("Task number")
    private Long id;

    @Schema(description = "Task Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test task")
    @ExcelProperty("Task Name")
    private String name;

    @Schema(description = "Task Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "Task Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.JOB_STATUS)
    private Integer status;

    @Schema(description = "Processor name", requiredMode = Schema.RequiredMode.REQUIRED, example = "sysUserSessionTimeoutJob")
    @ExcelProperty("Processor name")
    private String handlerName;

    @Schema(description = "Processor parameters", example = "test")
    @ExcelProperty("Processor parameters")
    private String handlerParam;

    @Schema(description = "CRON Expression", requiredMode = Schema.RequiredMode.REQUIRED, example = "0/10 * * * * ? *")
    @ExcelProperty("CRON Expression")
    private String cronExpression;

    @Schema(description = "Number of retries", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "The number of retries cannot be empty")
    private Integer retryCount;

    @Schema(description = "Retry interval", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer retryInterval;

    @Schema(description = "Monitoring timeout", example = "1000")
    @ExcelProperty("Monitoring timeout")
    private Integer monitorTimeout;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
