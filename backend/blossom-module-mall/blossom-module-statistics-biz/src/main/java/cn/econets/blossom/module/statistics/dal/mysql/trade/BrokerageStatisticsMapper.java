package cn.econets.blossom.module.statistics.dal.mysql.trade;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * Order distribution statistics Mapper
 *
 */
@Mapper
public interface BrokerageStatisticsMapper extends BaseMapperX<TradeStatisticsDO> {

    // TODO Already review
    Integer selectSummaryPriceByStatusAndUnfreezeTimeBetween(@Param("bizType") Integer bizType,
                                                             @Param("status") Integer status,
                                                             @Param("beginTime") LocalDateTime beginTime,
                                                             @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Long selectWithdrawCountByStatus(@Param("status") Integer status);

}
