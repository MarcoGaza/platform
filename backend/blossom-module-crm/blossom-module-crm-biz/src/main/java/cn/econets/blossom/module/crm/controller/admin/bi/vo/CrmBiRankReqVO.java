package cn.econets.blossom.module.crm.controller.admin.bi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM BI Rankings Request VO")
@Data
public class CrmBiRankReqVO {

    @Schema(description = "Department id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Department id Cannot be empty")
    private Long deptId;

    /**
     * userIds Currently no front-end transmission is required，Currently it is convenient for the backend to pass deptId After reading the number，Set back
     *
     * Follow-up，May support selecting some users for query
     */
    @Schema(description = "Responsible user id Collection", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "2")
    private List<Long> userIds;

    @Schema(description = "Time Range", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotEmpty(message = "Time range cannot be empty")
    private LocalDateTime[] times;

}
