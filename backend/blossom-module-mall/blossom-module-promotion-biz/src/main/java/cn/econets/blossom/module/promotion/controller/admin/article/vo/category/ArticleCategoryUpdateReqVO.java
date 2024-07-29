package cn.econets.blossom.module.promotion.controller.admin.article.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Article category update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleCategoryUpdateReqVO extends ArticleCategoryBaseVO {

    @Schema(description = "Article Category Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "19490")
    @NotNull(message = "The article category number cannot be empty")
    private Long id;

}
