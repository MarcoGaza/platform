package cn.econets.blossom.module.product.controller.app.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "User APP - Product Categories Response VO")
public class AppCategoryRespVO {

    @Schema(description = "Classification number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Long id;

    @Schema(description = "Parent category number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The parent category number cannot be empty")
    private Long parentId;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Office Supplies")
    @NotBlank(message = "Category name cannot be empty")
    private String name;

    @Schema(description = "Classified pictures", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Category image cannot be empty")
    private String picUrl;

}
