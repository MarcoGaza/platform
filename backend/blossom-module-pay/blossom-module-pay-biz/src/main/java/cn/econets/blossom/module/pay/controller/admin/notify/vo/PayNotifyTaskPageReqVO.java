package cn.econets.blossom.module.pay.controller.admin.notify.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Callback notification paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayNotifyTaskPageReqVO extends PageParam {

    @Schema(description = "Application Number", example = "10636")
    private Long appId;

    @Schema(description = "Notification type", example = "2")
    private Integer type;

    @Schema(description = "Data number", example = "6722")
    private Long dataId;

    @Schema(description = "Notification status", example = "1")
    private Integer status;

    @Schema(description = "Merchant order number", example = "26697")
    private String merchantOrderId;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
