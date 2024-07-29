package cn.econets.blossom.module.statistics.controller.admin.member;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.*;
import cn.econets.blossom.module.statistics.convert.member.MemberStatisticsConvert;
import cn.econets.blossom.module.statistics.service.infra.ApiAccessLogStatisticsService;
import cn.econets.blossom.module.statistics.service.member.MemberStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.TradeOrderStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Member Statistics")
@RestController
@RequestMapping("/statistics/member")
@Validated
@Slf4j
public class MemberStatisticsController {

    @Resource
    private MemberStatisticsService memberStatisticsService;
    @Resource
    private TradeOrderStatisticsService tradeOrderStatisticsService;
    @Resource
    private ApiAccessLogStatisticsService apiAccessLogStatisticsService;

    // TODO Already review
    @GetMapping("/summary")
    @Operation(summary = "Get member statistics（Real-time statistics）")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<MemberSummaryRespVO> getMemberSummary() {
        return success(memberStatisticsService.getMemberSummary());
    }

    // TODO Already review
    @GetMapping("/analyse")
    @Operation(summary = "Get member analysis data")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<MemberAnalyseRespVO> getMemberAnalyse(MemberAnalyseReqVO reqVO) {
        // 1. Query data
        LocalDateTime beginTime = ArrayUtil.get(reqVO.getTimes(), 0);
        LocalDateTime endTime = ArrayUtil.get(reqVO.getTimes(), 1);
        // 1.1 Query and analyze control data
        DataComparisonRespVO<MemberAnalyseDataRespVO> comparisonData = memberStatisticsService.getMemberAnalyseComparisonData(beginTime, endTime);
        // TODO This may be a little special，To follow create_time Come to check；Otherwise its funnel will not be uniform；Because it is the number of visits > Today's order > Today's Payer；is a unified dimension；
        // 1.2 Query the number of users who have completed transactions
        Integer payUserCount = tradeOrderStatisticsService.getPayUserCount(beginTime, endTime);
        // 1.3 Calculate the average order value
        int atv = 0;
        if (payUserCount != null && payUserCount > 0) {
            // TODO Similar to the above payUserCount
            Integer payPrice = tradeOrderStatisticsService.getOrderPayPrice(beginTime, endTime);
            atv = NumberUtil.div(payPrice, payUserCount).intValue();
        }
        // 1.4 Query the number of visitors
        Integer visitUserCount = apiAccessLogStatisticsService.getIpCount(UserTypeEnum.MEMBER.getValue(), beginTime, endTime);
        // 1.5 Number of users placing orders
        Integer orderUserCount = tradeOrderStatisticsService.getOrderUserCount(beginTime, endTime);

        // 2. Splicing returns
        return success(MemberStatisticsConvert.INSTANCE.convert(visitUserCount, orderUserCount, payUserCount, atv, comparisonData));
    }

    // TODO Already review
    @GetMapping("/area-statistics-list")
    @Operation(summary = "By province，Get member statistics list")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<List<MemberAreaStatisticsRespVO>> getMemberAreaStatisticsList() {
        return success(memberStatisticsService.getMemberAreaStatisticsList());
    }

    // TODO Already review
    @GetMapping("/sex-statistics-list")
    @Operation(summary = "According to gender，Get member statistics list")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<List<MemberSexStatisticsRespVO>> getMemberSexStatisticsList() {
        return success(memberStatisticsService.getMemberSexStatisticsList());
    }

    // TODO Already review
    @GetMapping("/terminal-statistics-list")
    @Operation(summary = "According to the terminal，Get member statistics list")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<List<MemberTerminalStatisticsRespVO>> getMemberTerminalStatisticsList() {
        return success(memberStatisticsService.getMemberTerminalStatisticsList());
    }

    // TODO Already review
    // TODO Pay attention date Sort by；
    @GetMapping("/user-count-comparison")
    @Operation(summary = "Get user number comparison")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<DataComparisonRespVO<MemberCountRespVO>> getUserCountComparison() {
        return success(memberStatisticsService.getUserCountComparison());
    }

    // TODO Already review
    @GetMapping("/register-count-list")
    @Operation(summary = "Get the member registration quantity list")
    @PreAuthorize("@ss.hasPermission('statistics:member:query')")
    public CommonResult<List<MemberRegisterCountRespVO>> getMemberRegisterCountList(MemberAnalyseReqVO reqVO) {
        return success(memberStatisticsService.getMemberRegisterCountList(
                ArrayUtil.get(reqVO.getTimes(), 0), ArrayUtil.get(reqVO.getTimes(), 1)));
    }

}
