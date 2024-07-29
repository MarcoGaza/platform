package cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "User App - Coupon Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCouponPageReqVO extends PageParam {

    @Schema(description = "Coupon Status", example = "1")
    @InEnum(value = CouponStatusEnum.class, message = "Coupon Status，Must be {value}")
    private Integer status;

}
