package cn.econets.blossom.module.infrastructure.job.logger;

import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.aop.TenantIgnore;
import cn.econets.blossom.module.infrastructure.service.logger.ApiAccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Physical deletion N Access log from days ago Job
 *
 */
@Component
@Slf4j
public class AccessLogCleanJob implements JobHandler {

    @Resource
    private ApiAccessLogService apiAccessLogService;

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
        Integer count = apiAccessLogService.cleanAccessLog(JOB_CLEAN_RETAIN_DAY, DELETE_LIMIT);
        log.info("[execute][Number of access logs to be cleaned up regularly ({}) pcs]", count);
        return String.format("Number of error logs to be cleaned up regularly %s pcs", count);
    }

}
