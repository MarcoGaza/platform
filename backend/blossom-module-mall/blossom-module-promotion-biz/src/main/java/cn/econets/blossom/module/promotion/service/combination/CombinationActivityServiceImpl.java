package cn.econets.blossom.module.promotion.service.combination;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductBaseVO;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.mysql.combination.CombinationActivityMapper;
import cn.econets.blossom.module.promotion.dal.mysql.combination.CombinationProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_NOT_EXISTS;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;
import static java.util.Collections.singletonList;

/**
 * Group buying activity Service Implementation class
 *
 */
@Service
@Validated
public class CombinationActivityServiceImpl implements CombinationActivityService {

    @Resource
    private CombinationActivityMapper combinationActivityMapper;
    @Resource
    private CombinationProductMapper combinationProductMapper;

    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCombinationActivity(CombinationActivityCreateReqVO createReqVO) {
        // Check product SPU Are there any other activities to participate in?
        validateProductConflict(createReqVO.getSpuId(), null);
        // Check if the product exists
        validateProductExists(createReqVO.getSpuId(), createReqVO.getProducts());

        // Insert group buying activity
        CombinationActivityDO activity = CombinationActivityConvert.INSTANCE.convert(createReqVO)
                .setStatus(CommonStatusEnum.ENABLE.getStatus());
        combinationActivityMapper.insert(activity);
        // Insert product
        List<CombinationProductDO> products = CombinationActivityConvert.INSTANCE.convertList(createReqVO.getProducts(), activity);
        combinationProductMapper.insertBatch(products);
        return activity.getId();
    }

