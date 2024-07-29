package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Order price change Request VO")
@Data
public class TradeOrderUpdatePriceReqVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Order number cannot be empty")
    private Long id;

    @Schema(description = "Order price adjustment，Unit：Points。Positive number，Add price；Negative number，Price reduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "-100")
    @NotNull(message = "The order price cannot be empty")
    private Integer adjustPrice;

}
