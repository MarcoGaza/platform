package cn.econets.blossom.module.product.controller.admin.spu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Products SKU Create/Update Request VO")
@Data
public class ProductSkuSaveReqVO {

    @Schema(description = "Products SKU Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short sleeves")
    @NotEmpty(message = "Products SKU The name cannot be empty")
    private String name;

    @Schema(description = "Sales Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    @NotNull(message = "Sales Price，Unit：The score cannot be empty")
    private Integer price;

    @Schema(description = "Market price", example = "2999")
    private Integer marketPrice;

    @Schema(description = "Cost price", example = "19")
    private Integer costPrice;

    @Schema(description = "Barcode", example = "15156165456")
    private String barCode;

    @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    @NotNull(message = "The image address cannot be empty")
    private String picUrl;

    @Schema(description = "Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    @NotNull(message = "Inventory cannot be empty")
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
    private List<Property> properties;

    @Schema(description = "Product attributes")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {

        @Schema(description = "Property number", example = "10")
        private Long propertyId;

        @Schema(description = "Attribute name", example = "Color")
        private String propertyName;

        @Schema(description = "Property value number", example = "10")
        private Long valueId;

        @Schema(description = "Attribute value name", example = "Red")
        private String valueName;

    }

}
