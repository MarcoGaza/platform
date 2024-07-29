package cn.econets.blossom.module.promotion.controller.app.coupon.vo.template;

import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Coupon Template Response VO")
@Data
public class AppCouponTemplateRespVO {

    @Schema(description = "Coupon template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Coupon Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Spring Festival send-off")
    private String name;

    @Schema(description = "Limited number of items per person", requiredMode = Schema.RequiredMode.REQUIRED, example = "66") // -1 - It means no restriction
    private Integer takeLimitCount;

    @Schema(description = "Whether to set the maximum amount of available funds", requiredMode = Schema.RequiredMode.REQUIRED, example = "100") // Unit：Points；0 - No restrictions
    private Integer usePrice;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer productScope;

    @Schema(description = "Array of product range numbers", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private List<Long> productScopeValues;

    @Schema(description = "Effective Date Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer validityType;

    @Schema(description = "Fixed date - Effective start time")
    private LocalDateTime validStartTime;

    @Schema(description = "Fixed date - Effective end time")
    private LocalDateTime validEndTime;

    @Schema(description = "Date of collection - Starting days")
    @Min(value = 0L, message = "The start day must be greater than 0")
    private Integer fixedStartTerm;

    @Schema(description = "Date of collection - Ending days")
    @Min(value = 1L, message = "The start day must be greater than 1")
    private Integer fixedEndTerm;

    @Schema(description = "Discount type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer discountType;

    @Schema(description = "Discount Percentage", example = "80") //  For example，80% for 80
    private Integer discountPercent;

    @Schema(description = "Discount amount", example = "10")
    @Min(value = 0, message = "The discount amount must be greater than or equal to 0")
    private Integer discountPrice;

    @Schema(description = "Discount limit", example = "100") // Unit：Points，Only in discountType for PERCENT Use
    private Integer discountLimitPrice;

    // ========== User-related fields ==========

    @Schema(description = "Can I claim it?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean canTake;

}
