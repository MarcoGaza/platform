package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "Management Backend - Template simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryExpressTemplateSimpleRespVO {

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Test template")
    private String name;

}
