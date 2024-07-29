package cn.econets.blossom.module.promotion.controller.admin.article.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Article management update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleUpdateReqVO extends ArticleBaseVO {

    @Schema(description = "Article number", requiredMode = Schema.RequiredMode.REQUIRED, example = "8606")
    @NotNull(message = "The article number cannot be empty")
    private Long id;

}
