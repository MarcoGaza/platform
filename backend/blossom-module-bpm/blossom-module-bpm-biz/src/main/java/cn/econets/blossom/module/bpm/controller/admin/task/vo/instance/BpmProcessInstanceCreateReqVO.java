package cn.econets.blossom.module.bpm.controller.admin.task.vo.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Schema(description = "Management Backend - Creation of process instance Request VO")
@Data
public class BpmProcessInstanceCreateReqVO {

    @Schema(description = "Process definition number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The process definition number cannot be empty")
    private String processDefinitionId;

    @Schema(description = "Variable instance")
    private Map<String, Object> variables;

    // TODO assignees plural
    @Schema(description = "Pre-assigned approver", requiredMode = Schema.RequiredMode.REQUIRED, example = "{taskKey1: [1, 2]}")
    private Map<String, List<Long>> assignee;

}
