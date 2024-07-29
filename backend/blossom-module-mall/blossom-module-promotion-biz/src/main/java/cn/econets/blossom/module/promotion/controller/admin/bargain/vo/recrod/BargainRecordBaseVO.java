package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.recrod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * Bargaining Record Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class BargainRecordBaseVO {

    @Schema(description = "Bargaining activity name", requiredMode = Schema.RequiredMode.REQUIRED, example = "22690")
    @NotNull(message = "The bargaining activity name cannot be empty")
    private Long activityId;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "9430")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23622")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "29950")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Bargaining starting price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "31160")
    @NotNull(message = "Bargaining starting price，Unit：The score cannot be empty")
    private Integer bargainFirstPrice;

    @Schema(description = "Current bargaining price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "22743")
    @NotNull(message = "Current bargaining price，Unit：The score cannot be empty")
    private Integer bargainPrice;

    @Schema(description = "Bargaining status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Bargaining status cannot be empty")
    private Integer status;

    @Schema(description = "Order Number", example = "27845")
    private Long orderId;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "End time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

}
