package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Rewind process task Request VO")
@Data
public class BpmTaskReturnReqVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "The task to which you are returning Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "The task to which you rolled back Key Cannot be empty")
    private String targetDefinitionKey;

    @Schema(description = "Return comments", requiredMode = Schema.RequiredMode.REQUIRED, example = "I just want to reject it")
    @NotEmpty(message = "Return comments cannot be empty")
    private String reason;

}
