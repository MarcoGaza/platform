package cn.econets.blossom.module.promotion.service.bargain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.bargain.BargainActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.dal.mysql.bargain.BargainActivityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.anyMatch;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_NOT_EXISTS;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Bargaining activity Service Implementation class
 *
 */
@Service
@Validated
public class BargainActivityServiceImpl implements BargainActivityService {

    @Resource
    private BargainActivityMapper bargainActivityMapper;

    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createBargainActivity(BargainActivityCreateReqVO createReqVO) {
        // Check product SPU Are there any other activities to participate in?
        validateBargainConflict(createReqVO.getSpuId(), null);
        // Check product sku Does it exist?
        validateSku(createReqVO.getSkuId());

        // Insert bargaining activity
        BargainActivityDO activityDO = BargainActivityConvert.INSTANCE.convert(createReqVO)
                .setTotalStock(createReqVO.getStock())
                .setStatus(CommonStatusEnum.ENABLE.getStatus());
        bargainActivityMapper.insert(activityDO);
        return activityDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBargainActivity(BargainActivityUpdateReqVO updateReqVO) {
        // Check existence
        BargainActivityDO activity = validateBargainActivityExists(updateReqVO.getId());
        // Verification Status
        if (ObjectUtil.equal(activity.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(BARGAIN_ACTIVITY_STATUS_DISABLE);
        }
        // Check product conflicts
        validateBargainConflict(updateReqVO.getSpuId(), updateReqVO.getId());
        // Check product sku Does it exist?
        validateSku(updateReqVO.getSkuId());

        // Update
        BargainActivityDO updateObj = BargainActivityConvert.INSTANCE.convert(updateReqVO);
        if (updateObj.getStock() > activity.getTotalStock()) { // If the updated inventory is greater than the original inventory，Update total inventory
            updateObj.setTotalStock(updateObj.getStock());
        }
        bargainActivityMapper.updateById(updateObj);
    }

    @Override
    public void updateBargainActivityStock(Long id, Integer count) {
        if (count < 0) {
            // Update Inventory。If the update fails，Throws an exception
            int updateCount = bargainActivityMapper.updateStock(id, count);
            if (updateCount == 0) {
                throw exception(BARGAIN_ACTIVITY_STOCK_NOT_ENOUGH);
            }
        } else if (count > 0) {
            bargainActivityMapper.updateStock(id, count);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeBargainActivityById(Long id) {
        // Check if the bargaining activity exists
        BargainActivityDO activity = validateBargainActivityExists(id);
        if (CommonStatusEnum.isDisable(activity.getStatus())) {
            throw exception(BARGAIN_ACTIVITY_STATUS_DISABLE);
        }

        bargainActivityMapper.updateById(new BargainActivityDO().setId(id)
                .setStatus(CommonStatusEnum.DISABLE.getStatus()));
    }

    private void validateBargainConflict(Long spuId, Long activityId) {
        // Query all opened bargaining activities
        List<BargainActivityDO> activityList = bargainActivityMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus());
        if (activityId != null) { // Exclude yourself when updating
            activityList.removeIf(item -> ObjectUtil.equal(item.getId(), activityId));
        }
        // Check product spu Have you participated in other activities?
        if (anyMatch(activityList, activity -> ObjectUtil.equal(activity.getSpuId(), spuId))) {
            throw exception(BARGAIN_ACTIVITY_SPU_CONFLICTS);
        }
    }

    private void validateSku(Long skuId) {
        ProductSkuRespDTO sku = productSkuApi.getSku(skuId);
        if (sku == null) {
            throw exception(SKU_NOT_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBargainActivity(Long id) {
        // Check existence
        BargainActivityDO activityDO = validateBargainActivityExists(id);
        // Verification status
        if (CommonStatusEnum.isEnable(activityDO.getStatus())) {
            throw exception(BARGAIN_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END);
        }

        // Delete
        bargainActivityMapper.deleteById(id);
    }

    private BargainActivityDO validateBargainActivityExists(Long id) {
        BargainActivityDO activityDO = bargainActivityMapper.selectById(id);
        if (activityDO == null) {
            throw exception(BARGAIN_ACTIVITY_NOT_EXISTS);
        }
        return activityDO;
    }

    @Override
    public BargainActivityDO getBargainActivity(Long id) {
        return bargainActivityMapper.selectById(id);
    }

    @Override
    public List<BargainActivityDO> getBargainActivityList(Set<Long> ids) {
        return bargainActivityMapper.selectBatchIds(ids);
    }

    @Override
    public BargainActivityDO validateBargainActivityCanJoin(Long id) {
        BargainActivityDO activity = bargainActivityMapper.selectById(id);
        if (activity == null) {
            throw exception(BARGAIN_ACTIVITY_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(activity.getStatus())) {
            throw exception(BARGAIN_ACTIVITY_STATUS_CLOSED);
        }
        if (activity.getStock() <= 0) {
            throw exception(BARGAIN_ACTIVITY_STOCK_NOT_ENOUGH);
        }
        if (!LocalDateTimeUtils.isBetween(activity.getStartTime(), activity.getEndTime())) {
            throw exception(BARGAIN_ACTIVITY_TIME_END);
        }
        return activity;
    }

    @Override
    public PageResult<BargainActivityDO> getBargainActivityPage(BargainActivityPageReqVO pageReqVO) {
        return bargainActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<BargainActivityDO> getBargainActivityPage(PageParam pageReqVO) {
        // Only query in progress，and within the time range
        return bargainActivityMapper.selectPage(pageReqVO, CommonStatusEnum.ENABLE.getStatus(), LocalDateTime.now());
    }

    @Override
    public List<BargainActivityDO> getBargainActivityListByCount(Integer count) {
        return bargainActivityMapper.selectList(count, CommonStatusEnum.ENABLE.getStatus(), LocalDateTime.now());
    }

    @Override
    public List<BargainActivityDO> getBargainActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime) {
        // 1. Query the specified spuId of spu The record of the activity participated in closest to the present time。If there are multiple ones，One spuId Corresponding to a recent activity number
        List<Map<String, Object>> spuIdAndActivityIdMaps = bargainActivityMapper.selectSpuIdAndActivityIdMapsBySpuIdsAndStatus(spuIds, status);
        if (CollUtil.isEmpty(spuIdAndActivityIdMaps)) {
            return Collections.emptyList();
        }
        // 2. Query event details
        return bargainActivityMapper.selectListByIdsAndDateTimeLt(
                convertSet(spuIdAndActivityIdMaps, map -> MapUtil.getLong(map, "activityId")), dateTime);
    }

}
