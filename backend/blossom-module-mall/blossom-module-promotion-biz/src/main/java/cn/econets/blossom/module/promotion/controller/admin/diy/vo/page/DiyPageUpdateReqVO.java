package cn.econets.blossom.module.promotion.controller.admin.diy.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Decoration page update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DiyPageUpdateReqVO extends DiyPageBaseVO {

    @Schema(description = "Decoration page number", requiredMode = Schema.RequiredMode.REQUIRED, example = "12082")
    @NotNull(message = "The decoration page number cannot be empty")
    private Long id;

}
