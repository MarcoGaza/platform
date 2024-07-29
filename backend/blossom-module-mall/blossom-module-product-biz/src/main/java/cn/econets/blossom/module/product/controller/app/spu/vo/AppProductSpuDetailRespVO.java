package cn.econets.blossom.module.product.controller.app.spu.vo;

import cn.econets.blossom.module.product.controller.app.property.vo.value.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Products SPU Details Response VO")
@Data
public class AppProductSpuDetailRespVO {

    @Schema(description = "Products SPU Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    // ========== Basic Information =========

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Product Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am a happy profile")
    private String introduction;

    @Schema(description = "Product details", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am the product description")
    private String description;

    @Schema(description = "Product Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long categoryId;

    @Schema(description = "Product cover image", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picUrl;

    @Schema(description = "Product Carousel", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> sliderPicUrls;

    // ========== Marketing related fields =========

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

    /**
     * SKU Array
     */
    private List<Sku> skus;

    // ========== Statistics related fields =========

    @Schema(description = "Product Sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer salesCount;

    @Schema(description = "User App - Products SPU Detailed SKU Information")
    @Data
    public static class Sku {

        @Schema(description = "Products SKU Number", example = "1")
        private Long id;

        /**
         * Product attribute array
         */
        private List<AppProductPropertyValueDetailRespVO> properties;

        @Schema(description = "Sales Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Integer price;

        @Schema(description = "Market price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Integer marketPrice;

        @Schema(description = "VIP Price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "968") // Pass the membership level，Calculate the discounted price
        private Integer vipPrice;

        @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
        private String picUrl;

        @Schema(description = "Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer stock;

        @Schema(description = "Product weight", example = "1") // Unit：kg kilogram
        private Double weight;

        @Schema(description = "Product volume", example = "1024") // Unit：m^3 square meters
        private Double volume;

    }

}
