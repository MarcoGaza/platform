package cn.econets.blossom.module.promotion.controller.app.bargain.vo.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Creation of bargaining assistance Request VO")
@Data
public class AppBargainHelpCreateReqVO {

    @Schema(description = "Bargaining record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The bargaining record number cannot be empty")
    private Long recordId;

}
