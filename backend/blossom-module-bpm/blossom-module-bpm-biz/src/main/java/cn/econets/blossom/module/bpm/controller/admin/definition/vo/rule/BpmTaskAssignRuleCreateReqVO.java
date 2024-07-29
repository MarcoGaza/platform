package cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Creation of process task allocation rules Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskAssignRuleCreateReqVO extends BpmTaskAssignRuleBaseVO {

    @Schema(description = "Process model number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The process model number cannot be empty")
    private String modelId;

    @Schema(description = "Number of process task definition", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotEmpty(message = "The process task definition number cannot be empty")
    private String taskDefinitionKey;

}
