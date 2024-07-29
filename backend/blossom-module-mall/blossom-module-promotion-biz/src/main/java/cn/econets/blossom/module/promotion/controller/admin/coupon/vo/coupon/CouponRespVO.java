package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Coupon Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponRespVO extends CouponBaseVO {

    @Schema(description = "Coupon number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
