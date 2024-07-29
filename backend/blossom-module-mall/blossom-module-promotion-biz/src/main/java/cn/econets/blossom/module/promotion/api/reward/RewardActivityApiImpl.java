package cn.econets.blossom.module.promotion.api.reward;

import cn.econets.blossom.module.promotion.api.reward.dto.RewardActivityMatchRespDTO;
import cn.econets.blossom.module.promotion.service.reward.RewardActivityService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Save a lot of money and get a free gift API Implementation class
 *
 */
@Service
@Validated
public class RewardActivityApiImpl implements RewardActivityApi {

    @Resource
    private RewardActivityService rewardActivityService;

    @Override
    public List<RewardActivityMatchRespDTO> getMatchRewardActivityList(Collection<Long> spuIds) {
        return rewardActivityService.getMatchRewardActivityList(spuIds);
    }

}
