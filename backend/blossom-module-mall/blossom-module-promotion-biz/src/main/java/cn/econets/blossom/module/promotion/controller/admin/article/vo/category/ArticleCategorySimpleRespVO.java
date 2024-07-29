package cn.econets.blossom.module.promotion.controller.admin.article.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Article classification simplified information Response VO")
@Data
public class ArticleCategorySimpleRespVO {

    @Schema(description = "Article Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "19490")
    private Long id;

    @Schema(description = "Article Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Second kill")
    private String name;

}
