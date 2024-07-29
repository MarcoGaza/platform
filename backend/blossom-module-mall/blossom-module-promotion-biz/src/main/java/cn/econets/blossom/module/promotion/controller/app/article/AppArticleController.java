package cn.econets.blossom.module.promotion.controller.app.article;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.promotion.controller.app.article.vo.article.AppArticlePageReqVO;
import cn.econets.blossom.module.promotion.controller.app.article.vo.article.AppArticleRespVO;
import cn.econets.blossom.module.promotion.convert.article.ArticleConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleDO;
import cn.econets.blossom.module.promotion.service.article.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User APP - Article")
@RestController
@RequestMapping("/promotion/article")
@Validated
public class AppArticleController {

    @Resource
    private ArticleService articleService;

    @RequestMapping("/list")
    @Operation(summary = "Get article details list")
    @Parameters({
            @Parameter(name = "recommendHot", description = "Is it popular?", example = "false"), // Scene 1：View the specified article
            @Parameter(name = "recommendBanner", description = "Whether to play the pictures in rotation", example = "false") // Scene 2：View the specified article
    })
    public CommonResult<List<AppArticleRespVO>> getArticleList(
            @RequestParam(value = "recommendHot", required = false) Boolean recommendHot,
            @RequestParam(value = "recommendBanner", required = false) Boolean recommendBanner) {
        return success(ArticleConvert.INSTANCE.convertList03(
                articleService.getArticleCategoryListByRecommend(recommendHot, recommendBanner)));
    }

    @RequestMapping("/page")
    @Operation(summary = "Get article details page")
    public CommonResult<PageResult<AppArticleRespVO>> getArticlePage(AppArticlePageReqVO pageReqVO) {
        return success(ArticleConvert.INSTANCE.convertPage02(articleService.getArticlePage(pageReqVO)));
    }

    @RequestMapping("/get")
    @Operation(summary = "Get article details")
    @Parameters({
            @Parameter(name = "id", description = "Article number", example = "1024"),
            @Parameter(name = "title", description = "Article Title", example = "1024"),
    })
    public CommonResult<AppArticleRespVO> getArticle(@RequestParam(value = "id", required = false) Long id,
                                                     @RequestParam(value = "title", required = false) String title) {
        ArticleDO article = id != null ? articleService.getArticle(id)
                : articleService.getLastArticleByTitle(title);
        return success(BeanUtils.toBean(article, AppArticleRespVO.class));
    }

    @PutMapping("/add-browse-count")
    @Operation(summary = "Increase article views")
    @Parameter(name = "id", description = "Article number", example = "1024")
    public CommonResult<Boolean> addBrowseCount(@RequestParam("id") Long id) {
        articleService.addArticleBrowseCount(id);
        return success(true);
    }

}
