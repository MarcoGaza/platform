package cn.econets.blossom.framework.quartz.core.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Job Log Framework Service Interface
 *
 */
public interface JobLogFrameworkService {

    /**
     * Create Job Log
     *
     * @param jobId           Task number
     * @param beginTime       Start time
     * @param jobHandlerName  Job Processor name
     * @param jobHandlerParam Job Processor parameters
     * @param executeIndex    Number of executions
     * @return Job Log number
     */
    Long createJobLog(@NotNull(message = "Task number cannot be empty") Long jobId,
                      @NotNull(message = "Start time") LocalDateTime beginTime,
                      @NotEmpty(message = "Job The processor name cannot be empty") String jobHandlerName,
                      String jobHandlerParam,
                      @NotNull(message = "The execution number cannot be empty") Integer executeIndex);

    /**
     * Update Job Log execution results
     *
     * @param logId    Log number
     * @param endTime  End time。Because it is asynchronous，Avoid recording time and not being allowed to go
     * @param duration Running time，Unit：Milliseconds
     * @param success  Whether successful
     * @param result   Successful data
     */
    void updateJobLogResultAsync(@NotNull(message = "The log number cannot be empty") Long logId,
                                 @NotNull(message = "End time cannot be empty") LocalDateTime endTime,
                                 @NotNull(message = "Run time cannot be empty") Integer duration,
                                 boolean success, String result);
}
