package cn.econets.blossom.module.bpm.controller.admin.task;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.task.*;
import cn.econets.blossom.module.bpm.service.task.BpmTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Process Task Instance")
@RestController
@RequestMapping("/bpm/task")
@Validated
public class BpmTaskController {

    @Resource
    private BpmTaskService taskService;

    @GetMapping("todo-page")
    @Operation(summary = "Get Todo To-do tasks page")
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public CommonResult<PageResult<BpmTaskTodoPageItemRespVO>> getTodoTaskPage(@Valid BpmTaskTodoPageReqVO pageVO) {
        return success(taskService.getTodoTaskPage(getLoginUserId(), pageVO));
    }

    @GetMapping("done-page")
    @Operation(summary = "Get Done Completed tasks page")
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public CommonResult<PageResult<BpmTaskDonePageItemRespVO>> getDoneTaskPage(@Valid BpmTaskDonePageReqVO pageVO) {
        return success(taskService.getDoneTaskPage(getLoginUserId(), pageVO));
    }

    @GetMapping("/list-by-process-instance-id")
    @Operation(summary = "Get the task list of the specified process instance", description = "Including completed、Unfinished")
    @Parameter(name = "processInstanceId", description = "Process instance number", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public CommonResult<List<BpmTaskRespVO>> getTaskListByProcessInstanceId(
            @RequestParam("processInstanceId") String processInstanceId) {
        return success(taskService.getTaskListByProcessInstanceId(processInstanceId));
    }

    @PutMapping("/approve")
    @Operation(summary = "Pass the mission")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> approveTask(@Valid @RequestBody BpmTaskApproveReqVO reqVO) {
        taskService.approveTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/reject")
    @Operation(summary = "Failed the task")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> rejectTask(@Valid @RequestBody BpmTaskRejectReqVO reqVO) {
        taskService.rejectTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-assignee")
    @Operation(summary = "Update the person in charge of the task", description = "Used for【Process details】of【Transfer】Button")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> updateTaskAssignee(@Valid @RequestBody BpmTaskUpdateAssigneeReqVO reqVO) {
        taskService.updateTaskAssignee(getLoginUserId(), reqVO);
        return success(true);
    }

    @GetMapping("/return-list")
    @Operation(summary = "Get all nodes that can be rolled back", description = "Used for【Process details】of【Return】Button")
    @Parameter(name = "taskId", description = "Current TaskID", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<List<BpmTaskSimpleRespVO>> getReturnList(@RequestParam("taskId") String taskId) {
        return success(taskService.getReturnTaskList(taskId));
    }

    @PutMapping("/return")
    @Operation(summary = "Rewind task", description = "Used for【Process details】of【Return】Button")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> returnTask(@Valid @RequestBody BpmTaskReturnReqVO reqVO) {
        taskService.returnTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/delegate")
    @Operation(summary = "Assignment Tasks", description = "Used for【Process details】of【Delegation】Button。And forward【Add signature】A bit like，The only difference is【Commission】No separate task was created")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> delegateTask(@Valid @RequestBody BpmTaskDelegateReqVO reqVO) {
        taskService.delegateTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/create-sign")
    @Operation(summary = "Add signature", description = "before Add signature before，after Add signature later")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> createSignTask(@Valid @RequestBody BpmTaskAddSignReqVO reqVO) {
        taskService.createSignTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @DeleteMapping("/delete-sign")
    @Operation(summary = "Reduce signature")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> deleteSignTask(@Valid @RequestBody BpmTaskSubSignReqVO reqVO) {
        taskService.deleteSignTask(getLoginUserId(), reqVO);
        return success(true);
    }

    @GetMapping("children-list")
    @Operation(summary = "Get the tasks that can be reduced")
    @Parameter(name = "parentId", description = "Parent Task ID", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<List<BpmTaskSubSignRespVO>> getChildrenTaskList(@RequestParam("parentId") String parentId) {
        return success(taskService.getChildrenTaskList(parentId));
    }

}
