package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
* Coupon Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class CouponBaseVO {

    // ========== Basic Information BEGIN ==========
    @Schema(description = "Coupon template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Coupon template number cannot be empty")
    private Long templateId;

    @Schema(description = "Coupon Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Spring Festival gifts")
    @NotNull(message = "The coupon name cannot be empty")
    private String name;

    @Schema(description = "Promotion code status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    // ========== Basic Information END ==========

    // ========== Collection Status BEGIN ==========
    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "How to receive", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The method of receiving cannot be empty")
    private Integer takeType;
    // ========== Collection Status END ==========

    // ========== Rules of Use BEGIN ==========
    @Schema(description = "Whether to set the maximum amount of available funds", requiredMode = Schema.RequiredMode.REQUIRED, example = "100") // Unit：Points；0 - No restrictions
    @NotNull(message = "Whether to set the minimum amount of available funds and cannot be left blank")
    private Integer usePrice;

    @Schema(description = "Fixed date - Effective start time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validStartTime;

    @Schema(description = "Fixed date - Effective end time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validEndTime;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product range cannot be empty")
    @InEnum(PromotionProductScopeEnum.class)
    private Integer productScope;

    @Schema(description = "Array of product range numbers", example = "1,3")
    private List<Long> productScopeValues;
    // ========== Rules of Use END ==========

    // ========== Effect of use BEGIN ==========
    @Schema(description = "Discount type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The discount type cannot be empty")
    @InEnum(PromotionDiscountTypeEnum.class)
    private Integer discountType;

    @Schema(description = "Discount Percentage", example = "80") // For example，80% for 80
    private Integer discountPercent;

    @Schema(description = "Discount amount", example = "10")
    @Min(value = 0, message = "The discount amount must be greater than or equal to 0")
    private Integer discountPrice;

    @Schema(description = "Discount limit", example = "100") // Unit：Points，Only in discountType for PERCENT Use
    private Integer discountLimitPrice;
    // ========== Effect of use END ==========

    // ========== Usage BEGIN ==========

    @Schema(description = "Use order number", example = "4096")
    private Long useOrderId;

    @Schema(description = "Usage time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime useTime;

    // ========== Usage END ==========

}
