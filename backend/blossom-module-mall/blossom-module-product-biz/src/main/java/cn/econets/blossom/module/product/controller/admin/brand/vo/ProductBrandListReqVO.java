package cn.econets.blossom.module.product.controller.admin.brand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Product Brand Pagination Request VO")
@Data
public class ProductBrandListReqVO {

    @Schema(description = "Brand Name", example = "Apple")
    private String name;

}
