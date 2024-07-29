package cn.econets.blossom.module.bpm.service.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.framework.flowable.core.context.FlowableContextHolder;
import cn.econets.blossom.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.instance.*;
import cn.econets.blossom.module.bpm.convert.task.BpmProcessInstanceConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import cn.econets.blossom.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import cn.econets.blossom.module.bpm.dal.mysql.task.BpmProcessInstanceExtMapper;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceDeleteReasonEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceStatusEnum;
import cn.econets.blossom.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventPublisher;
import cn.econets.blossom.module.bpm.service.definition.BpmProcessDefinitionService;
import cn.econets.blossom.module.bpm.service.message.BpmMessageService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * Process instance Service Implementation class
 * <p>
 * ProcessDefinition & ProcessInstance & Execution & Task Relationship：
 * 1. <a href="https://blog.csdn.net/bobozai86/article/details/105210414" />
 * <p>
 * HistoricProcessInstance & ProcessInstance Relationship：
 * 1. <a href=" https://my.oschina.net/843294669/blog/71902" />
 * <p>
 * In simple terms，The former = History + Running process instance，The latter is only a running process instance
 *
 * 
 */
@Service
@Validated
@Slf4j
public class BpmProcessInstanceServiceImpl implements BpmProcessInstanceService {

    @Resource
    private RuntimeService runtimeService;
    @Resource
    private BpmProcessInstanceExtMapper processInstanceExtMapper;
    @Resource
    @Lazy // Solve circular dependencies
    private BpmTaskService taskService;
    @Resource
    private BpmProcessDefinitionService processDefinitionService;
    @Resource
    private HistoryService historyService;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    private BpmProcessInstanceResultEventPublisher processInstanceResultEventPublisher;
    @Resource
    private BpmMessageService messageService;

    @Override
    public ProcessInstance getProcessInstance(String id) {
        return runtimeService.createProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(id)
                .singleResult();
    }

    @Override
    public List<ProcessInstance> getProcessInstances(Set<String> ids) {
        return runtimeService.createProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public PageResult<BpmProcessInstancePageItemRespVO> getMyProcessInstancePage(Long userId,
                                                                                 BpmProcessInstanceMyPageReqVO pageReqVO) {
        // Passed BpmProcessInstanceExtDO Table，Query the corresponding page first
        PageResult<BpmProcessInstanceExtDO> pageResult = processInstanceExtMapper.selectPage(userId, pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return new PageResult<>(pageResult.getTotal());
        }

        // Get process Task Map
        List<String> processInstanceIds = convertList(pageResult.getList(), BpmProcessInstanceExtDO::getProcessInstanceId);
        Map<String, List<Task>> taskMap = taskService.getTaskMapByProcessInstanceIds(processInstanceIds);
        // Convert and return
        return BpmProcessInstanceConvert.INSTANCE.convertPage(pageResult, taskMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO) {
        // Get process definition
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(createReqVO.getProcessDefinitionId());
        // Initiate process
        return createProcessInstance0(userId, definition, createReqVO.getVariables(), null, createReqVO.getAssignee());
    }

    @Override
    public String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO createReqDTO) {
        // Get process definition
        ProcessDefinition definition = processDefinitionService.getActiveProcessDefinition(createReqDTO.getProcessDefinitionKey());
        // Initiate process
        return createProcessInstance0(userId, definition, createReqDTO.getVariables(), createReqDTO.getBusinessKey(), createReqDTO.getAssignee());
    }

    @Override
    public BpmProcessInstanceRespVO getProcessInstanceVO(String id) {
        // Get process instance
        HistoricProcessInstance processInstance = getHistoricProcessInstance(id);
        if (processInstance == null) {
            return null;
        }
        BpmProcessInstanceExtDO processInstanceExt = processInstanceExtMapper.selectByProcessInstanceId(id);
        Assert.notNull(processInstanceExt, "Process instance expansion({}) Does not exist", id);

        // Get process definition
        ProcessDefinition processDefinition = processDefinitionService
                .getProcessDefinition(processInstance.getProcessDefinitionId());
        Assert.notNull(processDefinition, "Process definition({}) Does not exist", processInstance.getProcessDefinitionId());
        BpmProcessDefinitionExtDO processDefinitionExt = processDefinitionService.getProcessDefinitionExt(
                processInstance.getProcessDefinitionId());
        Assert.notNull(processDefinitionExt, "Process definition extension({}) Does not exist", id);
        String bpmnXml = processDefinitionService.getProcessDefinitionBpmnXML(processInstance.getProcessDefinitionId());

        // Get User
        AdminUserRespDTO startUser = adminUserApi.getUser(NumberUtils.parseLong(processInstance.getStartUserId()));
        DeptRespDTO dept = null;
        if (startUser != null) {
            dept = deptApi.getDept(startUser.getDeptId());
        }

        // Joining results
        return BpmProcessInstanceConvert.INSTANCE.convert2(processInstance, processInstanceExt,
                processDefinition, processDefinitionExt, bpmnXml, startUser, dept);
    }

    @Override
    public void cancelProcessInstance(Long userId, @Valid BpmProcessInstanceCancelReqVO cancelReqVO) {
        // Verify that the process instance exists
        ProcessInstance instance = getProcessInstance(cancelReqVO.getId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }
        // You can only cancel your own
        if (!Objects.equals(instance.getStartUserId(), String.valueOf(userId))) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF);
        }

