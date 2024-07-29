package cn.econets.blossom.module.pay.job.order;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Payment order expired Job
 *
 * When payment exceeds the expiration date，The payment channel will not notify of expiration，So it is necessary to close the expired items regularly。
 *
 *
 */
@Component
public class PayOrderExpireJob implements JobHandler {

    @Resource
    private PayOrderService orderService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = orderService.expireOrder();
        return StrUtil.format("Payment expired {} pcs", count);
    }

}
