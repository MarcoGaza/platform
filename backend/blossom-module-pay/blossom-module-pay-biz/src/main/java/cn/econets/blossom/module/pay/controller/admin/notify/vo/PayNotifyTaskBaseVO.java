package cn.econets.blossom.module.pay.controller.admin.notify.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Callback notification Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class PayNotifyTaskBaseVO {

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10636")
    private Long appId;

    @Schema(description = "Notification type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Byte type;

    @Schema(description = "Data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "6722")
    private Long dataId;

    @Schema(description = "Notification status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Byte status;

    @Schema(description = "Merchant order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "26697")
    private String merchantOrderId;

    @Schema(description = "Next notification time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime nextNotifyTime;

    @Schema(description = "Last execution time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime lastExecuteTime;

    @Schema(description = "Current notification count", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte notifyTimes;

    @Schema(description = "Maximum number of notifications", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte maxNotifyTimes;

    @Schema(description = "Asynchronous notification address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    private String notifyUrl;

}
