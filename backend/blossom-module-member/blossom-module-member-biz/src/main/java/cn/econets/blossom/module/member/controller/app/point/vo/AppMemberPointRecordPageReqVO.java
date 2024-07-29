package cn.econets.blossom.module.member.controller.app.point.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "User App - User Points Record Paging Request VO")
@Data
public class AppMemberPointRecordPageReqVO extends PageParam {

    @Schema(description = "Whether to increase points", example = "true")
    private Boolean addStatus; // true - Increase；false - Reduce；null - Do not filter

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
