package cn.econets.blossom.module.product.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* Product Brand Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class ProductBrandBaseVO {

    @Schema(description = "Brand Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Apple")
    @NotNull(message = "Brand name cannot be empty")
    private String name;

    @Schema(description = "Brand picture", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Brand image cannot be empty")
    private String picUrl;

    @Schema(description = "Brand sorting", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Brand sorting cannot be empty")
    private Integer sort;

    @Schema(description = "Brand Description", example = "Description")
    private String description;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

}
