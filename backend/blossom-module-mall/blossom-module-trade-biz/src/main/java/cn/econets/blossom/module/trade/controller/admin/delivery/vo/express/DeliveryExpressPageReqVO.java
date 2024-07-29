package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.econets.blossom.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Express delivery company page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryExpressPageReqVO extends PageParam {

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
