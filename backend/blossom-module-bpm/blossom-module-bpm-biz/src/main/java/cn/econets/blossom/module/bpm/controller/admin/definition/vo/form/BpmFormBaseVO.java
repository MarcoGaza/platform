package cn.econets.blossom.module.bpm.controller.admin.definition.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* Dynamic Form Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class BpmFormBaseVO {

    @Schema(description = "Form Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    @NotNull(message = "The form name cannot be empty")
    private String name;

    @Schema(description = "Form Status-See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The form status cannot be empty")
    private Integer status;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

}
