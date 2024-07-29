package cn.econets.blossom.module.promotion.dal.mysql.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.promotion.controller.admin.coupon.vo.template.CouponTemplatePageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * Coupon Template Mapper
 *
 */
@Mapper
public interface CouponTemplateMapper extends BaseMapperX<CouponTemplateDO> {

    default PageResult<CouponTemplateDO> selectPage(CouponTemplatePageReqVO reqVO) {
        // Construct query conditions for collection
        Consumer<LambdaQueryWrapper<CouponTemplateDO>> canTakeConsumer = buildCanTakeQueryConsumer(reqVO.getCanTakeTypes());
        // Execute paging query
        return selectPage(reqVO, new LambdaQueryWrapperX<CouponTemplateDO>()
                .likeIfPresent(CouponTemplateDO::getName, reqVO.getName())
                .eqIfPresent(CouponTemplateDO::getStatus, reqVO.getStatus())
                .eqIfPresent(CouponTemplateDO::getDiscountType, reqVO.getDiscountType())
                .betweenIfPresent(CouponTemplateDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(CouponTemplateDO::getProductScope, reqVO.getProductScope())
                .and(reqVO.getProductScopeValue() != null, w -> w.apply("FIND_IN_SET({0}, product_scope_values)",
                        reqVO.getProductScopeValue()))
                .and(canTakeConsumer != null, canTakeConsumer)
                .orderByDesc(CouponTemplateDO::getId));
    }

    default void updateTakeCount(Long id, Integer incrCount) {
        update(null, new LambdaUpdateWrapper<CouponTemplateDO>()
                .eq(CouponTemplateDO::getId, id)
                .setSql("take_count = take_count + " + incrCount));
    }

    default List<CouponTemplateDO> selectListByTakeType(Integer takeType) {
        return selectList(CouponTemplateDO::getTakeType, takeType);
    }

    default List<CouponTemplateDO> selectList(List<Integer> canTakeTypes, Integer productScope, Long productScopeValue, Integer count) {
        // Construct the query conditions that can be collected
        Consumer<LambdaQueryWrapper<CouponTemplateDO>> canTakeConsumer = buildCanTakeQueryConsumer(canTakeTypes);
        return selectList(new LambdaQueryWrapperX<CouponTemplateDO>()
                .eqIfPresent(CouponTemplateDO::getProductScope, productScope)
                .and(productScopeValue != null, w -> w.apply("FIND_IN_SET({0}, product_scope_values)",
                        productScopeValue))
                .and(canTakeConsumer != null, canTakeConsumer)
                .last(" LIMIT " + count)
                .orderByDesc(CouponTemplateDO::getId));
    }

    static Consumer<LambdaQueryWrapper<CouponTemplateDO>> buildCanTakeQueryConsumer(List<Integer> canTakeTypes) {
        Consumer<LambdaQueryWrapper<CouponTemplateDO>> canTakeConsumer = null;
        if (CollUtil.isNotEmpty(canTakeTypes)) {
            canTakeConsumer = w ->
                    w.eq(CouponTemplateDO::getStatus, CommonStatusEnum.ENABLE.getStatus()) // 1. The status is available
                            .in(CouponTemplateDO::getTakeType, canTakeTypes) // 2. The collection method is the same
                            .and(ww -> ww.isNull(CouponTemplateDO::getValidEndTime)  // 3. Not expired
                                    .or().gt(CouponTemplateDO::getValidEndTime, LocalDateTime.now()))
                            .apply(" take_count < total_count "); // 4. The remaining quantity is greater than 0
        }
        return canTakeConsumer;
    }

}
