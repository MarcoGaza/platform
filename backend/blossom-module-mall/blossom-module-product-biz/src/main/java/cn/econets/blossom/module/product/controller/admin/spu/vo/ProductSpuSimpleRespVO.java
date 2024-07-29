package cn.econets.blossom.module.product.controller.admin.spu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "Management Backend - Products SPU Streamlined Response VO")
@Data
@ToString(callSuper = true)
public class ProductSpuSimpleRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "213")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cool short sleeves")
    private String name;

    @Schema(description = "Product price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1999")
    private Integer price;

    @Schema(description = "Commodity market price，Unit use：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "199")
    private Integer marketPrice;

    @Schema(description = "Commodity cost price，Unit use：points", requiredMode = Schema.RequiredMode.REQUIRED, example = "19")
    private Integer costPrice;

    @Schema(description = "Product Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    private Integer stock;

    // ========== Statistics related fields =========

    @Schema(description = "Product sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer salesCount;

    @Schema(description = "Product virtual sales", requiredMode = Schema.RequiredMode.REQUIRED, example = "20000")
    private Integer virtualSalesCount;

    @Schema(description = "Product views", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    private Integer browseCount;

}
