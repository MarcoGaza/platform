package cn.econets.blossom.module.product.controller.app.spu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Products SPU Response VO")
@Data
public class AppProductSpuRespVO {

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Product Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "Introduction to cool short-sleeved shirts")
    private String introduction;

    @Schema(description = "Classification Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(description = "Product cover image", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picUrl;

    @Schema(description = "Product Carousel", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> sliderPicUrls;

    // ========== SKU Related fields =========

    @Schema(description = "Specification type", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean specType;

    @Schema(description = "Product price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer price;

    @Schema(description = "Market price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer marketPrice;

    @Schema(description = "VIP Price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "968") // Pass the membership level，Calculate the discounted price
    private Integer vipPrice;

    @Schema(description = "Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private Integer stock;

    // ========== Marketing related fields =========

    // ========== Statistics related fields =========

    @Schema(description = "Product sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer salesCount;

}
