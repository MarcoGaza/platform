package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Bargaining activity Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class BargainActivityBaseVO {

    @Schema(description = "Bargaining activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "The more you cut, the more you save，If you are my brother, come and chop me up")
    @NotNull(message = "The bargaining name cannot be empty")
    private String name;

    @Schema(description = "Products SPU Number", example = "1")
    @NotNull(message = "The bargaining item cannot be empty")
    private Long spuId;

    @Schema(description = "Products skuId", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    @NotNull(message = "Products skuId Cannot be empty")
    private Long skuId;

    @Schema(description = "Bargaining starting price", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    @NotNull(message = "The starting price for bargaining cannot be empty")
    private Integer bargainFirstPrice;

    @Schema(description = "Bargaining price", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    @NotNull(message = "The bargaining bottom price cannot be empty")
    private Integer bargainMinPrice;

    @Schema(description = "Activity Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    @NotNull(message = "Activity inventory cannot be empty")
    private Integer stock;

    @Schema(description = "Total purchase limit quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "16218")
    @NotNull(message = "The total purchase limit quantity cannot be empty")
    private Integer totalLimitCount;

    @Schema(description = "Activity start time", requiredMode = Schema.RequiredMode.REQUIRED, example = "[2022-07-01 23:59:59]")
    @NotNull(message = "The activity start time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "Event end time", requiredMode = Schema.RequiredMode.REQUIRED, example = "[2022-07-01 23:59:59]")
    @NotNull(message = "The activity end time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Maximum number of assists", requiredMode = Schema.RequiredMode.REQUIRED, example = "25222")
    @NotNull(message = "The maximum number of assists cannot be empty")
    private Integer helpMaxCount;

    @Schema(description = "Maximum number of times to help cut", requiredMode = Schema.RequiredMode.REQUIRED, example = "25222")
    @NotNull(message = "The maximum number of assisting times cannot be empty")
    private Integer bargainCount;

    @Schema(description = "The minimum amount a user can bargain for each time", requiredMode = Schema.RequiredMode.REQUIRED, example = "25222")
    @NotNull(message = "The minimum amount that the user can bargain for each time cannot be empty")
    private Integer randomMinPrice;

    @Schema(description = "The maximum amount a user can bargain for each time", requiredMode = Schema.RequiredMode.REQUIRED, example = "25222")
    @NotNull(message = "The maximum amount a user can bargain for each time cannot be empty")
    private Integer randomMaxPrice;

}
