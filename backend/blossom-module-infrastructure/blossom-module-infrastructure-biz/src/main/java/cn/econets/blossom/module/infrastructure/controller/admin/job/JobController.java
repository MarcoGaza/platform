package cn.econets.blossom.module.infrastructure.controller.admin.job;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.quartz.core.util.CronUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job.JobSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.job.JobDO;
import cn.econets.blossom.module.infrastructure.service.job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Scheduled tasks")
@RestController
@RequestMapping("/infra/job")
@Validated
public class JobController {

    @Resource
    private JobService jobService;

    @PostMapping("/create")
    @Operation(summary = "Create a scheduled task")
    @PreAuthorize("@ss.hasPermission('infra:job:create')")
    public CommonResult<Long> createJob(@Valid @RequestBody JobSaveReqVO createReqVO)
            throws SchedulerException {
        return success(jobService.createJob(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update scheduled tasks")
    @PreAuthorize("@ss.hasPermission('infra:job:update')")
    public CommonResult<Boolean> updateJob(@Valid @RequestBody JobSaveReqVO updateReqVO)
            throws SchedulerException {
        jobService.updateJob(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "Update the status of scheduled tasks")
    @Parameters({
            @Parameter(name = "id", description = "Number", required = true, example = "1024"),
            @Parameter(name = "status", description = "Status", required = true, example = "1"),
    })
    @PreAuthorize("@ss.hasPermission('infra:job:update')")
    public CommonResult<Boolean> updateJobStatus(@RequestParam(value = "id") Long id, @RequestParam("status") Integer status)
            throws SchedulerException {
        jobService.updateJobStatus(id, status);
        return success(true);
    }

	@DeleteMapping("/delete")
    @Operation(summary = "Delete scheduled tasks")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
	@PreAuthorize("@ss.hasPermission('infra:job:delete')")
    public CommonResult<Boolean> deleteJob(@RequestParam("id") Long id)
            throws SchedulerException {
        jobService.deleteJob(id);
        return success(true);
    }

    @PutMapping("/trigger")
    @Operation(summary = "Trigger scheduled tasks")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:job:trigger')")
    public CommonResult<Boolean> triggerJob(@RequestParam("id") Long id) throws SchedulerException {
        jobService.triggerJob(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get scheduled tasks")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<JobRespVO> getJob(@RequestParam("id") Long id) {
        JobDO job = jobService.getJob(id);
        return success(BeanUtils.toBean(job, JobRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get scheduled task paging")
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<PageResult<JobRespVO>> getJobPage(@Valid JobPageReqVO pageVO) {
        PageResult<JobDO> pageResult = jobService.getJobPage(pageVO);
        return success(BeanUtils.toBean(pageResult, JobRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export scheduled tasks Excel")
    @PreAuthorize("@ss.hasPermission('infra:job:export')")
    @OperateLog(type = EXPORT)
    public void exportJobExcel(@Valid JobPageReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<JobDO> list = jobService.getJobPage(exportReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Scheduled tasks.xls", "Data", JobRespVO.class,
                BeanUtils.toBean(list, JobRespVO.class));
    }

    @GetMapping("/get_next_times")
    @Operation(summary = "Get the next scheduled task n Execution time")
    @Parameters({
            @Parameter(name = "id", description = "Number", required = true, example = "1024"),
            @Parameter(name = "count", description = "Quantity", example = "5")
    })
    @PreAuthorize("@ss.hasPermission('infra:job:query')")
    public CommonResult<List<LocalDateTime>> getJobNextTimes(
            @RequestParam("id") Long id,
            @RequestParam(value = "count", required = false, defaultValue = "5") Integer count) {
        JobDO job = jobService.getJob(id);
        if (job == null) {
            return success(Collections.emptyList());
        }
        return success(CronUtils.getNextTimes(job.getCronExpression(), count));
    }

}
