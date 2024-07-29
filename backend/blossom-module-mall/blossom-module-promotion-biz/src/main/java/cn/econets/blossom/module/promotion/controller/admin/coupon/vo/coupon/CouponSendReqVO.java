package cn.econets.blossom.module.promotion.controller.admin.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(description = "Management Backend - Coupon issuance Request VO")
@Data
@ToString(callSuper = true)
public class CouponSendReqVO {

    @Schema(description = "Coupon template number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The coupon template number cannot be empty")
    private Long templateId;

    @Schema(description = "User ID list", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2]")
    @NotEmpty(message = "The user number list cannot be empty")
    private Set<Long> userIds;

}
