package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Coupon template paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponTemplatePageReqVO extends PageParam {

    @Schema(description = "Coupon Name", example = "Hello")
    private String name;

    @Schema(description = "Status", example = "1")
    private Integer status;

    @Schema(description = "Discount type", example = "1")
    private Integer discountType;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "Types that can be collected", example = "[1, 2, 3]")
    @InEnum(value = CouponTakeTypeEnum.class, message = "Types that can be collected，Must be {value}")
    private List<Integer> canTakeTypes;

    @Schema(description = "Product Range", example = "1")
    @InEnum(value = PromotionProductScopeEnum.class, message = "Product Range，Must be {value}")
    private Integer productScope;

    @Schema(description = "Product range number", example = "1")
    private Long productScopeValue;

}
