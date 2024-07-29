package cn.econets.blossom.module.product.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Newly added product categories/Update Request VO")
@Data
public class ProductCategorySaveReqVO {

    @Schema(description = "Classification number", example = "2")
    private Long id;

    @Schema(description = "Parent category number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The parent category number cannot be empty")
    private Long parentId;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Office Supplies")
    @NotBlank(message = "Category name cannot be empty")
    private String name;

    @Schema(description = "Mobile terminal classification map", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "The mobile classification map cannot be empty")
    private String picUrl;

    @Schema(description = "Category sorting", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;

    @Schema(description = "Open status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Open status cannot be empty")
    private Integer status;

    @Schema(description = "Category description", example = "Description")
    private String description;

}
