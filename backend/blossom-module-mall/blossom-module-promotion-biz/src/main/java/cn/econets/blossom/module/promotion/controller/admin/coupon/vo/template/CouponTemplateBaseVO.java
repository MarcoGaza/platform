package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTemplateValidityTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
* Coupon Template Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class CouponTemplateBaseVO {

    @Schema(description = "Coupon Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Spring Festival send-off")
    @NotNull(message = "The coupon name cannot be empty")
    private String name;

    @Schema(description = "Total Issued Volume", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024") // -1 - It means there is no limit on the number of distributions
    @NotNull(message = "The total issuance amount cannot be empty")
    private Integer totalCount;

    @Schema(description = "Limited number of items per person", requiredMode = Schema.RequiredMode.REQUIRED, example = "66") // -1 - It means no restriction
    @NotNull(message = "The number of items per person cannot be empty")
    private Integer takeLimitCount;

    @Schema(description = "How to receive", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The method of receiving cannot be empty")
    private Integer takeType;

    @Schema(description = "Whether to set the maximum amount of available funds", requiredMode = Schema.RequiredMode.REQUIRED, example = "100") // Unit：Points；0 - No restrictions
    @NotNull(message = "Whether to set the minimum amount of available funds and cannot be left blank")
    private Integer usePrice;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product range cannot be empty")
    @InEnum(PromotionProductScopeEnum.class)
    private Integer productScope;

    @Schema(description = "Array of product range numbers", example = "[1, 3]")
    private List<Long> productScopeValues;

    @Schema(description = "Effective Date Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Effective date type cannot be empty")
    @InEnum(CouponTemplateValidityTypeEnum.class)
    private Integer validityType;

    @Schema(description = "Fixed date - Effective start time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validStartTime;

    @Schema(description = "Fixed date - Effective end time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime validEndTime;

    @Schema(description = "Date of collection - Starting days")
    @Min(value = 0L, message = "The start day must be greater than 0")
    private Integer fixedStartTerm;

    @Schema(description = "Date of collection - Ending days")
    @Min(value = 1L, message = "The start day must be greater than 1")
    private Integer fixedEndTerm;

    @Schema(description = "Discount type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The discount type cannot be empty")
    @InEnum(PromotionDiscountTypeEnum.class)
    private Integer discountType;

    @Schema(description = "Discount Percentage", example = "80") //  For example，80% for 80
    private Integer discountPercent;

    @Schema(description = "Discount amount", example = "10")
    @Min(value = 0, message = "The discount amount must be greater than or equal to 0")
    private Integer discountPrice;

    @Schema(description = "Discount limit", example = "100") // Unit：Points，Only in discountType for PERCENT Use
    private Integer discountLimitPrice;

    @AssertTrue(message = "The array of product range numbers cannot be empty")
    @JsonIgnore
    public boolean isProductScopeValuesValid() {
        return Objects.equals(productScope, PromotionProductScopeEnum.ALL.getScope()) // Full range，Can be empty
                || CollUtil.isNotEmpty(productScopeValues);
    }

    @AssertTrue(message = "The effective start time cannot be empty")
    @JsonIgnore
    public boolean isValidStartTimeValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.DATE.getType())
                || validStartTime != null;
    }

    @AssertTrue(message = "The effective end time cannot be empty")
    @JsonIgnore
    public boolean isValidEndTimeValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.DATE.getType())
                || validEndTime != null;
    }

    @AssertTrue(message = "The start day cannot be empty")
    @JsonIgnore
    public boolean isFixedStartTermValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.TERM.getType())
                || fixedStartTerm != null;
    }

    @AssertTrue(message = "The end days cannot be empty")
    @JsonIgnore
    public boolean isFixedEndTermValid() {
        return ObjectUtil.notEqual(validityType, CouponTemplateValidityTypeEnum.TERM.getType())
                || fixedEndTerm != null;
    }

    @AssertTrue(message = "The discount percentage needs to be greater than or equal to 1，Less than or equal to 99")
    @JsonIgnore
    public boolean isDiscountPercentValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PERCENT.getType())
                || (discountPercent != null && discountPercent >= 1 && discountPercent<= 99);
    }

    @AssertTrue(message = "The discount amount cannot be empty")
    @JsonIgnore
    public boolean isDiscountPriceValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PRICE.getType())
                || discountPrice != null;
    }

    @AssertTrue(message = "The discount limit cannot be empty")
    @JsonIgnore
    public boolean isDiscountLimitPriceValid() {
        return ObjectUtil.notEqual(discountType, PromotionDiscountTypeEnum.PERCENT.getType())
                || discountLimitPrice != null;
    }

}
