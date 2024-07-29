package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Express delivery fee template update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryExpressTemplateUpdateReqVO extends DeliveryExpressTemplateBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "371")
    @NotNull(message = "The number cannot be empty")
    private Long id;

    @Schema(description = "Regional freight list")
    @Valid
    private List<DeliveryExpressTemplateChargeBaseVO> charges;

    @Schema(description = "Free shipping area list")
    @Valid
    private List<DeliveryExpressTemplateFreeBaseVO> frees;

}
