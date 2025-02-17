package cn.econets.blossom.module.product.dal.mysql.comment;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.product.controller.admin.comment.vo.ProductCommentPageReqVO;
import cn.econets.blossom.module.product.controller.app.comment.vo.AppCommentPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.comment.ProductCommentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductCommentMapper extends BaseMapperX<ProductCommentDO> {

    default PageResult<ProductCommentDO> selectPage(ProductCommentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductCommentDO>()
                .likeIfPresent(ProductCommentDO::getUserNickname, reqVO.getUserNickname())
                .eqIfPresent(ProductCommentDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(ProductCommentDO::getSpuId, reqVO.getSpuId())
                .eqIfPresent(ProductCommentDO::getScores, reqVO.getScores())
                .eqIfPresent(ProductCommentDO::getReplyStatus, reqVO.getReplyStatus())
                .betweenIfPresent(ProductCommentDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(ProductCommentDO::getSpuName, reqVO.getSpuName())
                .orderByDesc(ProductCommentDO::getId));
    }

    static void appendTabQuery(LambdaQueryWrapperX<ProductCommentDO> queryWrapper, Integer type) {
        LambdaQueryWrapperX<ProductCommentDO> queryWrapperX = new LambdaQueryWrapperX<>();
        // Build a positive review query statement：Good reviews calculation Overall Review >= 4
        if (ObjectUtil.equal(type, AppCommentPageReqVO.GOOD_COMMENT)) {
            queryWrapperX.ge(ProductCommentDO::getScores, 4);
        }
        // Build mid-term review query statement：Zhongping Calculation Overall Review >= 3 and Overall Review < 4
        if (ObjectUtil.equal(type, AppCommentPageReqVO.MEDIOCRE_COMMENT)) {
            queryWrapperX.ge(ProductCommentDO::getScores, 3);
            queryWrapperX.lt(ProductCommentDO::getScores, 4);
        }
        // Build a negative review query statement：Bad review calculation Overall Review < 3
        if (ObjectUtil.equal(type, AppCommentPageReqVO.NEGATIVE_COMMENT)) {
            queryWrapperX.lt(ProductCommentDO::getScores, 3);
        }
    }

    default PageResult<ProductCommentDO> selectPage(AppCommentPageReqVO reqVO, Boolean visible) {
        LambdaQueryWrapperX<ProductCommentDO> queryWrapper = new LambdaQueryWrapperX<ProductCommentDO>()
                .eqIfPresent(ProductCommentDO::getSpuId, reqVO.getSpuId())
                .eqIfPresent(ProductCommentDO::getVisible, visible);
        // Build evaluation query statement
        appendTabQuery(queryWrapper, reqVO.getType());
        // Sort by review time, with the latest one at the front
        queryWrapper.orderByDesc(ProductCommentDO::getCreateTime);
        return selectPage(reqVO, queryWrapper);
    }

    default ProductCommentDO selectByUserIdAndOrderItemId(Long userId, Long orderItemId) {
        return selectOne(new LambdaQueryWrapperX<ProductCommentDO>()
                .eq(ProductCommentDO::getUserId, userId)
                .eq(ProductCommentDO::getOrderItemId, orderItemId));
    }

}
