package cn.econets.blossom.module.pay.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Payment order Excel Export Request VO，Parameters and PayOrderPageReqVO is consistent")
@Data
public class PayOrderExportReqVO {

    @Schema(description = "Application Number", example = "1024")
    private Long appId;

    @Schema(description = "Channel Code", example = "wx_app")
    private String channelCode;

    @Schema(description = "Merchant order number", example = "4096")
    private String merchantOrderId;

    @Schema(description = "Channel Number", example = "1888")
    private String channelOrderNo;

    @Schema(description = "Payment order number", example = "2014888")
    private String no;

    @Schema(description = "Payment Status", example = "0")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
