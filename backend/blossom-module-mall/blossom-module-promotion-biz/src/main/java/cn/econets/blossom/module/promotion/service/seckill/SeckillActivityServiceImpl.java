package cn.econets.blossom.module.promotion.service.seckill;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.api.seckill.dto.SeckillValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product.SeckillProductBaseVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.convert.seckill.seckillactivity.SeckillActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillProductDO;
import cn.econets.blossom.module.promotion.dal.mysql.seckill.seckillactivity.SeckillActivityMapper;
import cn.econets.blossom.module.promotion.dal.mysql.seckill.seckillactivity.SeckillProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.collection.CollUtil.isNotEmpty;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.isBetween;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_NOT_EXISTS;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;
import static java.util.Collections.singletonList;

/**
 * Second-sale event Service Implementation class
 *
 */
@Service
@Validated
public class SeckillActivityServiceImpl implements SeckillActivityService {

    @Resource
    private SeckillActivityMapper seckillActivityMapper;
    @Resource
    private SeckillProductMapper seckillProductMapper;
    @Resource
    private SeckillConfigService seckillConfigService;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSeckillActivity(SeckillActivityCreateReqVO createReqVO) {
        // 1.1 Check if the product flash sale period conflicts
        validateProductConflict(createReqVO.getConfigIds(), createReqVO.getSpuId(), null);
        // 1.2 Check if the product exists
        validateProductExists(createReqVO.getSpuId(), createReqVO.getProducts());

        // 2.1 Insert flash sale activity
        SeckillActivityDO activity = SeckillActivityConvert.INSTANCE.convert(createReqVO)
                .setStatus(CommonStatusEnum.ENABLE.getStatus())
                .setStock(getSumValue(createReqVO.getProducts(), SeckillProductBaseVO::getStock, Integer::sum));
        activity.setTotalStock(activity.getStock());
        seckillActivityMapper.insert(activity);
        // 2.2 Insert product
        List<SeckillProductDO> products = SeckillActivityConvert.INSTANCE.convertList(createReqVO.getProducts(), activity);
        seckillProductMapper.insertBatch(products);
        return activity.getId();
    }

