package cn.econets.blossom.module.statistics.dal.mysql.pay;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.WalletSummaryRespBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * Payment wallet statistics Mapper
 *
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface PayWalletStatisticsMapper extends BaseMapperX {

    // TODO Already review；
    WalletSummaryRespBO selectRechargeSummaryByPayTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                              @Param("endTime") LocalDateTime endTime,
                                                              @Param("payStatus") Boolean payStatus);

    // TODO Already review；
    WalletSummaryRespBO selectRechargeSummaryByRefundTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                                 @Param("endTime") LocalDateTime endTime,
                                                                 @Param("refundStatus") Integer refundStatus);

    // TODO Already review；
    Integer selectPriceSummaryByBizTypeAndCreateTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                            @Param("endTime") LocalDateTime endTime,
                                                            @Param("bizType") Integer bizType);

    // TODO Already review；
    RechargeSummaryRespBO selectRechargeSummaryGroupByWalletId(@Param("beginTime") LocalDateTime beginTime,
                                                               @Param("endTime") LocalDateTime endTime,
                                                               @Param("payStatus") Boolean payStatus);

    // TODO Already review；
    Integer selectRechargePriceSummary(@Param("payStatus") Integer payStatus);

}
