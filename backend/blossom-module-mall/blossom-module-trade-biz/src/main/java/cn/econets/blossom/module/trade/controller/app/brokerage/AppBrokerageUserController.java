package cn.econets.blossom.module.trade.controller.app.brokerage;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.*;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageRecordConvert;
import cn.econets.blossom.module.trade.convert.brokerage.BrokerageUserConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageRecordService;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageUserService;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageWithdrawService;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - Distribution User")
@RestController
@RequestMapping("/trade/brokerage-user")
@Validated
@Slf4j
public class AppBrokerageUserController {

    @Resource
    private BrokerageUserService brokerageUserService;
    @Resource
    private BrokerageRecordService brokerageRecordService;
    @Resource
    private BrokerageWithdrawService brokerageWithdrawService;

    @Resource
    private MemberUserApi memberUserApi;

    @GetMapping("/get")
    @Operation(summary = "Get personal distribution information")
    @PreAuthenticated
    public CommonResult<AppBrokerageUserRespVO> getBrokerageUser() {
        Optional<BrokerageUserDO> user = Optional.ofNullable(brokerageUserService.getBrokerageUser(getLoginUserId()));
        // Return data
        AppBrokerageUserRespVO respVO = new AppBrokerageUserRespVO()
                .setBrokerageEnabled(user.map(BrokerageUserDO::getBrokerageEnabled).orElse(false))
                .setBrokeragePrice(user.map(BrokerageUserDO::getBrokeragePrice).orElse(0))
                .setFrozenPrice(user.map(BrokerageUserDO::getFrozenPrice).orElse(0));
        return success(respVO);
    }

    @PutMapping("/bind")
    @Operation(summary = "Bind promoter")
    @PreAuthenticated
    public CommonResult<Boolean> bindBrokerageUser(@Valid @RequestBody AppBrokerageUserBindReqVO reqVO) {
        return success(brokerageUserService.bindBrokerageUser(getLoginUserId(), reqVO.getBindUserId()));
    }

    @GetMapping("/get-summary")
    @Operation(summary = "Get personal distribution statistics")
    @PreAuthenticated
    public CommonResult<AppBrokerageUserMySummaryRespVO> getBrokerageUserSummary() {
        // Query the current logged-in user information
        BrokerageUserDO brokerageUser = brokerageUserService.getBrokerageUser(getLoginUserId());
        // Statistics of user's commission yesterday
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime beginTime = LocalDateTimeUtil.beginOfDay(yesterday);
        LocalDateTime endTime = LocalDateTimeUtil.endOfDay(yesterday);
        Integer yesterdayPrice = brokerageRecordService.getSummaryPriceByUserId(brokerageUser.getId(),
                BrokerageRecordBizTypeEnum.ORDER, BrokerageRecordStatusEnum.SETTLEMENT, beginTime, endTime);
        // Statistics of user withdrawal commissions
        Integer withdrawPrice = brokerageWithdrawService.getWithdrawSummaryListByUserId(Collections.singleton(brokerageUser.getId()),
                        BrokerageWithdrawStatusEnum.AUDIT_SUCCESS).stream()
                .findFirst().map(BrokerageWithdrawSummaryRespBO::getPrice).orElse(0);
        // Count the number of distribution users（Level 1）
        Long firstBrokerageUserCount = brokerageUserService.getBrokerageUserCountByBindUserId(brokerageUser.getId(), 1);
        // Count the number of distribution users（Level 2）
        Long secondBrokerageUserCount = brokerageUserService.getBrokerageUserCountByBindUserId(brokerageUser.getId(), 2);

        // Splicing returns
        return success(BrokerageUserConvert.INSTANCE.convert(yesterdayPrice, withdrawPrice, firstBrokerageUserCount, secondBrokerageUserCount, brokerageUser));
    }

    @GetMapping("/rank-page-by-user-count")
    @Operation(summary = "Get the distribution user ranking paging（Based on the number of users）")
    @PreAuthenticated
    public CommonResult<PageResult<AppBrokerageUserRankByUserCountRespVO>> getBrokerageUserRankPageByUserCount(AppBrokerageUserRankPageReqVO pageReqVO) {
        // Paged query
        PageResult<AppBrokerageUserRankByUserCountRespVO> pageResult = brokerageUserService.getBrokerageUserRankPageByUserCount(pageReqVO);
        // Splicing data
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(pageResult.getList(), AppBrokerageUserRankByUserCountRespVO::getId));
        return success(BrokerageUserConvert.INSTANCE.convertPage03(pageResult, userMap));
    }

    @GetMapping("/rank-page-by-price")
    @Operation(summary = "Get the distribution user ranking paging（Commission-based）")
    @PreAuthenticated
    public CommonResult<PageResult<AppBrokerageUserRankByPriceRespVO>> getBrokerageUserChildSummaryPageByPrice(AppBrokerageUserRankPageReqVO pageReqVO) {
        // Paged query
        PageResult<AppBrokerageUserRankByPriceRespVO> pageResult = brokerageRecordService.getBrokerageUserChildSummaryPageByPrice(pageReqVO);
        // Splicing data
        Map<Long, MemberUserRespDTO> userMap = memberUserApi.getUserMap(convertSet(pageResult.getList(), AppBrokerageUserRankByPriceRespVO::getId));
        return success(BrokerageRecordConvert.INSTANCE.convertPage03(pageResult, userMap));
    }

    @GetMapping("/child-summary-page")
    @Operation(summary = "Get the lower-level distribution statistics page")
    @PreAuthenticated
    public CommonResult<PageResult<AppBrokerageUserChildSummaryRespVO>> getBrokerageUserChildSummaryPage(
            AppBrokerageUserChildSummaryPageReqVO pageReqVO) {
        PageResult<AppBrokerageUserChildSummaryRespVO> pageResult = brokerageUserService.getBrokerageUserChildSummaryPage(pageReqVO, getLoginUserId());
        return success(pageResult);
    }

    @GetMapping("/get-rank-by-price")
    @Operation(summary = "Get the distribution user ranking（Commission-based）")
    @Parameter(name = "times", description = "Time period", required = true)
    public CommonResult<Integer> getRankByPrice(
            @RequestParam("times") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime[] times) {
        return success(brokerageRecordService.getUserRankByPrice(getLoginUserId(), times));
    }

}
