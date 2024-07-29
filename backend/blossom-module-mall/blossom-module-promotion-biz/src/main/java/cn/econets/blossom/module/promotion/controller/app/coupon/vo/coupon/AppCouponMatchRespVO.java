package cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Coupon Response VO")
@Data
public class AppCouponMatchRespVO extends AppCouponRespVO {

    @Schema(description = "Whether it matches", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean match;

    @Schema(description = "Prompt for matching conditions", example = "The items being settled do not meet the conditions")
    private String description;

}
