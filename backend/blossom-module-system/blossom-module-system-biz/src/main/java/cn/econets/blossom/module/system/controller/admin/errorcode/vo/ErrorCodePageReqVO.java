package cn.econets.blossom.module.system.controller.admin.errorcode.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Error code paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ErrorCodePageReqVO extends PageParam {

    @Schema(description = "Error code type，See ErrorCodeTypeEnum Enumeration class", example = "1")
    private Integer type;

    @Schema(description = "Application name", example = "dashboard")
    private String applicationName;

    @Schema(description = "Error code", example = "1234")
    private Integer code;

    @Schema(description = "Error code error prompt", example = "Handsome")
    private String message;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
