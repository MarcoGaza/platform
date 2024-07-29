package cn.econets.blossom.module.pay.controller.admin.demo.vo.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Sample business transfer order Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class PayDemoTransferRespVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long appId;

    @Schema(description = "Transfer amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "22338")
    private Integer price;

    @Schema(description = "Transfer Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer type;

    @Schema(description = "Payee's name",  example = "test")
    private String userName;

    @Schema(description = "Alipay login number", example = "32167")
    private String alipayLogonId;

    @Schema(description = "WeChat openId", example = "31139")
    private String openid;

    @Schema(description = "Transfer Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer transferStatus;

    @Schema(description = "Transfer order number", example = "23695")
    private Long payTransferId;

    @Schema(description = "Transfer payment success channel")
    private String payChannelCode;

    @Schema(description = "Transfer payment time")
    private LocalDateTime transferTime;
}
