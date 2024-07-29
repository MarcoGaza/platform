package cn.econets.blossom.module.bpm.controller.admin.definition.vo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Dynamic form update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormUpdateReqVO extends BpmFormBaseVO {

    @Schema(description = "Form number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Form number cannot be empty")
    private Long id;

    @Schema(description = "Form configuration-JSON String", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The form configuration cannot be empty")
    private String conf;

    @Schema(description = "Array of form items-JSON Array of strings", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The array of form items cannot be empty")
    private List<String> fields;

}
