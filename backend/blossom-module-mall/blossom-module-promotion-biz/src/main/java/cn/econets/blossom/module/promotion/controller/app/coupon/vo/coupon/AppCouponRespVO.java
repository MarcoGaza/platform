package cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "User App - Coupon Response VO")
@Data
public class AppCouponRespVO {

    @Schema(description = "Coupon number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Coupon Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Spring Festival send-off")
    private String name;

    @Schema(description = "Coupon Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1") // See CouponStatusEnum Enumeration
    private Integer status;

    @Schema(description = "Whether to set the maximum amount of available funds", requiredMode = Schema.RequiredMode.REQUIRED, example = "100") // Unit：Points；0 - No restrictions
    private Integer usePrice;

    @Schema(description = "Product Range", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer productScope;

    @Schema(description = "Array of product range numbers", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private List<Long> productScopeValues;

    @Schema(description = "Fixed date - Effective start time")
    private LocalDateTime validStartTime;

    @Schema(description = "Fixed date - Effective end time")
    private LocalDateTime validEndTime;

    @Schema(description = "Discount type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer discountType;

    @Schema(description = "Discount Percentage", example = "80") //  For example，80% for 80
    private Integer discountPercent;

    @Schema(description = "Discount amount", example = "10")
    @Min(value = 0, message = "The discount amount must be greater than or equal to 0")
    private Integer discountPrice;

    @Schema(description = "Discount limit", example = "100") // Unit：Points，Only in discountType for PERCENT Use
    private Integer discountLimitPrice;

}
