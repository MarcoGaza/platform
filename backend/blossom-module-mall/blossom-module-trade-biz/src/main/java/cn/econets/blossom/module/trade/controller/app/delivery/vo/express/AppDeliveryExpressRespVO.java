package cn.econets.blossom.module.trade.controller.app.delivery.vo.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Express Delivery Company Response VO")
@Data
public class AppDeliveryExpressRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Store Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "SF Express")
    private String name;

}
