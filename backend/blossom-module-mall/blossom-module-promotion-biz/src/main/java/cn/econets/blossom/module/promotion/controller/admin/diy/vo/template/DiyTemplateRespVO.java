package cn.econets.blossom.module.promotion.controller.admin.diy.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Decoration template Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiyTemplateRespVO extends DiyTemplateBaseVO {

    @Schema(description = "Decoration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Whether to use", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean used;

    @Schema(description = "Usage time", example = "Usage time")
    private LocalDateTime usedTime;

}
