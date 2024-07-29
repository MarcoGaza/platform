package cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Payment Refund Plan Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class CrmReceivablePlanBaseVO {

    @Schema(description = "Issue number", example = "1")
    private Integer period;

    @Schema(description = "Payment collection plan number", example = "19852")
    private Long receivableId;

    @Schema(description = "Planned payment amount", example = "29675")
    private Integer price;

    @Schema(description = "Planned payment date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime returnTime;

    @Schema(description = "Remind a few days in advance")
    private Integer remindDays;

    @Schema(description = "Reminder date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime remindTime;

    @Schema(description = "Customer Name", example = "18026")
    private Long customerId;

    @Schema(description = "Contract Number", example = "3473")
    private Long contractId;

    // TODO @liuhongfeng：Person in charge number
    @Schema(description = "Person in charge number", example = "17828")
    private Long ownerUserId;

    @Schema(description = "Display order")
    private Integer sort;

    @Schema(description = "Remarks", example = "Remarks")
    private String remark;

}
