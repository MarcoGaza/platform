package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Order Notes Request VO")
@Data
public class TradeOrderRemarkReqVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Order number cannot be empty")
    private Long id;

    @Schema(description = "Merchant Notes", example = "Guess")
    @NotEmpty(message = "Order notes cannot be empty")
    private String remark;

}
