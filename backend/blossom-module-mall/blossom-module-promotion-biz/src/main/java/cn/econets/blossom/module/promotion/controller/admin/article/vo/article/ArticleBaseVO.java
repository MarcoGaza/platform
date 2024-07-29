package cn.econets.blossom.module.promotion.controller.admin.article.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Article Management Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class ArticleBaseVO {

    @Schema(description = "Article Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15458")
    @NotNull(message = "The article category number cannot be empty")
    private Long categoryId;

    @Schema(description = "Related product number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22378")
    @NotNull(message = "Related products cannot be empty")
    private Long spuId;

    @Schema(description = "Article Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is a title")
    @NotNull(message = "The article title cannot be empty")
    private String title;

    @Schema(description = "Article author", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    private String author;

    @Schema(description = "Article cover image address", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn")
    @NotNull(message = "The article cover image address cannot be empty")
    private String picUrl;

    @Schema(description = "Article Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is a brief introduction")
    private String introduction;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Is it popular?(Mini Program)", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Is it popular?(Mini Program)Cannot be empty")
    private Boolean recommendHot;

    @Schema(description = "Whether to play the pictures in rotation(Mini Program)", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to play the pictures in rotation(Mini Program)Cannot be empty")
    private Boolean recommendBanner;

    @Schema(description = "Article content", requiredMode = Schema.RequiredMode.REQUIRED, example = "This is the content of the article")
    @NotNull(message = "The article content cannot be empty")
    private String content;

}
