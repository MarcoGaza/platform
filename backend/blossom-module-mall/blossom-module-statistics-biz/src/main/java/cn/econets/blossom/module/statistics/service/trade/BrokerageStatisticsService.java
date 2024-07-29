package cn.econets.blossom.module.statistics.service.trade;

import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;

import java.time.LocalDateTime;

/**
 * Distribution Statistics Service Interface
 *
 */
public interface BrokerageStatisticsService {

    // TODO Already review
    /**
     * Get the settled commission amount
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return The amount of commission settled
     */
    Integer getBrokerageSettlementPriceSummary(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the number of withdrawal records in the specified status
     *
     * @param status Withdrawal record status
     * @return Number of withdrawal records
     */
    Long getWithdrawCountByStatus(BrokerageWithdrawStatusEnum status);

}
