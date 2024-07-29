package cn.econets.blossom.module.statistics.service.trade;

import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.*;
import cn.econets.blossom.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeOrderSummaryRespBO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Trading order statistics Service Interface
 *
 */
public interface TradeOrderStatisticsService {

    /**
     * Get order statistics
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Order statistics results
     */
    TradeOrderSummaryRespBO getOrderSummary(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get regional order statistics
     *
     * @return Order statistics results
     */
    List<MemberAreaStatisticsRespBO> getSummaryListByAreaId();

    // TODO Already review
    /**
     * Get the number of users who placed orders
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Number of users placing orders
     */
    Integer getOrderUserCount(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the number of paying users
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Number of paying users
     */
    Integer getPayUserCount(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get payment amount
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Pay user amount
     */
    Integer getOrderPayPrice(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * According to order status、Logistics type，Get the number of transaction orders
     *
     * @return Order Quantity
     */
    Long getCountByStatusAndDeliveryType(Integer status, Integer deliveryType);

    // TODO Already review
    /**
     * Transaction order sales comparison
     *
     * @return Sales comparison
     */
    DataComparisonRespVO<TradeOrderSummaryRespVO> getOrderComparison();

    // TODO Already review
    /**
     * Get order volume trend statistics
     *
     * @param reqVO Statistical parameters
     * @return Order volume trend statistics
     */
    List<DataComparisonRespVO<TradeOrderTrendRespVO>> getOrderCountTrendComparison(TradeOrderTrendReqVO reqVO);

}
