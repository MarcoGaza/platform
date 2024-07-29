package cn.econets.blossom.module.statistics.convert.trade;

import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderCountRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeSummaryRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeTrendSummaryExcelVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeTrendSummaryRespVO;
import cn.econets.blossom.module.statistics.dal.dataobject.trade.TradeStatisticsDO;
import cn.econets.blossom.module.statistics.service.trade.bo.AfterSaleSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeOrderSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.WalletSummaryRespBO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction Statistics Convert
 *
 */
@Mapper
public interface TradeStatisticsConvert {

    TradeStatisticsConvert INSTANCE = Mappers.getMapper(TradeStatisticsConvert.class);

    default DataComparisonRespVO<TradeSummaryRespVO> convert(TradeSummaryRespBO yesterdayData,
                                                             TradeSummaryRespBO beforeYesterdayData,
                                                             TradeSummaryRespBO monthData,
                                                             TradeSummaryRespBO lastMonthData) {
        return convert(convert(yesterdayData, monthData), convert(beforeYesterdayData, lastMonthData));
    }


    default TradeSummaryRespVO convert(TradeSummaryRespBO yesterdayData, TradeSummaryRespBO monthData) {
        return new TradeSummaryRespVO()
                .setYesterdayOrderCount(yesterdayData.getCount()).setYesterdayPayPrice(yesterdayData.getSummary())
                .setMonthOrderCount(monthData.getCount()).setMonthPayPrice(monthData.getSummary());
    }

    DataComparisonRespVO<TradeSummaryRespVO> convert(TradeSummaryRespVO value, TradeSummaryRespVO reference);

    DataComparisonRespVO<TradeTrendSummaryRespVO> convert(TradeTrendSummaryRespVO value,
                                                          TradeTrendSummaryRespVO reference);

    List<TradeTrendSummaryExcelVO> convertList02(List<TradeTrendSummaryRespVO> list);

    TradeStatisticsDO convert(LocalDateTime time, TradeOrderSummaryRespBO orderSummary,
                              AfterSaleSummaryRespBO afterSaleSummary, Integer brokerageSettlementPrice,
                              WalletSummaryRespBO walletSummary);

    @IterableMapping(qualifiedByName = "convert")
    List<TradeTrendSummaryRespVO> convertList(List<TradeStatisticsDO> list);

    TradeTrendSummaryRespVO convertA(TradeStatisticsDO tradeStatistics);

    @Named("convert")
    default TradeTrendSummaryRespVO convert(TradeStatisticsDO tradeStatistics) {
        TradeTrendSummaryRespVO vo = convertA(tradeStatistics);
        return vo
                .setDate(tradeStatistics.getTime().toLocalDate())
                // Sales = Product payment amount + Recharge amount
                .setTurnoverPrice(tradeStatistics.getOrderPayPrice() + tradeStatistics.getRechargePayPrice())
                // Expenditure amount = Balance payment amount + Amount of commission paid + Product refund amount
                .setExpensePrice(tradeStatistics.getWalletPayPrice() + tradeStatistics.getBrokerageSettlementPrice() + tradeStatistics.getAfterSaleRefundPrice());
    }

    TradeOrderCountRespVO convert(Long undelivered, Long pickUp, Long afterSaleApply, Long auditingWithdraw);

}
