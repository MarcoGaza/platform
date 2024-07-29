package cn.econets.blossom.module.bpm.controller.admin.definition.vo.process;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Each item in the paging defined by the process Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessDefinitionPageItemRespVO extends BpmProcessDefinitionRespVO {

    @Schema(description = "Form name", example = "Leave form")
    private String formName;

    @Schema(description = "Deployment time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime deploymentTime;

}
