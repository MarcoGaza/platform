package cn.econets.blossom.module.promotion.convert.article;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleRespVO;
import cn.econets.blossom.module.promotion.controller.admin.article.vo.article.ArticleUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.app.article.vo.article.AppArticleRespVO;
import cn.econets.blossom.module.promotion.dal.dataobject.article.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Article Management Convert
 *
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleDO convert(ArticleCreateReqVO bean);

    ArticleDO convert(ArticleUpdateReqVO bean);

    ArticleRespVO convert(ArticleDO bean);

    List<ArticleRespVO> convertList(List<ArticleDO> list);

    PageResult<ArticleRespVO> convertPage(PageResult<ArticleDO> page);

    AppArticleRespVO convert01(ArticleDO article);

    PageResult<AppArticleRespVO> convertPage02(PageResult<ArticleDO> articlePage);

    List<AppArticleRespVO> convertList03(List<ArticleDO> articleCategoryListByRecommendHotAndRecommendBanner);

}
