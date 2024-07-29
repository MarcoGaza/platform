package cn.econets.blossom.module.promotion.controller.app.coupon.vo.template;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "User App - Coupon template pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCouponTemplatePageReqVO extends PageParam {

    @Schema(description = "Product Range", example = "1")
    @InEnum(value = PromotionProductScopeEnum.class, message = "Product Range，Must be {value}")
    private Integer productScope;

    @Schema(description = "Product number", example = "1")
    private Long spuId;

}
