package cn.econets.blossom.module.statistics.service.trade;

import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeTrendSummaryRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeSummaryRespBO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction Statistics Service Interface
 *
 */
public interface TradeStatisticsService {

    /**
     * Get transaction status statistics comparison
     *
     * @return Statistical data comparison
     */
    DataComparisonRespVO<TradeTrendSummaryRespVO> getTradeStatisticsAnalyse(
            LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * Get transaction statistics
     *
     * @param beginTime Start time
     * @param endTime   End time
     * @return Statistical data comparison
     */
    Integer getExpensePrice(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * Get transaction status details
     *
     * @param beginTime Start time
     * @param endTime   End time
     * @return Statistical data list
     */
    List<TradeStatisticsDO> getTradeStatisticsList(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review；
    /**
     * Statistics of transaction data for a specified number of days
     *
     * @return Statistical results
     */
    String statisticsTrade(Integer days);

    // TODO Already review
    /**
     * Statistics of transaction data for a specified date
     *
     * @param days Increased number of days
     * @return Transaction data
     */
    TradeSummaryRespBO getTradeSummaryByDays(int days);

    // TODO Already review
    /**
     * Statistics of transaction data for a specified month
     *
     * @param months Increased number of months
     * @return Transaction data
     */
    TradeSummaryRespBO getTradeSummaryByMonths(int months);

}
