package cn.econets.blossom.module.statistics.service.pay;

import cn.econets.blossom.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.WalletSummaryRespBO;

import java.time.LocalDateTime;

/**
 * Wallet statistics Service Interface
 *
 */
public interface PayWalletStatisticsService {

    // TODO Already review
    /**
     * Get wallet statistics
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Wallet Statistics
     */
    WalletSummaryRespBO getWalletSummary(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get wallet recharge statistics
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Wallet recharge statistics
     */
    RechargeSummaryRespBO getUserRechargeSummary(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the total amount of recharge
     *
     * @return Total amount of recharge
     */
    Integer getRechargePriceSummary();

}
