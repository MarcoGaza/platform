package cn.econets.blossom.module.bpm.controller.admin.oa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Leave Application Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmOALeaveRespVO extends BpmOALeaveBaseVO {

    @Schema(description = "Leave form primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Status-See bpm_process_instance_result Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer result;

    @Schema(description = "Application time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Application time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "Processid")
    private String processInstanceId;

}
