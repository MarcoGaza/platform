package cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - API Error log paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApiErrorLogPageReqVO extends PageParam {

    @Schema(description = "User ID", example = "666")
    private Long userId;

    @Schema(description = "User Type", example = "1")
    private Integer userType;

    @Schema(description = "Application name", example = "dashboard")
    private String applicationName;

    @Schema(description = "Request address", example = "/xx/yy")
    private String requestUrl;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Abnormality time")
    private LocalDateTime[] exceptionTime;

    @Schema(description = "Processing status", example = "0")
    private Integer processStatus;

}
