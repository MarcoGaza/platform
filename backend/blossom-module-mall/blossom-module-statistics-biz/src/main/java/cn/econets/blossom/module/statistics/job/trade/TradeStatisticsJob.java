package cn.econets.blossom.module.statistics.job.trade;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.statistics.service.trade.TradeStatisticsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// TODO Missing one Job Configuration；Equal Product Configure together
/**
 * Transaction Statistics Job
 *
 */
@Component
public class TradeStatisticsJob implements JobHandler {

    @Resource
    private TradeStatisticsService tradeStatisticsService;

    /**
     * Execute transaction statistics tasks
     *
     * @param param Number of days to be counted，Can only be positive integers，1 Represents yesterday's data
     * @return Statistical results
     */
    @Override
    @TenantJob
    public String execute(String param) {
        // Default yesterday
        param = ObjUtil.defaultIfBlank(param, "1");
        // Check the rationality of parameters
        if (!NumberUtil.isInteger(param)) {
            throw new RuntimeException("The parameters of the transaction statistics task can only be positive integers");
        }
        Integer days = Convert.toInt(param, 0);
        if (days < 1) {
            throw new RuntimeException("The parameters of the transaction statistics task can only be positive integers");
        }
        String result = tradeStatisticsService.statisticsTrade(days);
        return StrUtil.format("Transaction Statistics:\n{}", result);
    }

}
