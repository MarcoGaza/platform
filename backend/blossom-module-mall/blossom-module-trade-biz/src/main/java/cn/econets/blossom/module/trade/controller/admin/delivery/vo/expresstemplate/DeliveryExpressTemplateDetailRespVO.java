package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Details of the express delivery fee template Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryExpressTemplateDetailRespVO extends DeliveryExpressTemplateBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "371")
    private Long id;

    @Schema(description = "Freight template freight settings", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<DeliveryExpressTemplateChargeBaseVO> charges;

    @Schema(description = "Free shipping area of ​​shipping template", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<DeliveryExpressTemplateFreeBaseVO> frees;

}
