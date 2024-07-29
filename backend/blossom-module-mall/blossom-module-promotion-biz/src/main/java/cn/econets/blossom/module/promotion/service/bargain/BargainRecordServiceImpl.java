package cn.econets.blossom.module.promotion.service.bargain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.recrod.BargainRecordPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordCreateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainRecordDO;
import cn.econets.blossom.module.promotion.dal.mysql.bargain.BargainRecordMapper;
import cn.econets.blossom.module.promotion.enums.bargain.BargainRecordStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

/**
 * Bargaining Record Service Implementation class
 *
 */
@Service
@Validated
public class BargainRecordServiceImpl implements BargainRecordService {

    @Resource
    private BargainActivityService bargainActivityService;

    @Resource
    private BargainRecordMapper bargainRecordMapper;

    @Override
    public Long createBargainRecord(Long userId, AppBargainRecordCreateReqVO reqVO) {
        // 1. Check the bargaining activity（Including inventory）
        BargainActivityDO activity = bargainActivityService.validateBargainActivityCanJoin(reqVO.getActivityId());

        // 2.1 Check whether there is any bargaining activity currently in progress
        if (CollUtil.isNotEmpty(bargainRecordMapper.selectListByUserIdAndActivityIdAndStatus(
                userId, reqVO.getActivityId(), BargainRecordStatusEnum.IN_PROGRESS.getStatus()))) {
            throw exception(BARGAIN_RECORD_CREATE_FAIL_EXISTS);
        }
        // 2.2 Whether the participation limit has been exceeded
        if (bargainRecordMapper.selectCountByUserIdAndActivityIdAndStatus(
                userId, reqVO.getActivityId(), BargainRecordStatusEnum.SUCCESS.getStatus()) >= activity.getTotalLimitCount()) {
            throw exception(BARGAIN_RECORD_CREATE_FAIL_LIMIT);
        }

        // 3. Create bargaining record
        BargainRecordDO record = BargainRecordDO.builder().userId(userId)
                .activityId(reqVO.getActivityId()).spuId(activity.getSpuId()).skuId(activity.getSkuId())
                .bargainFirstPrice(activity.getBargainFirstPrice()).bargainPrice(activity.getBargainFirstPrice())
                .status(BargainRecordStatusEnum.IN_PROGRESS.getStatus()).build();
        bargainRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public Boolean updateBargainRecordBargainPrice(Long id, Integer whereBargainPrice,
                                                   Integer reducePrice, Boolean success) {
        BargainRecordDO updateObj = new BargainRecordDO().setBargainPrice(whereBargainPrice - reducePrice);
        if (success) {
            updateObj.setStatus(BargainRecordStatusEnum.SUCCESS.getStatus());
        }
        return bargainRecordMapper.updateByIdAndBargainPrice(id, whereBargainPrice, updateObj) > 0;
    }

    @Override
    public BargainValidateJoinRespDTO validateJoinBargain(Long userId, Long bargainRecordId, Long skuId) {
        // 1.1 Bargaining record does not exist
        BargainRecordDO record = bargainRecordMapper.selectByIdAndUserId(bargainRecordId, userId);
        if (record == null) {
            throw exception(BARGAIN_RECORD_NOT_EXISTS);
        }
        // 1.2 Bargaining record is not in progress
        if (ObjUtil.notEqual(record.getStatus(), BargainRecordStatusEnum.SUCCESS.getStatus())) {
            throw exception(BARGAIN_JOIN_RECORD_NOT_SUCCESS);
        }
        // 1.3 The bargaining record has been placed
        if (record.getOrderId() != null) {
            throw exception(BARGAIN_JOIN_RECORD_ALREADY_ORDER);
        }

        // 2.1 Check the bargaining activity（Including inventory）
        BargainActivityDO activity = bargainActivityService.validateBargainActivityCanJoin(record.getActivityId());
        Assert.isTrue(Objects.equals(skuId, activity.getSkuId()), "Bargained items do not match"); // Defensive validation
        return new BargainValidateJoinRespDTO().setActivityId(activity.getId()).setName(activity.getName())
                .setBargainPrice(record.getBargainPrice());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBargainRecordOrderId(Long id, Long orderId) {
        // Update failed，Indicates that the order has been placed
        int updateCount = bargainRecordMapper.updateOrderIdById(id, orderId);
        if (updateCount == 0) {
            throw exception(BARGAIN_JOIN_RECORD_ALREADY_ORDER);
        }
    }

    @Override
    public BargainRecordDO getBargainRecord(Long id) {
        return bargainRecordMapper.selectById(id);
    }

    @Override
    public BargainRecordDO getLastBargainRecord(Long userId, Long activityId) {
        return bargainRecordMapper.selectLastByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public Map<Long, Integer> getBargainRecordUserCountMap(Collection<Long> activityIds, @Nullable Integer status) {
        return bargainRecordMapper.selectUserCountByActivityIdsAndStatus(activityIds, status);
    }

    @Override
    public Integer getBargainRecordUserCount(Integer status) {
        return bargainRecordMapper.selectUserCountByStatus(status);
    }

    @Override
    public Integer getBargainRecordUserCount(Long activityId, Integer status) {
        return bargainRecordMapper.selectUserCountByActivityIdAndStatus(activityId, status);
    }

    @Override
    public PageResult<BargainRecordDO> getBargainRecordPage(BargainRecordPageReqVO pageReqVO) {
        return bargainRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<BargainRecordDO> getBargainRecordPage(Long userId, PageParam pageParam) {
        return bargainRecordMapper.selectBargainRecordPage(userId, pageParam);
    }

    @Override
    public List<BargainRecordDO> getBargainRecordList(Integer status, Integer count) {
        return bargainRecordMapper.selectListByStatusAndCount(status, count);
    }

}
