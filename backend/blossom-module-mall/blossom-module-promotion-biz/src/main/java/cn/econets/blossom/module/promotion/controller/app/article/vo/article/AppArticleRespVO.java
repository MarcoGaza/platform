package cn.econets.blossom.module.promotion.controller.app.article.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Application App - Article Response VO")
@Data
public class AppArticleRespVO {

    @Schema(description = "Article number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Article Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code - Promotion Module")
    private String title;

    @Schema(description = "Article author", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    private String author;

    @Schema(description = "Classification number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long categoryId;

    @Schema(description = "Image and text cover", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String picUrl;

    @Schema(description = "Article Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "My profile")
    private String introduction;

    @Schema(description = "Article content", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am detailed")
    private String content;

    @Schema(description = "Release time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Views", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer browseCount;

    @Schema(description = "Related products SPU Number", example = "1024")
    private Long spuId;

}
