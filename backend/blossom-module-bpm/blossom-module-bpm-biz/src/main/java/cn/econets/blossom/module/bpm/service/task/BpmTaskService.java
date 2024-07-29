package cn.econets.blossom.module.bpm.service.task;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.task.*;
import cn.econets.blossom.module.bpm.dal.dataobject.task.BpmTaskExtDO;
import org.flowable.task.api.Task;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Process Task Instance Service Interface
 * 
 */
public interface BpmTaskService {

    /**
     * Get the pending process task pages
     *
     * @param userId    User Number
     * @param pageReqVO Pagination request
     * @return Process Task Paging
     */
    PageResult<BpmTaskTodoPageItemRespVO> getTodoTaskPage(Long userId, BpmTaskTodoPageReqVO pageReqVO);

    /**
     * Get the completed process task pages
     *
     * @param userId    User Number
     * @param pageReqVO Pagination request
     * @return Process Task Paging
     */
    PageResult<BpmTaskDonePageItemRespVO> getDoneTaskPage(Long userId, BpmTaskDonePageReqVO pageReqVO);

    /**
     * Get process tasks Map
     *
     * @param processInstanceIds Process instance number array
     * @return Process Task Map
     */
    default Map<String, List<Task>> getTaskMapByProcessInstanceIds(List<String> processInstanceIds) {
        return CollectionUtils.convertMultiMap(getTasksByProcessInstanceIds(processInstanceIds),
                Task::getProcessInstanceId);
    }

    /**
     * Get the process task list
     *
     * @param processInstanceIds Process instance number array
     * @return Process Task List
     */
    List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds);

    /**
     * Get the process task list of the instruction process instance，Includes all states
     *
     * @param processInstanceId Process instance number
     * @return Process Task List
     */
    List<BpmTaskRespVO> getTaskListByProcessInstanceId(String processInstanceId);


    /**
     * Pass the mission ID Collection，Get task extension table information collection
     *
     * @param taskIdList Mission ID Collection
     * @return Task List
     */
    List<BpmTaskExtDO> getTaskListByTaskIdList(List<String> taskIdList);

    /**
     * Pass the mission
     *
     * @param userId User Number
     * @param reqVO  By request
     */
    void approveTask(Long userId, @Valid BpmTaskApproveReqVO reqVO);

    /**
     * Failed the task
     *
     * @param userId User Number
     * @param reqVO  Reject request
     */
    void rejectTask(Long userId, @Valid BpmTaskRejectReqVO reqVO);

    /**
     * Assign process tasks to specified users
     *
     * @param userId User Number
     * @param reqVO  Assignment Request
     */
    void updateTaskAssignee(Long userId, BpmTaskUpdateAssigneeReqVO reqVO);

    /**
     * Assign process tasks to specified users
     *
     * @param id     Process task number
     * @param userId User Number
     */
    void updateTaskAssignee(String id, Long userId);

    /**
     * Create Task Expanded Records
     *
     * @param task Task Entity
     */
    void createTaskExt(Task task);

    /**
     * Update Task Extended record is completed
     *
     * @param task Task Entity
     */
    void updateTaskExtComplete(Task task);

    /**
     * Update Task The expansion record is canceled
     *
     * @param taskId Task number
     */
    void updateTaskExtCancel(String taskId);

    /**
     * Update Task Expanded Records，And send notification
     *
     * @param task Task Entity
     */
    void updateTaskExtAssign(Task task);

    /**
     * Get the set of reversible processes for the current task
     *
     * @param taskId Current Tasks ID
     * @return List of nodes that can be rolled back
     */
    List<BpmTaskSimpleRespVO> getReturnTaskList(String taskId);

    /**
     * Return the task to the specified one targetDefinitionKey Location
     *
     * @param userId User Number
     * @param reqVO  Reverted taskskeyand the current taskID
     */
    void returnTask(Long userId, BpmTaskReturnReqVO reqVO);


    /**
     * Delegating the specified task to others，Wait for the recipient to process it before returning it to the original approver for approval
     *
     * @param userId User Number
     * @param reqVO  Parameters of the assignee and the assigned task number and reason
     */
    void delegateTask(Long userId, BpmTaskDelegateReqVO reqVO);

    /**
     * Task Signature
     *
     * @param userId Added users and tasks ID，Additional signature type
     * @param reqVO  Current user ID
     */
    void createSignTask(Long userId, BpmTaskAddSignReqVO reqVO);

    /**
     * Task minus signature
     *
     * @param userId Current userID
     * @param reqVO  The task that was reduced ID，Reason
     */
    void deleteSignTask(Long userId, BpmTaskSubSignReqVO reqVO);

    /**
     * Get the subtask and approver information of the specified task
     *
     * @param parentId Specified tasksID
     * @return Subtask List
     */
    List<BpmTaskSubSignRespVO> getChildrenTaskList(String parentId);

}
