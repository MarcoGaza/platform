package cn.econets.blossom.module.bpm.controller.admin.task.vo.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Cancellation of process instance Request VO")
@Data
public class BpmProcessInstanceCancelReqVO {

    @Schema(description = "Process instance number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The process instance number cannot be empty")
    private String id;

    @Schema(description = "Reason for cancellation", requiredMode = Schema.RequiredMode.REQUIRED, example = "No more leave！")
    @NotEmpty(message = "Cancellation reason cannot be empty")
    private String reason;

}
