package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* Transaction and after-sales Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class AfterSaleBaseVO {

    @Schema(description = "After-sales serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "202211190847450020500077")
    @NotNull(message = "After-sales serial number cannot be empty")
    private String no;

    @Schema(description = "After-sales status", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "After-sales status cannot be empty")
    private Integer status;

    @Schema(description = "After-sales type", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    @NotNull(message = "After-sales type cannot be empty")
    private Integer type;

    @Schema(description = "After-sales service", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "After-sales method cannot be empty")
    private Integer way;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "30337")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Reason for application", requiredMode = Schema.RequiredMode.REQUIRED, example = "Dislike")
    @NotNull(message = "Application reason cannot be empty")
    private String applyReason;

    @Schema(description = "Additional description", example = "You are right")
    private String applyDescription;

    @Schema(description = "Supplementary voucher image", example = "https://www.econets.cn/1.png")
    private List<String> applyPicUrls;

    @Schema(description = "Order Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18078")
    @NotNull(message = "Order number cannot be empty")
    private Long orderId;

    @Schema(description = "Order serial number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022111917190001")
    @NotNull(message = "Order serial number cannot be empty")
    private String orderNo;

    @Schema(description = "Order Item Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "572")
    @NotNull(message = "The order item number cannot be empty")
    private Long orderItemId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2888")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

    @Schema(description = "Products SPU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "Products SPU The name cannot be empty")
    private String spuName;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15657")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Product images", example = "https://www.econets.cn/2.png")
    private String picUrl;

    @Schema(description = "Purchase quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "20012")
    @NotNull(message = "The purchase quantity cannot be empty")
    private Integer count;

    @Schema(description = "Approval time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime auditTime;

    @Schema(description = "Approver", example = "30835")
    private Long auditUserId;

    @Schema(description = "Approval Notes", example = "Not fragrant")
    private String auditReason;

    @Schema(description = "Refund amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "18077")
    @NotNull(message = "Refund amount，Unit：The score cannot be empty")
    private Integer refundPrice;

    @Schema(description = "Payment refund number", example = "10271")
    private Long payRefundId;

    @Schema(description = "Refund Time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime refundTime;

    @Schema(description = "Return logistics company number", example = "10")
    private Long logisticsId;

    @Schema(description = "Return logistics order number", example = "610003952009")
    private String logisticsNo;

    @Schema(description = "Return Time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deliveryTime;

    @Schema(description = "Delivery time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime receiveTime;

    @Schema(description = "Receipt Notes", example = "Dislike")
    private String receiveReason;

}