        // By deleting the process instance，Realize the cancellation of process instances,
        // Delete process instance，Regular execution task ACT_RU_TASK. The task will be deleted。Query through the history table
        deleteProcessInstance(cancelReqVO.getId(),
                BpmProcessInstanceDeleteReasonEnum.CANCEL_TASK.format(cancelReqVO.getReason()));
    }

    /**
     * Get the historical process instance
     *
     * @param id Process instance number
     * @return Historical process instance
     */
    @Override
    public HistoricProcessInstance getHistoricProcessInstance(String id) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(id).singleResult();
    }

    @Override
    public List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceIds(ids).list();
    }

    @Override
    public void createProcessInstanceExt(ProcessInstance instance) {
        // Get process definition
        ProcessDefinition definition = processDefinitionService.getProcessDefinition2(instance.getProcessDefinitionId());
        // Insert BpmProcessInstanceExtDO Object
        BpmProcessInstanceExtDO instanceExtDO = new BpmProcessInstanceExtDO()
                .setProcessInstanceId(instance.getId())
                .setProcessDefinitionId(definition.getId())
                .setName(instance.getProcessDefinitionName())
                .setStartUserId(Long.valueOf(instance.getStartUserId()))
                .setCategory(definition.getCategory())
                .setStatus(BpmProcessInstanceStatusEnum.RUNNING.getStatus())
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());

        processInstanceExtMapper.insert(instanceExtDO);
    }

    @Override
    public void updateProcessInstanceExtCancel(FlowableCancelledEvent event) {
        // Judge whether Reject Not passed。If yes，No update will be performed.
        // Because，updateProcessInstanceExtReject Method，Updated
        if (BpmProcessInstanceDeleteReasonEnum.isRejectReason((String) event.getCause())) {
            return;
        }

        // Need to actively query，Because instance Only id Properties
        // In addition，If you query at this time ProcessInstance Words，The field is incomplete，So I went to check HistoricProcessInstance
        HistoricProcessInstance processInstance = getHistoricProcessInstance(event.getProcessInstanceId());
        // Update extension table
        BpmProcessInstanceExtDO instanceExtDO = new BpmProcessInstanceExtDO()
                .setProcessInstanceId(event.getProcessInstanceId())
                .setEndTime(LocalDateTime.now()) // Because ProcessInstance There is no way to get it endTime，So here is the setting
                .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
                .setResult(BpmProcessInstanceResultEnum.CANCEL.getResult());
        processInstanceExtMapper.updateByProcessInstanceId(instanceExtDO);

        // Send process instance status events
        processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
                BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExtDO.getResult()));
    }

    @Override
    public void updateProcessInstanceExtComplete(ProcessInstance instance) {
        // Need to actively query，Because instance Only id Properties
        // In addition，If you query at this time ProcessInstance Words，The field is incomplete，So I went to check HistoricProcessInstance
        HistoricProcessInstance processInstance = getHistoricProcessInstance(instance.getId());
        // Update extension table
        BpmProcessInstanceExtDO instanceExtDO = new BpmProcessInstanceExtDO()
                .setProcessInstanceId(instance.getProcessInstanceId())
                .setEndTime(LocalDateTime.now()) // Because ProcessInstance There is no way to get it endTime，So here is the setting
                .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult()); // If it is completely normal，Approval passed
        processInstanceExtMapper.updateByProcessInstanceId(instanceExtDO);

        // Send a message that the process has been passed
        messageService.sendMessageWhenProcessInstanceApprove(BpmProcessInstanceConvert.INSTANCE.convert2ApprovedReq(instance));

        // Send process instance status events
        processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
                BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExtDO.getResult()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProcessInstanceExtReject(String id, String reason) {
        // Need to actively query，Because instance Only id Properties
        ProcessInstance processInstance = getProcessInstance(id);
        // Delete process instance，To achieve the rejection task，Cancel the entire approval process
        deleteProcessInstance(id, StrUtil.format(BpmProcessInstanceDeleteReasonEnum.REJECT_TASK.format(reason)));

        // Update status + result
        // Attention，Cannot change the position with the above logic。Because deleteProcessInstance Will trigger the cancellation of the process，And then call updateProcessInstanceExtCancel Method，
        // Settings result for BpmProcessInstanceStatusEnum.CANCEL，Obviously result Not necessarily consistent
        BpmProcessInstanceExtDO instanceExtDO = new BpmProcessInstanceExtDO().setProcessInstanceId(id)
                .setStatus(BpmProcessInstanceStatusEnum.FINISH.getStatus())
                .setResult(BpmProcessInstanceResultEnum.REJECT.getResult());
        processInstanceExtMapper.updateByProcessInstanceId(instanceExtDO);

        // The message that the sending process failed
        messageService.sendMessageWhenProcessInstanceReject(BpmProcessInstanceConvert.INSTANCE.convert2RejectReq(processInstance, reason));

        // Send process instance status events
        processInstanceResultEventPublisher.sendProcessInstanceResultEvent(
                BpmProcessInstanceConvert.INSTANCE.convert(this, processInstance, instanceExtDO.getResult()));
    }

    private void deleteProcessInstance(String id, String reason) {
        runtimeService.deleteProcessInstance(id, reason);
    }

    private String createProcessInstance0(Long userId, ProcessDefinition definition,
                                          Map<String, Object> variables, String businessKey,
                                          Map<String, List<Long>> assignee) {
        // Verify process definition
        if (definition == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }
        if (definition.isSuspended()) {
            throw exception(PROCESS_DEFINITION_IS_SUSPENDED);
        }
        // Set context information
        // TODO How about going to variables Save to a global fixed value key Sato，Reduce reliance on context
        FlowableContextHolder.setAssignee(assignee);

        // Create a process instance
        ProcessInstance instance = runtimeService.createProcessInstanceBuilder()
                .processDefinitionId(definition.getId())
                .businessKey(businessKey)
                .name(definition.getName().trim())
                .variables(variables)
                .start();
        // Set the process name
        runtimeService.setProcessInstanceName(instance.getId(), definition.getName());

        // Complete the extended table of process instances
        processInstanceExtMapper.updateByProcessInstanceId(new BpmProcessInstanceExtDO().setProcessInstanceId(instance.getId())
                .setFormVariables(variables).setAssignee(assignee));
        return instance.getId();
    }

    @Override
    public List<Long> getAssigneeByProcessInstanceIdAndTaskDefinitionKey(String processInstanceId, String taskDefinitionKey) {
        // 1. Get from context first，Cannot be found in the database for the first submission
        List<Long> result = FlowableContextHolder.getAssigneeByTaskDefinitionKey(taskDefinitionKey);
        if (CollUtil.isNotEmpty(result)) {
            return result;
        }
        // 2. Get from database
        BpmProcessInstanceExtDO instance = processInstanceExtMapper.selectByProcessInstanceId(processInstanceId);
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS);
        }
        if (CollUtil.isNotEmpty(instance.getAssignee())) {
            return instance.getAssignee().get(taskDefinitionKey);
        }
        return Collections.emptyList();
    }

}
