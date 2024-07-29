package cn.econets.blossom.module.pay.controller.admin.transfer.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.econets.blossom.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Transfer Order Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayTransferPageReqVO extends PageParam {

    @Schema(description = "Transfer Order Number")
    private String no;

    @Schema(description = "Application Number", example = "12831")
    private Long appId;

    @Schema(description = "Channel Code", example = "wx_app")
    private String channelCode;

    @Schema(description = "Merchant transfer order number", example = "17481")
    private String merchantTransferId;

    @Schema(description = "Type", example = "2")
    private Integer type;

    @Schema(description = "Transfer Status", example = "2")
    private Integer status;

    @Schema(description = "Recipient's name", example = "Wang Wu")
    private String userName;

    @Schema(description = "Channel transfer order number")
    private String channelTransferNo;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
