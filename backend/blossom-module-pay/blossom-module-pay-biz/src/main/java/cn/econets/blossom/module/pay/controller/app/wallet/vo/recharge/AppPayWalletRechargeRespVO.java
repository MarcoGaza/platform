package cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User APP - Wallet recharge record Resp VO")
@Data
public class AppPayWalletRechargeRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Actual balance of the user", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer totalPrice;

    @Schema(description = "Actual payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer payPrice;

    @Schema(description = "Wallet gift amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "80")
    private Integer bonusPrice;

    @Schema(description = "Payment channel for successful payment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payChannelCode;

    @Schema(description = "Payment channel name", example = "WeChat Mini Program Payment")
    private String payChannelName;

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long payOrderId;

    @Schema(description = "External order number of successful payment", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payOrderChannelOrderNo; // From PayOrderDO of channelOrderNo Field

    @Schema(description = "Order payment time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime payTime;

    @Schema(description = "Refund Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer refundStatus;

}
