package cn.econets.blossom.module.promotion.controller.admin.banner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 *
 */
@Schema(description = "Management Backend - BannerUpdate Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BannerUpdateReqVO extends BannerBaseVO {

    @Schema(description = "banner Number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "banner The number cannot be empty")
    private Long id;

}
