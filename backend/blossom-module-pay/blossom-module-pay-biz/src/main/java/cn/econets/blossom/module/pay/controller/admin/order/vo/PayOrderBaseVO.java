package cn.econets.blossom.module.pay.controller.admin.order.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Payment order Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 *
 */
@Data
public class PayOrderBaseVO {

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Application number cannot be empty")
    private Long appId;

    @Schema(description = "Channel Number", example = "2048")
    private Long channelId;

    @Schema(description = "Channel Code", example = "wx_app")
    private String channelCode;

    @Schema(description = "Merchant order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "888")
    @NotNull(message = "Merchant order number cannot be empty")
    private String merchantOrderId;

    @Schema(description = "Product Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    @NotNull(message = "Product title cannot be empty")
    private String subject;

    @Schema(description = "Product Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am a potato")
    @NotNull(message = "Product description cannot be empty")
    private String body;

    @Schema(description = "Asynchronous notification address", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://127.0.0.1:48080/pay/notify")
    @NotNull(message = "Asynchronous notification address cannot be empty")
    private String notifyUrl;

    @Schema(description = "Payment amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "Payment amount，Unit：The score cannot be empty")
    private Long price;

    @Schema(description = "Channel Fee，Unit：Percentage", example = "10")
    private Double channelFeeRate;

    @Schema(description = "Channel fee amount，Unit：Points", example = "100")
    private Integer channelFeePrice;

    @Schema(description = "Payment Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Payment status cannot be empty")
    private Integer status;

    @Schema(description = "User IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @NotNull(message = "User IPCannot be empty")
    private String userIp;

    @Schema(description = "Order expiration time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Order expiration time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime expireTime;

    @Schema(description = "Time when order payment is successful")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime successTime;

    @Schema(description = "The order extension number of successful payment", example = "50")
    private Long extensionId;

    @Schema(description = "Payment order number", example = "2048888")
    private String no;

    @Schema(description = "Total refund amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "Total refund amount，Unit：The score cannot be empty")
    private Long refundPrice;

    @Schema(description = "Channel user number", example = "2048")
    private String channelUserId;

    @Schema(description = "Channel order number", example = "4096")
    private String channelOrderNo;

}
