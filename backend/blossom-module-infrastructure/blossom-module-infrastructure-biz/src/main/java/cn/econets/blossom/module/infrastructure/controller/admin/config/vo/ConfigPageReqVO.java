package cn.econets.blossom.module.infrastructure.controller.admin.config.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Parameter configuration paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConfigPageReqVO extends PageParam {

    @Schema(description = "Data source name，Fuzzy matching", example = "Name")
    private String name;

    @Schema(description = "Parameter key name，Fuzzy matching", example = "ximu.db.username")
    private String key;

    @Schema(description = "Parameter type，See SysConfigTypeEnum Enumeration", example = "1")
    private Integer type;

    @Schema(description = "Creation time", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
