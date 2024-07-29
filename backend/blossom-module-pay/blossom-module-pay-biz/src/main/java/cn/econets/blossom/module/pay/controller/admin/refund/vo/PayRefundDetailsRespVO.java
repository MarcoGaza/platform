package cn.econets.blossom.module.pay.controller.admin.refund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Refund order details Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayRefundDetailsRespVO extends PayRefundBaseVO {

    @Schema(description = "Payment refund number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Application Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "I ameconets")
    private String appName;

    @Schema(description = "Payment order", requiredMode = Schema.RequiredMode.REQUIRED)
    private Order order;

    @Schema(description = "Creation time")
    private LocalDateTime createTime;

    @Schema(description = "Update time")
    private LocalDateTime updateTime;

    @Schema(description = "Management Backend - Payment order")
    @Data
    public static class Order {

        @Schema(description = "Product Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
        private String subject;

    }

}
