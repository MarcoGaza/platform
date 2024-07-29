package cn.econets.blossom.module.system.controller.admin.sensitiveword.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Sensitive word paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SensitiveWordPageReqVO extends PageParam {

    @Schema(description = "Sensitive words", example = "Sensitive words")
    private String name;

    @Schema(description = "Tag", example = "SMS,Comments")
    private String tag;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
