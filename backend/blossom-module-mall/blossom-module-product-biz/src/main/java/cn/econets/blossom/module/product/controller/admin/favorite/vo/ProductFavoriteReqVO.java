package cn.econets.blossom.module.product.controller.admin.favorite.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Single item in the collection Response VO")
@Data
@ToString(callSuper = true)
public class ProductFavoriteReqVO extends  ProductFavoriteBaseVO {

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "32734")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;
}