    /**
     * Check whether there is any conflict in the activities that the flash sale products participate in
     *
     * 1. Check if the flash sale period exists
     * 2. Whether flash sale products can participate in other activities
     *
     * @param configIds  Second sale time array
     * @param spuId      Products SPU Number
     * @param activityId Second sale activity number
     */
    private void validateProductConflict(List<Long> configIds, Long spuId, Long activityId) {
        // 1. Check if the flash sale period exists
        seckillConfigService.validateSeckillConfigExists(configIds);

        // 2.1 Query all enabled flash sales activities
        List<SeckillActivityDO> activityList = seckillActivityMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus());
        if (activityId != null) { // Exclude yourself
            activityList.removeIf(item -> ObjectUtil.equal(item.getId(), activityId));
        }
        // 2.2 Filter out all configIds Intersecting activities，Judge whether there is overlap
        List<SeckillActivityDO> conflictActivityList = filterList(activityList, s -> containsAny(s.getConfigIds(), configIds));
        if (isNotEmpty(conflictActivityList)) {
            throw exception(SECKILL_ACTIVITY_SPU_CONFLICTS);
        }
    }

    /**
     * Check whether all flash sale products exist
     *
     * @param spuId    Products SPU Number
     * @param products Second-selling products
     */
    private void validateProductExists(Long spuId, List<SeckillProductBaseVO> products) {
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
    public void updateSeckillActivity(SeckillActivityUpdateReqVO updateReqVO) {
        // 1.1 Verify existence
        SeckillActivityDO activity = validateSeckillActivityExists(updateReqVO.getId());
        if (CommonStatusEnum.DISABLE.getStatus().equals(activity.getStatus())) {
            throw exception(SECKILL_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED);
        }
        // 1.2 Check if the product conflicts
        validateProductConflict(updateReqVO.getConfigIds(), updateReqVO.getSpuId(), updateReqVO.getId());
        // 1.3 Check if the product exists
        validateProductExists(updateReqVO.getSpuId(), updateReqVO.getProducts());

        // 2.1 Update activity
        SeckillActivityDO updateObj = SeckillActivityConvert.INSTANCE.convert(updateReqVO)
                .setStock(getSumValue(updateReqVO.getProducts(), SeckillProductBaseVO::getStock, Integer::sum));
        if (updateObj.getStock() > activity.getTotalStock()) { // If the updated inventory is greater than the original inventory，Update total inventory
            updateObj.setTotalStock(updateObj.getStock());
        }
        seckillActivityMapper.updateById(updateObj);
        // 2.2 Update product
        updateSeckillProduct(updateObj, updateReqVO.getProducts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSeckillStockDecr(Long id, Long skuId, Integer count) {
        // 1.1 Check if the activity inventory is sufficient
        SeckillActivityDO seckillActivity = validateSeckillActivityExists(id);
        if (count > seckillActivity.getTotalStock()) {
            throw exception(SECKILL_ACTIVITY_UPDATE_STOCK_FAIL);
        }
        // 1.2 Check if the product inventory is sufficient
        SeckillProductDO product = seckillProductMapper.selectByActivityIdAndSkuId(id, skuId);
        if (product == null || count > product.getStock()) {
            throw exception(SECKILL_ACTIVITY_UPDATE_STOCK_FAIL);
        }

        // 2.1 Update the inventory of active products
        int updateCount = seckillProductMapper.updateStockDecr(product.getId(), count);
        if (updateCount == 0) {
            throw exception(SECKILL_ACTIVITY_UPDATE_STOCK_FAIL);
        }

        // 2.2 Update activity inventory
        updateCount = seckillActivityMapper.updateStockDecr(seckillActivity.getId(), count);
        if (updateCount == 0) {
            throw exception(SECKILL_ACTIVITY_UPDATE_STOCK_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSeckillStockIncr(Long id, Long skuId, Integer count) {
        SeckillProductDO product = seckillProductMapper.selectByActivityIdAndSkuId(id, skuId);
        // Update the inventory of active products
        seckillProductMapper.updateStockIncr(product.getId(), count);
        // Update activity inventory
        seckillActivityMapper.updateStockIncr(id, count);
    }

    /**
     * Update flash sale products
     *
     * @param activity Second-sale event
     * @param products The latest product configuration for this event
     */
    private void updateSeckillProduct(SeckillActivityDO activity, List<SeckillProductBaseVO> products) {
        // First step，Compare new and old data，Get added、Modify、Deleted list
        List<SeckillProductDO> newList = SeckillActivityConvert.INSTANCE.convertList(products, activity);
        List<SeckillProductDO> oldList = seckillProductMapper.selectListByActivityId(activity.getId());
        List<List<SeckillProductDO>> diffList = diffList(oldList, newList, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getSkuId(), newVal.getSkuId());
            if (same) {
                newVal.setId(oldVal.getId());
            }
            return same;
        });

        // Step 2，Batch add、Modify、Delete
        if (isNotEmpty(diffList.get(0))) {
            seckillProductMapper.insertBatch(diffList.get(0));
        }
        if (isNotEmpty(diffList.get(1))) {
            seckillProductMapper.updateBatch(diffList.get(1));
        }
        if (isNotEmpty(diffList.get(2))) {
            seckillProductMapper.deleteBatchIds(convertList(diffList.get(2), SeckillProductDO::getId));
        }
    }

    @Override
    public void closeSeckillActivity(Long id) {
        // Verify existence
        SeckillActivityDO activity = validateSeckillActivityExists(id);
        if (CommonStatusEnum.DISABLE.getStatus().equals(activity.getStatus())) {
            throw exception(SECKILL_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED);
        }

        // Update
        SeckillActivityDO updateObj = new SeckillActivityDO().setId(id).setStatus(CommonStatusEnum.DISABLE.getStatus());
        seckillActivityMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSeckillActivity(Long id) {
        // Verify existence
        SeckillActivityDO seckillActivity = this.validateSeckillActivityExists(id);
        if (CommonStatusEnum.ENABLE.getStatus().equals(seckillActivity.getStatus())) {
            throw exception(SECKILL_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END);
        }

        // Delete activity
        seckillActivityMapper.deleteById(id);
        // Delete the promotional product
        List<SeckillProductDO> products = seckillProductMapper.selectListByActivityId(id);
        seckillProductMapper.deleteBatchIds(convertSet(products, SeckillProductDO::getId));
    }

    private SeckillActivityDO validateSeckillActivityExists(Long id) {
        SeckillActivityDO seckillActivity = seckillActivityMapper.selectById(id);
        if (seckillActivity == null) {
            throw exception(SECKILL_ACTIVITY_NOT_EXISTS);
        }
        return seckillActivity;
    }

    @Override
    public SeckillActivityDO getSeckillActivity(Long id) {
        return seckillActivityMapper.selectById(id);
    }

    @Override
    public PageResult<SeckillActivityDO> getSeckillActivityPage(SeckillActivityPageReqVO pageReqVO) {
        return seckillActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SeckillProductDO> getSeckillProductListByActivityId(Long activityId) {
        return seckillProductMapper.selectListByActivityId(activityId);
    }

    @Override
    public List<SeckillProductDO> getSeckillProductListByActivityId(Collection<Long> activityIds) {
        return seckillProductMapper.selectListByActivityId(activityIds);
    }

    @Override
    public List<SeckillActivityDO> getSeckillActivityListByConfigIdAndStatus(Long configId, Integer status) {
        return filterList(seckillActivityMapper.selectList(SeckillActivityDO::getStatus, status),
                item -> anyMatch(item.getConfigIds(), id -> ObjectUtil.equal(id, configId)) // Verification period
                        && isBetween(item.getStartTime(), item.getEndTime())); // Add a check condition to see if the current date is between the active dates
    }

    @Override
    public PageResult<SeckillActivityDO> getSeckillActivityAppPageByConfigId(AppSeckillActivityPageReqVO pageReqVO) {
        return seckillActivityMapper.selectPage(pageReqVO, CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public SeckillValidateJoinRespDTO validateJoinSeckill(Long activityId, Long skuId, Integer count) {
        // 1.1 Check if the flash sale activity exists
        SeckillActivityDO activity = validateSeckillActivityExists(activityId);
        if (CommonStatusEnum.isDisable(activity.getStatus())) {
            throw exception(SECKILL_JOIN_ACTIVITY_STATUS_CLOSED);
        }
        // 1.2 Is it within the activity time range?
        if (!LocalDateTimeUtils.isBetween(activity.getStartTime(), activity.getEndTime())) {
            throw exception(SECKILL_JOIN_ACTIVITY_TIME_ERROR);
        }
        SeckillConfigDO config = seckillConfigService.getCurrentSeckillConfig();
        if (config == null || !CollectionUtil.contains(activity.getConfigIds(), config.getId())) {
            throw exception(SECKILL_JOIN_ACTIVITY_TIME_ERROR);
        }
        // 1.3 Exceeded single purchase limit
        if (count > activity.getSingleLimitCount()) {
            throw exception(SECKILL_JOIN_ACTIVITY_SINGLE_LIMIT_COUNT_EXCEED);
        }

        // 2.1 Check if the flash sale product exists
        SeckillProductDO product = seckillProductMapper.selectByActivityIdAndSkuId(activityId, skuId);
        if (product == null) {
            throw exception(SECKILL_JOIN_ACTIVITY_PRODUCT_NOT_EXISTS);
        }
        // 2.2 Check if the inventory is sufficient
        if (count > product.getStock()) {
            throw exception(SECKILL_ACTIVITY_UPDATE_STOCK_FAIL);
        }
        return SeckillActivityConvert.INSTANCE.convert02(activity, product);
    }

    @Override
    public List<SeckillActivityDO> getSeckillActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime) {
        // 1.Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to a recent activity number
        List<Map<String, Object>> spuIdAndActivityIdMaps = seckillActivityMapper.selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(spuIds, status);
        if (CollUtil.isEmpty(spuIdAndActivityIdMaps)) {
            return Collections.emptyList();
        }
        // 2.Query event details
        return seckillActivityMapper.selectListByIdsAndDateTimeLt(
                convertSet(spuIdAndActivityIdMaps, map -> MapUtil.getLong(map, "activityId")), dateTime);
    }

}
