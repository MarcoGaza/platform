package cn.econets.blossom.module.promotion.controller.admin.reward.vo;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionConditionTypeEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
* Save a lot of money and get a free gift Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class RewardActivityBaseVO {

    @Schema(description = "Activity Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Manila Manila")
    @NotNull(message = "Activity title cannot be empty")
    private String name;

    @Schema(description = "Start time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Start time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "End time cannot be empty")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Future(message = "The end time must be greater than the current time")
    private LocalDateTime endTime;

    @Schema(description = "Remarks", example = "biubiubiu")
    private String remark;

    @Schema(description = "Condition type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Condition type cannot be empty")
    @InEnum(value = PromotionConditionTypeEnum.class, message = "Condition type must be {value}")
    private Integer conditionType;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Product range cannot be empty")
    @InEnum(value = PromotionProductScopeEnum.class, message = "Product range must be {value}")
    private Integer productScope;

    @Schema(description = "Products SPU Numbered array", example = "1,2,3")
    private List<Long> productSpuIds;

    /**
     * Array of preferential rules
     */
    @Valid // Check the sub-object
    private List<Rule> rules;

    @Schema(description = "Promotion Rules")
    @Data
    public static class Rule {

        @Schema(description = "Preferential threshold", requiredMode = Schema.RequiredMode.REQUIRED, example = "100") // 1. Full N Yuan，Unit：Points; 2. Full N Item
        @Min(value = 1L, message = "The discount threshold must be greater than or equal to 1")
        private Integer limit;

        @Schema(description = "Discounted price", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @Min(value = 1L, message = "The discount price must be greater than or equal to 1")
        private Integer discountPrice;

        @Schema(description = "Is shipping free?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        private Boolean freeDelivery;

        @Schema(description = "Gifted points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        @Min(value = 1L, message = "The points given must be greater than or equal to 1")
        private Integer point;

        @Schema(description = "Array of coupon numbers given", example = "1,2,3")
        private List<Long> couponIds;

        @Schema(description = "Array of the number of coupons given", example = "1,2,3")
        private List<Integer> couponCounts;

        @AssertTrue(message = "Coupons and quantities must correspond one to one")
        @JsonIgnore
        public boolean isCouponCountsValid() {
            return CollUtil.size(couponCounts) == CollUtil.size(couponCounts);
        }

    }

}