    /**
     * Check whether there is any conflict in the activities that the group purchase products participate in
     *
     * @param spuId      Products SPU Number
     * @param activityId Group buying activity number
     */
    private void validateProductConflict(Long spuId, Long activityId) {
        // Query all opened group buying activities
        List<CombinationActivityDO> activityList = combinationActivityMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus());
        if (activityId != null) { // Exclude yourself when
            activityList.removeIf(item -> ObjectUtil.equal(item.getId(), activityId));
        }
        // Check if there are other events，Selected this product
        List<CombinationActivityDO> matchActivityList = filterList(activityList, activity -> ObjectUtil.equal(activity.getId(), spuId));
        if (CollUtil.isNotEmpty(matchActivityList)) {
            throw exception(COMBINATION_ACTIVITY_SPU_CONFLICTS);
        }
    }

    /**
     * Check whether all group purchase products exist
     *
     * @param spuId    Products SPU Number
     * @param products Group buy product
     */
    private void validateProductExists(Long spuId, List<CombinationProductBaseVO> products) {
        // 1. Check product spu Does it exist?
        ProductSpuRespDTO spu = productSpuApi.getSpu(spuId);
        if (spu == null) {
            throw exception(SPU_NOT_EXISTS);
        }

        // 2. Check product sku All exist
        List<ProductSkuRespDTO> skus = productSkuApi.getSkuListBySpuId(singletonList(spuId));
        Map<Long, ProductSkuRespDTO> skuMap = convertMap(skus, ProductSkuRespDTO::getId);
        products.forEach(product -> {
            if (!skuMap.containsKey(product.getSkuId())) {
                throw exception(SKU_NOT_EXISTS);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCombinationActivity(CombinationActivityUpdateReqVO updateReqVO) {
        // Check existence
        CombinationActivityDO activityDO = validateCombinationActivityExists(updateReqVO.getId());
        // Verification status
        if (ObjectUtil.equal(activityDO.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(COMBINATION_ACTIVITY_STATUS_DISABLE_NOT_UPDATE);
        }
        // Check product conflicts
        validateProductConflict(updateReqVO.getSpuId(), updateReqVO.getId());
        // Check if the product exists
        validateProductExists(updateReqVO.getSpuId(), updateReqVO.getProducts());

        // Update activity
        CombinationActivityDO updateObj = CombinationActivityConvert.INSTANCE.convert(updateReqVO);
        combinationActivityMapper.updateById(updateObj);
        // Update product
        updateCombinationProduct(updateObj, updateReqVO.getProducts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeCombinationActivityById(Long id) {
        // Check if the activity exists
        CombinationActivityDO activity = validateCombinationActivityExists(id);
        if (CommonStatusEnum.isDisable(activity.getStatus())) {
            throw exception(COMBINATION_ACTIVITY_STATUS_DISABLE_NOT_UPDATE);
        }

        // Close the activity
        combinationActivityMapper.updateById(new CombinationActivityDO().setId(id)
                .setStatus(CommonStatusEnum.DISABLE.getStatus()));
    }

    /**
     * Update group buying products
     *
     * @param activity Group buying activity
     * @param products The latest product configuration for this event
     */
    private void updateCombinationProduct(CombinationActivityDO activity, List<CombinationProductBaseVO> products) {
        // First step，Compare new and old data，Get added、Modify、Deleted list
        List<CombinationProductDO> newList = CombinationActivityConvert.INSTANCE.convertList(products, activity);
        List<CombinationProductDO> oldList = combinationProductMapper.selectListByActivityIds(CollUtil.newArrayList(activity.getId()));
        List<List<CombinationProductDO>> diffList = CollectionUtils.diffList(oldList, newList, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getSkuId(), newVal.getSkuId());
            if (same) {
                newVal.setId(oldVal.getId());
            }
            return same;
        });

        // Step 2，Batch add、Modify、Delete
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            combinationProductMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            combinationProductMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            combinationProductMapper.deleteBatchIds(CollectionUtils.convertList(diffList.get(2), CombinationProductDO::getId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCombinationActivity(Long id) {
        // Verify existence
        CombinationActivityDO activity = validateCombinationActivityExists(id);
        // Verification Status
        if (CommonStatusEnum.isEnable(activity.getStatus())) {
            throw exception(COMBINATION_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END);
        }

        // Delete
        combinationActivityMapper.deleteById(id);
    }

    @Override
    public CombinationActivityDO validateCombinationActivityExists(Long id) {
        CombinationActivityDO activityDO = combinationActivityMapper.selectById(id);
        if (activityDO == null) {
            throw exception(COMBINATION_ACTIVITY_NOT_EXISTS);
        }
        return activityDO;
    }

    @Override
    public CombinationActivityDO getCombinationActivity(Long id) {
        return validateCombinationActivityExists(id);
    }

    @Override
    public PageResult<CombinationActivityDO> getCombinationActivityPage(CombinationActivityPageReqVO pageReqVO) {
        return combinationActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CombinationProductDO> getCombinationProductListByActivityIds(Collection<Long> activityIds) {
        return combinationProductMapper.selectListByActivityIds(activityIds);
    }

    @Override
    public List<CombinationActivityDO> getCombinationActivityListByIds(Collection<Long> ids) {
        return combinationActivityMapper.selectList(CombinationActivityDO::getId, ids);
    }

    @Override
    public List<CombinationActivityDO> getCombinationActivityListByCount(Integer count) {
        return combinationActivityMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus(), count);
    }

    @Override
    public PageResult<CombinationActivityDO> getCombinationActivityPage(PageParam pageParam) {
        return combinationActivityMapper.selectPage(pageParam, CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public CombinationProductDO selectByActivityIdAndSkuId(Long activityId, Long skuId) {
        return combinationProductMapper.selectOne(
                CombinationProductDO::getActivityId, activityId,
                CombinationProductDO::getSkuId, skuId);
    }

    @Override
    public List<CombinationActivityDO> getCombinationActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime) {
        // 1.Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple words，One spuId Corresponding to a recent activity number
        List<Map<String, Object>> spuIdAndActivityIdMaps = combinationActivityMapper.selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(spuIds, status);
        if (CollUtil.isEmpty(spuIdAndActivityIdMaps)) {
            return Collections.emptyList();
        }
        // 2.Query event details
        return combinationActivityMapper.selectListByIdsAndDateTimeLt(
                convertSet(spuIdAndActivityIdMaps, map -> MapUtil.getLong(map, "activityId")), dateTime);
    }

}
