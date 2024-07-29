package cn.econets.blossom.module.promotion.service.article;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticlePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.app.article.vo.article.AppArticlePageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Article Service Interface
 *
 */
public interface ArticleService {

    /**
     * Create article
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createArticle(@Valid ArticleCreateReqVO createReqVO);

    /**
     * Update article
     *
     * @param updateReqVO Update information
     */
    void updateArticle(@Valid ArticleUpdateReqVO updateReqVO);

    /**
     * Delete article
     *
     * @param id Number
     */
    void deleteArticle(Long id);

    /**
     * Get the article
     *
     * @param id Number
     * @return Article
     */
    ArticleDO getArticle(Long id);

    /**
     * Based on title，Get the article
     *
     * If there are articles with the same name，Get the last published
     *
     * @param title Title
     * @return Article
     */
    ArticleDO getLastArticleByTitle(String title);

    /**
     * Get article paging
     *
     * @param pageReqVO Paged query
     * @return Article pagination
     */
    PageResult<ArticleDO> getArticlePage(ArticlePageReqVO pageReqVO);

    /**
     * Get article list
     *
     * @param recommendHot    Is it popular?
     * @param recommendBanner Whether to play the pictures in rotation
     * @return Article List
     */
    List<ArticleDO> getArticleCategoryListByRecommend(Boolean recommendHot, Boolean recommendBanner);

    /**
     * Get article paging
     *
     * @param pageReqVO Paged query
     * @return Article pagination
     */
    PageResult<ArticleDO> getArticlePage(AppArticlePageReqVO pageReqVO);

    /**
     * Get the number of articles in the specified category
     *
     * @param categoryId Article Category Number
     * @return Number of articles
     */
    Long getArticleCountByCategoryId(Long categoryId);

    /**
     * Increase article views
     *
     * @param id Article number
     */
    void addArticleBrowseCount(Long id);

}
