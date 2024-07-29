package cn.econets.blossom.module.product.controller.admin.spu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Management Backend - Products SKU Response VO")
@Data
public class ProductSkuRespVO {

    @Schema(description = "Primary Key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Products SKU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short sleeves")
    private String name;

    @Schema(description = "Sales Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    private Integer price;

    @Schema(description = "Market price", example = "2999")
    private Integer marketPrice;

    @Schema(description = "Cost price", example = "19")
    private Integer costPrice;

    @Schema(description = "Barcode", example = "15156165456")
    private String barCode;

    @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    private String picUrl;

    @Schema(description = "Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer stock;

    @Schema(description = "Product weight,Unit：kg kilogram", example = "1.2")
    private Double weight;

    @Schema(description = "Product volume,Unit：m^3 square meters", example = "2.5")
    private Double volume;

    @Schema(description = "First-level distribution commission，Unit：Points", example = "199")
    private Integer firstBrokeragePrice;

    @Schema(description = "Second-level distribution commission，Unit：Points", example = "19")
    private Integer secondBrokeragePrice;

    @Schema(description = "Attribute array")
    private List<ProductSkuSaveReqVO.Property> properties;

}
