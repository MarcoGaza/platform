package cn.econets.blossom.module.pay.controller.admin.transfer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - Transfer sheet paging items Response VO")
@Data
public class PayTransferPageItemRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2931")
    private Long id;

    @Schema(description = "Transfer Order Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private String no;

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "12831")
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

    @Schema(description = "Channel transfer order number")
    private String channelTransferNo;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;
}
