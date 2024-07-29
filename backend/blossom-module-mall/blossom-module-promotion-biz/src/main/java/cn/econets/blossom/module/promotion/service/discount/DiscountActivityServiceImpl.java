package cn.econets.blossom.module.promotion.service.discount;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityBaseVO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.discount.DiscountActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountProductDO;
import cn.econets.blossom.module.promotion.dal.mysql.discount.DiscountActivityMapper;
import cn.econets.blossom.module.promotion.dal.mysql.discount.DiscountProductMapper;
import cn.econets.blossom.module.promotion.enums.common.PromotionActivityStatusEnum;
import cn.econets.blossom.module.promotion.util.PromotionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Limited time discount Service Implementation class
 *
 */
@Service
@Validated
public class DiscountActivityServiceImpl implements DiscountActivityService {

    @Resource
    private DiscountActivityMapper discountActivityMapper;
    @Resource
    private DiscountProductMapper discountProductMapper;

    @Override
    public List<DiscountProductDO> getMatchDiscountProductList(Collection<Long> skuIds) {
        // TODO Can we go directly here? return discountProductMapper.getMatchDiscountProductList(skuIds)； Generally speaking，If idea Report“Yellow”Warning，Try to deal with them all；The principle is，All warnings，All are abnormal（Error），This will allow you to write better code。
        List<DiscountProductDO> matchDiscountProductList = discountProductMapper.getMatchDiscountProductList(skuIds);
        return matchDiscountProductList;
    }

    @Override
    public Long createDiscountActivity(DiscountActivityCreateReqVO createReqVO) {
        // Check whether the product conflicts
        validateDiscountActivityProductConflicts(null, createReqVO.getProducts());

        // Insert activity
        DiscountActivityDO discountActivity = DiscountActivityConvert.INSTANCE.convert(createReqVO)
                // TODO Remove the call here，Force is enabled；
                .setStatus(PromotionUtils.calculateActivityStatus(createReqVO.getEndTime()));
        discountActivityMapper.insert(discountActivity);
        // Insert product
        // TODO activityStatus The best code，Also make some settings。
        List<DiscountProductDO> discountProducts = convertList(createReqVO.getProducts(),
                product -> DiscountActivityConvert.INSTANCE.convert(product).setActivityId(discountActivity.getId()));
        discountProductMapper.insertBatch(discountProducts);
        // Return
        return discountActivity.getId();
    }

    @Override
    public void updateDiscountActivity(DiscountActivityUpdateReqVO updateReqVO) {
        // Check existence
        DiscountActivityDO discountActivity = validateDiscountActivityExists(updateReqVO.getId());
        if (discountActivity.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) { // Closed event，Cannot be modified
            throw exception(DISCOUNT_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED);
        }
        // Check whether the product conflicts
        validateDiscountActivityProductConflicts(updateReqVO.getId(), updateReqVO.getProducts());

        // Update activity
        DiscountActivityDO updateObj = DiscountActivityConvert.INSTANCE.convert(updateReqVO)
                .setStatus(PromotionUtils.calculateActivityStatus(updateReqVO.getEndTime()));
        discountActivityMapper.updateById(updateObj);
        // Update product
        updateDiscountProduct(updateReqVO);
    }

    private void updateDiscountProduct(DiscountActivityUpdateReqVO updateReqVO) {
        // TODO The logic here，It can be optimized；Reference CombinationActivityServiceImpl of updateCombinationProduct，Mainly CollectionUtils.diffList Use Ha；
        //  Then originally it was used DiscountActivityConvert.INSTANCE.isEquals Comparison，Now let's see if the simplification is based on skuId Comparison is all；The previous writing was too detailed，Not very meaningful；
        List<DiscountProductDO> dbDiscountProducts = discountProductMapper.selectListByActivityId(updateReqVO.getId());
        // Calculate the records to be deleted
        List<Long> deleteIds = convertList(dbDiscountProducts, DiscountProductDO::getId,
                discountProductDO -> updateReqVO.getProducts().stream()
                        .noneMatch(product -> DiscountActivityConvert.INSTANCE.isEquals(discountProductDO, product)));
        if (CollUtil.isNotEmpty(deleteIds)) {
            discountProductMapper.deleteBatchIds(deleteIds);
        }
        // Calculate newly added records
        List<DiscountProductDO> newDiscountProducts = convertList(updateReqVO.getProducts(),
                product -> DiscountActivityConvert.INSTANCE.convert(product).setActivityId(updateReqVO.getId()));
        newDiscountProducts.removeIf(product -> dbDiscountProducts.stream().anyMatch(
                dbProduct -> DiscountActivityConvert.INSTANCE.isEquals(dbProduct, product))); // If matched，The description is updated
        if (CollectionUtil.isNotEmpty(newDiscountProducts)) {
            discountProductMapper.insertBatch(newDiscountProducts);
        }
    }

