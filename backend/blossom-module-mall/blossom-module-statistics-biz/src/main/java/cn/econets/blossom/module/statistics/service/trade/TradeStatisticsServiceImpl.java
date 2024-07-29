package cn.econets.blossom.module.statistics.service.trade;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeTrendSummaryRespVO;
import cn.econets.blossom.module.statistics.convert.trade.TradeStatisticsConvert;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import cn.econets.blossom.module.statistics.dal.mysql.trade.TradeStatisticsMapper;
import cn.econets.blossom.module.statistics.service.pay.PayWalletStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.bo.AfterSaleSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeOrderSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.WalletSummaryRespBO;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Transaction Statistics Service Implementation class
 *
 */
@Service
@Validated
public class TradeStatisticsServiceImpl implements TradeStatisticsService {

    @Resource
    private TradeStatisticsMapper tradeStatisticsMapper;

    @Resource
    private TradeOrderStatisticsService tradeOrderStatisticsService;
    @Resource
    private AfterSaleStatisticsService afterSaleStatisticsService;
    @Resource
    private BrokerageStatisticsService brokerageStatisticsService;
    @Resource
    private PayWalletStatisticsService payWalletStatisticsService;

    @Override
    public TradeSummaryRespBO getTradeSummaryByDays(int days) {
        LocalDateTime date = LocalDateTime.now().plusDays(days);
        return tradeStatisticsMapper.selectOrderCreateCountSumAndOrderPayPriceSumByTimeBetween(
                LocalDateTimeUtil.beginOfDay(date), LocalDateTimeUtil.endOfDay(date));
    }

    @Override
    public TradeSummaryRespBO getTradeSummaryByMonths(int months) {
        LocalDateTime monthDate = LocalDateTime.now().plusMonths(months);
        return tradeStatisticsMapper.selectOrderCreateCountSumAndOrderPayPriceSumByTimeBetween(
                LocalDateTimeUtils.beginOfMonth(monthDate), LocalDateTimeUtils.endOfMonth(monthDate));
    }

    @Override
    public DataComparisonRespVO<TradeTrendSummaryRespVO> getTradeStatisticsAnalyse(LocalDateTime beginTime,
                                                                                        LocalDateTime endTime) {
        // Statistics
        TradeTrendSummaryRespVO value = tradeStatisticsMapper.selectVoByTimeBetween(beginTime, endTime);
        // Comparison data
        LocalDateTime referenceBeginTime = beginTime.minus(Duration.between(beginTime, endTime));
        TradeTrendSummaryRespVO reference = tradeStatisticsMapper.selectVoByTimeBetween(referenceBeginTime, beginTime);
        return TradeStatisticsConvert.INSTANCE.convert(value, reference);
    }

    @Override
    public Integer getExpensePrice(LocalDateTime beginTime, LocalDateTime endTime) {
        return tradeStatisticsMapper.selectExpensePriceByTimeBetween(beginTime, endTime);
    }

    @Override
    public List<TradeStatisticsDO> getTradeStatisticsList(LocalDateTime beginTime, LocalDateTime endTime) {
        return tradeStatisticsMapper.selectListByTimeBetween(beginTime, endTime);
    }

    @Override
    public String statisticsTrade(Integer days) {
        LocalDateTime today = LocalDateTime.now();
        return IntStream.rangeClosed(1, days)
                .mapToObj(day -> statisticsTrade(today.minusDays(day)))
                .sorted()
                .collect(Collectors.joining("\n"));
    }

    /**
     * Statistical transaction data
     *
     * @param date Date to be counted
     * @return Statistical results
     */
    private String statisticsTrade(LocalDateTime date) {
        // 1. Processing statistics time range
        LocalDateTime beginTime = LocalDateTimeUtil.beginOfDay(date);
        LocalDateTime endTime = LocalDateTimeUtil.endOfDay(date);
        String dateStr = DatePattern.NORM_DATE_FORMATTER.format(date);
        // 2. Check whether the day has been counted
        TradeStatisticsDO entity = tradeStatisticsMapper.selectByTimeBetween(beginTime, endTime);
        if (entity != null) {
            return dateStr + " Data already exists，If you need to re-count，Please delete the corresponding data first";
        }

        // 3. From each data table，Statistical corresponding data
        StopWatch stopWatch = new StopWatch(dateStr);
        // 3.1 Statistical Orders
        stopWatch.start("Statistical Orders");
        TradeOrderSummaryRespBO orderSummary = tradeOrderStatisticsService.getOrderSummary(beginTime, endTime);
        stopWatch.stop();
        // 3.2 After-sales statistics
        stopWatch.start("After-sales statistics");
        AfterSaleSummaryRespBO afterSaleSummary = afterSaleStatisticsService.getAfterSaleSummary(beginTime, endTime);
        stopWatch.stop();
        // 3.3 Statistical commission
        stopWatch.start("Statistical commission");
        Integer brokerageSettlementPrice = brokerageStatisticsService.getBrokerageSettlementPriceSummary(beginTime, endTime);
        stopWatch.stop();
        // 3.4 Statistical recharge
        stopWatch.start("Statistical recharge");
        WalletSummaryRespBO walletSummary = payWalletStatisticsService.getWalletSummary(beginTime, endTime);
        stopWatch.stop();

        // 4. Insert data
        entity = TradeStatisticsConvert.INSTANCE.convert(date, orderSummary, afterSaleSummary, brokerageSettlementPrice,
                walletSummary);
        tradeStatisticsMapper.insert(entity);
        return stopWatch.prettyPrint();
    }

}
