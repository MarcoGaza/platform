package cn.econets.blossom.module.product.controller.app.favorite.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "User APP - Single item in the collection Request VO") // For collection、Cancel favorites、Get Collection
@Data
public class AppFavoriteReqVO {

    @Schema(description = "Products SPU Number", requiredMode = REQUIRED, example = "29502")
    @NotNull(message = "Products SPU The number cannot be empty")
    private Long spuId;

}
