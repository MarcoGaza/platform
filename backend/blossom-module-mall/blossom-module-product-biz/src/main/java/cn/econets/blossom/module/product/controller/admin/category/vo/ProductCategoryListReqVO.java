package cn.econets.blossom.module.product.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;

@Schema(description = "Management Backend - Product Category List Query Request VO")
@Data
public class ProductCategoryListReqVO {

    @Schema(description = "Category Name", example = "Office Supplies")
    private String name;

    @Schema(description = "Open status", example = "0")
    private Integer status;

    @Schema(description = "Parent category number", example = "1")
    private Long parentId;

    @Schema(description = "Parent category number array", example = "1,2,3")
    private Collection<Long> parentIds;

}
