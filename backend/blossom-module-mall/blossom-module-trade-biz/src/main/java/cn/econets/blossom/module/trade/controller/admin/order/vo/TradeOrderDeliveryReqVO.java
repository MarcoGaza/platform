package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Order delivery Request VO")
@Data
public class TradeOrderDeliveryReqVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Order number cannot be empty")
    private Long id;

    @Schema(description = "Shipping logistics company number", example = "1")
    @NotNull(message = "Shipping logistics company cannot be empty")
    private Long logisticsId;

    @Schema(description = "Shipping logistics order number", example = "SF123456789")
    private String logisticsNo;

}
