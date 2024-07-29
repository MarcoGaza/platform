package cn.econets.blossom.module.bpm.service.task;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.instance.*;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Process instance Service Interface
 *
 * 
 */
public interface BpmProcessInstanceService {

    /**
     * Get process instance
     *
     * @param id Process instance number
     * @return Process instance
     */
    ProcessInstance getProcessInstance(String id);

    /**
     * Get the process instance list
     *
     * @param ids A collection of process instance numbers
     * @return Process instance list
     */
    List<ProcessInstance> getProcessInstances(Set<String> ids);

    /**
     * Get process instance Map
     *
     * @param ids A collection of process instance numbers
     * @return Process instance list Map
     */
    default Map<String, ProcessInstance> getProcessInstanceMap(Set<String> ids) {
        return CollectionUtils.convertMap(getProcessInstances(ids), ProcessInstance::getProcessInstanceId);
    }

    /**
     * Get the paging of the process instance
     *
     * @param userId    User ID
     * @param pageReqVO Pagination request
     * @return Process instance paging
     */
    PageResult<BpmProcessInstancePageItemRespVO> getMyProcessInstancePage(Long userId,
                                                                          @Valid BpmProcessInstanceMyPageReqVO pageReqVO);

    /**
     * Create a process instance（Provided to the front end）
     *
     * @param userId      User ID
     * @param createReqVO Create information
     * @return Instance number
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO);

    /**
     * Create a process instance（Provided to internal users）
     *
     * @param userId       User ID
     * @param createReqDTO Create information
     * @return Instance number
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO createReqDTO);

    /**
     * Get process instance VO Information
     *
     * @param id Process instance number
     * @return Process instance
     */
    BpmProcessInstanceRespVO getProcessInstanceVO(String id);

    /**
     * Cancel process instance
     *
     * @param userId      User Number
     * @param cancelReqVO Cancel message
     */
    void cancelProcessInstance(Long userId, @Valid BpmProcessInstanceCancelReqVO cancelReqVO);

    /**
     * Get the historical process instance
     *
     * @param id Process instance number
     * @return Historical process instance
     */
    HistoricProcessInstance getHistoricProcessInstance(String id);

    /**
     * Get the historical process instance list
     *
     * @param ids Collection of process instance numbers
     * @return Historical process instance list
     */
    List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids);

    /**
     * Get the historical process instance Map
     *
     * @param ids Collection of process instance numbers
     * @return Historical process instance list Map
     */
    default Map<String, HistoricProcessInstance> getHistoricProcessInstanceMap(Set<String> ids) {
        return CollectionUtils.convertMap(getHistoricProcessInstances(ids), HistoricProcessInstance::getId);
    }

    /**
     * Create ProcessInstance Expanded Records
     *
     * @param instance Process Task
     */
    void createProcessInstanceExt(ProcessInstance instance);

    /**
     * Update ProcessInstance Extended record is canceled
     *
     * @param event Process cancellation event
     */
    void updateProcessInstanceExtCancel(FlowableCancelledEvent event);

    /**
     * Update ProcessInstance Extended record is completed
     *
     * @param instance Process Task
     */
    void updateProcessInstanceExtComplete(ProcessInstance instance);

    /**
     * Update ProcessInstance Extended record is not passed
     *
     * @param id     Process number
     * @param reason Reason。For example，When approval is not passed，The value needs to be passed
     */
    void updateProcessInstanceExtReject(String id, String reason);

    // TODO Change to getProcessInstanceAssigneesByTaskDefinitionKey(String id, String taskDefinitionKey)
    /**
     * Getting process instance，Get the approver specified in advance for the specified process task
     *
     * @param processInstanceId Process instance number
     * @param taskDefinitionKey Process task definition key
     * @return Approver collection
     */
    List<Long> getAssigneeByProcessInstanceIdAndTaskDefinitionKey(String processInstanceId, String taskDefinitionKey);

}
