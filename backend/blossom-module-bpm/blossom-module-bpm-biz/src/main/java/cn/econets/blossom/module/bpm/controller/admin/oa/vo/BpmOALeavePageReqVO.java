package cn.econets.blossom.module.bpm.controller.admin.oa.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Leave Application Page Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmOALeavePageReqVO extends PageParam {

    @Schema(description = "Status-See bpm_process_instance_result Enumeration", example = "1")
    private Integer result;

    @Schema(description = "Leave Type-See bpm_oa_type", example = "1")
    private Integer type;

    @Schema(description = "Reason-Fuzzy matching", example = "Read source code")
    private String reason;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Application time")
    private LocalDateTime[] createTime;

}
