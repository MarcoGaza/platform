package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Commission Record Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BrokerageRecordBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25973")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Business Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23353")
    @NotEmpty(message = "Business number cannot be empty")
    private String bizId;

    @Schema(description = "Business Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Business type cannot be empty")
    private Integer bizType;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "28731")
    @NotNull(message = "Amount cannot be empty")
    private Integer price;

    @Schema(description = "Current total commission", requiredMode = Schema.RequiredMode.REQUIRED, example = "13226")
    @NotNull(message = "The current total commission cannot be empty")
    private Integer totalPrice;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "You are right")
    @NotNull(message = "Description cannot be empty")
    private String description;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Freeze time（Sky）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Freeze time（Sky）Cannot be empty")
    private Integer frozenDays;

    @Schema(description = "Thaw time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime unfreezeTime;

    @Schema(description = "Source user level")
    private Integer sourceUserLevel;

    @Schema(description = "Source user number")
    private Long sourceUserId;
}
