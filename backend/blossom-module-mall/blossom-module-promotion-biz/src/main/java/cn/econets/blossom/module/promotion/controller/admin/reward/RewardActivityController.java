package cn.econets.blossom.module.promotion.controller.admin.reward;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityRespVO;
import cn.econets.blossom.module.promotion.controller.admin.reward.vo.RewardActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.convert.reward.RewardActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.reward.RewardActivityDO;
import cn.econets.blossom.module.promotion.service.reward.RewardActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Save a lot of money and get a free gift")
@RestController
@RequestMapping("/promotion/reward-activity")
@Validated
public class RewardActivityController {

    @Resource
    private RewardActivityService rewardActivityService;

    @PostMapping("/create")
    @Operation(summary = "Create a free gift event")
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:create')")
    public CommonResult<Long> createRewardActivity(@Valid @RequestBody RewardActivityCreateReqVO createReqVO) {
        return success(rewardActivityService.createRewardActivity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update the full discount event")
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:update')")
    public CommonResult<Boolean> updateRewardActivity(@Valid @RequestBody RewardActivityUpdateReqVO updateReqVO) {
        rewardActivityService.updateRewardActivity(updateReqVO);
        return success(true);
    }

    @PutMapping("/close")
    @Operation(summary = "Close the free gift promotion")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:close')")
    public CommonResult<Boolean> closeRewardActivity(@RequestParam("id") Long id) {
        rewardActivityService.closeRewardActivity(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the free gift activity")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:delete')")
    public CommonResult<Boolean> deleteRewardActivity(@RequestParam("id") Long id) {
        rewardActivityService.deleteRewardActivity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get discounts and free gifts")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:query')")
    public CommonResult<RewardActivityRespVO> getRewardActivity(@RequestParam("id") Long id) {
        RewardActivityDO rewardActivity = rewardActivityService.getRewardActivity(id);
        return success(RewardActivityConvert.INSTANCE.convert(rewardActivity));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the discount activity page")
    @PreAuthorize("@ss.hasPermission('promotion:reward-activity:query')")
    public CommonResult<PageResult<RewardActivityRespVO>> getRewardActivityPage(@Valid RewardActivityPageReqVO pageVO) {
        PageResult<RewardActivityDO> pageResult = rewardActivityService.getRewardActivityPage(pageVO);
        return success(RewardActivityConvert.INSTANCE.convertPage(pageResult));
    }

}
