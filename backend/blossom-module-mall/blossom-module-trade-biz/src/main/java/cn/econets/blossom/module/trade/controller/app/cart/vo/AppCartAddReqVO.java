package cn.econets.blossom.module.trade.controller.app.cart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Add shopping items to the shopping cart Request VO")
@Data
public class AppCartAddReqVO {

    @Schema(description = "Products SKU Number", requiredMode = Schema.RequiredMode.REQUIRED,example = "1024")
    @NotNull(message = "Products SKU The number cannot be empty")
    private Long skuId;

    @Schema(description = "Number of new products", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The quantity cannot be empty")
    private Integer count;

}
