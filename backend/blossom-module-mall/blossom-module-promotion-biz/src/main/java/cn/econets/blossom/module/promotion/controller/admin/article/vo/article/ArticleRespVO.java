package cn.econets.blossom.module.promotion.controller.admin.article.vo.article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Article Management Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticleRespVO extends ArticleBaseVO {

    @Schema(description = "Article number", requiredMode = Schema.RequiredMode.REQUIRED, example = "8606")
    private Long id;

    @Schema(description = "Views", requiredMode = Schema.RequiredMode.REQUIRED, example = "99999")
    private Integer browseCount;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
