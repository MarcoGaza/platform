package cn.econets.blossom.module.pay.controller.admin.demo.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
* Sample Order Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class PayDemoOrderRespVO {

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23199")
    private Long userId;

    @Schema(description = "Product Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "17682")
    private Long spuId;

    @Schema(description = "Merchant Notes", example = "Li Si")
    private String spuName;

    @Schema(description = "Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "30381")
    private Integer price;

    @Schema(description = "Has it been paid?", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean payStatus;

    @Schema(description = "Payment order number", example = "16863")
    private Long payOrderId;

    @Schema(description = "Order payment time")
    private LocalDateTime payTime;

    @Schema(description = "Payment channel", example = "alipay_qr")
    private String payChannelCode;

    @Schema(description = "Payment refund number", example = "23366")
    private Long payRefundId;

    @Schema(description = "Refund amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "14039")
    private Integer refundPrice;

    @Schema(description = "Refund Time")
    private LocalDateTime refundTime;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
