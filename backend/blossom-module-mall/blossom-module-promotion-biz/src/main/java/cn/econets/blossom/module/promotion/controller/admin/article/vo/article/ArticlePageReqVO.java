package cn.econets.blossom.module.promotion.controller.admin.article.vo.article;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Article management paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArticlePageReqVO extends PageParam {

    @Schema(description = "Article Category Number", example = "15458")
    private Long categoryId;

    @Schema(description = "Related product number", example = "22378")
    private Long spuId;

    @Schema(description = "Article Title")
    private String title;

    @Schema(description = "Article author")
    private String author;

    @Schema(description = "Status", example = "2")
    private Integer status;

    @Schema(description = "Is it popular?(Mini Program)")
    private Boolean recommendHot;

    @Schema(description = "Whether to play the pictures in rotation(Mini Program)")
    private Boolean recommendBanner;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
