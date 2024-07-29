package cn.econets.blossom.framework.quartz.core.scheduler;

import cn.econets.blossom.framework.quartz.core.enums.JobDataKeyEnum;
import cn.econets.blossom.framework.quartz.core.handler.JobHandlerInvoker;
import org.quartz.*;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception0;

/**
 * {@link org.quartz.Scheduler} Manager，Responsible for creating tasks
 *
 * Considering the simplicity of implementation，We use jobHandlerName As a unique identifier，That is：
 * 1. Job of {@link JobDetail#getKey()}
 * 2. Trigger of {@link Trigger#getKey()}
 *
 * In addition，jobHandlerName Corresponding to Spring Bean Name，Direct call
 *
 */
public class SchedulerManager {

    private final Scheduler scheduler;

    public SchedulerManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Add Job To Quartz Medium
     *
     * @param jobId Task number
     * @param jobHandlerName Task processor name
     * @param jobHandlerParam Task processor parameters
     * @param cronExpression CRON Expression
     * @param retryCount Number of retries
     * @param retryInterval Retry interval
     * @throws SchedulerException Add exception
     */
    public void addJob(Long jobId, String jobHandlerName, String jobHandlerParam, String cronExpression,
                       Integer retryCount, Integer retryInterval)
            throws SchedulerException {
        validateScheduler();
        // Create JobDetail Object
        JobDetail jobDetail = JobBuilder.newJob(JobHandlerInvoker.class)
                .usingJobData(JobDataKeyEnum.JOB_ID.name(), jobId)
                .usingJobData(JobDataKeyEnum.JOB_HANDLER_NAME.name(), jobHandlerName)
                .withIdentity(jobHandlerName).build();
        // Create Trigger Object
        Trigger trigger = this.buildTrigger(jobHandlerName, jobHandlerParam, cronExpression, retryCount, retryInterval);
        // Add new scheduling
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * Update Job To Quartz
     *
     * @param jobHandlerName Task processor name
     * @param jobHandlerParam Task processor parameters
     * @param cronExpression CRON Expression
     * @param retryCount Number of retries
     * @param retryInterval Retry interval
     * @throws SchedulerException Update abnormality
     */
    public void updateJob(String jobHandlerName, String jobHandlerParam, String cronExpression,
                          Integer retryCount, Integer retryInterval)
            throws SchedulerException {
        validateScheduler();
        // Create a new one Trigger Object
        Trigger newTrigger = this.buildTrigger(jobHandlerName, jobHandlerParam, cronExpression, retryCount, retryInterval);
        // Modify schedule
        scheduler.rescheduleJob(new TriggerKey(jobHandlerName), newTrigger);
    }

    /**
     * Delete Quartz In Job
     *
     * @param jobHandlerName Task processor name
     * @throws SchedulerException Delete exception
     */
    public void deleteJob(String jobHandlerName) throws SchedulerException {
        validateScheduler();
        scheduler.deleteJob(new JobKey(jobHandlerName));
    }

    /**
     * Pause Quartz In Job
     *
     * @param jobHandlerName Task processor name
     * @throws SchedulerException Suspended abnormally
     */
    public void pauseJob(String jobHandlerName) throws SchedulerException {
        validateScheduler();
        scheduler.pauseJob(new JobKey(jobHandlerName));
    }

    /**
     * Start Quartz In Job
     *
     * @param jobHandlerName Task processor name
     * @throws SchedulerException Startup exception
     */
    public void resumeJob(String jobHandlerName) throws SchedulerException {
        validateScheduler();
        scheduler.resumeJob(new JobKey(jobHandlerName));
        scheduler.resumeTrigger(new TriggerKey(jobHandlerName));
    }

    /**
     * Trigger once immediately Quartz In Job
     *
     * @param jobId Task number
     * @param jobHandlerName Task processor name
     * @param jobHandlerParam Task processor parameters
     * @throws SchedulerException Triggered exception
     */
    public void triggerJob(Long jobId, String jobHandlerName, String jobHandlerParam)
            throws SchedulerException {
        validateScheduler();
        // Trigger task
        JobDataMap data = new JobDataMap(); // No need to retry，So do not set retryCount Japanese retryInterval
        data.put(JobDataKeyEnum.JOB_ID.name(), jobId);
        data.put(JobDataKeyEnum.JOB_HANDLER_NAME.name(), jobHandlerName);
        data.put(JobDataKeyEnum.JOB_HANDLER_PARAM.name(), jobHandlerParam);
        scheduler.triggerJob(new JobKey(jobHandlerName), data);
    }

    private Trigger buildTrigger(String jobHandlerName, String jobHandlerParam, String cronExpression,
                                 Integer retryCount, Integer retryInterval) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobHandlerName)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(JobDataKeyEnum.JOB_HANDLER_PARAM.name(), jobHandlerParam)
                .usingJobData(JobDataKeyEnum.JOB_RETRY_COUNT.name(), retryCount)
                .usingJobData(JobDataKeyEnum.JOB_RETRY_INTERVAL.name(), retryInterval)
                .build();
    }

    private void validateScheduler() {
        if (scheduler == null) {
            throw exception0(NOT_IMPLEMENTED.getCode(),
                    "[Scheduled tasks - Disabled][Reference https://doc.econets.cn/job/ Open]");
        }
    }

}
