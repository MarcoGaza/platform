package cn.econets.blossom.module.promotion.controller.app.activity;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.promotion.controller.app.activity.vo.AppActivityRespVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import cn.econets.blossom.module.promotion.enums.common.PromotionActivityStatusEnum;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.promotion.service.bargain.BargainActivityService;
import cn.econets.blossom.module.promotion.service.combination.CombinationActivityService;
import cn.econets.blossom.module.promotion.service.discount.DiscountActivityService;
import cn.econets.blossom.module.promotion.service.reward.RewardActivityService;
import cn.econets.blossom.module.promotion.service.seckill.SeckillActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;

@Tag(name = "User APP - Marketing Activities") // Used to provide services across multiple activities HTTP Interface
@RestController
@RequestMapping("/promotion/activity")
@Validated
public class AppActivityController {

    @Resource
    private CombinationActivityService combinationActivityService;
    @Resource
    private SeckillActivityService seckillActivityService;
    @Resource
    private BargainActivityService bargainActivityService;
    @Resource
    private DiscountActivityService discountActivityService;
    @Resource
    private RewardActivityService rewardActivityService;

    @GetMapping("/list-by-spu-id")
    @Operation(summary = "Get a single product，Every activity recently participated in")
    @Parameter(name = "spuId", description = "Product Number", required = true)
    public CommonResult<List<AppActivityRespVO>> getActivityListBySpuId(@RequestParam("spuId") Long spuId) {
        // Every activity，Return only one
        return success(getAppActivityList(Collections.singletonList(spuId)));
    }

    @GetMapping("/list-by-spu-ids")
    @Operation(summary = "Get multiple products，Every activity recently participated in")
    @Parameter(name = "spuIds", description = "Product number array", required = true)
    public CommonResult<Map<Long, List<AppActivityRespVO>>> getActivityListBySpuIds(@RequestParam("spuIds") List<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return success(MapUtil.empty());
        }
        // Every activity，Return only one；key for SPU Number
        return success(convertMultiMap(getAppActivityList(spuIds), AppActivityRespVO::getSpuId));
    }

    private List<AppActivityRespVO> getAppActivityList(Collection<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return new ArrayList<>();
        }
        // Get the opened, started and unfinished activities
        List<AppActivityRespVO> activityList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        // 1. Group buying activity
        getCombinationActivities(spuIds, now, activityList);
        // 2. Second-sale event
        getSeckillActivities(spuIds, now, activityList);
        // 3. Bargaining activity
        getBargainActivities(spuIds, now, activityList);
        // 4. Limited time discount event
        getDiscountActivities(spuIds, now, activityList);
        // 5. Save a lot of money and get a free gift
        getRewardActivities(spuIds, now, activityList);
        return activityList;
    }

    private void getCombinationActivities(Collection<Long> spuIds, LocalDateTime now, List<AppActivityRespVO> activityList) {
        List<CombinationActivityDO> combinationActivities = combinationActivityService.getCombinationActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isEmpty(combinationActivities)) {
            return;
        }

        combinationActivities.forEach(item -> {
            activityList.add(new AppActivityRespVO(item.getId(), PromotionTypeEnum.COMBINATION_ACTIVITY.getType(),
                    item.getName(), item.getSpuId(), item.getStartTime(), item.getEndTime()));
        });
    }

    private void getSeckillActivities(Collection<Long> spuIds, LocalDateTime now, List<AppActivityRespVO> activityList) {
        List<SeckillActivityDO> seckillActivities = seckillActivityService.getSeckillActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isEmpty(seckillActivities)) {
            return;
        }

        seckillActivities.forEach(item -> {
            activityList.add(new AppActivityRespVO(item.getId(), PromotionTypeEnum.SECKILL_ACTIVITY.getType(),
                    item.getName(), item.getSpuId(), item.getStartTime(), item.getEndTime()));
        });
    }

    private void getBargainActivities(Collection<Long> spuIds, LocalDateTime now, List<AppActivityRespVO> activityList) {
        List<BargainActivityDO> bargainActivities = bargainActivityService.getBargainActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isNotEmpty(bargainActivities)) {
            return;
        }

        bargainActivities.forEach(item -> {
            activityList.add(new AppActivityRespVO(item.getId(), PromotionTypeEnum.BARGAIN_ACTIVITY.getType(),
                    item.getName(), item.getSpuId(), item.getStartTime(), item.getEndTime()));
        });
    }

    private void getDiscountActivities(Collection<Long> spuIds, LocalDateTime now, List<AppActivityRespVO> activityList) {
        List<DiscountActivityDO> discountActivities = discountActivityService.getDiscountActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, CommonStatusEnum.ENABLE.getStatus(), now);
        if (CollUtil.isEmpty(discountActivities)) {
            return;
        }

        List<DiscountProductDO> products = discountActivityService.getDiscountProductsByActivityId(
                convertSet(discountActivities, DiscountActivityDO::getId));
        Map<Long, Long> productMap = convertMap(products, DiscountProductDO::getActivityId, DiscountProductDO::getSpuId);
        discountActivities.forEach(item -> activityList.add(new AppActivityRespVO(item.getId(), PromotionTypeEnum.DISCOUNT_ACTIVITY.getType(),
                item.getName(), productMap.get(item.getId()), item.getStartTime(), item.getEndTime())));
    }

    private void getRewardActivities(Collection<Long> spuIds, LocalDateTime now, List<AppActivityRespVO> activityList) {
        // TODO Yes 3 Range，Not only spuId，And categoryId，All
        List<RewardActivityDO> rewardActivityList = rewardActivityService.getRewardActivityBySpuIdsAndStatusAndDateTimeLt(
                spuIds, PromotionActivityStatusEnum.RUN.getStatus(), now);
        if (CollUtil.isEmpty(rewardActivityList)) {
            return;
        }

        Map<Long, Optional<RewardActivityDO>> spuIdAndActivityMap = spuIds.stream()
                .collect(Collectors.toMap(
                        spuId -> spuId,
                        spuId -> rewardActivityList.stream()
                                .filter(activity -> activity.getProductSpuIds().contains(spuId))
                                .max(Comparator.comparing(RewardActivityDO::getCreateTime))));
        for (Long supId : spuIdAndActivityMap.keySet()) {
            if (!spuIdAndActivityMap.get(supId).isPresent()) {
                continue;
            }

            RewardActivityDO rewardActivityDO = spuIdAndActivityMap.get(supId).get();
            activityList.add(new AppActivityRespVO(rewardActivityDO.getId(), PromotionTypeEnum.REWARD_ACTIVITY.getType(),
                    rewardActivityDO.getName(), supId, rewardActivityDO.getStartTime(), rewardActivityDO.getEndTime()));
        }
    }

}
