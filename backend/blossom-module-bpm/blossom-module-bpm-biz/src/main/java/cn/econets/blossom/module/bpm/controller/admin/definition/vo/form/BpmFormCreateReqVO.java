package cn.econets.blossom.module.bpm.controller.admin.definition.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Dynamic form creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormCreateReqVO extends BpmFormBaseVO {

    @Schema(description = "Form configuration-JSON String", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The form configuration cannot be empty")
    private String conf;

    @Schema(description = "Array of form items-JSON Array of strings", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The array of form items cannot be empty")
    private List<String> fields;

}
