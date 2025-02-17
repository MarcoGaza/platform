package cn.econets.blossom.module.promotion.controller.app.reward;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.promotion.controller.app.reward.vo.AppRewardActivityRespVO;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;
import cn.econets.blossom.module.promotion.service.reward.RewardActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Save a lot of money and get a free gift")
@RestController
@RequestMapping("/promotion/reward-activity")
@Validated
public class AppRewardActivityController {

    @Resource
    private RewardActivityService rewardActivityService;

    @GetMapping("/get")
    @Operation(summary = "Get discounts and free gifts")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<AppRewardActivityRespVO> getRewardActivity(@RequestParam("id") Long id) {
        RewardActivityDO rewardActivity = rewardActivityService.getRewardActivity(id);
        return success(BeanUtils.toBean(rewardActivity, AppRewardActivityRespVO.class));
    }

}
