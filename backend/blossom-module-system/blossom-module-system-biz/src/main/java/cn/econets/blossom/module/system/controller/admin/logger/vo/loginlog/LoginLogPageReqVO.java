package cn.econets.blossom.module.system.controller.admin.logger.vo.loginlog;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - Login log paging list Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginLogPageReqVO extends PageParam {

    @Schema(description = "User IP，Simulation matching", example = "127.0.0.1")
    private String userIp;

    @Schema(description = "User account，Simulation matching", example = "blossom")
    private String username;

    @Schema(description = "Operation status", example = "true")
    private Boolean status;

    @Schema(description = "Login time", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
