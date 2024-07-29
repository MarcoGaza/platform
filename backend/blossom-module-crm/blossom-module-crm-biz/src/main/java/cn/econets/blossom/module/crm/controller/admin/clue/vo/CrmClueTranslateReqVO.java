package cn.econets.blossom.module.crm.controller.admin.clue.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Schema(description = "Management Backend - Convert leads into customers Request VO")
@Data
public class CrmClueTranslateReqVO {

    @Schema(description = "Clue number", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1024, 1025]")
    @NotEmpty(message = "The clue number cannot be empty")
    private Set<Long> ids;

}
