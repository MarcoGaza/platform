package cn.econets.blossom.module.trade.job.order;

import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.trade.service.order.TradeOrderUpdateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Automatic receipt of transaction orders Job
 *
 */
@Component
public class TradeOrderAutoReceiveJob implements JobHandler {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = tradeOrderUpdateService.receiveOrderBySystem();
        return String.format("Automatic delivery %s pcs", count);
    }

}
