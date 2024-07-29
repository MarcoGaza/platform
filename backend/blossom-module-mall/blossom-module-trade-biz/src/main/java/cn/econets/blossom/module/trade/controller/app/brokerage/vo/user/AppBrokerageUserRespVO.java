package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Distribution user information Response VO")
@Data
public class AppBrokerageUserRespVO {

    @Schema(description = "Is it eligible for distribution?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean brokerageEnabled;

    @Schema(description = "Available commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "2408")
    private Integer brokeragePrice;

    @Schema(description = "Frozen commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "234")
    private Integer frozenPrice;

}
