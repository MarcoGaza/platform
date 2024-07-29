package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Import of process model Request VO Compared to the creation of a new process model，Just one more bpmnFile File")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmModeImportReqVO extends BpmModelCreateReqVO {

    @Schema(description = "BPMN File", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "BPMN The file cannot be empty")
    private MultipartFile bpmnFile;

}
