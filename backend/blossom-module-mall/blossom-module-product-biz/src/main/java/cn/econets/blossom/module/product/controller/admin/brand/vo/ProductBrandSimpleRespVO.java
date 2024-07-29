package cn.econets.blossom.module.product.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Management Backend - Brand simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBrandSimpleRespVO {

    @Schema(description = "Brand Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Brand Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Apple")
    private String name;

}
