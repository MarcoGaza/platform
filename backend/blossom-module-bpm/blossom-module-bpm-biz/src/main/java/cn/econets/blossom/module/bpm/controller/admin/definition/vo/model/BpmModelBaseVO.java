package cn.econets.blossom.module.bpm.controller.admin.definition.vo.model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
* Process Model Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class BpmModelBaseVO {

    @Schema(description = "Process ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "process_blossom")
    @NotEmpty(message = "Process ID cannot be empty")
    private String key;

    @Schema(description = "Process name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    @NotEmpty(message = "Process name cannot be empty")
    private String name;

    @Schema(description = "Process description", example = "I am describing")
    private String description;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", example = "1")
    @NotEmpty(message = "Process category cannot be empty")
    private String category;

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
