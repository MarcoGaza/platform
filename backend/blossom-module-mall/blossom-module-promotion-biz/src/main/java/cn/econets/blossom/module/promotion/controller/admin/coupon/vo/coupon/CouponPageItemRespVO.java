package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Each item in the coupon page Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponPageItemRespVO extends CouponRespVO {

    @Schema(description = "User Nickname", example = "Oldeconets")
    private String nickname;

}