    /**
     * Check whether the product conflicts
     *
     * @param id       Number
     * @param products Product List
     */
    private void validateDiscountActivityProductConflicts(Long id, List<DiscountActivityBaseVO.Product> products) {
        if (CollUtil.isEmpty(products)) {
            return;
        }
        // Query the activities that the product participates in
        // TODO Below 121 This query，Is it unnecessary to do it?；Directly convert Out skuId Gather together ok La；
        List<DiscountProductDO> list = discountProductMapper.selectListByActivityId(id);
        // TODO Generally simple stream Method，It is recommended to use CollectionUtils，For example, here is convertList Yes。
        List<Long> skuIds = list.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
        List<DiscountProductDO> matchDiscountProductList = getMatchDiscountProductList(skuIds);
        if (id != null) { // Exclude yourself from this activity
            matchDiscountProductList.removeIf(product -> id.equals(product.getActivityId()));
        }
        // If not empty，This indicates a conflict
        if (CollUtil.isNotEmpty(matchDiscountProductList)) {
            throw exception(DISCOUNT_ACTIVITY_SPU_CONFLICTS);
        }
    }

    @Override
    public void closeDiscountActivity(Long id) {
        // Check existence
        DiscountActivityDO activity = validateDiscountActivityExists(id);
        if (activity.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) { // Closed event，Cannot be closed
            throw exception(DISCOUNT_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED);
        }

        // Update
        DiscountActivityDO updateObj = new DiscountActivityDO().setId(id).setStatus(PromotionActivityStatusEnum.CLOSE.getStatus());
        discountActivityMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiscountActivity(Long id) {
        // Check existence
        DiscountActivityDO activity = validateDiscountActivityExists(id);
        if (CommonStatusEnum.isEnable(activity.getStatus())) { // Unclosed activities，Cannot delete
            throw exception(DISCOUNT_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED);
        }

        // Delete
        discountActivityMapper.deleteById(id);
    }

    private DiscountActivityDO validateDiscountActivityExists(Long id) {
        DiscountActivityDO discountActivity = discountActivityMapper.selectById(id);
        if (discountActivity == null) {
            throw exception(DISCOUNT_ACTIVITY_NOT_EXISTS);
        }
        return discountActivity;
    }

    @Override
    public DiscountActivityDO getDiscountActivity(Long id) {
        return discountActivityMapper.selectById(id);
    }

    @Override
    public PageResult<DiscountActivityDO> getDiscountActivityPage(DiscountActivityPageReqVO pageReqVO) {
        return discountActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiscountProductDO> getDiscountProductsByActivityId(Long activityId) {
        return discountProductMapper.selectListByActivityId(activityId);
    }

    @Override
    public List<DiscountProductDO> getDiscountProductsByActivityId(Collection<Long> activityIds) {
        return discountProductMapper.selectList("activity_id", activityIds);
    }

    @Override
    public List<DiscountActivityDO> getDiscountActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime) {
        // 1. Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to a recent activity number
        List<Map<String, Object>> spuIdAndActivityIdMaps = discountProductMapper.selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(spuIds, status);
        if (CollUtil.isEmpty(spuIdAndActivityIdMaps)) {
            return Collections.emptyList();
        }

        // 2. Query event details
        return discountActivityMapper.selectListByIdsAndDateTimeLt(
                convertSet(spuIdAndActivityIdMaps, map -> MapUtil.getLong(map, "activityId")), dateTime);
    }

}
