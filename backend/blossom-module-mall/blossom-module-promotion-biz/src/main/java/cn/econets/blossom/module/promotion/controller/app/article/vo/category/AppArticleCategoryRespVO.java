package cn.econets.blossom.module.promotion.controller.app.article.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Application App - Article Category Response VO")
@Data
public class AppArticleCategoryRespVO {

    @Schema(description = "Classification Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Category Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Technology")
    private String name;

    @Schema(description = "Category Icon", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

}
