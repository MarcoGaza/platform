package cn.econets.blossom.module.bpm.service.task;

import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.framework.flowable.core.util.BpmnModelUtils;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.task.*;
import cn.econets.blossom.module.bpm.convert.task.BpmTaskConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.task.BpmTaskExtDO;
import cn.econets.blossom.module.bpm.dal.mysql.task.BpmTaskExtMapper;
import cn.econets.blossom.module.bpm.enums.task.BpmCommentTypeEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceDeleteReasonEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmTaskAddSignTypeEnum;
import cn.econets.blossom.module.bpm.service.definition.BpmModelService;
import cn.econets.blossom.module.bpm.service.message.BpmMessageService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * Process Task Instance Service Implementation class
 *
 */
@Slf4j
@Service
public class BpmTaskServiceImpl implements BpmTaskService {

    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private BpmProcessInstanceService processInstanceService;
    @Resource
    private BpmModelService bpmModelService;
    @Resource
    private BpmMessageService messageService;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;

    @Resource
    private BpmTaskExtMapper taskExtMapper;

    @Resource
    private ManagementService managementService;

    @Override
    public PageResult<BpmTaskTodoPageItemRespVO> getTodoTaskPage(Long userId, BpmTaskTodoPageReqVO pageVO) {
        // Query pending tasks
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(String.valueOf(userId)) // Assign to myself
                .orderByTaskCreateTime().desc(); // Creation time in reverse order
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 0) != null) {
            taskQuery.taskCreatedAfter(DateUtils.of(pageVO.getCreateTime()[0]));
        }
        if (ArrayUtil.get(pageVO.getCreateTime(), 1) != null) {
            taskQuery.taskCreatedBefore(DateUtils.of(pageVO.getCreateTime()[1]));
        }
        // Execute query
        List<Task> tasks = taskQuery.listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return PageResult.empty(taskQuery.count());
        }

        // Get ProcessInstance Map
        Map<String, ProcessInstance> processInstanceMap =
                processInstanceService.getProcessInstanceMap(convertSet(tasks, Task::getProcessInstanceId));
        // Get User Map
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
        // Joining results
        return new PageResult<>(BpmTaskConvert.INSTANCE.convertList1(tasks, processInstanceMap, userMap),
                taskQuery.count());
    }

    @Override
    public PageResult<BpmTaskDonePageItemRespVO> getDoneTaskPage(Long userId, BpmTaskDonePageReqVO pageVO) {
        // Query completed tasks
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().finished() // Completed
                .taskAssignee(String.valueOf(userId)) // Assign to myself
                .orderByHistoricTaskInstanceEndTime().desc(); // Approval time in reverse order
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (pageVO.getBeginCreateTime() != null) {
            taskQuery.taskCreatedAfter(DateUtils.of(pageVO.getBeginCreateTime()));
        }
        if (pageVO.getEndCreateTime() != null) {
            taskQuery.taskCreatedBefore(DateUtils.of(pageVO.getEndCreateTime()));
        }
        // Execute query
        List<HistoricTaskInstance> tasks = taskQuery.listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return PageResult.empty(taskQuery.count());
        }

        // Get TaskExtDO Map
        List<BpmTaskExtDO> bpmTaskExtDOs =
                taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExtDO::getTaskId);
        // Get ProcessInstance Map
        Map<String, HistoricProcessInstance> historicProcessInstanceMap =
                processInstanceService.getHistoricProcessInstanceMap(
                        convertSet(tasks, HistoricTaskInstance::getProcessInstanceId));
        // Get User Map
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(historicProcessInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
        // Joining results
        return new PageResult<>(
                BpmTaskConvert.INSTANCE.convertList2(tasks, bpmTaskExtDOMap, historicProcessInstanceMap, userMap),
                taskQuery.count());
    }

    @Override
    public List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds) {
        if (CollUtil.isEmpty(processInstanceIds)) {
            return Collections.emptyList();
        }
        return taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
    }

    @Override
    public List<BpmTaskRespVO> getTaskListByProcessInstanceId(String processInstanceId) {
        // Get the task list
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().desc() // Creation time in reverse order
                .list();
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }

        // Obtain TaskExtDO Map
        List<BpmTaskExtDO> bpmTaskExtDOs = taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExtDO::getTaskId);
        // Get ProcessInstance Map
        HistoricProcessInstance processInstance = processInstanceService.getHistoricProcessInstance(processInstanceId);
        // Get User Map
        Set<Long> userIds = convertSet(tasks, task -> NumberUtils.parseLong(task.getAssignee()));
        userIds.add(NumberUtils.parseLong(processInstance.getStartUserId()));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);
        // Get Dept Map
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));

        // Splicing data
        List<BpmTaskRespVO> result = BpmTaskConvert.INSTANCE.convertList3(tasks, bpmTaskExtDOMap, processInstance, userMap, deptMap);
        return BpmTaskConvert.INSTANCE.convertChildrenList(result);
    }

    @Override
    public List<BpmTaskExtDO> getTaskListByTaskIdList(List<String> taskIdList) {
        return taskExtMapper.selectListByTaskIds(taskIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveTask(Long userId, @Valid BpmTaskApproveReqVO reqVO) {
        // 1.1 Verify that the task exists
        Task task = validateTask(userId, reqVO.getId());
        // 1.2 Verify that the process instance exists
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }

        // Situation 1：Assigned tasks，Not called complete Go and complete the task
        if (DelegationState.PENDING.equals(task.getDelegationState())) {
            approveDelegateTask(reqVO, task);
            return;
        }

        // Situation 2：Tasks to be signed later
        if (BpmTaskAddSignTypeEnum.AFTER.getType().equals(task.getScopeType())) {
            // Post-signature processing
            approveAfterSignTask(task, reqVO);
            return;
        }

        // Case 3：Tasks approved by myself，Call complete Go and complete the task
        // Complete the task，Approved
        taskService.complete(task.getId(), instance.getProcessVariables());
        // Update the task expansion table to pass
        taskExtMapper.updateByTaskId(
                new BpmTaskExtDO().setTaskId(task.getId()).setResult(BpmProcessInstanceResultEnum.APPROVE.getResult())
                        .setReason(reqVO.getReason()));
        // Processing signature tasks
        handleParentTask(task);
    }


    /**
     * Approved and exists“Add signature later”Task。
     * <p>
     * Attention：This task cannot be completed immediately，Need an intermediate state（SIGN_AFTER），And activate all remaining subtasks（PROCESS）Can be approved for processing
     *
     * @param task  Current Task
     * @param reqVO Front-end request parameters
     */
    private void approveAfterSignTask(Task task, BpmTaskApproveReqVO reqVO) {
        // 1. There is a back signature，The task status is temporarily set to ADD_SIGN_AFTER Status
        taskExtMapper.updateByTaskId(
                new BpmTaskExtDO().setTaskId(task.getId()).setResult(BpmProcessInstanceResultEnum.SIGN_AFTER.getResult())
                        .setReason(reqVO.getReason()).setEndTime(LocalDateTime.now()));

        // 2. Activate subtask
        List<String> childrenTaskIdList = getChildrenTaskIdList(task.getId());
        for (String childrenTaskId : childrenTaskIdList) {
            taskService.resolveTask(childrenTaskId);
        }
        // 2.1 Update the subtasks in the task extension table to be in progress
        taskExtMapper.updateBatchByTaskIdList(childrenTaskIdList,
                new BpmTaskExtDO().setResult(BpmProcessInstanceResultEnum.PROCESS.getResult()));
    }

    /**
     * Process the parent task of the current task，Main processing“Add signature”Situation
     *
     * @param task Current Task
     */
    private void handleParentTask(Task task) {
        String parentTaskId = task.getParentTaskId();
        if (StrUtil.isBlank(parentTaskId)) {
            return;
        }
        // 1. Judge whether the parent task of the current task has subtasks
        Long childrenTaskCount = getChildrenTaskCount(parentTaskId);
        if (childrenTaskCount > 0) {
            return;
        }
        // 2. Get parent task
        Task parentTask = validateTaskExist(parentTaskId);

        // 3. Handling additional signatures
        String scopeType = parentTask.getScopeType();
        if(!validateSignType(scopeType)){
            return;
        }
        // 3.1 Situation 1：Processing forward signature
        if (BpmTaskAddSignTypeEnum.BEFORE.getType().equals(scopeType)) {
            // 3.1.1 If it is a task to add a signature forward，Then call resolveTask Assign parent task，Will owner Reassign to the parent task assignee，So that it can be approved
            taskService.resolveTask(parentTaskId);
            // 3.1.2 Update task expansion table is in progress
            taskExtMapper.updateByTaskId(
                    new BpmTaskExtDO().setTaskId(parentTask.getId()).setResult(BpmProcessInstanceResultEnum.PROCESS.getResult()));
        } else if (BpmTaskAddSignTypeEnum.AFTER.getType().equals(scopeType)) {
            // 3.2 Case 2：Processing of adding signatures backwards
            handleParentTaskForAfterSign(parentTask);
        }

        // 4. Subtask has been processed，Clear scopeType Field，Modify parentTask Information，It is convenient to continue to add signatures forward and backward in the future
        // The reason for querying again is to avoid errors：Task was updated by another transaction concurrently
        // Because the previous processing may cause parentTask rev Field modified，Need to re-obtain the latest
        parentTask = getTask(parentTaskId);
        if (parentTask == null) {
            // If it is empty,：Already passed handleAfterSign The method completed the task，So ru_task No data can be found in the table
            return;
        }
        clearTaskScopeTypeAndSave(parentTask);
    }


    /**
     * Add signature task after processing
     *
     * @param parentTask The parent task of the current approval task
     */
    // TODO ：This logic，How can it feel like it is parentTask of parent，Call again handleParentTask Method；We can chat on WeChat；
    private void handleParentTaskForAfterSign(Task parentTask) {
        String parentTaskId = parentTask.getId();
        // 1. Update parentTask The task expansion table is passed，and call complete Complete yourself
        BpmTaskExtDO currentTaskExt = taskExtMapper.selectByTaskId(parentTask.getId());
        BpmTaskExtDO currentTaskExtUpdateObj = new BpmTaskExtDO().setTaskId(parentTask.getId())
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult());
        if (currentTaskExt.getEndTime() == null) {
            // 1.1 This judgment is made because,The end time has not been set before，Just go and set it up
            currentTaskExtUpdateObj.setEndTime(LocalDateTime.now());
        }
        taskExtMapper.updateByTaskId(currentTaskExtUpdateObj);
        // 1.2 Complete yourself（Because it has no subtasks，So it can also be completed）
        taskService.complete(parentTaskId);

        // 2. If there is a parent，Recursively check whether all upper-level tasks have been completed
        if (StrUtil.isEmpty(parentTask.getParentTaskId())) {
            return;
        }
        // 2.1 Judge whether the task of the entire link is completed
        // For example, from A An additional task has been signed B Mission，B Another task has been signed C Mission，C The task has been signed D Mission
        // At this time，D Mission completed，Go up all the way to find the ancestor mission ACall complete Method completed A Mission
        boolean allChildrenTaskFinish = true;
        while (StrUtil.isNotBlank(parentTask.getParentTaskId())) {
            parentTask = validateTaskExist(parentTask.getParentTaskId());
            BpmTaskExtDO parentTaskExt = taskExtMapper.selectByTaskId(parentTask.getId());
            if (parentTaskExt == null) {
                break;
            }
            boolean currentTaskFinish = BpmProcessInstanceResultEnum.isEndResult(parentTaskExt.getResult());
            // 2.2 If allChildrenTaskFinish has been assigned to false，will no longer be assigned to true，Because the entire link is not completed
            if (allChildrenTaskFinish) {
                allChildrenTaskFinish = currentTaskFinish;
            }
            // 2.3 Task has been completed, do not process
            if (currentTaskFinish) {
                continue;
            }

            // 3 Handle tasks in non-completed state
            // 3.1 Judge whether the parent task of the current task has subtasks
            Long childrenTaskCount = getChildrenTaskCount(parentTaskExt.getTaskId());
            if (childrenTaskCount > 0) {
                continue;
            }
            // 3.2 No subtasks，Judge whether the current task status is ADD_SIGN_BEFORE Wait for the previous signing task to be completed
            if (BpmProcessInstanceResultEnum.SIGN_BEFORE.getResult().equals(parentTaskExt.getResult())) {
                // 3.3 The task status needs to be changed to processing
                taskService.resolveTask(parentTaskExt.getTaskId());
                parentTaskExt.setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
                taskExtMapper.updateByTaskId(parentTaskExt);
            }
            // 3.4 Clear scopeType Field，This method is used when the task has no subtasks，Convenient tasks can be signed in different ways again
            parentTask = validateTaskExist(parentTaskExt.getTaskId());
            clearTaskScopeTypeAndSave(parentTask);
        }

        // 4. Complete the last top ancestor mission
        if (allChildrenTaskFinish) {
            taskService.complete(parentTask.getId());
        }
    }

    /**
     * Clear scopeType Field，This method is used when the task has no subtasks，Convenient tasks can be signed in different ways again
     *
     * @param task Tasks that need to be cleared
     */
    private void clearTaskScopeTypeAndSave(Task task) {
        TaskEntityImpl taskImpl = (TaskEntityImpl) task;
        taskImpl.setScopeType(null);
        taskService.saveTask(task);
    }

    /**
     * Get the number of subtasks
     *
     * @param parentTaskId Parent Task ID
     * @return Number of remaining subtasks
     */
    private Long getChildrenTaskCount(String parentTaskId) {
        String tableName = managementService.getTableName(TaskEntity.class);
        String sql = "SELECT COUNT(1) from " + tableName + " WHERE PARENT_TASK_ID_=#{parentTaskId}";
        return taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).count();
    }

    /**
     * Approve delegated tasks
     *
     * @param reqVO Front-end request parameters，Include current taskID，Approval opinions, etc.
     * @param task  Currently approved tasks
     */
    private void approveDelegateTask(BpmTaskApproveReqVO reqVO, Task task) {
        // 1. Add approval comments
        AdminUserRespDTO currentUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        AdminUserRespDTO sourceApproveUser = adminUserApi.getUser(NumberUtils.parseLong(task.getOwner()));
        Assert.notNull(sourceApproveUser, "The original approver cannot be found for the delegated task，Need to check data");
        String comment = StrUtil.format("[{}]Complete the assigned task，The mission is back[{}]In hand，Approval opinion is:{}", currentUser.getNickname(),
                sourceApproveUser.getNickname(), reqVO.getReason());
        taskService.addComment(reqVO.getId(), task.getProcessInstanceId(),
                BpmCommentTypeEnum.DELEGATE.getType().toString(), comment);

        // 2.1 Call resolveTask Complete the task。
        // Low-level call TaskHelper.changeTaskAssignee(task, task.getOwner())：Will owner Set to assignee
        taskService.resolveTask(task.getId());
        // 2.2 Update task expansion table to【Processing】
        taskExtMapper.updateByTaskId(
                new BpmTaskExtDO().setTaskId(task.getId()).setResult(BpmProcessInstanceResultEnum.PROCESS.getResult())
                        .setReason(reqVO.getReason()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectTask(Long userId, @Valid BpmTaskRejectReqVO reqVO) {
        Task task = validateTask(userId, reqVO.getId());
        // Verify that the process instance exists
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }

        // Update process instance to fail
        processInstanceService.updateProcessInstanceExtReject(instance.getProcessInstanceId(), reqVO.getReason());

        // Update the task expansion table to fail
        taskExtMapper.updateByTaskId(
                new BpmTaskExtDO().setTaskId(task.getId()).setResult(BpmProcessInstanceResultEnum.REJECT.getResult())
                        .setEndTime(LocalDateTime.now()).setReason(reqVO.getReason()));
    }

    @Override
    public void updateTaskAssignee(Long userId, BpmTaskUpdateAssigneeReqVO reqVO) {
        // Verify that the task exists
        Task task = validateTask(userId, reqVO.getId());
        // Update person in charge
        updateTaskAssignee(task.getId(), reqVO.getAssigneeUserId());
    }

    @Override
    public void updateTaskAssignee(String id, Long userId) {
        taskService.setAssignee(id, String.valueOf(userId));
    }

    /**
     * Check if the task exists， And whether it is a task assigned to yourself
     *
     * @param userId User id
     * @param taskId task id
     */
    private Task validateTask(Long userId, String taskId) {
        Task task = validateTaskExist(taskId);
        if (!Objects.equals(userId, NumberUtils.parseLong(task.getAssignee()))) {
            throw exception(TASK_OPERATE_FAIL_ASSIGN_NOT_SELF);
        }
        return task;
    }

    @Override
    public void createTaskExt(Task task) {
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert2TaskExt(task)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        // Tasks generated by adding signatures later，The status cannot be in progress，Need to wait for the previous parent task to complete
        if (BpmTaskAddSignTypeEnum.AFTER_CHILDREN_TASK.getType().equals(task.getScopeType())) {
            taskExtDO.setResult(BpmProcessInstanceResultEnum.WAIT_BEFORE_TASK.getResult());
        }
        taskExtMapper.insert(taskExtDO);
    }

    @Override
    public void updateTaskExtComplete(Task task) {
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert2TaskExt(task)
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult()) // It's not a big problem if you don't set it，Because Complete Generally approved，Already set
                .setEndTime(LocalDateTime.now());
        taskExtMapper.updateByTaskId(taskExtDO);
    }

    @Override
    public void updateTaskExtCancel(String taskId) {
        // Need to be done after transaction is committed，Only then conduct the query。Otherwise the reason why the history cannot be found
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                // Maybe just an activity，Not a task，So the query cannot be found
                HistoricTaskInstance task = getHistoricTask(taskId);
                if (task == null) {
                    return;
                }

                // If the task expansion table is already completed，Skip
                BpmTaskExtDO taskExt = taskExtMapper.selectByTaskId(taskId);
                if (taskExt == null) {
                    log.error("[updateTaskExtCancel][taskId({}) Cannot find the corresponding record，There may be problems]", taskId);
                    return;
                }
                // If it is already the final result，Skip
                if (BpmProcessInstanceResultEnum.isEndResult(taskExt.getResult())) {
                    log.error("[updateTaskExtCancel][taskId({}) In the result({})，No update required]", taskId, taskExt.getResult());
                    return;
                }

                // Update task
                taskExtMapper.updateById(new BpmTaskExtDO().setId(taskExt.getId()).setResult(BpmProcessInstanceResultEnum.CANCEL.getResult())
                        .setEndTime(LocalDateTime.now()).setReason(BpmProcessInstanceDeleteReasonEnum.translateReason(task.getDeleteReason())));
            }

        });
    }

    @Override
    public void updateTaskExtAssign(Task task) {
        BpmTaskExtDO taskExtDO =
                new BpmTaskExtDO().setAssigneeUserId(NumberUtils.parseLong(task.getAssignee())).setTaskId(task.getId());
        taskExtMapper.updateByTaskId(taskExtDO);
        // Send notification。When the transaction is committed，Batch execution operations，So direct query will not be able to find the result ProcessInstance，So this is achieved by monitoring the submission of transactions。
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                if (StrUtil.isNotEmpty(task.getAssignee())) {
                    ProcessInstance processInstance =
                            processInstanceService.getProcessInstance(task.getProcessInstanceId());
                    AdminUserRespDTO startUser = adminUserApi.getUser(Long.valueOf(processInstance.getStartUserId()));
                    messageService.sendMessageWhenTaskAssigned(
                            BpmTaskConvert.INSTANCE.convert(processInstance, startUser, task));
                }
            }
        });
    }

    private Task validateTaskExist(String id) {
        Task task = getTask(id);
        if (task == null) {
            throw exception(TASK_NOT_EXISTS);
        }
        return task;
    }

    private Task getTask(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }

    private HistoricTaskInstance getHistoricTask(String id) {
        return historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
    }

    @Override
    public List<BpmTaskSimpleRespVO> getReturnTaskList(String taskId) {
        // 1. Verify current task task Exists
        Task task = validateTaskExist(taskId);
        // Get process model information based on process definition
        BpmnModel bpmnModel = bpmModelService.getBpmnModelByDefinitionId(task.getProcessDefinitionId());
        FlowElement source = BpmnModelUtils.getFlowElementById(bpmnModel, task.getTaskDefinitionKey());
        if (source == null) {
            throw exception(TASK_NOT_EXISTS);
        }

        // 2.1 Query the predecessor task node of this task key Collection
        List<UserTask> previousUserList = BpmnModelUtils.getPreviousUserTaskList(source, null, null);
        if (CollUtil.isEmpty(previousUserList)) {
            return Collections.emptyList();
        }
        // 2.2 Filter：Only serially reachable nodes，You can roll back。Similar to non-serial、The subprocess cannot be returned
        previousUserList.removeIf(userTask -> !BpmnModelUtils.isSequentialReachable(source, userTask, null));
        return BpmTaskConvert.INSTANCE.convertList(previousUserList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnTask(Long userId, BpmTaskReturnReqVO reqVO) {
        // 1.1 Current Task task
        Task task = validateTask(userId, reqVO.getId());
        if (task.isSuspended()) {
            throw exception(TASK_IS_PENDING);
        }
        // 1.2 Verify the relationship between source and target nodes，and return the target element
        FlowElement targetElement = validateTargetTaskCanReturn(task.getTaskDefinitionKey(), reqVO.getTargetDefinitionKey(), task.getProcessDefinitionId());

        // 2. Call flowable Framework fallback logic
        returnTask0(task, targetElement, reqVO);

        // 3. Update task extension table
        taskExtMapper.updateByTaskId(new BpmTaskExtDO().setTaskId(task.getId())
                .setResult(BpmProcessInstanceResultEnum.BACK.getResult())
                .setEndTime(LocalDateTime.now()).setReason(reqVO.getReason()));
    }

    /**
     * When rolling back a process node，Check whether the target task node can be rolled back
     *
     * @param sourceKey           Current task node Key
     * @param targetKey           Target task node key
     * @param processDefinitionId Current process definition ID
     * @return Target task node element
     */
    private FlowElement validateTargetTaskCanReturn(String sourceKey, String targetKey, String processDefinitionId) {
        // 1.1 Get process model information
        BpmnModel bpmnModel = bpmModelService.getBpmnModelByDefinitionId(processDefinitionId);
        // 1.3 Get the current task node element
        FlowElement source = BpmnModelUtils.getFlowElementById(bpmnModel, sourceKey);
        // 1.3 Get the node element to jump to
        FlowElement target = BpmnModelUtils.getFlowElementById(bpmnModel, targetKey);
        if (target == null) {
            throw exception(TASK_TARGET_NODE_NOT_EXISTS);
        }

        // 2.2 Only serially reachable nodes，You can roll back。Similar to non-serial、Subprocess cannot be rolled back
        if (!BpmnModelUtils.isSequentialReachable(source, target, null)) {
            throw exception(TASK_RETURN_FAIL_SOURCE_TARGET_ERROR);
        }
        return target;
    }

    /**
     * Execute fallback logic
     *
     * @param currentTask   The current rolled back task
     * @param targetElement The target task to be rolled back
     * @param reqVO         Front-end parameter encapsulation
     */
    public void returnTask0(Task currentTask, FlowElement targetElement, BpmTaskReturnReqVO reqVO) {
        // 1. Get all tasks that need to be withdrawn taskDefinitionKey，For later use moveActivityIdsToSingleActivityId Retraction
        // 1.1 Get all normal task nodes Key
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(currentTask.getProcessInstanceId()).list();
        List<String> runTaskKeyList = convertList(taskList, Task::getTaskDefinitionKey);
        // 1.2 Passed targetElement Export connection，Calculated in runTaskKeyList Which ones are there key Needs to be withdrawn
        // Why not just use it runTaskKeyList ,？Because there may be multiple approval branches，For example：A -> B -> C Japanese D -> F，As long as C Withdraw to A，Need to be excluded F
        List<UserTask> returnUserTaskList = BpmnModelUtils.iteratorFindChildUserTasks(targetElement, runTaskKeyList, null, null);
        List<String> returnTaskKeyList = convertList(returnUserTaskList, UserTask::getId);

        // 2. To the current one to be rolled back task Array，Set rollback advice
        taskList.forEach(task -> {
            // Need to be excluded，Tasks that do not require rollback comments
            if (!returnTaskKeyList.contains(task.getTaskDefinitionKey())) {
                return;
            }
            taskService.addComment(task.getId(), currentTask.getProcessInstanceId(),
                    BpmCommentTypeEnum.BACK.getType().toString(), reqVO.getReason());
        });

        // 3. Execution Rejection
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(currentTask.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(returnTaskKeyList, // Current node list to jump to( 1 or more)
                        reqVO.getTargetDefinitionKey()) // targetKey The node to jump to(1)
                .changeState();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(Long userId, BpmTaskDelegateReqVO reqVO) {
        // 1.1 Verification task
        Task task = validateTaskCanDelegate(userId, reqVO);
        // 1.2 Verify that the target user exists
        AdminUserRespDTO delegateUser = adminUserApi.getUser(reqVO.getDelegateUserId());
        if (delegateUser == null) {
            throw exception(TASK_DELEGATE_FAIL_USER_NOT_EXISTS);
        }

        // 2. Add approval comments
        AdminUserRespDTO currentUser = adminUserApi.getUser(userId);
        String comment = StrUtil.format("[{}]Delegating tasks to[{}]，The reason for delegation is:{}", currentUser.getNickname(),
                delegateUser.getNickname(), reqVO.getReason());
        String taskId = reqVO.getId();
        taskService.addComment(taskId, task.getProcessInstanceId(),
                BpmCommentTypeEnum.DELEGATE.getType().toString(), comment);

        // 3.1 Set the task owner (owner) The person who handled the original task (assignee)
        taskService.setOwner(taskId, task.getAssignee());
        // 3.2 Execute delegation，Delegating tasks to receiveId
        taskService.delegateTask(taskId, reqVO.getDelegateUserId().toString());
        // 3.3 Update the task expansion table to【Delegation】
        taskExtMapper.updateByTaskId(
                new BpmTaskExtDO().setTaskId(task.getId()).setResult(BpmProcessInstanceResultEnum.DELEGATE.getResult())
                        .setReason(reqVO.getReason()));
    }

    /**
     * Check task delegation parameters
     *
     * @param userId User Number
     * @param reqVO  Task number，RecipientID
     * @return Current task information
     */
    private Task validateTaskCanDelegate(Long userId, BpmTaskDelegateReqVO reqVO) {
        // Verification task
        Task task = validateTask(userId, reqVO.getId());
        // Verify that the current approver and the delegate are not the same person
        if (task.getAssignee().equals(reqVO.getDelegateUserId().toString())) {
            throw exception(TASK_DELEGATE_FAIL_USER_REPEAT);
        }
        return task;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSignTask(Long userId, BpmTaskAddSignReqVO reqVO) {
        // 1. Get and verify tasks
        TaskEntityImpl taskEntity = validateAddSign(userId, reqVO);
        List<AdminUserRespDTO> userList = adminUserApi.getUserList(reqVO.getUserIdList());
        if (CollUtil.isEmpty(userList)) {
            throw exception(TASK_ADD_SIGN_USER_NOT_EXIST);
        }

        // 2. Process the current task
        // 2.1 Turn on the counting function，Mainly used to make the table ACT_RU_TASK In SUB_TASK_COUNT_ The field records the total number of subtasks，May be useful later
        taskEntity.setCountEnabled(true);
        if (reqVO.getType().equals(BpmTaskAddSignTypeEnum.BEFORE.getType())) {
            // 2.2 Add signature forward，Settings owner，Leave blank assign。Wait until all subtasks are completed，Call again resolveTask Re-set owner Set to assign
            // The reason is：Cannot be approved together with the subtasks added forward，You need to wait until all previous subtasks are completed before you can approve
            taskEntity.setOwner(taskEntity.getAssignee());
            taskEntity.setAssignee(null);
            // 2.3 Update extension table status
            taskExtMapper.updateByTaskId(
                    new BpmTaskExtDO().setTaskId(taskEntity.getId())
                            .setResult(BpmProcessInstanceResultEnum.SIGN_BEFORE.getResult())
                            .setReason(reqVO.getReason()));
        }
        // 2.4 Record signing method，Judgment is required to complete the task
        taskEntity.setScopeType(reqVO.getType());
        // 2.5 Save the modified value of the current task
        taskService.saveTask(taskEntity);

        // 3. Create a signature task
        createSignTask(convertList(reqVO.getUserIdList(), String::valueOf), taskEntity);

        // 4. Record Signature comment，The splicing result is： [Current user]Add signature forward/Added the signature to the back[Multiple users]，Reason：reason
        AdminUserRespDTO currentUser = adminUserApi.getUser(userId);
        String comment = StrUtil.format(BpmCommentTypeEnum.ADD_SIGN.getComment(), currentUser.getNickname(),
                BpmTaskAddSignTypeEnum.formatDesc(reqVO.getType()), String.join(",", convertList(userList, AdminUserRespDTO::getNickname)), reqVO.getReason());
        taskService.addComment(reqVO.getId(), taskEntity.getProcessInstanceId(),
                BpmCommentTypeEnum.ADD_SIGN.getType().toString(), comment);
    }


    /**
     * Check whether the signatures of the tasks are consistent
     * <p>
     * 1. If exists“Add signature forward”Task，No“Add signature to the back”
     * 2. If exists“Add signature to the back”Mission，No“Add signature forward”
     *
     * @param userId Current user ID
     * @param reqVO  Request parameters，Includes tasks ID And signature type
     * @return Current Task
     */
    private TaskEntityImpl validateAddSign(Long userId, BpmTaskAddSignReqVO reqVO) {
        TaskEntityImpl taskEntity = (TaskEntityImpl) validateTask(userId, reqVO.getId());
        // Forward and backward signatures cannot exist at the same time
        if (StrUtil.isNotBlank(taskEntity.getScopeType())
                && ObjectUtil.notEqual(BpmTaskAddSignTypeEnum.AFTER_CHILDREN_TASK.getType(), taskEntity.getScopeType())
                && ObjectUtil.notEqual(taskEntity.getScopeType(), reqVO.getType())) {
            throw exception(TASK_ADD_SIGN_TYPE_ERROR,
                    BpmTaskAddSignTypeEnum.formatDesc(taskEntity.getScopeType()), BpmTaskAddSignTypeEnum.formatDesc(reqVO.getType()));
        }
        // The same key Task，Approver is not repeated
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(taskEntity.getProcessInstanceId())
                .taskDefinitionKey(taskEntity.getTaskDefinitionKey()).list();
        List<Long> currentAssigneeList = convertList(taskList, task -> NumberUtils.parseLong(task.getAssignee()));
        // Keep intersection in currentAssigneeList Medium
        currentAssigneeList.retainAll(reqVO.getUserIdList());
        if (CollUtil.isNotEmpty(currentAssigneeList)) {
            List<AdminUserRespDTO> userList = adminUserApi.getUserList(currentAssigneeList);
            throw exception(TASK_ADD_SIGN_USER_REPEAT, String.join(",", convertList(userList, AdminUserRespDTO::getNickname)));
        }
        return taskEntity;
    }

    /**
     * Create a signature subtask
     *
     * @param addSingUserIdList The user whose signature is added ID
     * @param taskEntity        The task that was signed
     */
    private void createSignTask(List<String> addSingUserIdList, TaskEntityImpl taskEntity) {
        if (CollUtil.isEmpty(addSingUserIdList)) {
            return;
        }
        // Create a new task for the signer，All based on taskEntity Create for the parent task
        for (String addSignId : addSingUserIdList) {
            if (StrUtil.isBlank(addSignId)) {
                continue;
            }
            createSignTask(taskEntity, addSignId);
        }
    }

    /**
     * Create a signature subtask
     *
     * @param parentTask Parent Task
     * @param assignee   Subtask executor
     */
    private void createSignTask(TaskEntityImpl parentTask, String assignee) {
        // 1. Generate subtasks
        TaskEntityImpl task = (TaskEntityImpl) taskService.newTask(IdUtil.fastSimpleUUID());
        task = BpmTaskConvert.INSTANCE.convert(task, parentTask);
        if (BpmTaskAddSignTypeEnum.BEFORE.getType().equals(parentTask.getScopeType())) {
            // 2.1 Add signature before，Set approver
            task.setAssignee(assignee);
        } else {
            // 2.2.1 Settings owner Not set assignee Because it cannot be approved at the same time，Need to wait for the parent task to complete
            task.setOwner(assignee);
            // 2.2.2 Set up the task of adding signatures later scopeType for afterChildrenTask，Used to set the status of the task extension table
            task.setScopeType(BpmTaskAddSignTypeEnum.AFTER_CHILDREN_TASK.getType());
        }
        // 2. Save subtasks
        taskService.saveTask(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSignTask(Long userId, BpmTaskSubSignReqVO reqVO) {
        // 1.1 Verification task Can be reduced in number
        Task task = validateSubSign(reqVO.getId());
        // 1.2 Verify that the canceler exists
        AdminUserRespDTO cancelUser = null;
        if (StrUtil.isNotBlank(task.getAssignee())) {
            cancelUser = adminUserApi.getUser(NumberUtils.parseLong(task.getAssignee()));
        }
        if (cancelUser == null && StrUtil.isNotBlank(task.getOwner())) {
            cancelUser = adminUserApi.getUser(NumberUtils.parseLong(task.getOwner()));
        }
        Assert.notNull(cancelUser, "There is no owner or approver in the task，Data error");

        // 2. Delete tasks and corresponding subtasks
        // 2.1 Get all tasks that need to be deleted ID ，Includes the current task and all subtasks
        List<String> allTaskIdList = getAllChildTaskIds(task.getId());
        // 2.2 Delete the task and all subtasks
        taskService.deleteTasks(allTaskIdList);
        // 2.3 Change the extension table status to cancel
        AdminUserRespDTO user = adminUserApi.getUser(userId);
        taskExtMapper.updateBatchByTaskIdList(allTaskIdList, new BpmTaskExtDO().setResult(BpmProcessInstanceResultEnum.CANCEL.getResult())
                .setReason(StrUtil.format("Because{}Operation[Reduce signature]，The task was cancelled", user.getNickname())));

        // 3. Record logs to the parent task。Record the log first because，Passed handleParentTask After the method，The task may have been completed，and no longer exists，Will report abnormality，So record it first
        String comment = StrUtil.format(BpmCommentTypeEnum.SUB_SIGN.getComment(), user.getNickname(), cancelUser.getNickname());
        taskService.addComment(task.getParentTaskId(), task.getProcessInstanceId(),
                BpmCommentTypeEnum.SUB_SIGN.getType().toString(), comment);

        // 4. Process the parent task of the current task
        handleParentTask(task);
    }

    /**
     * Check whether the task can be reduced
     *
     * @param id MissionID
     * @return Task Information
     */
    private Task validateSubSign(String id) {
        Task task = validateTaskExist(id);

        // Must have scopeType
        String scopeType = task.getScopeType();
        if (StrUtil.isEmpty(scopeType)) {
            throw exception(TASK_SUB_SIGN_NO_PARENT);
        }
        // and the value is Add signature forward and backward
        if (!validateSignType(scopeType)) {
            throw exception(TASK_SUB_SIGN_NO_PARENT);
        }
        return task;
    }

    /**
     * Judge whether the current type is signature
     * @param scopeType Task scopeType
     * @return Current scopeType Return if it is a signature true
     */
    private boolean validateSignType(String scopeType){
        return StrUtil.equalsAny(scopeType,BpmTaskAddSignTypeEnum.BEFORE.getType(),scopeType, BpmTaskAddSignTypeEnum.AFTER.getType());
    }

    /**
     * Get all deleted tasks to be canceled ID Collection
     *
     * @param parentTaskId Parent TaskID
     * @return All tasksID
     */
    public List<String> getAllChildTaskIds(String parentTaskId) {
        List<String> allChildTaskIds = new ArrayList<>();
        // 1. Get children recursively
        Stack<String> stack = new Stack<>();
        // 1.1 Root taskIDPush to stack
        stack.push(parentTaskId);
        //Control the number of traversals not to exceed Byte.MAX_VALUE，Avoid dead loop caused by dirty data
        int count = 0;
        // TODO ：< spaces before and after，Pay attention；
        while (!stack.isEmpty() && count<Byte.MAX_VALUE) {
            // 1.2 Pop the top task of the stackID
            String taskId = stack.pop();
            // 1.3 The task will beIDAdd to result set
            allChildTaskIds.add(taskId);
            // 1.4 Get the subtask list of this task
            // TODO ：There is a more efficient way to write；One-time visit in One floor；Otherwise each node，Check them all once db， What a waste；Every time in，Finally O(h) Query，Instead of O(n) Query；
            List<String> childrenTaskIdList = getChildrenTaskIdList(taskId);
            if (CollUtil.isNotEmpty(childrenTaskIdList)) {
                for (String childTaskId : childrenTaskIdList) {
                    // 1.5 SubtaskIDPush to stack，For subsequent processing
                    stack.push(childTaskId);
                }
            }
            count++;
        }
        return allChildTaskIds;
    }

    /**
     * Get all subtasks of the specified parent task ID Collection
     *
     * @param parentTaskId Parent Task ID
     * @return All subtasks ID Collection
     */
    private List<String> getChildrenTaskIdList(String parentTaskId) {
        return convertList(getChildrenTaskList0(parentTaskId), Task::getId);
    }

    /**
     * Get all subtasks of the specified parent task ID Collection
     *
     * @param parentTaskId Parent Task ID
     * @return All subtasks ID Collection
     */
    private List<Task> getChildrenTaskList0(String parentTaskId) {
        String tableName = managementService.getTableName(TaskEntity.class);
        // taskService.createTaskQuery() No parentId Parameters，So write sql Query
        String sql = "select ID_,OWNER_,ASSIGNEE_ from " + tableName + " where PARENT_TASK_ID_=#{parentTaskId}";
        return taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).list();
    }


    @Override
    public List<BpmTaskSubSignRespVO> getChildrenTaskList(String parentId) {
        // 1. Only query ongoing tasks Tasks to be signed later，May not exist assignee，So we still need to query owner
        List<Task> taskList = getChildrenTaskList0(parentId);
        if (CollUtil.isEmpty(taskList)) {
            return Collections.emptyList();
        }
        List<String> childrenTaskIdList = convertList(taskList, Task::getId);

        // 2.1 Will owner Japanese assignee Unified into one set
        List<Long> userIds = convertListByFlatMap(taskList, control ->
                Stream.of(NumberUtils.parseLong(control.getAssignee()), NumberUtils.parseLong(control.getOwner()))
                        .filter(Objects::nonNull));
        // 2.2 Assembly data
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);
        List<BpmTaskExtDO> taskExtList = taskExtMapper.selectProcessListByTaskIds(childrenTaskIdList);
        Map<String, Task> idTaskMap = convertMap(taskList, TaskInfo::getId);
        return BpmTaskConvert.INSTANCE.convertList(taskExtList, userMap, idTaskMap);
    }

}
