package cn.econets.blossom.module.promotion.controller.app.article.vo.article;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Application App - Article pagination Request VO")
@Data
public class AppArticlePageReqVO extends PageParam {

    @Schema(description = "Classification Number", example = "2048")
    private Long categoryId;

}
