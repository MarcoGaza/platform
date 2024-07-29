package cn.econets.blossom.module.product.controller.app.favorite.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "User App - Product Collection Response VO")
@Data
public class AppFavoriteRespVO {

    @Schema(description = "Number", requiredMode = REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Products SPU Number", requiredMode = REQUIRED, example = "29502")
    private Long spuId;

    // ========== Product related fields ==========

    @Schema(description = "Products SPU Name", example = "Zhao Liu")
    private String spuName;

    @Schema(description = "Product cover image", example = "https://domain/pic.png")
    private String picUrl;

    @Schema(description = "Product price", example = "100")
    private Integer price;

}
