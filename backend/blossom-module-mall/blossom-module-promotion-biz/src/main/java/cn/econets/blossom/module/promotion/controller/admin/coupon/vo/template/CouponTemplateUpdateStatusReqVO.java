package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Coupon template update status Request VO")
@Data
public class CouponTemplateUpdateStatusReqVO {

    @Schema(description = "Coupon template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Coupon template number cannot be empty")
    private Long id;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

}
