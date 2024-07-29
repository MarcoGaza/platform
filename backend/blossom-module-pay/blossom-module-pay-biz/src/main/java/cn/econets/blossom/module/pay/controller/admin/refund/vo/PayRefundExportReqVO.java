package cn.econets.blossom.module.pay.controller.admin.refund.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Refund order Excel Export Request VO，Parameters and PayRefundPageReqVO is consistent")
@Data
public class PayRefundExportReqVO {

    @Schema(description = "Application Number", example = "1024")
    private Long appId;

    @Schema(description = "Channel Code", example = "wx_app")
    private String channelCode;

    @Schema(description = "Merchant payment order number", example = "10")
    private String merchantOrderId;

    @Schema(description = "Merchant refund order number", example = "20")
    private String merchantRefundId;

    @Schema(description = "Channel payment order number", example = "30")
    private String channelOrderNo;

    @Schema(description = "Channel refund order number", example = "40")
    private String channelRefundNo;

    @Schema(description = "Refund Status", example = "0")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
