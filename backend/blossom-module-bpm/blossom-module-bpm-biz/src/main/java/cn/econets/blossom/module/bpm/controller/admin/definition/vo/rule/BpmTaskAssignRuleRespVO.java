package cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Process task allocation rules Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskAssignRuleRespVO extends BpmTaskAssignRuleBaseVO {

    @Schema(description = "The number of the task allocation rule", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Process model number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private String modelId;

    @Schema(description = "Process definition number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4096")
    private String processDefinitionId;

    @Schema(description = "Number of process task definition", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private String taskDefinitionKey;
    @Schema(description = "The name of the process task definition", requiredMode = Schema.RequiredMode.REQUIRED, example = "Follow Yudao")
    private String taskDefinitionName;

}
