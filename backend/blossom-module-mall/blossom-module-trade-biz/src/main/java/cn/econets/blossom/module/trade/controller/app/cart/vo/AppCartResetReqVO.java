package cn.econets.blossom.module.trade.controller.app.cart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description = "User App - Shopping cart reset Request VO")
@Data
public class AppCartResetReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The number cannot be empty")
    private Long id;

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED,example = "1024")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Quantity of goods", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The quantity cannot be empty")
    @Min(message = "The quantity must be greater than 0", value = 1L)
    private Integer count;

}
