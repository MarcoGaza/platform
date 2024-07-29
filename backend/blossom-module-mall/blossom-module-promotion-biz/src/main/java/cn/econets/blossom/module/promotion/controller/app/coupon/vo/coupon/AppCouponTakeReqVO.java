package cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Get coupons Request VO")
@Data
public class AppCouponTakeReqVO {

    @Schema(description = "Coupon template number", example = "1")
    @NotNull(message = "Coupon template number cannot be empty")
    private Long templateId;

}
