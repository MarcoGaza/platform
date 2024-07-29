package cn.econets.blossom.module.promotion.service.reward;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.reward.RewardActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;
import cn.econets.blossom.module.promotion.dal.mysql.reward.RewardActivityMapper;
import cn.econets.blossom.module.promotion.enums.common.PromotionActivityStatusEnum;
import cn.econets.blossom.module.promotion.util.PromotionUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.promotion.enums.ErrorCodeConstants.*;
import static java.util.Arrays.asList;

/**
 * Save a lot of money and get a free gift Service Implementation class
 *
 */
@Service
@Validated
public class RewardActivityServiceImpl implements RewardActivityService {

    @Resource
    private RewardActivityMapper rewardActivityMapper;

    @Override
    public Long createRewardActivity(RewardActivityCreateReqVO createReqVO) {
        // Check if the product conflicts
        validateRewardActivitySpuConflicts(null, createReqVO.getProductSpuIds());

        // Insert
        RewardActivityDO rewardActivity = RewardActivityConvert.INSTANCE.convert(createReqVO)
                .setStatus(PromotionUtils.calculateActivityStatus(createReqVO.getEndTime()));
        rewardActivityMapper.insert(rewardActivity);
        // Return
        return rewardActivity.getId();
    }

    @Override
    public void updateRewardActivity(RewardActivityUpdateReqVO updateReqVO) {
        // Check existence
        RewardActivityDO dbRewardActivity = validateRewardActivityExists(updateReqVO.getId());
        if (dbRewardActivity.getStatus().equals(PromotionActivityStatusEnum.CLOSE.getStatus())) { // Closed event，Cannot be modified
            throw exception(REWARD_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED);
        }
        // Check if the product conflicts
        validateRewardActivitySpuConflicts(updateReqVO.getId(), updateReqVO.getProductSpuIds());

        // Update
        RewardActivityDO updateObj = RewardActivityConvert.INSTANCE.convert(updateReqVO)
                .setStatus(PromotionUtils.calculateActivityStatus(updateReqVO.getEndTime()));
        rewardActivityMapper.updateById(updateObj);
    }

    @Override
    public void closeRewardActivity(Long id) {
        // Check existence
        RewardActivityDO dbRewardActivity = validateRewardActivityExists(id);
        if (dbRewardActivity.getStatus().equals(PromotionActivityStatusEnum.CLOSE.getStatus())) { // Closed event，Cannot be closed
            throw exception(REWARD_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED);
        }
        if (dbRewardActivity.getStatus().equals(PromotionActivityStatusEnum.END.getStatus())) { // Closed event，Cannot be closed
            throw exception(REWARD_ACTIVITY_CLOSE_FAIL_STATUS_END);
        }

        // Update
        RewardActivityDO updateObj = new RewardActivityDO().setId(id).setStatus(PromotionActivityStatusEnum.CLOSE.getStatus());
        rewardActivityMapper.updateById(updateObj);
    }

    @Override
    public void deleteRewardActivity(Long id) {
        // Check existence
        RewardActivityDO dbRewardActivity = validateRewardActivityExists(id);
        if (!dbRewardActivity.getStatus().equals(PromotionActivityStatusEnum.CLOSE.getStatus())) { // Unclosed activities，Cannot delete
            throw exception(REWARD_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED);
        }

        // Delete
        rewardActivityMapper.deleteById(id);
    }

    private RewardActivityDO validateRewardActivityExists(Long id) {
        RewardActivityDO activity = rewardActivityMapper.selectById(id);
        if (activity == null) {
            throw exception(REWARD_ACTIVITY_NOT_EXISTS);
        }
        return activity;
    }

    // TODO ：Logical problems，Needs optimization；To be divided into the whole field、Verify with the specified value；

    /**
     * Check whether the product participates in any conflicting activities
     *
     * @param id     Activity number
     * @param spuIds Products SPU Number array
     */
    private void validateRewardActivitySpuConflicts(Long id, Collection<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return;
        }
        // Query the activities that the product participates in
        List<RewardActivityDO> rewardActivityList = getRewardActivityListBySpuIds(spuIds,
                asList(PromotionActivityStatusEnum.WAIT.getStatus(), PromotionActivityStatusEnum.RUN.getStatus()));
        if (id != null) { // Exclude yourself from this activity
            rewardActivityList.removeIf(activity -> id.equals(activity.getId()));
        }
        // If not empty，This indicates a conflict
        if (CollUtil.isNotEmpty(rewardActivityList)) {
            throw exception(REWARD_ACTIVITY_SPU_CONFLICTS);
        }
    }

    /**
     * Get the array of products participating in the full discount and free gift activities
     *
     * @param spuIds   Products SPU Number array
     * @param statuses Activity status array
     * @return Array of products participating in the full discount and free gift activities
     */
    private List<RewardActivityDO> getRewardActivityListBySpuIds(Collection<Long> spuIds,
                                                                 Collection<Integer> statuses) {
        List<RewardActivityDO> list = rewardActivityMapper.selectListByStatus(statuses);
        return CollUtil.filter(list, activity -> CollUtil.containsAny(activity.getProductSpuIds(), spuIds));
    }

    @Override
    public RewardActivityDO getRewardActivity(Long id) {
        return rewardActivityMapper.selectById(id);
    }

    @Override
    public PageResult<RewardActivityDO> getRewardActivityPage(RewardActivityPageReqVO pageReqVO) {
        return rewardActivityMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RewardActivityMatchRespDTO> getMatchRewardActivityList(Collection<Long> spuIds) {
        // TODO To be implemented；Specify first，Then globally；
//        // If there is a global activity，Select it directly
//        List<RewardActivityDO> allActivities = rewardActivityMapper.selectListByProductScopeAndStatus(
//                PromotionProductScopeEnum.ALL.getScope(), PromotionActivityStatusEnum.RUN.getStatus());
//        if (CollUtil.isNotEmpty(allActivities)) {
//            return MapUtil.builder(allActivities.get(0), spuIds).build();
//        }
//
//        // Query the activities that a certain activity participates in
//        List<RewardActivityDO> productActivityList = getRewardActivityListBySpuIds(spuIds,
//                singleton(PromotionActivityStatusEnum.RUN.getStatus()));
//        return convertMap(productActivityList, activity -> activity,
//                rewardActivityDO -> intersectionDistinct(rewardActivityDO.getProductSpuIds(), spuIds)); // Intersection return
        return null;
    }

    @Override
    public List<RewardActivityDO> getRewardActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime) {
        // 1. Query the specified spuId of spu Activities participated in
        List<RewardActivityDO> rewardActivityList = rewardActivityMapper.selectListBySpuIdsAndStatus(spuIds, status);
        if (CollUtil.isEmpty(rewardActivityList)) {
            return Collections.emptyList();
        }

        // 2. Query event details
        return rewardActivityMapper.selectListByIdsAndDateTimeLt(convertSet(rewardActivityList, RewardActivityDO::getId), dateTime);
    }

}
