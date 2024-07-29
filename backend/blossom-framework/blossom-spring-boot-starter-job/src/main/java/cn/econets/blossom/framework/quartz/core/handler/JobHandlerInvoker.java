package cn.econets.blossom.framework.quartz.core.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;
import cn.econets.blossom.framework.quartz.core.enums.JobDataKeyEnum;
import cn.econets.blossom.framework.quartz.core.service.JobLogFrameworkService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;

/**
 * Basics Job Caller，Responsible for calling {@link JobHandler#execute(String)} Execute the mission
 *
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Slf4j
public class JobHandlerInvoker extends QuartzJobBean {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private JobLogFrameworkService jobLogFrameworkService;

    @Override
    protected void executeInternal(JobExecutionContext executionContext) throws JobExecutionException {
        // First step，Get Job Data
        Long jobId = executionContext.getMergedJobDataMap().getLong(JobDataKeyEnum.JOB_ID.name());
        String jobHandlerName = executionContext.getMergedJobDataMap().getString(JobDataKeyEnum.JOB_HANDLER_NAME.name());
        String jobHandlerParam = executionContext.getMergedJobDataMap().getString(JobDataKeyEnum.JOB_HANDLER_PARAM.name());
        int refireCount  = executionContext.getRefireCount();
        int retryCount = (Integer) executionContext.getMergedJobDataMap().getOrDefault(JobDataKeyEnum.JOB_RETRY_COUNT.name(), 0);
        int retryInterval = (Integer) executionContext.getMergedJobDataMap().getOrDefault(JobDataKeyEnum.JOB_RETRY_INTERVAL.name(), 0);

        // Step 2，Execute the mission
        Long jobLogId = null;
        LocalDateTime startTime = LocalDateTime.now();
        String data = null;
        Throwable exception = null;
        try {
            // Record Job Log（Initial）
            jobLogId = jobLogFrameworkService.createJobLog(jobId, startTime, jobHandlerName, jobHandlerParam, refireCount + 1);
            // Execute the mission
            data = this.executeInternal(jobHandlerName, jobHandlerParam);
        } catch (Throwable ex) {
            exception = ex;
        }

        // Step 3，Record execution log
        this.updateJobLogResultAsync(jobLogId, startTime, data, exception, executionContext);

        // Step 4，Handle abnormal situations
        handleException(exception, refireCount, retryCount, retryInterval);
    }

    private String executeInternal(String jobHandlerName, String jobHandlerParam) throws Exception {
        // Get JobHandler Object
        JobHandler jobHandler = applicationContext.getBean(jobHandlerName, JobHandler.class);
        Assert.notNull(jobHandler, "JobHandler Will not be empty");
        // Execute the mission
        return jobHandler.execute(jobHandlerParam);
    }

    private void updateJobLogResultAsync(Long jobLogId, LocalDateTime startTime, String data, Throwable exception,
                                         JobExecutionContext executionContext) {
        LocalDateTime endTime = LocalDateTime.now();
        // Whether the processing is successful
        boolean success = exception == null;
        if (!success) {
            data = getRootCauseMessage(exception);
        }
        // Update log
        try {
            jobLogFrameworkService.updateJobLogResultAsync(jobLogId, endTime, (int) LocalDateTimeUtil.between(startTime, endTime).toMillis(), success, data);
        } catch (Exception ex) {
            log.error("[executeInternal][Job({}) logId({}) Failed to record execution log({}/{})]",
                    executionContext.getJobDetail().getKey(), jobLogId, success, data);
        }
    }

    private void handleException(Throwable exception,
                                 int refireCount, int retryCount, int retryInterval) throws JobExecutionException {
        // If there is an abnormality，Then retry
        if (exception == null) {
            return;
        }
        // Situation 1：If the retry limit is reached，Just throw an exception directly
        if (refireCount >= retryCount) {
            throw new JobExecutionException(exception);
        }

        // Situation 2：If the retry limit is not reached，Then sleep A certain interval time，Then try again
        // Used here sleep To achieve，I hope it's simple to implement。Because，At the same time，There won't be a lot of failures Job。
        if (retryInterval > 0) {
            ThreadUtil.sleep(retryInterval);
        }
        // The second parameter，refireImmediately = true，Retry immediately
        throw new JobExecutionException(exception, true);
    }

}
