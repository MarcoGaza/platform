package cn.econets.blossom.module.infrastructure.service.job;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.quartz.core.service.JobLogFrameworkService;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.log.JobLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.job.JobLogDO;

/**
 * Job Log Service Interface
 *
 */
public interface JobLogService extends JobLogFrameworkService {

    /**
     * Get scheduled tasks
     *
     * @param id Number
     * @return Scheduled tasks
     */
    JobLogDO getJobLog(Long id);

    /**
     * Get scheduled task paging
     *
     * @param pageReqVO Paged query
     * @return Scheduled task paging
     */
    PageResult<JobLogDO> getJobLogPage(JobLogPageReqVO pageReqVO);

    /**
     * Clean up exceedDay Task log from days ago
     *
     * @param exceedDay After how many days, clean up will be done
     * @param deleteLimit Number of intervals to clean
     */
    Integer cleanJobLog(Integer exceedDay, Integer deleteLimit);

}
