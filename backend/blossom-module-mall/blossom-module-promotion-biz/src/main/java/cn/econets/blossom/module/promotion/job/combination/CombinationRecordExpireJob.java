package cn.econets.blossom.module.promotion.job.combination;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.promotion.service.combination.CombinationRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Group buy expired Job
 *
 */
@Component
public class CombinationRecordExpireJob implements JobHandler {

    @Resource
    private CombinationRecordService combinationRecordService;

    @Override
    @TenantJob
    public String execute(String param) {
        KeyValue<Integer, Integer> keyValue = combinationRecordService.expireCombinationRecord();
        return StrUtil.format("Expired group buy {} pcs, Virtual group formation {} pcs", keyValue.getKey(), keyValue.getValue());
    }

}
