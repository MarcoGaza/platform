package cn.econets.blossom.module.promotion.service.combination;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateReqDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordReqPageVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordPageReqVO;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import cn.econets.blossom.module.promotion.dal.mysql.combination.CombinationRecordMapper;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import cn.econets.blossom.module.trade.api.order.TradeOrderApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.afterNow;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.beforeNow;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;

// TODO Wait for the group purchase record to be completed，Complete review Next

/**
 * Group buying record Service Implementation class
 *
 */
@Service
@Slf4j
@Validated
public class CombinationRecordServiceImpl implements CombinationRecordService {

    @Resource
    private CombinationActivityService combinationActivityService;
    @Resource
    private CombinationRecordMapper combinationRecordMapper;

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Resource
    @Lazy
    private TradeOrderApi tradeOrderApi;

    // TODO ：Under detailed preview；
    @Override
    public KeyValue<CombinationActivityDO, CombinationProductDO> validateCombinationRecord(
            Long userId, Long activityId, Long headId, Long skuId, Integer count) {
        // 1. Check if group buying activity exists
        CombinationActivityDO activity = combinationActivityService.validateCombinationActivityExists(activityId);
        // 1.1 Check whether the activity is enabled
        if (ObjUtil.equal(activity.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(COMBINATION_ACTIVITY_STATUS_DISABLE);
        }
        // 1.2. Check activity start time
        if (afterNow(activity.getStartTime())) {
            throw exception(COMBINATION_RECORD_FAILED_TIME_NOT_START);
        }
        // 1.3 Check whether the single purchase limit is exceeded
        if (count > activity.getSingleLimitCount()) {
            throw exception(COMBINATION_RECORD_FAILED_SINGLE_LIMIT_COUNT_EXCEED);
        }

        // 2. Does the parent group exist?,Is it full?
        if (headId != null) {
            // 2.1. Query the parent group in progress
            CombinationRecordDO record = combinationRecordMapper.selectByHeadId(headId, CombinationRecordStatusEnum.IN_PROGRESS.getStatus());
            if (record == null) {
                throw exception(COMBINATION_RECORD_HEAD_NOT_EXISTS);
            }
            // 2.2. Check if the group is full
            if (ObjUtil.equal(record.getUserCount(), record.getUserSize())) {
                throw exception(COMBINATION_RECORD_USER_FULL);
            }
            // 2.3 Check if the group purchase has expired（When there is a parent group, only check the expiration time of the parent group）
            if (beforeNow(record.getExpireTime())) {
                throw exception(COMBINATION_RECORD_FAILED_TIME_END);
            }
        } else {
            // 3. Check whether the current activity has ended(Only when you are the parent of the group can you check whether the activity has ended)
            if (beforeNow(activity.getEndTime())) {
                throw exception(COMBINATION_RECORD_FAILED_TIME_END);
            }
        }

        // 4.1 Check if the active product exists
        CombinationProductDO product = combinationActivityService.selectByActivityIdAndSkuId(activityId, skuId);
        if (product == null) {
            throw exception(COMBINATION_JOIN_ACTIVITY_PRODUCT_NOT_EXISTS);
        }
        // 4.2 Verification sku Does it exist?
        ProductSkuRespDTO sku = productSkuApi.getSku(skuId);
        if (sku == null) {
            throw exception(COMBINATION_JOIN_ACTIVITY_PRODUCT_NOT_EXISTS);
        }
        // 4.3 Check if the inventory is sufficient
        if (count > sku.getStock()) {
            throw exception(COMBINATION_ACTIVITY_UPDATE_STOCK_FAIL);
        }

        // 6.1 Check whether there is a group purchase record
        List<CombinationRecordDO> recordList = combinationRecordMapper.selectListByUserIdAndActivityId(userId, activityId);
        recordList.removeIf(record -> CombinationRecordStatusEnum.isFailed(record.getStatus())); // Cancelled order，Doesn't count
        if (CollUtil.isEmpty(recordList)) { // If empty，Indicates that you can participate，Return directly
            return new KeyValue<>(activity, product);
        }
        // 6.2 Check whether the user has a group purchase for this activity in progress
        CombinationRecordDO inProgressRecord = findFirst(recordList,
                record -> CombinationRecordStatusEnum.isInProgress(record.getStatus()));
        if (inProgressRecord != null) {
            throw exception(COMBINATION_RECORD_FAILED_HAVE_JOINED);
        }
        // 6.3 Check whether the total purchase limit is exceeded
        Integer sumValue = getSumValue(recordList, CombinationRecordDO::getCount, Integer::sum);
        if (sumValue != null && sumValue + count > activity.getTotalLimitCount()) {
            throw exception(COMBINATION_RECORD_FAILED_TOTAL_LIMIT_COUNT_EXCEED);
        }
        return new KeyValue<>(activity, product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CombinationRecordDO createCombinationRecord(CombinationRecordCreateReqDTO reqDTO) {
        // 1. Check group buying activity
        KeyValue<CombinationActivityDO, CombinationProductDO> keyValue = validateCombinationRecord(reqDTO.getUserId(),
                reqDTO.getActivityId(), reqDTO.getHeadId(), reqDTO.getSkuId(), reqDTO.getCount());

        // 2. Combine data to create group purchase record
        MemberUserRespDTO user = memberUserApi.getUser(reqDTO.getUserId());
        ProductSpuRespDTO spu = productSpuApi.getSpu(reqDTO.getSpuId());
        ProductSkuRespDTO sku = productSkuApi.getSku(reqDTO.getSkuId());
        CombinationRecordDO record = CombinationActivityConvert.INSTANCE.convert(reqDTO, keyValue.getKey(), user, spu, sku);
        // 2.1. If you are the team leader, you need to set it headId for CombinationRecordDO#HEAD_ID_GROUP
        if (record.getHeadId() == null) {
            record.setStartTime(LocalDateTime.now())
                    .setExpireTime(LocalDateTime.now().plusHours(keyValue.getKey().getLimitDuration()))
                    .setHeadId(CombinationRecordDO.HEAD_ID_GROUP);
        } else {
            // 2.2.If there is a group leader, you need to set the start time and expiration time to the group leader
            CombinationRecordDO headRecord = combinationRecordMapper.selectByHeadId(record.getHeadId(),
                    CombinationRecordStatusEnum.IN_PROGRESS.getStatus()); // Query the parent group in progress
            record.setStartTime(headRecord.getStartTime()).setExpireTime(headRecord.getExpireTime());
        }
        combinationRecordMapper.insert(record);

        // 3. Update group purchase record
        if (ObjUtil.notEqual(CombinationRecordDO.HEAD_ID_GROUP, record.getHeadId())) {
            updateCombinationRecordWhenCreate(reqDTO.getHeadId(), keyValue.getKey());
        }
        return record;
    }

    /**
     * When adding a new group purchase，Update the progress of group purchase records
     *
     * @param headId   Team Leader Number
     * @param activity Activities
     */
    private void updateCombinationRecordWhenCreate(Long headId, CombinationActivityDO activity) {
        // 1. Team Leader + Team member
        List<CombinationRecordDO> records = getCombinationRecordListByHeadId(headId);
        if (CollUtil.isEmpty(records)) {
            return;
        }
        CombinationRecordDO headRecord = combinationRecordMapper.selectById(headId);

        // 2. Batch update records
        List<CombinationRecordDO> updateRecords = new ArrayList<>();
        records.add(headRecord); // Join the group leader，The leader also needs to be updated
        boolean isFull = records.size() >= activity.getUserSize();
        LocalDateTime now = LocalDateTime.now();
        records.forEach(item -> {
            CombinationRecordDO updateRecord = new CombinationRecordDO();
            updateRecord.setId(item.getId()).setUserCount(records.size());
            if (isFull) {
                updateRecord.setStatus(CombinationRecordStatusEnum.SUCCESS.getStatus());
                updateRecord.setEndTime(now);
            }
            updateRecords.add(updateRecord);
        });
        combinationRecordMapper.updateBatch(updateRecords);
    }

    @Override
    public CombinationRecordDO getCombinationRecord(Long userId, Long orderId) {
        return combinationRecordMapper.selectByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public CombinationValidateJoinRespDTO validateJoinCombination(Long userId, Long activityId, Long headId,
                                                                  Long skuId, Integer count) {
        KeyValue<CombinationActivityDO, CombinationProductDO> keyValue = validateCombinationRecord(userId, activityId,
                headId, skuId, count);
        return new CombinationValidateJoinRespDTO().setActivityId(keyValue.getKey().getId())
                .setName(keyValue.getKey().getName()).setCombinationPrice(keyValue.getValue().getCombinationPrice());
    }

    @Override
    public Long getCombinationRecordCount(@Nullable Integer status, @Nullable Boolean virtualGroup, @Nullable Long headId) {
        return combinationRecordMapper.selectCountByHeadAndStatusAndVirtualGroup(status, virtualGroup, headId);
    }

    @Override
    public Long getCombinationUserCount() {
        return combinationRecordMapper.selectUserCount();
    }

    @Override
    public List<CombinationRecordDO> getLatestCombinationRecordList(int count) {
        return combinationRecordMapper.selectLatestList(count);
    }

    @Override
    public List<CombinationRecordDO> getHeadCombinationRecordList(Long activityId, Integer status, Integer count) {
        return combinationRecordMapper.selectListByActivityIdAndStatusAndHeadId(activityId, status,
                CombinationRecordDO.HEAD_ID_GROUP, count);
    }

    @Override
    public CombinationRecordDO getCombinationRecordById(Long id) {
        return combinationRecordMapper.selectById(id);
    }

    @Override
    public List<CombinationRecordDO> getCombinationRecordListByHeadId(Long headId) {
        return combinationRecordMapper.selectList(CombinationRecordDO::getHeadId, headId);
    }

    @Override
    public PageResult<CombinationRecordDO> getCombinationRecordPage(CombinationRecordReqPageVO pageVO) {
        return combinationRecordMapper.selectPage(pageVO);
    }

    @Override
    public Map<Long, Integer> getCombinationRecordCountMapByActivity(Collection<Long> activityIds,
                                                                     @Nullable Integer status, @Nullable Long headId) {
        return combinationRecordMapper.selectCombinationRecordCountMapByActivityIdAndStatusAndHeadId(activityIds, status, headId);
    }

    @Override
    public CombinationRecordDO getCombinationRecordByIdAndUser(Long userId, Long id) {
        return combinationRecordMapper.selectOne(CombinationRecordDO::getUserId, userId, CombinationRecordDO::getId, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelCombinationRecord(Long userId, Long id, Long headId) {
        // Delete record
        combinationRecordMapper.deleteById(id);

        // Records that need to be updated
        List<CombinationRecordDO> updateRecords = new ArrayList<>();
        // If it is the leader，Then the order（Order time）Inheritance
        if (Objects.equals(headId, CombinationRecordDO.HEAD_ID_GROUP)) { // Situation 1：Team Leader
            // Team member
            List<CombinationRecordDO> list = getCombinationRecordListByHeadId(id);
            if (CollUtil.isEmpty(list)) {
                return;
            }
            // Sort by creation time in ascending order
            list.sort(Comparator.comparing(CombinationRecordDO::getCreateTime)); // Influence source list
            CombinationRecordDO newHead = list.get(0); // New leader takes over
            list.forEach(item -> {
                CombinationRecordDO recordDO = new CombinationRecordDO();
                recordDO.setId(item.getId());
                if (ObjUtil.equal(item.getId(), newHead.getId())) { // New Leader
                    recordDO.setHeadId(CombinationRecordDO.HEAD_ID_GROUP);
                } else {
                    recordDO.setHeadId(newHead.getId());
                }
                recordDO.setUserCount(list.size());
                updateRecords.add(recordDO);
            });
        } else { // Case 2：Team member
            // Team Leader
            CombinationRecordDO recordHead = combinationRecordMapper.selectById(headId);
            // Team member
            List<CombinationRecordDO> records = getCombinationRecordListByHeadId(headId);
            if (CollUtil.isEmpty(records)) {
                return;
            }
            records.add(recordHead); // Join the group leader，The leader's data also needs to be updated
            records.forEach(item -> {
                CombinationRecordDO recordDO = new CombinationRecordDO();
                recordDO.setId(item.getId());
                recordDO.setUserCount(records.size());
                updateRecords.add(recordDO);
            });
        }

        // Update group purchase record
        combinationRecordMapper.updateBatch(updateRecords);
    }

    @Override
    public KeyValue<Integer, Integer> expireCombinationRecord() {
        // 1. Get all the ongoing expired parent groups
        List<CombinationRecordDO> headExpireRecords = combinationRecordMapper.selectListByHeadIdAndStatusAndExpireTimeLt(
                CombinationRecordDO.HEAD_ID_GROUP, CombinationRecordStatusEnum.IN_PROGRESS.getStatus(), LocalDateTime.now());
        if (CollUtil.isEmpty(headExpireRecords)) {
            return new KeyValue<>(0, 0);
        }

        // 2. Get group buying activities
        List<CombinationActivityDO> activities = combinationActivityService.getCombinationActivityListByIds(
                convertSet(headExpireRecords, CombinationRecordDO::getActivityId));
        Map<Long, CombinationActivityDO> activityMap = convertMap(activities, CombinationActivityDO::getId);

        // 3. Process group purchases one by one，Expired or Virtual group formation
        KeyValue<Integer, Integer> keyValue = new KeyValue<>(0, 0); // Statistics of expired group purchases and virtual group purchases
        for (CombinationRecordDO record : headExpireRecords) {
            try {
                CombinationActivityDO activity = activityMap.get(record.getActivityId());
                if (activity == null || !activity.getVirtualGroup()) { // Cannot obtain the activity or it is not a virtual group purchase
                    // 3.1. Handle expired group purchases
                    getSelf().handleExpireRecord(record);
                    keyValue.setKey(keyValue.getKey() + 1);
                } else {
                    // 3.2. Processing virtual clustering
                    getSelf().handleVirtualGroupRecord(record);
                    keyValue.setValue(keyValue.getValue() + 1);
                }
            } catch (Exception ignored) { // Handle exception and continue loop
                log.error("[expireCombinationRecord][record({}) Handling exceptions，Please process！record The data is：{}]",
                        record.getId(), JsonUtils.toJsonString(record));
            }
        }
        return keyValue;
    }

    /**
     * Handle expired group purchases
     *
     * @param headRecord Expired group leader record
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleExpireRecord(CombinationRecordDO headRecord) {
        // 1. Update group purchase record
        List<CombinationRecordDO> headAndRecords = updateBatchCombinationRecords(headRecord,
                CombinationRecordStatusEnum.FAILED);
        // 2. Order Cancelled
        headAndRecords.forEach(item -> tradeOrderApi.cancelPaidOrder(item.getUserId(), item.getOrderId()));
    }

    /**
     * Processing virtual group buying
     *
     * @param headRecord Virtual group leader record
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleVirtualGroupRecord(CombinationRecordDO headRecord) {
        // 1. Team members are completed
        combinationRecordMapper.insertBatch(CombinationActivityConvert.INSTANCE.convertVirtualRecordList(headRecord));
        // 2. Update group purchase record
        updateBatchCombinationRecords(headRecord, CombinationRecordStatusEnum.SUCCESS);
    }

    /**
     * Update group purchase record
     *
     * @param headRecord Leader's Record
     * @param status     Status-Group buying failed FAILED Success SUCCESS
     * @return Entire group record（Including the leader and members）
     */
    private List<CombinationRecordDO> updateBatchCombinationRecords(CombinationRecordDO headRecord, CombinationRecordStatusEnum status) {
        // 1. Query team members（Including the leader）
        List<CombinationRecordDO> records = combinationRecordMapper.selectListByHeadId(headRecord.getId());
        records.add(headRecord);// Add the leader

        // 2. Batch update group purchase records status Japanese endTime
        List<CombinationRecordDO> updateRecords = new ArrayList<>(records.size());
        LocalDateTime now = LocalDateTime.now();
        records.forEach(item -> {
            CombinationRecordDO updateRecord = new CombinationRecordDO().setId(item.getId())
                    .setStatus(status.getStatus()).setEndTime(now);
            if (CombinationRecordStatusEnum.isSuccess(status.getStatus())) { // After the virtual group is formed and the status is changed successfully, the number of participants needs to be changed to the number required for the group to form
                updateRecord.setUserCount(updateRecord.getUserSize());
            }
            updateRecords.add(updateRecord);
        });
        combinationRecordMapper.updateBatch(updateRecords);
        return records;
    }

    @Override
    public PageResult<CombinationRecordDO> getCombinationRecordPage(Long userId, AppCombinationRecordPageReqVO pageReqVO) {
        return combinationRecordMapper.selectPage(userId, pageReqVO);
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private CombinationRecordServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
