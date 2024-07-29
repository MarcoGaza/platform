package cn.econets.blossom.module.promotion.service.reward;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Save a lot of money and get a free gift Service Interface
 *
 */
public interface RewardActivityService {

    /**
     * Create a free gift event
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createRewardActivity(@Valid RewardActivityCreateReqVO createReqVO);

    /**
     * Update the full discount event
     *
     * @param updateReqVO Update information
     */
    void updateRewardActivity(@Valid RewardActivityUpdateReqVO updateReqVO);

    /**
     * Close the free gift promotion
     *
     * @param id Activity number
     */
    void closeRewardActivity(Long id);

    /**
     * Delete the free gift activity
     *
     * @param id Number
     */
    void deleteRewardActivity(Long id);

    /**
     * Get discounts and free gifts
     *
     * @param id Number
     * @return Save a lot of money and get a free gift
     */
    RewardActivityDO getRewardActivity(Long id);

    /**
     * Get the discount activity page
     *
     * @param pageReqVO Paged query
     * @return Paging for discounts and free gifts
     */
    PageResult<RewardActivityDO> getRewardActivityPage(RewardActivityPageReqVO pageReqVO);

    /**
     * Based on the specified SPU Number array，Get their matching full discount activities
     *
     * @param spuIds SPU Number array
     * @return List of free gift and discount activities
     */
    List<RewardActivityMatchRespDTO> getMatchRewardActivityList(Collection<Long> spuIds);

    /**
     * Get the specified value spu Number of the most recently participated event，Each spuId Only one record is returned
     *
     * @param spuIds   spu Number
     * @param status   Status
     * @param dateTime Current date and time
     * @return List of free gift and discount activities
     */
    List<RewardActivityDO> getRewardActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime);

}
