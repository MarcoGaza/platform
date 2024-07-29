package cn.econets.blossom.module.pay.job.order;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Synchronization of payment orders Job
 *
 * Due to the status of the payment order，Synchronize via asynchronous notification from payment channel，Consider that asynchronous notifications may fail（Small probability），So synchronization is required regularly。
 *
 *
 */
@Component
public class PayOrderSyncJob implements JobHandler {

    /**
     * Synchronous creation time is N Orders from minutes ago
     *
     * Why synchronize? 10 Orders from minutes ago？
     *  Because an order initiated payment，Payment successful，Most in 10 Within minutes，Need to ensure polling。
     *  If set to 30、60 or a larger time range，Too many orders will cause polling，Affects performance。Of course，You can also handle it according to your own business situation。
     */
    private static final Duration CREATE_TIME_DURATION_BEFORE = Duration.ofMinutes(10);

    @Resource
    private PayOrderService orderService;

    @Override
    @TenantJob
    public String execute(String param) {
        LocalDateTime minCreateTime = LocalDateTime.now().minus(CREATE_TIME_DURATION_BEFORE);
        int count = orderService.syncOrder(minCreateTime);
        return StrUtil.format("Synchronize payment order {} pcs", count);
    }

}
