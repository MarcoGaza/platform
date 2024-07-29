package cn.econets.blossom.module.promotion.controller.app.combination;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordDetailRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordRespVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordSummaryRespVO;
import cn.econets.blossom.module.promotion.convert.combination.CombinationActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import cn.econets.blossom.module.promotion.service.combination.CombinationRecordService;
import cn.econets.blossom.module.trade.api.order.TradeOrderApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Lazy;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - Group buying activity")
@RestController
@RequestMapping("/promotion/combination-record")
@Validated
public class AppCombinationRecordController {

    @Resource
    private CombinationRecordService combinationRecordService;
    @Resource
    @Lazy
    private TradeOrderApi tradeOrderApi;

    @GetMapping("/get-summary")
    @Operation(summary = "Get summary information of group purchase records", description = "For the mini program home page")
    public CommonResult<AppCombinationRecordSummaryRespVO> getCombinationRecordSummary() {
        AppCombinationRecordSummaryRespVO summary = new AppCombinationRecordSummaryRespVO();
        // 1. Get the number of users participating in the group purchase
        Long userCount = combinationRecordService.getCombinationUserCount();
        if (userCount == 0) {
            summary.setAvatars(Collections.emptyList());
            summary.setUserCount(userCount);
            return success(summary);
        }
        summary.setUserCount(userCount);

        // 2. Get the group record avatar
        List<CombinationRecordDO> records = combinationRecordService.getLatestCombinationRecordList(
                AppCombinationRecordSummaryRespVO.AVATAR_COUNT);
        summary.setAvatars(convertList(records, CombinationRecordDO::getAvatar));
        return success(summary);
    }

    @GetMapping("/get-head-list")
    @Operation(summary = "Get the most recent n Group buying records（Initiated by the team leader）")
    @Parameters({
            @Parameter(name = "activityId", description = "Group buying activity number"),
            @Parameter(name = "status", description = "Group buying status"), // Corresponding CombinationRecordStatusEnum Enumeration
            @Parameter(name = "count", description = "Quantity")
    })
    public CommonResult<List<AppCombinationRecordRespVO>> getHeadCombinationRecordList(
            @RequestParam(value = "activityId", required = false) Long activityId,
            @RequestParam("status") Integer status,
            @RequestParam(value = "count", defaultValue = "20") @Max(20) Integer count) {
        List<CombinationRecordDO> list = combinationRecordService.getHeadCombinationRecordList(activityId, status, count);
        return success(BeanUtils.toBean(list, AppCombinationRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get my group purchase record page")
    @PreAuthenticated
    public CommonResult<PageResult<AppCombinationRecordRespVO>> getCombinationRecordPage(
            @Valid AppCombinationRecordPageReqVO pageReqVO) {
        PageResult<CombinationRecordDO> pageResult = combinationRecordService.getCombinationRecordPage(
                getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppCombinationRecordRespVO.class));
    }

    @GetMapping("/get-detail")
    @Operation(summary = "Get the details of the group purchase record")
    @Parameter(name = "id", description = "Group buying record number", required = true, example = "1024")
    public CommonResult<AppCombinationRecordDetailRespVO> getCombinationRecordDetail(@RequestParam("id") Long id) {
        // 1. Search for this group purchase record
        CombinationRecordDO record = combinationRecordService.getCombinationRecordById(id);
        if (record == null) {
            return success(null);
        }

        // 2. Search for the group joining record
        CombinationRecordDO headRecord;
        List<CombinationRecordDO> memberRecords;
        if (Objects.equals(record.getHeadId(), CombinationRecordDO.HEAD_ID_GROUP)) { // Situation 1：Team Leader
            headRecord = record;
            memberRecords = combinationRecordService.getCombinationRecordListByHeadId(record.getId());
        } else { // Case 2：Team member
            headRecord = combinationRecordService.getCombinationRecordById(record.getHeadId());
            memberRecords = combinationRecordService.getCombinationRecordListByHeadId(headRecord.getId());
        }

        // 3. Splicing data
        return success(CombinationActivityConvert.INSTANCE.convert(getLoginUserId(), headRecord, memberRecords));
    }

    @GetMapping("/cancel")
    @Operation(summary = "Cancel group buy")
    @Parameter(name = "id", description = "Group buying record number", required = true, example = "1024")
    public CommonResult<Boolean> cancelCombinationRecord(@RequestParam("id") Long id) {
        Long userId = getLoginUserId();
        // 1、Search for this group purchase record
        CombinationRecordDO record = combinationRecordService.getCombinationRecordByIdAndUser(userId, id);
        if (record == null) {
            return success(Boolean.FALSE);
        }
        // 1.1、Need to verify the group purchase record first, it is not completed；
        if (!CombinationRecordStatusEnum.isInProgress(record.getStatus())) {
            return success(Boolean.FALSE);
        }

        // 2. Cancel the paid order
        tradeOrderApi.cancelPaidOrder(userId, record.getOrderId());
        // 3. Cancel group purchase record
        combinationRecordService.cancelCombinationRecord(userId, record.getId(), record.getHeadId());
        return success(Boolean.TRUE);
    }

}
