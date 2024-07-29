package cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Update of process task allocation rules Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskAssignRuleUpdateReqVO extends BpmTaskAssignRuleBaseVO {

    @Schema(description = "The number of the task allocation rule", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The number of the task assignment rule cannot be empty")
    private Long id;

}
