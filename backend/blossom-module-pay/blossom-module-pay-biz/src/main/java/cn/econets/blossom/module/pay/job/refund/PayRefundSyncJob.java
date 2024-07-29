package cn.econets.blossom.module.pay.job.refund;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Synchronization of refund orders Job
 *
 * The status of the order due to refund，Synchronize via asynchronous notification from payment channel，Consider that asynchronous notifications may fail（Small probability），So it needs to be synchronized regularly。
 *
 *
 */
@Component
public class PayRefundSyncJob implements JobHandler {

    @Resource
    private PayRefundService refundService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = refundService.syncRefund();
        return StrUtil.format("Synchronize refund orders {} pcs", count);
    }

}
