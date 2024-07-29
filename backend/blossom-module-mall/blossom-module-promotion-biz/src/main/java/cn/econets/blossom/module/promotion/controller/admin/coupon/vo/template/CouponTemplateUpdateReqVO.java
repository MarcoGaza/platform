package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Coupon template update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponTemplateUpdateReqVO extends CouponTemplateBaseVO {

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Template number cannot be empty")
    private Long id;

}
