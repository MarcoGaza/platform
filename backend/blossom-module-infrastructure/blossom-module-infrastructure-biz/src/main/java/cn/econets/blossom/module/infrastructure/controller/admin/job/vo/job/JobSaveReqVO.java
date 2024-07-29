package cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Creating scheduled tasks/Modify Request VO")
@Data
public class JobSaveReqVO {

    @Schema(description = "Task number", example = "1024")
    private Long id;

    @Schema(description = "Task Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test task")
    @NotEmpty(message = "Task name cannot be empty")
    private String name;

    @Schema(description = "Processor name", requiredMode = Schema.RequiredMode.REQUIRED, example = "sysUserSessionTimeoutJob")
    @NotEmpty(message = "The processor name cannot be empty")
    private String handlerName;

    @Schema(description = "Processor parameters", example = "test")
    private String handlerParam;

    @Schema(description = "CRON Expression", requiredMode = Schema.RequiredMode.REQUIRED, example = "0/10 * * * * ? *")
    @NotEmpty(message = "CRON The expression cannot be empty")
    private String cronExpression;

    @Schema(description = "Number of retries", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "The number of retries cannot be empty")
    private Integer retryCount;

    @Schema(description = "Retry interval", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "Retry interval cannot be empty")
    private Integer retryInterval;

    @Schema(description = "Monitoring timeout", example = "1000")
    private Integer monitorTimeout;

}
