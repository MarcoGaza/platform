package cn.econets.blossom.module.infrastructure.service.job;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.quartz.core.scheduler.SchedulerManager;
import cn.econets.blossom.framework.quartz.core.util.CronUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.job.JobDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.job.JobMapper;
import cn.econets.blossom.module.infrastructure.enums.job.JobStatusEnum;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.containsAny;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.*;

/**
 * Scheduled tasks Service Implementation class
 *
 */
@Service
@Validated
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private SchedulerManager schedulerManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createJob(JobSaveReqVO createReqVO) throws SchedulerException {
        validateCronExpression(createReqVO.getCronExpression());
        // Verify uniqueness
        if (jobMapper.selectByHandlerName(createReqVO.getHandlerName()) != null) {
            throw exception(JOB_HANDLER_EXISTS);
        }
        // Insert
        JobDO job = BeanUtils.toBean(createReqVO, JobDO.class);
        job.setStatus(JobStatusEnum.INIT.getStatus());
        fillJobMonitorTimeoutEmpty(job);
        jobMapper.insert(job);

        // Add Job To Quartz Medium
        schedulerManager.addJob(job.getId(), job.getHandlerName(), job.getHandlerParam(), job.getCronExpression(),
                createReqVO.getRetryCount(), createReqVO.getRetryInterval());
        // Update
        JobDO updateObj = JobDO.builder().id(job.getId()).status(JobStatusEnum.NORMAL.getStatus()).build();
        jobMapper.updateById(updateObj);

        // Return
        return job.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(JobSaveReqVO updateReqVO) throws SchedulerException {
        validateCronExpression(updateReqVO.getCronExpression());
        // Check existence
        JobDO job = validateJobExists(updateReqVO.getId());
        // Only in the enabled state，Can be modified.The reason is，If the pause state is reached，Modify Quartz Job Time，will cause the task to start executing again
        if (!job.getStatus().equals(JobStatusEnum.NORMAL.getStatus())) {
            throw exception(JOB_UPDATE_ONLY_NORMAL_STATUS);
        }
        // Update
        JobDO updateObj = BeanUtils.toBean(updateReqVO, JobDO.class);
        fillJobMonitorTimeoutEmpty(updateObj);
        jobMapper.updateById(updateObj);

        // Update Job To Quartz Medium
        schedulerManager.updateJob(job.getHandlerName(), updateReqVO.getHandlerParam(), updateReqVO.getCronExpression(),
                updateReqVO.getRetryCount(), updateReqVO.getRetryInterval());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJobStatus(Long id, Integer status) throws SchedulerException {
        // Verification status
        if (!containsAny(status, JobStatusEnum.NORMAL.getStatus(), JobStatusEnum.STOP.getStatus())) {
            throw exception(JOB_CHANGE_STATUS_INVALID);
        }
        // Check existence
        JobDO job = validateJobExists(id);
        // Check whether it is the current state
        if (job.getStatus().equals(status)) {
            throw exception(JOB_CHANGE_STATUS_EQUALS);
        }
        // Update Job Status
        JobDO updateObj = JobDO.builder().id(id).status(status).build();
        jobMapper.updateById(updateObj);

        // Update status Job To Quartz Medium
        if (JobStatusEnum.NORMAL.getStatus().equals(status)) { // Open
            schedulerManager.resumeJob(job.getHandlerName());
        } else { // Pause
            schedulerManager.pauseJob(job.getHandlerName());
        }
    }

    @Override
    public void triggerJob(Long id) throws SchedulerException {
        // Check existence
        JobDO job = validateJobExists(id);

        // Trigger Quartz In Job
        schedulerManager.triggerJob(job.getId(), job.getHandlerName(), job.getHandlerParam());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob(Long id) throws SchedulerException {
        // Check existence
        JobDO job = validateJobExists(id);
        // Update
        jobMapper.deleteById(id);

        // Delete Job To Quartz Medium
        schedulerManager.deleteJob(job.getHandlerName());
    }

    private JobDO validateJobExists(Long id) {
        JobDO job = jobMapper.selectById(id);
        if (job == null) {
            throw exception(JOB_NOT_EXISTS);
        }
        return job;
    }

    private void validateCronExpression(String cronExpression) {
        if (!CronUtils.isValid(cronExpression)) {
            throw exception(JOB_CRON_EXPRESSION_VALID);
        }
    }

    @Override
    public JobDO getJob(Long id) {
        return jobMapper.selectById(id);
    }

    @Override
    public PageResult<JobDO> getJobPage(JobPageReqVO pageReqVO) {
		return jobMapper.selectPage(pageReqVO);
    }

    private static void fillJobMonitorTimeoutEmpty(JobDO job) {
        if (job.getMonitorTimeout() == null) {
            job.setMonitorTimeout(0);
        }
    }

}
