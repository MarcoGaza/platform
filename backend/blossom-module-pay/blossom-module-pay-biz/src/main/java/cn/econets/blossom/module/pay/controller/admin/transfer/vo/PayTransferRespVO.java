package cn.econets.blossom.module.pay.controller.admin.transfer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Management Backend - Transfer slip Response VO")
@Data
public class PayTransferRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2931")
    private Long id;

    @Schema(description = "Transfer Order Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String no;

    @Schema(description = "Application number", requiredMode = Schema.RequiredMode.REQUIRED, example = "12831")
    private Long appId;

    @Schema(description = "Transfer channel number", requiredMode = Schema.RequiredMode.REQUIRED, example = "24833")
    private Long channelId;

    @Schema(description = "Transfer channel code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String channelCode;

    @Schema(description = "Merchant transfer order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "17481")
    private String merchantTransferId;

    @Schema(description = "Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer type;

    @Schema(description = "Transfer Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer status;

    @Schema(description = "Time of successful transfer")
    private LocalDateTime successTime;

    @Schema(description = "Transfer amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "964")
    private Integer price;

    @Schema(description = "Transfer Title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @Schema(description = "Recipient's name", example = "Wang Wu")
    private String userName;

    @Schema(description = "Alipay login number", example = "29245")
    private String alipayLogonId;

    @Schema(description = "WeChat openId", example = "26589")
    private String openid;

    @Schema(description = "Asynchronous notification of merchant address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    private String notifyUrl;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userIp;

    @Schema(description = "Additional parameters for channels")
    private Map<String, String> channelExtras;

    @Schema(description = "Channel transfer order number")
    private String channelTransferNo;

    @Schema(description = "Error code for calling the channel")
    private String channelErrorCode;

    @Schema(description = "Error message when calling the channel")
    private String channelErrorMsg;

    @Schema(description = "Channel synchronization/Content of asynchronous notification")
    private String channelNotifyData;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
