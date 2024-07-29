package cn.econets.blossom.module.statistics.service.trade;

import cn.econets.blossom.module.statistics.dal.mysql.trade.AfterSaleStatisticsMapper;
import cn.econets.blossom.module.statistics.service.trade.bo.AfterSaleSummaryRespBO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * After-sales statistics Service Implementation class
 *
 */
@Service
@Validated
public class AfterSaleStatisticsServiceImpl implements AfterSaleStatisticsService {

    @Resource
    private AfterSaleStatisticsMapper afterSaleStatisticsMapper;

    @Override
    public AfterSaleSummaryRespBO getAfterSaleSummary(LocalDateTime beginTime, LocalDateTime endTime) {
        return afterSaleStatisticsMapper.selectSummaryByRefundTimeBetween(beginTime, endTime);
    }

    @Override
    public Long getCountByStatus(AfterSaleStatusEnum status) {
        return afterSaleStatisticsMapper.selectCountByStatus(status.getStatus());
    }

}
