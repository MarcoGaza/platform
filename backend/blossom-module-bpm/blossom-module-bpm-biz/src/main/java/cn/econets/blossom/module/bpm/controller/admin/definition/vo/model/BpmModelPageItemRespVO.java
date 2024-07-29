package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Each item in the pagination of the process model Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModelPageItemRespVO extends BpmModelBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "Form name", example = "Leave form")
    private String formName;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    /**
     * The latest deployed process definition
     */
    private ProcessDefinition processDefinition;

    @Schema(description = "Process definition")
    @Data
    public static class ProcessDefinition {

        @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private String id;

        @Schema(description = "Version", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer version;

        @Schema(description = "Deployment time", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDateTime deploymentTime;

        @Schema(description = "Interrupt status-See SuspensionState Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer suspensionState;

    }

}
