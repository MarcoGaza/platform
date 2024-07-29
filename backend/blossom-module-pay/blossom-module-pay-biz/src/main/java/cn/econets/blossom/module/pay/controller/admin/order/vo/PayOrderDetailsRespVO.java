package cn.econets.blossom.module.pay.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Payment order details Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayOrderDetailsRespVO extends PayOrderBaseVO {

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Application Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Source code")
    private String appName;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    /**
     * Payment Order Extension
     */
    private PayOrderExtension extension;

    @Data
    @Schema(description = "Payment Order Extension")
    public static class PayOrderExtension {

        @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private String no;

        @Schema(description = "Content of payment asynchronous notification")
        private String channelNotifyData;

    }

}
