package cn.econets.blossom.module.trade.controller.app.delivery.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// TODO To be implemented later，Delivery Configuration；Subsequent integration into AppTradeConfigRespVO Medium
@Schema(description = "User App - Delivery Configuration Response VO")
@Data
public class AppDeliveryConfigRespVO {

    @Schema(description = "Tencent Map KEY", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String tencentLbsKey;

    @Schema(description = "Whether to enable self-collection", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean pickUpEnable;

}
