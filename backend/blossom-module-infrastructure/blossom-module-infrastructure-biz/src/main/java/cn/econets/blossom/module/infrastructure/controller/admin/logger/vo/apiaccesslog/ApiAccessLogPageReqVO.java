package cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apiaccesslog;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;


@Schema(description = "Management Backend - API Access log paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApiAccessLogPageReqVO extends PageParam {

    @Schema(description = "User ID", example = "666")
    private Long userId;

    @Schema(description = "User Type", example = "2")
    private Integer userType;

    @Schema(description = "Application name", example = "dashboard")
    private String applicationName;

    @Schema(description = "Request address，Fuzzy matching", example = "/xxx/yyy")
    private String requestUrl;

    @Schema(description = "Start time", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] beginTime;

    @Schema(description = "Execution duration,Greater than or equal to，Unit：Milliseconds", example = "100")
    private Integer duration;

    @Schema(description = "Result code", example = "0")
    private Integer resultCode;

}
