package cn.econets.blossom.module.promotion.controller.admin.article;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.*;
import cn.econets.blossom.module.promotion.convert.article.ArticleCategoryConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleCategoryDO;
import cn.econets.blossom.module.promotion.service.article.ArticleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Article Category")
@RestController
@RequestMapping("/promotion/article-category")
@Validated
public class ArticleCategoryController {

    @Resource
    private ArticleCategoryService articleCategoryService;

    @PostMapping("/create")
    @Operation(summary = "Create article category")
    @PreAuthorize("@ss.hasPermission('promotion:article-category:create')")
    public CommonResult<Long> createArticleCategory(@Valid @RequestBody ArticleCategoryCreateReqVO createReqVO) {
        return success(articleCategoryService.createArticleCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update article categories")
    @PreAuthorize("@ss.hasPermission('promotion:article-category:update')")
    public CommonResult<Boolean> updateArticleCategory(@Valid @RequestBody ArticleCategoryUpdateReqVO updateReqVO) {
        articleCategoryService.updateArticleCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete article category")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:article-category:delete')")
    public CommonResult<Boolean> deleteArticleCategory(@RequestParam("id") Long id) {
        articleCategoryService.deleteArticleCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get article classification")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:article-category:query')")
    public CommonResult<ArticleCategoryRespVO> getArticleCategory(@RequestParam("id") Long id) {
        ArticleCategoryDO category = articleCategoryService.getArticleCategory(id);
        return success(ArticleCategoryConvert.INSTANCE.convert(category));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a list of simplified information about article categories", description = "Only include enabled article categories，Mainly used for front-end drop-down options")
    public CommonResult<List<ArticleCategorySimpleRespVO>> getSimpleDeptList() {
        // Get the category list，As long as it is turned on
        List<ArticleCategoryDO> list = articleCategoryService.getArticleCategoryListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // After sorting in descending order，Return to the front end
        list.sort(Comparator.comparing(ArticleCategoryDO::getSort).reversed());
        return success(ArticleCategoryConvert.INSTANCE.convertList03(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get article category pages")
    @PreAuthorize("@ss.hasPermission('promotion:article-category:query')")
    public CommonResult<PageResult<ArticleCategoryRespVO>> getArticleCategoryPage(@Valid ArticleCategoryPageReqVO pageVO) {
        PageResult<ArticleCategoryDO> pageResult = articleCategoryService.getArticleCategoryPage(pageVO);
        return success(ArticleCategoryConvert.INSTANCE.convertPage(pageResult));
    }

}
