package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Streamlining of process tasks Response VO")
@Data
public class BpmTaskSimpleRespVO {

    @Schema(description = "Task definition identifier", requiredMode = Schema.RequiredMode.REQUIRED, example = "Activity_one")
    private String definitionKey;

    @Schema(description = "Task noun", requiredMode = Schema.RequiredMode.REQUIRED, example = "Manager Approval")
    private String name;

}
