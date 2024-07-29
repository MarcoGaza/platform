package cn.econets.blossom.module.trade.controller.app.aftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Return goods after transaction Request VO")
@Data
public class AppAfterSaleDeliveryReqVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "After-sales number cannot be empty")
    private Long id;

    @Schema(description = "Return Logistics Company Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The return logistics company number cannot be empty")
    private Long logisticsId;

    @Schema(description = "Return logistics order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "SF123456789")
    @NotNull(message = "Return logistics order number cannot be empty")
    private String logisticsNo;

}
