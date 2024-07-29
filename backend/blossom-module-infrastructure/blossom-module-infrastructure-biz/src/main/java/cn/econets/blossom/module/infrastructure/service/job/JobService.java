package cn.econets.blossom.module.infrastructure.service.job;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.job.JobDO;
import org.quartz.SchedulerException;

import javax.validation.Valid;

/**
 * Scheduled tasks Service Interface
 *
 */
public interface JobService {

    /**
     * Create a scheduled task
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createJob(@Valid JobSaveReqVO createReqVO) throws SchedulerException;

    /**
     * Update scheduled tasks
     *
     * @param updateReqVO Update information
     */
    void updateJob(@Valid JobSaveReqVO updateReqVO) throws SchedulerException;

    /**
     * Update the status of scheduled tasks
     *
     * @param id Task number
     * @param status Status
     */
    void updateJobStatus(Long id, Integer status) throws SchedulerException;

    /**
     * Trigger scheduled tasks
     *
     * @param id Task number
     */
    void triggerJob(Long id) throws SchedulerException;

    /**
     * Delete scheduled tasks
     *
     * @param id Number
     */
    void deleteJob(Long id) throws SchedulerException;

    /**
     * Get scheduled tasks
     *
     * @param id Number
     * @return Scheduled tasks
     */
    JobDO getJob(Long id);

    /**
     * Get scheduled task paging
     *
     * @param pageReqVO Paged query
     * @return Scheduled task paging
     */
    PageResult<JobDO> getJobPage(JobPageReqVO pageReqVO);

}
