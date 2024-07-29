package cn.econets.blossom.module.promotion.service.article;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleCategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Article Category Service Interface
 *
 */
public interface ArticleCategoryService {

    /**
     * Create article category
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createArticleCategory(@Valid ArticleCategoryCreateReqVO createReqVO);

    /**
     * Update article categories
     *
     * @param updateReqVO Update information
     */
    void updateArticleCategory(@Valid ArticleCategoryUpdateReqVO updateReqVO);

    /**
     * Delete article category
     *
     * @param id Number
     */
    void deleteArticleCategory(Long id);

    /**
     * Get article classification
     *
     * @param id Number
     * @return Article Category
     */
    ArticleCategoryDO getArticleCategory(Long id);

    /**
     * Get article category pages
     *
     * @param pageReqVO Paged query
     * @return Article category paging
     */
    PageResult<ArticleCategoryDO> getArticleCategoryPage(ArticleCategoryPageReqVO pageReqVO);

    /**
     * Get the article category list of the specified status
     *
     * @param status Status
     * @return Article Category List
     */
    List<ArticleCategoryDO> getArticleCategoryListByStatus(Integer status);

}
