package cn.econets.blossom.module.system.controller.admin.logger.vo.operatelog;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Operation log paging list Request VO")
@Data
public class OperateLogPageReqVO extends PageParam {

    @Schema(description = "Operation module，Simulation matching", example = "Order")
    private String module;

    @Schema(description = "User Nickname，Simulation matching", example = "blossom")
    private String userNickname;

    @Schema(description = "Operation Category，See OperateLogTypeEnum Enumeration class", example = "1")
    private Integer type;

    @Schema(description = "Operation status", example = "true")
    private Boolean success;

    @Schema(description = "Start time", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

}
