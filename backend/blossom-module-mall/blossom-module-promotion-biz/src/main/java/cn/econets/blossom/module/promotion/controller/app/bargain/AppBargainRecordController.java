package cn.econets.blossom.module.promotion.controller.app.bargain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordCreateReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordRespVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordSummaryRespVO;
import cn.econets.blossom.module.promotion.convert.bargain.BargainRecordConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainRecordDO;
import cn.econets.blossom.module.promotion.enums.bargain.BargainRecordStatusEnum;
import cn.econets.blossom.module.promotion.service.bargain.BargainActivityService;
import cn.econets.blossom.module.promotion.service.bargain.BargainHelpService;
import cn.econets.blossom.module.promotion.service.bargain.BargainRecordService;
import cn.econets.blossom.module.trade.api.order.TradeOrderApi;
import cn.econets.blossom.module.trade.api.order.dto.TradeOrderRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Bargaining Record")
@RestController
@RequestMapping("/promotion/bargain-record")
@Validated
public class AppBargainRecordController {

    @Resource
    private BargainHelpService bargainHelpService;
    @Resource
    private BargainRecordService bargainRecordService;
    @Resource
    private BargainActivityService bargainActivityService;

    @Resource
    private TradeOrderApi tradeOrderApi;
    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuApi productSpuApi;

    @GetMapping("/get-summary")
    @Operation(summary = "Get the summary information of bargaining records", description = "For the mini program homepage")
    public CommonResult<AppBargainRecordSummaryRespVO> getBargainRecordSummary() {
        // Number of users who successfully bargained
        Integer successUserCount = bargainRecordService.getBargainRecordUserCount(
                BargainRecordStatusEnum.SUCCESS.getStatus());
        if (successUserCount == 0) {
            return success(new AppBargainRecordSummaryRespVO().setSuccessUserCount(0)
                    .setSuccessList(Collections.emptyList()));
        }
        // List of users who successfully bargained
        List<BargainRecordDO> successList = bargainRecordService.getBargainRecordList(
                BargainRecordStatusEnum.SUCCESS.getStatus(), 7);
        List<BargainActivityDO> activityList = bargainActivityService.getBargainActivityList(
                convertSet(successList, BargainRecordDO::getActivityId));
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(
                convertSet(successList, BargainRecordDO::getUserId));
        // Splicing returns
        return success(BargainRecordConvert.INSTANCE.convert(successUserCount, successList, activityList, userMap));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get the details of bargaining records")
    @Parameters({
            @Parameter(name = "id", description = "Bargaining record number", example = "111"), // Scene 1：View the specified bargaining record
            @Parameter(name = "activityId", description = "Bargaining activity number", example = "222") // Scene 2：View the specified bargaining activities
    })
    public CommonResult<AppBargainRecordDetailRespVO> getBargainRecordDetail(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "activityId", required = false) Long activityId) {
        // 1. Query bargaining records + Bargaining activity
        Assert.isTrue(id != null || activityId != null, "Bargaining record number and activity number cannot be empty at the same time");
        BargainRecordDO record = id != null ? bargainRecordService.getBargainRecord(id)
                : bargainRecordService.getLastBargainRecord(getLoginUserId(), activityId);
        if (activityId == null || record != null) {
            activityId = record.getActivityId();
        }
        // 2. Query support records
        Long userId = getLoginUserId();
        Integer helpAction = getHelpAction(userId, record, activityId);
        // 3. If it is your own order，Query order information
        TradeOrderRespDTO order = record != null && record.getOrderId() != null && record.getUserId().equals(getLoginUserId())
                ? tradeOrderApi.getOrder(record.getOrderId()) : null;
        // TODO Continue to query other fields

        // Splicing returns
        return success(BargainRecordConvert.INSTANCE.convert02(record, helpAction, order));
    }

    private Integer getHelpAction(Long userId, BargainRecordDO record, Long activityId) {
        // 0.1 If there is no activity，Cannot help cut
        if (activityId == null) {
            return null;
        }
        // 0.2 If it is your own bargaining record，Cannot help cut
        if (record != null && record.getUserId().equals(userId)) {
            return null;
        }

        // 1. Judge whether assistance has been provided
        if (record != null
            && bargainHelpService.getBargainHelp(record.getId(), userId) != null) {
            return AppBargainRecordDetailRespVO.HELP_ACTION_SUCCESS;
        }
        // 2. Judge whether the support is full
        BargainActivityDO activity = bargainActivityService.getBargainActivity(activityId);
        if (activity != null
            && bargainHelpService.getBargainHelpCountByActivity(activityId, userId) >= activity.getBargainCount()) {
            return AppBargainRecordDetailRespVO.HELP_ACTION_FULL;
        }
        // 3. Allow assistance
        return AppBargainRecordDetailRespVO.HELP_ACTION_NONE;
    }

    @GetMapping("/page")
    @Operation(summary = "Get the paging of bargaining records")
    public CommonResult<PageResult<AppBargainRecordRespVO>> getBargainRecordPage(PageParam pageParam) {
        PageResult<BargainRecordDO> pageResult = bargainRecordService.getBargainRecordPage(getLoginUserId(), pageParam);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // Splicing data
        List<BargainActivityDO> activityList = bargainActivityService.getBargainActivityList(
                convertSet(pageResult.getList(), BargainRecordDO::getActivityId));
        List<ProductSpuRespDTO> spuList = productSpuApi.getSpuList(
                convertSet(pageResult.getList(), BargainRecordDO::getSpuId));
        List<TradeOrderRespDTO> orderList = tradeOrderApi.getOrderList(
                convertSet(pageResult.getList(), BargainRecordDO::getOrderId));
        return success(BargainRecordConvert.INSTANCE.convertPage02(pageResult, activityList, spuList, orderList));
    }

    @PostMapping("/create")
    @Operation(summary = "Create bargaining record", description = "Participate in bargaining activities")
    @PreAuthenticated
    public CommonResult<Long> createBargainRecord(@RequestBody AppBargainRecordCreateReqVO reqVO) {
        Long recordId = bargainRecordService.createBargainRecord(getLoginUserId(), reqVO);
        return success(recordId);
    }

}
