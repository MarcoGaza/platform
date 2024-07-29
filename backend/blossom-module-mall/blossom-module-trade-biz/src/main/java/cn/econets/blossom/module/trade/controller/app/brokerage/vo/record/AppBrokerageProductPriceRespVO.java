package cn.econets.blossom.module.trade.controller.app.brokerage.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Product distribution amount Response VO")
@Data
public class AppBrokerageProductPriceRespVO {

    @Schema(description = "Is it enabled?", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Boolean enabled;

    @Schema(description = "Minimum amount of distribution，Unit：Points", example = "100")
    private Integer brokerageMinPrice;

    @Schema(description = "Maximum amount of distribution，Unit：Points", example = "100")
    private Integer brokerageMaxPrice;

}
