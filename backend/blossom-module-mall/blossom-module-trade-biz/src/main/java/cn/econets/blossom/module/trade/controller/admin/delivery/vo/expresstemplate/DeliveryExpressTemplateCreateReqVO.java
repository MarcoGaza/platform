package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

@Schema(description = "Management Backend - Express freight template creation Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryExpressTemplateCreateReqVO extends DeliveryExpressTemplateBaseVO {

    @Schema(description = "Regional freight list")
    @Valid
    private List<DeliveryExpressTemplateChargeBaseVO> charges;

    @Schema(description = "Free shipping area list")
    @Valid
    private List<DeliveryExpressTemplateFreeBaseVO> frees;

}
