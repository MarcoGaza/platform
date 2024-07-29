package cn.econets.blossom.module.crm.job.customer;

import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * The customer automatically fell into the high seas Job
 *
 */
@Component
public class CrmCustomerAutoPutPoolJob implements JobHandler {

    @Resource
    private CrmCustomerService customerService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = customerService.autoPutCustomerPool();
        return String.format("Customer fell into the high seas %s pcs", count);
    }

}
