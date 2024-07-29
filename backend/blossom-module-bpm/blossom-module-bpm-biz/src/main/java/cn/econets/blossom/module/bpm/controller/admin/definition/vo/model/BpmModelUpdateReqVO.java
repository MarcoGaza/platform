package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "Management Backend - Update of process model Request VO")
@Data
public class BpmModelUpdateReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "The number cannot be empty")
    private String id;

    @Schema(description = "Process Name", example = "Taro Road")
    private String name;

    @Schema(description = "Process description", example = "I am describing")
    private String description;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", example = "1")
    private String category;

    @Schema(description = "BPMN XML", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bpmnXml;

    @Schema(description = "Form type-See bpm_model_form_type Data dictionary", example = "1")
    private Integer formType;
    @Schema(description = "Form number-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", example = "1024")
    private Long formId;
    @Schema(description = "Customize the submission path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
            example = "/bpm/oa/leave/create")
    private String formCustomCreatePath;
    @Schema(description = "Customize the viewing path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
            example = "/bpm/oa/leave/view")
    private String formCustomViewPath;

}
