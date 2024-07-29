package cn.econets.blossom.module.promotion.service.article;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.category.ArticleCategoryUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.article.ArticleCategoryConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleCategoryDO;
import cn.econets.blossom.module.promotion.dal.mysql.article.ArticleCategoryMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.ARTICLE_CATEGORY_DELETE_FAIL_HAVE_ARTICLES;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.ARTICLE_CATEGORY_NOT_EXISTS;

/**
 * Article Category Service Implementation class
 *
 */
@Service
@Validated
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Resource
    private ArticleCategoryMapper articleCategoryMapper;

    @Resource
    @Lazy // Delayed loading，Solve the circular dependency problem
    private ArticleService articleService;

    @Override
    public Long createArticleCategory(ArticleCategoryCreateReqVO createReqVO) {
        // Insert
        ArticleCategoryDO category = ArticleCategoryConvert.INSTANCE.convert(createReqVO);
        articleCategoryMapper.insert(category);
        // Return
        return category.getId();
    }

    @Override
    public void updateArticleCategory(ArticleCategoryUpdateReqVO updateReqVO) {
        // Check existence
        validateArticleCategoryExists(updateReqVO.getId());
        // Update
        ArticleCategoryDO updateObj = ArticleCategoryConvert.INSTANCE.convert(updateReqVO);
        articleCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteArticleCategory(Long id) {
        // Check existence
        validateArticleCategoryExists(id);
        // Check if there are related articles
        Long count = articleService.getArticleCountByCategoryId(id);
        if (count > 0) {
            throw exception(ARTICLE_CATEGORY_DELETE_FAIL_HAVE_ARTICLES);
        }

        // Delete
        articleCategoryMapper.deleteById(id);
    }

    private void validateArticleCategoryExists(Long id) {
        if (articleCategoryMapper.selectById(id) == null) {
            throw exception(ARTICLE_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public ArticleCategoryDO getArticleCategory(Long id) {
        return articleCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<ArticleCategoryDO> getArticleCategoryPage(ArticleCategoryPageReqVO pageReqVO) {
        return articleCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ArticleCategoryDO> getArticleCategoryListByStatus(Integer status) {
        return articleCategoryMapper.selectListByStatus(status);
    }

}
