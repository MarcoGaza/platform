package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Coupon Template Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponTemplateRespVO extends CouponTemplateBaseVO {

    @Schema(description = "Template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Number of coupons received", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer takeCount;

    @Schema(description = "Number of times the coupon was used", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Integer useCount;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
