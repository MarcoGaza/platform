package cn.econets.blossom.module.system.controller.admin.permission.vo.role;


import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - Role Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageReqVO extends PageParam {

    @Schema(description = "Role name，Fuzzy matching", example = "blossom")
    private String name;

    @Schema(description = "Role ID，Fuzzy matching", example = "admin")
    private String code;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

    @Schema(description = "Creation time", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
