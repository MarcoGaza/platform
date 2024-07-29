package cn.econets.blossom.module.statistics.service.trade;

import cn.econets.blossom.module.statistics.service.trade.bo.AfterSaleSummaryRespBO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;

import java.time.LocalDateTime;

/**
 * After-sales statistics Service Interface
 *
 */
public interface AfterSaleStatisticsService {

    // TODO Already review
    /**
     * Get after-sales order statistics
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return After-sales statistics results
     */
    AfterSaleSummaryRespBO getAfterSaleSummary(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the number of after-sales orders in the specified status
     *
     * @param status After-sales status
     * @return After-sales order quantity
     */
    Long getCountByStatus(AfterSaleStatusEnum status);

}
