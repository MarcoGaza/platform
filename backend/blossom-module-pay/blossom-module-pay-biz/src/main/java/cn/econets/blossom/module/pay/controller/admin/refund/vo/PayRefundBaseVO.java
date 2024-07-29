package cn.econets.blossom.module.pay.controller.admin.refund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
* Refund order Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class PayRefundBaseVO {

    @Schema(description = "External refund number", requiredMode = Schema.RequiredMode.REQUIRED, example = "110")
    private String no;

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long appId;

    @Schema(description = "Channel Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long channelId;

    @Schema(description = "Channel Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_app")
    private String channelCode;

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long orderId;

    // ========== Merchant related fields ==========

    @Schema(description = "Merchant order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "225")
    private String merchantOrderId;

    @Schema(description = "Merchant refund order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "512")
    private String merchantRefundId;

    @Schema(description = "Asynchronous notification address", requiredMode = Schema.RequiredMode.REQUIRED)
    private String notifyUrl;

    // ========== Refund related fields ==========

    @Schema(description = "Refund Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer status;

    @Schema(description = "Payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long payPrice;

    @Schema(description = "Refund amount,Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Long refundPrice;

    @Schema(description = "Refund reason", requiredMode = Schema.RequiredMode.REQUIRED, example = "I want to quit")
    private String reason;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String userIp;

    // ========== Channel related fields ==========

    @Schema(description = "Channel order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "233")
    private String channelOrderNo;

    @Schema(description = "Channel refund order number", example = "2022")
    private String channelRefundNo;

    @Schema(description = "Refund success time")
    private LocalDateTime successTime;

    @Schema(description = "Error code for calling the channel")
    private String channelErrorCode;

    @Schema(description = "Error message when calling the channel")
    private String channelErrorMsg;

    @Schema(description = "Additional parameters for payment channels")
    private String channelNotifyData;

}
