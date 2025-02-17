package cn.econets.blossom.module.statistics.service.trade;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderSummaryRespVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderTrendReqVO;
import cn.econets.blossom.module.statistics.controller.admin.trade.vo.TradeOrderTrendRespVO;
import cn.econets.blossom.module.statistics.dal.mysql.trade.TradeOrderStatisticsMapper;
import cn.econets.blossom.module.statistics.enums.TimeRangeTypeEnum;
import cn.econets.blossom.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import cn.econets.blossom.module.statistics.service.trade.bo.TradeOrderSummaryRespBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Transaction Order Statistics Service Implementation class
 *
 */
@Service
@Validated
public class TradeOrderStatisticsServiceImpl implements TradeOrderStatisticsService {

    @Resource
    private TradeOrderStatisticsMapper tradeOrderStatisticsMapper;

    @Override
    public TradeOrderSummaryRespBO getOrderSummary(LocalDateTime beginTime, LocalDateTime endTime) {
        return new TradeOrderSummaryRespBO()
                .setOrderCreateCount(tradeOrderStatisticsMapper.selectCountByCreateTimeBetween(beginTime, endTime))
                .setOrderPayCount(tradeOrderStatisticsMapper.selectCountByPayTimeBetween(beginTime, endTime))
                .setOrderPayPrice(tradeOrderStatisticsMapper.selectSummaryPriceByPayTimeBetween(beginTime, endTime));
    }

    @Override
    public List<MemberAreaStatisticsRespBO> getSummaryListByAreaId() {
        return tradeOrderStatisticsMapper.selectSummaryListByAreaId();
    }

    @Override
    public Integer getOrderUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        return tradeOrderStatisticsMapper.selectUserCountByCreateTimeBetween(beginTime, endTime);
    }

    @Override
    public Integer getPayUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        return tradeOrderStatisticsMapper.selectUserCountByPayTimeBetween(beginTime, endTime);
    }

    @Override
    public Integer getOrderPayPrice(LocalDateTime beginTime, LocalDateTime endTime) {
        return tradeOrderStatisticsMapper.selectSummaryPriceByPayTimeBetween(beginTime, endTime);
    }

    @Override
    public Long getCountByStatusAndDeliveryType(Integer status, Integer deliveryType) {
        return tradeOrderStatisticsMapper.selectCountByStatusAndDeliveryType(status, deliveryType);
    }

    @Override
    public DataComparisonRespVO<TradeOrderSummaryRespVO> getOrderComparison() {
        return new DataComparisonRespVO<TradeOrderSummaryRespVO>()
                .setValue(getPayPriceSummary(LocalDateTime.now()))
                .setReference(getPayPriceSummary(LocalDateTime.now().minusDays(1)));
    }

    private TradeOrderSummaryRespVO getPayPriceSummary(LocalDateTime date) {
        LocalDateTime beginTime = LocalDateTimeUtil.beginOfDay(date);
        LocalDateTime endTime = LocalDateTimeUtil.beginOfDay(date);
        return tradeOrderStatisticsMapper.selectPaySummaryByStatusAndPayTimeBetween(
                PayOrderStatusEnum.SUCCESS.getStatus(), beginTime, endTime);
    }

    @Override
    public List<DataComparisonRespVO<TradeOrderTrendRespVO>> getOrderCountTrendComparison(TradeOrderTrendReqVO reqVO) {
        // Query current data
        List<TradeOrderTrendRespVO> value = getOrderCountTrend(reqVO.getType(), reqVO.getBeginTime(), reqVO.getEndTime());
        // Query comparison data
        LocalDateTime referenceEndTime = reqVO.getBeginTime().minusDays(1);
        LocalDateTime referenceBeginTime = referenceEndTime.minus(Duration.between(reqVO.getBeginTime(), reqVO.getEndTime()));
        List<TradeOrderTrendRespVO> reference = getOrderCountTrend(reqVO.getType(), referenceBeginTime, referenceEndTime);
        // Sequential comparison return
        return IntStream.range(0, value.size())
                .mapToObj(index -> new DataComparisonRespVO<TradeOrderTrendRespVO>()
                        .setValue(CollUtil.get(value, index))
                        .setReference(CollUtil.get(reference, index)))
                .collect(Collectors.toList());
    }

    private List<TradeOrderTrendRespVO> getOrderCountTrend(Integer timeRangeType, LocalDateTime beginTime, LocalDateTime endTime) {
        // Situation 1：Statistics by year，Group by month
        if (TimeRangeTypeEnum.YEAR.getType().equals(timeRangeType)) {
            return tradeOrderStatisticsMapper.selectListByPayTimeBetweenAndGroupByMonth(beginTime, endTime);
        }
        // Situation 2：Other groups by day（Sky、Week、Moon）
        return tradeOrderStatisticsMapper.selectListByPayTimeBetweenAndGroupByDay(beginTime, endTime);
    }

}
