package cn.econets.blossom.module.crm.controller.admin.business.vo.business;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Business Opportunities Response VO")
@Data
public class CrmBusinessRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "32129")
    private Long id;

    @Schema(description = "Opportunity Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "Opportunity name cannot be empty")
    private String name;

    @Schema(description = "Opportunity status type number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25714")
    @NotNull(message = "Opportunity status type cannot be empty")
    private Long statusTypeId;

    @Schema(description = "Opportunity status number", requiredMode = Schema.RequiredMode.REQUIRED, example = "30320")
    @NotNull(message = "Opportunity status cannot be empty")
    private Long statusId;

    @Schema(description = "Next contact time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactNextTime;

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10299")
    @NotNull(message = "Customer cannot be empty")
    private Long customerId;

    @Schema(description = "Expected transaction date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dealTime;

    @Schema(description = "Opportunity Amount", example = "12371")
    private Integer price;

    // TODO @ljileo：Discount usage Integer Type，When storing，Default * 100；When showing，Front-end needs / 100；Avoid loss of precision
    @Schema(description = "Entire order discount")
    private Integer discountPercent;

    @Schema(description = "Total amount of product", example = "12025")
    private BigDecimal productPrice;

    @Schema(description = "Remarks", example = "Whatever")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Customer Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    private String customerName;

    @Schema(description = "Status type name", requiredMode = Schema.RequiredMode.REQUIRED, example = "In progress")
    private String statusTypeName;

    @Schema(description = "Status Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Following up")
    private String statusName;

}
