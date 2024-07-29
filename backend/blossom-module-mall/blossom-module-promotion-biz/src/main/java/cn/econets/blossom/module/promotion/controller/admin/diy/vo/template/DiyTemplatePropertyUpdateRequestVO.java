package cn.econets.blossom.module.promotion.controller.admin.diy.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Decoration template property update Request VO")
@Data
@ToString(callSuper = true)
public class DiyTemplatePropertyUpdateRequestVO {

    @Schema(description = "Decoration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    @NotNull(message = "The decoration template number cannot be empty")
    private Long id;

    @Schema(description = "Template properties，JSON Format", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    @NotBlank(message = "Template attributes cannot be empty")
    private String property;

}
