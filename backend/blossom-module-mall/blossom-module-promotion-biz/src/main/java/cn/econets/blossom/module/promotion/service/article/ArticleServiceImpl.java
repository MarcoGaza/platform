package cn.econets.blossom.module.promotion.service.article;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticlePageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.app.article.vo.article.AppArticlePageReqVO;
import cn.econets.blossom.module.promotion.convert.article.ArticleConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleCategoryDO;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleDO;
import cn.econets.blossom.module.promotion.dal.mysql.article.ArticleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.ARTICLE_CATEGORY_NOT_EXISTS;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.ARTICLE_NOT_EXISTS;

/**
 * Article Management Service Implementation class
 *
 */
@Service
@Validated
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleCategoryService articleCategoryService;

    @Override
    public Long createArticle(ArticleCreateReqVO createReqVO) {
        // Check that the category exists
        validateArticleCategoryExists(createReqVO.getCategoryId());

        // Insert
        ArticleDO article = ArticleConvert.INSTANCE.convert(createReqVO);
        article.setBrowseCount(0); // Initial page views
        articleMapper.insert(article);
        // Return
        return article.getId();
    }

    @Override
    public void updateArticle(ArticleUpdateReqVO updateReqVO) {
        // Check existence
        validateArticleExists(updateReqVO.getId());
        // Check that the category exists
        validateArticleCategoryExists(updateReqVO.getCategoryId());

        // Update
        ArticleDO updateObj = ArticleConvert.INSTANCE.convert(updateReqVO);
        articleMapper.updateById(updateObj);
    }

    @Override
    public void deleteArticle(Long id) {
        // Check existence
        validateArticleExists(id);
        // Delete
        articleMapper.deleteById(id);
    }

    private void validateArticleExists(Long id) {
        if (articleMapper.selectById(id) == null) {
            throw exception(ARTICLE_NOT_EXISTS);
        }
    }

    private void validateArticleCategoryExists(Long categoryId) {
        ArticleCategoryDO articleCategory = articleCategoryService.getArticleCategory(categoryId);
        if (articleCategory == null) {
            throw exception(ARTICLE_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public ArticleDO getArticle(Long id) {
        return articleMapper.selectById(id);
    }

    @Override
    public ArticleDO getLastArticleByTitle(String title) {
        List<ArticleDO> articles = articleMapper.selectListByTitle(title);
        return CollUtil.getLast(articles);
    }

    @Override
    public PageResult<ArticleDO> getArticlePage(ArticlePageReqVO pageReqVO) {
        return articleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ArticleDO> getArticleCategoryListByRecommend(Boolean recommendHot, Boolean recommendBanner) {
        return articleMapper.selectList(recommendHot, recommendBanner);
    }

    @Override
    public PageResult<ArticleDO> getArticlePage(AppArticlePageReqVO pageReqVO) {
        return articleMapper.selectPage(pageReqVO);
    }

    @Override
    public Long getArticleCountByCategoryId(Long categoryId) {
        return articleMapper.selectCount(ArticleDO::getCategoryId, categoryId);
    }

    @Override
    public void addArticleBrowseCount(Long id) {
        // Check if the article exists
        validateArticleExists(id);
        // Increase the number of views
        articleMapper.updateBrowseCount(id);
    }

}
