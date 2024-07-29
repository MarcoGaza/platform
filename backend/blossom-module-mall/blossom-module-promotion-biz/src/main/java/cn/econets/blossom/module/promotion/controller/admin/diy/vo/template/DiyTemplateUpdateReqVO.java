package cn.econets.blossom.module.promotion.controller.admin.diy.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Decoration template update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiyTemplateUpdateReqVO extends DiyTemplateBaseVO {

    @Schema(description = "Decoration template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "31209")
    @NotNull(message = "The decoration template number cannot be empty")
    private Long id;

}
