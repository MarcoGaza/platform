package cn.econets.blossom.module.promotion.api.reward;

import cn.econets.blossom.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * Save a lot of money and get a free gift API Interface
 *
 */
public interface RewardActivityApi {


    /**
     * Based on the specified SPU Number array，Get their matching full discount activities
     *
     * @param spuIds SPU Number array
     * @return List of free gift and discount activities
     */
    List<RewardActivityMatchRespDTO> getMatchRewardActivityList(Collection<Long> spuIds);

}
