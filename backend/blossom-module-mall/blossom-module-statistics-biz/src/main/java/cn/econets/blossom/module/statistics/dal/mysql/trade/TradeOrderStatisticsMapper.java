package cn.econets.blossom.module.statistics.dal.mysql.trade;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderSummaryRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderTrendRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import cn.econets.blossom.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Trading order statistics Mapper
 *
 */
@Mapper
public interface TradeOrderStatisticsMapper extends BaseMapperX<TradeStatisticsDO> {

    // TODO Already review
    List<MemberAreaStatisticsRespBO> selectSummaryListByAreaId();

    // TODO Already review
    Integer selectCountByCreateTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                           @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Integer selectCountByPayTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                        @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Integer selectSummaryPriceByPayTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                               @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Integer selectUserCountByCreateTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                               @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Integer selectUserCountByPayTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                            @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    /**
     * Count orders by payment time（Group by day）
     *
     * @param beginTime Payment start time
     * @param endTime   Payment deadline
     * @return Order Statistics List
     */
    List<TradeOrderTrendRespVO> selectListByPayTimeBetweenAndGroupByDay(@Param("beginTime") LocalDateTime beginTime,
                                                                        @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    /**
     * Count orders by payment time（Group by month）
     *
     * @param beginTime Payment start time
     * @param endTime   Payment deadline
     * @return Order Statistics List
     */
    List<TradeOrderTrendRespVO> selectListByPayTimeBetweenAndGroupByMonth(@Param("beginTime") LocalDateTime beginTime,
                                                                          @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Long selectCountByStatusAndDeliveryType(@Param("status") Integer status, @Param("deliveryType") Integer deliveryType);

    // TODO Already review
    TradeOrderSummaryRespVO selectPaySummaryByStatusAndPayTimeBetween(@Param("status") Integer status,
                                                                      @Param("beginTime") LocalDateTime beginTime,
                                                                      @Param("endTime") LocalDateTime endTime);

}
