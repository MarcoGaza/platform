package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Not passing the process task Request VO")
@Data
public class BpmTaskRejectReqVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "Approval opinion", requiredMode = Schema.RequiredMode.REQUIRED, example = "Not bad！")
    @NotEmpty(message = "Approval comments cannot be empty")
    private String reason;

}
