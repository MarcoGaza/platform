package cn.econets.blossom.module.promotion.controller.admin.discount.vo;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionDiscountTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* Limited time discount event Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class DiscountActivityBaseVO {

    @Schema(description = "Activity Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "A title")
    @NotNull(message = "Activity title cannot be empty")
    private String name;

    @Schema(description = "Start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Start time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "End time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

    @Schema(description = "Products")
    @Data
    public static class Product {

        @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "Products SPU The number cannot be empty")
        private Long spuId;

        @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "Products SKU The number cannot be empty")
        private Long skuId;

        @Schema(description = "Discount type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "The discount type cannot be empty")
        @InEnum(PromotionDiscountTypeEnum.class)
        private Integer discountType;

        @Schema(description = "Discount Percentage", example = "80") // For example，80% for 80
        private Integer discountPercent;

        @Schema(description = "Discount amount", example = "10")
        @Min(value = 0, message = "The discount amount must be greater than or equal to 0")
        private Integer discountPrice;

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

    }
}
