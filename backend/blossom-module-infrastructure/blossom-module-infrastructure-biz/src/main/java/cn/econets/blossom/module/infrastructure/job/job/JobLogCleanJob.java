package cn.econets.blossom.module.infrastructure.job.job;

import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.aop.TenantIgnore;
import cn.econets.blossom.module.infrastructure.service.job.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Physical deletion N Task log from days ago Job
 *
 */
@Slf4j
@Component
public class JobLogCleanJob implements JobHandler {

    @Resource
    private JobLogService jobLogService;

    /**
     * Clean up more than（14）Day's Log
     */
    private static final Integer JOB_CLEAN_RETAIN_DAY = 14;

    /**
     * Number of entries to delete each time，If the value is too high, it may cause excessive pressure on the database
     */
    private static final Integer DELETE_LIMIT = 100;

    @Override
    @TenantIgnore
    public String execute(String param) {
        Integer count = jobLogService.cleanJobLog(JOB_CLEAN_RETAIN_DAY, DELETE_LIMIT);
        log.info("[execute][Number of scheduled task logs to be cleaned up regularly ({}) pcs]", count);
        return String.format("Number of scheduled task logs to be cleaned up regularly %s pcs", count);
    }

}
