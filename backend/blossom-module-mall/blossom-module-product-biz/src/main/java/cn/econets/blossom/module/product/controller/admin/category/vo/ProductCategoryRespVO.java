package cn.econets.blossom.module.product.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Product Categories Response VO")
@Data
public class ProductCategoryRespVO {

    @Schema(description = "Classification number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long id;

    @Schema(description = "Parent category number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long parentId;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Office Supplies")
    private String name;

    @Schema(description = "Mobile terminal classification map", requiredMode = Schema.RequiredMode.REQUIRED)
    private String picUrl;

    @Schema(description = "Category sorting", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;

    @Schema(description = "Open status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer status;

    @Schema(description = "Category description", example = "Description")
    private String description;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
