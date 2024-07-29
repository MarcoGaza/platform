package cn.econets.blossom.module.promotion.controller.admin.diy.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Decoration page property update Request VO")
@Data
@ToString(callSuper = true)
public class DiyPagePropertyUpdateRequestVO {

    @Schema(description = "Decoration page number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    @NotNull(message = "The decoration page number cannot be empty")
    private Long id;

    @Schema(description = "Page properties，JSON Format", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    @NotBlank(message = "Page attributes cannot be empty")
    private String property;

}
