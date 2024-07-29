package cn.econets.blossom.module.promotion.controller.app.diy.vo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "User App - Decoration template properties Response VO")
@Data
@ToString(callSuper = true)
public class AppDiyTemplatePropertyRespVO {

    @Schema(description = "Decoration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Default theme")
    private String name;

    @Schema(description = "Template properties", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    @JsonRawValue
    private String property;

    @Schema(description = "Home", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    @JsonRawValue
    private String home;

    @Schema(description = "My", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    @JsonRawValue
    private String user;

}
