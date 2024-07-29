package cn.econets.blossom.module.promotion.controller.admin.article.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Article Category Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class ArticleCategoryBaseVO {

    @Schema(description = "Article Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Second kill")
    @NotNull(message = "Article category name cannot be empty")
    private String name;

    @Schema(description = "Icon address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    private String picUrl;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

}
