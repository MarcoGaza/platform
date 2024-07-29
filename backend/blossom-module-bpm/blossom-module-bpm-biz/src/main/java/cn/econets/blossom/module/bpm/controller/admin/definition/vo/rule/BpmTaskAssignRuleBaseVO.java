package cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Process task allocation rules Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BpmTaskAssignRuleBaseVO {

    @Schema(description = "Rule Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "bpm_task_assign_rule_type")
    @NotNull(message = "Rule type cannot be empty")
    private Integer type;

    @Schema(description = "Rule value array", requiredMode = Schema.RequiredMode.REQUIRED, example = "1,2,3")
    @NotNull(message = "Rule value array cannot be empty")
    private Set<Long> options;

}
