package cn.econets.blossom.module.pay.job.transfer;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.pay.service.transfer.PayTransferService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Synchronization of transfer orders Job
 *
 * Due to the transfer result of the transfer order，Some channels are synchronized through asynchronous notifications，Consider that asynchronous notifications may fail（Small probability），So synchronization is required regularly。
 *
 */
@Component
public class PayTransferSyncJob implements JobHandler {

    @Resource
    private PayTransferService transferService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = transferService.syncTransfer();
        return StrUtil.format("Synchronous transfer order {} pcs", count);
    }
}
