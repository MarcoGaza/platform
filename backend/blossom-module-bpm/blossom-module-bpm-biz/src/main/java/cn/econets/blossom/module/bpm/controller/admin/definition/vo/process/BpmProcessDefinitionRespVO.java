package cn.econets.blossom.module.bpm.controller.admin.definition.vo.process;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Management Backend - Process definition Response VO")
@Data
public class BpmProcessDefinitionRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "Version", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer version;

    @Schema(description = "Process Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    @NotEmpty(message = "Process name cannot be empty")
    private String name;

    @Schema(description = "Process description", example = "I am describing")
    private String description;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", example = "1")
    @NotEmpty(message = "Process category cannot be empty")
    private String category;

    @Schema(description = "Form type-See also bpm_model_form_type Data dictionary", example = "1")
    private Integer formType;
    @Schema(description = "Form number-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", example = "1024")
    private Long formId;
    @Schema(description = "Form configuration-JSON String。In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", requiredMode = Schema.RequiredMode.REQUIRED)
    private String formConf;
    @Schema(description = "Array of form items-JSON Array of strings。In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> formFields;
    @Schema(description = "Customize the submission path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
            example = "/bpm/oa/leave/create")
    private String formCustomCreatePath;
    @Schema(description = "Customize the viewing path of the form，Use Vue The routing address-In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time，Must not be empty",
            example = "/bpm/oa/leave/view")
    private String formCustomViewPath;

    @Schema(description = "Interrupt status-See SuspensionState Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer suspensionState;

}
