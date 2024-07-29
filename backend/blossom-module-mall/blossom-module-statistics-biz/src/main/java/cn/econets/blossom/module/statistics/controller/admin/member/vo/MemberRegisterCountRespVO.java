package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Schema(description = "Management Backend - Number of member registrations Response VO")
@Data
public class MemberRegisterCountRespVO {

    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    @Schema(description = "Date", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private LocalDate date;

    @Schema(description = "Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer count;

}
