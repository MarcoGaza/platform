package cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "User App - Coupon matching Request VO")
@Data
public class AppCouponMatchReqVO {

    @Schema(description = "Product amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The product amount cannot be empty")
    private Integer price;

    @Schema(description = "Products SPU Numbered array", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2]")
    @NotEmpty(message = "Products SPU The number cannot be empty")
    private List<Long> spuIds;

    @Schema(description = "Products SKU Numbered array", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2]")
    @NotEmpty(message = "Products SKU The number cannot be empty")
    private List<Long> skuIds;

    @Schema(description = "Array of category numbers", requiredMode = Schema.RequiredMode.REQUIRED, example = "[10, 20]")
    @NotEmpty(message = "Category number cannot be empty")
    private List<Long> categoryIds;

}
