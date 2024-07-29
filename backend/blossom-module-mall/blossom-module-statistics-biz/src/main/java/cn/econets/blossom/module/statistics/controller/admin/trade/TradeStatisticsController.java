package cn.econets.blossom.module.statistics.controller.admin.trade;

import cn.hutool.core.util.ArrayUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.*;
import cn.econets.blossom.module.statistics.convert.trade.TradeStatisticsConvert;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import cn.econets.blossom.module.statistics.service.trade.AfterSaleStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.BrokerageStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.TradeOrderStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.TradeStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeSummaryRespBO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Transaction Statistics")
@RestController
@RequestMapping("/statistics/trade")
@Validated
@Slf4j
public class TradeStatisticsController {

    @Resource
    private TradeStatisticsService tradeStatisticsService;
    @Resource
    private TradeOrderStatisticsService tradeOrderStatisticsService;
    @Resource
    private AfterSaleStatisticsService afterSaleStatisticsService;
    @Resource
    private BrokerageStatisticsService brokerageStatisticsService;

    @GetMapping("/summary")
    @Operation(summary = "Get transaction statistics")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<DataComparisonRespVO<TradeSummaryRespVO>> getTradeSummaryComparison() {
        // 1.1 Yesterday's data
        TradeSummaryRespBO yesterdayData = tradeStatisticsService.getTradeSummaryByDays(-1);
        // 1.2 Data from the day before yesterday（Used to compare yesterday's data）
        TradeSummaryRespBO beforeYesterdayData = tradeStatisticsService.getTradeSummaryByDays(-2);

        // 2.1 This month's data
        TradeSummaryRespBO monthData = tradeStatisticsService.getTradeSummaryByMonths(0);
        // 2.2 Last month's data（Used to compare this month's data）
        TradeSummaryRespBO lastMonthData = tradeStatisticsService.getTradeSummaryByMonths(-1);
        // Splicing data
        return success(TradeStatisticsConvert.INSTANCE.convert(yesterdayData, beforeYesterdayData, monthData, lastMonthData));
    }

    @GetMapping("/analyse")
    @Operation(summary = "Get transaction statistics")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<DataComparisonRespVO<TradeTrendSummaryRespVO>> getTradeStatisticsAnalyse(TradeTrendReqVO reqVO) {
        return success(tradeStatisticsService.getTradeStatisticsAnalyse(ArrayUtil.get(reqVO.getTimes(), 0),
                ArrayUtil.get(reqVO.getTimes(), 1)));
    }

    @GetMapping("/list")
    @Operation(summary = "Get transaction status details")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<List<TradeTrendSummaryRespVO>> getTradeStatisticsList(TradeTrendReqVO reqVO) {
        List<TradeStatisticsDO> list = tradeStatisticsService.getTradeStatisticsList(ArrayUtil.get(reqVO.getTimes(), 0),
                ArrayUtil.get(reqVO.getTimes(), 1));
        return success(TradeStatisticsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export to obtain transaction status details Excel")
    @PreAuthorize("@ss.hasPermission('statistics:trade:export')")
    public void exportTradeStatisticsExcel(TradeTrendReqVO reqVO, HttpServletResponse response) throws IOException {
        List<TradeStatisticsDO> list = tradeStatisticsService.getTradeStatisticsList(ArrayUtil.get(reqVO.getTimes(), 0),
                ArrayUtil.get(reqVO.getTimes(), 1));
        // Export Excel
        List<TradeTrendSummaryRespVO> voList = TradeStatisticsConvert.INSTANCE.convertList(list);
        List<TradeTrendSummaryExcelVO> data = TradeStatisticsConvert.INSTANCE.convertList02(voList);
        ExcelUtils.write(response, "Transaction Status.xls", "Data", TradeTrendSummaryExcelVO.class, data);
    }

    @GetMapping("/order-count")
    @Operation(summary = "Get the number of transaction orders")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<TradeOrderCountRespVO> getOrderCount() {
        // Order Statistics
        Long undeliveredCount = tradeOrderStatisticsService.getCountByStatusAndDeliveryType(
                TradeOrderStatusEnum.UNDELIVERED.getStatus(), DeliveryTypeEnum.EXPRESS.getType());
        // TODO After order payment，If you pick up the item at the store，Need update Success DELIVERED；；Not yet done~~Suddenly reacted
        Long pickUpCount = tradeOrderStatisticsService.getCountByStatusAndDeliveryType(
                TradeOrderStatusEnum.DELIVERED.getStatus(), DeliveryTypeEnum.PICK_UP.getType());
        // After-sales statistics
        Long afterSaleApplyCount = afterSaleStatisticsService.getCountByStatus(AfterSaleStatusEnum.APPLY);
        Long auditingWithdrawCount = brokerageStatisticsService.getWithdrawCountByStatus(BrokerageWithdrawStatusEnum.AUDITING);
        // Splicing returns
        return success(TradeStatisticsConvert.INSTANCE.convert(undeliveredCount, pickUpCount, afterSaleApplyCount, auditingWithdrawCount));
    }

    @GetMapping("/order-comparison")
    @Operation(summary = "Get the number of transaction orders")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<DataComparisonRespVO<TradeOrderSummaryRespVO>> getOrderComparison() {
        return success(tradeOrderStatisticsService.getOrderComparison());
    }

    @GetMapping("/order-count-trend")
    @Operation(summary = "Get order volume trend statistics")
    @PreAuthorize("@ss.hasPermission('statistics:trade:query')")
    public CommonResult<List<DataComparisonRespVO<TradeOrderTrendRespVO>>> getOrderCountTrendComparison(@Valid TradeOrderTrendReqVO reqVO) {
        // TODO Pay attention date Sort by；
        return success(tradeOrderStatisticsService.getOrderCountTrendComparison(reqVO));
    }

}
