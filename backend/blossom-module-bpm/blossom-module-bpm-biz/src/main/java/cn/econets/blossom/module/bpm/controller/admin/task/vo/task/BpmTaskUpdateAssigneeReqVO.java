package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - The person in charge of updating the process task Request VO")
@Data
public class BpmTaskUpdateAssigneeReqVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "User ID of the new approver", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "The user ID of the new approver cannot be empty")
    private Long assigneeUserId;

}
