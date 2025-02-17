package cn.econets.blossom.module.promotion.controller.admin.article;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticlePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleRespVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.article.ArticleConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleDO;
import cn.econets.blossom.module.promotion.service.article.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Article Management")
@RestController
@RequestMapping("/promotion/article")
@Validated
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/create")
    @Operation(summary = "Create article management")
    @PreAuthorize("@ss.hasPermission('promotion:article:create')")
    public CommonResult<Long> createArticle(@Valid @RequestBody ArticleCreateReqVO createReqVO) {
        return success(articleService.createArticle(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update article management")
    @PreAuthorize("@ss.hasPermission('promotion:article:update')")
    public CommonResult<Boolean> updateArticle(@Valid @RequestBody ArticleUpdateReqVO updateReqVO) {
        articleService.updateArticle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete article management")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:article:delete')")
    public CommonResult<Boolean> deleteArticle(@RequestParam("id") Long id) {
        articleService.deleteArticle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get article management")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:article:query')")
    public CommonResult<ArticleRespVO> getArticle(@RequestParam("id") Long id) {
        ArticleDO article = articleService.getArticle(id);
        return success(ArticleConvert.INSTANCE.convert(article));
    }

    @GetMapping("/page")
    @Operation(summary = "Get article management paging")
    @PreAuthorize("@ss.hasPermission('promotion:article:query')")
    public CommonResult<PageResult<ArticleRespVO>> getArticlePage(@Valid ArticlePageReqVO pageVO) {
        PageResult<ArticleDO> pageResult = articleService.getArticlePage(pageVO);
        return success(ArticleConvert.INSTANCE.convertPage(pageResult));
    }

}
