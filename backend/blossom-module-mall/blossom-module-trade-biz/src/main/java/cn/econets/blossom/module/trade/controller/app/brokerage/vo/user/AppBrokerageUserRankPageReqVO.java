package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Application App - Distribution User Ranking Request VO")
@Data
public class AppBrokerageUserRankPageReqVO extends PageParam {

    @Schema(description = "Start + End time", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotEmpty(message = "Time cannot be empty")
    private LocalDateTime[] times;

}
