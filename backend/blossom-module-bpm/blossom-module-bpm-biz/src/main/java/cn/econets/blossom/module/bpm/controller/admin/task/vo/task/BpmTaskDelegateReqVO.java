package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Delegating process tasks Request VO")
@Data
public class BpmTaskDelegateReqVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "Delegated person ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Delegated person ID Cannot be empty")
    private Long delegateUserId;

    @Schema(description = "Reason for delegation", requiredMode = Schema.RequiredMode.REQUIRED, example = "Can't make a decision，I need your help first")
    @NotEmpty(message = "The delegation reason cannot be empty")
    private String reason;

}
