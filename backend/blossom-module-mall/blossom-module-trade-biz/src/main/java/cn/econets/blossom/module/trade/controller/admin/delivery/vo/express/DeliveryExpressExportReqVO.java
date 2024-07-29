package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Express Delivery Company Excel Export Request VO")
@Data
public class DeliveryExpressExportReqVO {

    @Schema(description = "Courier company code")
    private String code;

    @Schema(description = "Express delivery company name", example = "Li Si")
    private String name;

    @Schema(description = "Status（0Normal 1Disable）", example = "1")
    private Integer status;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
