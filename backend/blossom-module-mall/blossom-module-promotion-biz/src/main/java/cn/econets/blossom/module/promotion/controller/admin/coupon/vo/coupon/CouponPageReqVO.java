package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Collection;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Coupon Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CouponPageReqVO extends PageParam {

    @Schema(description = "Coupon template number", example = "2048")
    private Long templateId;

    @Schema(description = "Promotion code status", example = "1")
    @InEnum(value = CouponStatusEnum.class, message = "Coupon Status，Must be {value}")
    private Integer status;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "User Nickname", example = "econets")
    private String nickname;

    @Schema(description = "User Number", example = "1")
    private Collection<Long> userIds;

}
