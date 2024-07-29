package cn.econets.blossom.module.promotion.controller.app.diy.vo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "User App - Decoration page properties Response VO")
@Data
@ToString(callSuper = true)
public class AppDiyPagePropertyRespVO {

    @Schema(description = "Decoration page number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    private Long id;

    @Schema(description = "Page Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    private String name;

    @Schema(description = "Page properties", example = "[]")
    @JsonRawValue
    private String property;

}
