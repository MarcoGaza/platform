package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Distribution User Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BrokerageUserBaseVO {

    @Schema(description = "Promoter Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "4587")
    @NotNull(message = "The promoter number cannot be empty")
    private Long bindUserId;

    @Schema(description = "Promoter binding time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime bindUserTime;

    @Schema(description = "Promotion Qualification", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Promotion qualification cannot be empty")
    private Boolean brokerageEnabled;

    @Schema(description = "Time to become a distributor")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime brokerageTime;

    @Schema(description = "Available commission", requiredMode = Schema.RequiredMode.REQUIRED, example = "11089")
    @NotNull(message = "Available commission cannot be empty")
    private Integer price;

    @Schema(description = "Freeze commission", requiredMode = Schema.RequiredMode.REQUIRED, example = "30916")
    @NotNull(message = "Frozen commission cannot be empty")
    private Integer frozenPrice;

}
