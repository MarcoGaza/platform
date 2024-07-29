package cn.econets.blossom.module.promotion.controller.app.article;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.promotion.controller.app.article.vo.category.AppArticleCategoryRespVO;
import cn.econets.blossom.module.promotion.convert.article.ArticleCategoryConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleCategoryDO;
import cn.econets.blossom.module.promotion.service.article.ArticleCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User APP - Article Category")
@RestController
@RequestMapping("/promotion/article-category")
@Validated
public class AppArticleCategoryController {

    @Resource
    private ArticleCategoryService articleCategoryService;

    @RequestMapping("/list")
    @Operation(summary = "Get article category list")
    public CommonResult<List<AppArticleCategoryRespVO>> getArticleCategoryList() {
        List<ArticleCategoryDO> categoryList = articleCategoryService.getArticleCategoryListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        categoryList.sort(Comparator.comparing(ArticleCategoryDO::getSort)); // Press sort Sort in descending order
        return success(ArticleCategoryConvert.INSTANCE.convertList04(categoryList));
    }

}
