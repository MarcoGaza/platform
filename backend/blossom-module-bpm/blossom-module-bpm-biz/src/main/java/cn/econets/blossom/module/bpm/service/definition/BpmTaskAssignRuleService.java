package cn.econets.blossom.module.bpm.service.definition;

import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleRespVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleUpdateReqVO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmTaskAssignRuleDO;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * BPM Task allocation rules Service Interface
 *
 * 
 */
public interface BpmTaskAssignRuleService {

    /**
     * Get the task allocation rule array defined in the process
     *
     * @param processDefinitionId Process definition number
     * @param taskDefinitionKey Process task definition Key。Allow empty space
     * @return Task rule array
     */
    List<BpmTaskAssignRuleDO> getTaskAssignRuleListByProcessDefinitionId(String processDefinitionId,
                                                                         @Nullable String taskDefinitionKey);

    /**
     * Get the task rule array of the process model
     *
     * @param modelId Process model number
     * @return Task rule array
     */
    List<BpmTaskAssignRuleDO> getTaskAssignRuleListByModelId(String modelId);

    /**
     * Get the task allocation rule array defined in the process
     *
     * @param modelId Process model number
     * @param processDefinitionId Process definition number
     * @return Task rule array
     */
    List<BpmTaskAssignRuleRespVO> getTaskAssignRuleList(String modelId, String processDefinitionId);

    /**
     * Create task assignment rules
     *
     * @param reqVO Create information
     * @return Rule number
     */
    Long createTaskAssignRule(@Valid BpmTaskAssignRuleCreateReqVO reqVO);

    /**
     * Update task allocation rules
     *
     * @param reqVO Create information
     */
    void updateTaskAssignRule(@Valid BpmTaskAssignRuleUpdateReqVO reqVO);

    /**
     * Judge whether the allocation rules of the specified process model and process definition are equal
     *
     * @param modelId Process model number
     * @param processDefinitionId Process definition number
     * @return Are they equal?
     */
    boolean isTaskAssignRulesEquals(String modelId, String processDefinitionId);

    /**
     * Assigning rules to the process flow model，Copy a copy to the process definition
     * Purpose：Every time a process model is deployed，A new process definition will be generated，At this time, the process of each deployment should be considered immutable，So you need to copy a copy to the process definition
     *
     * @param fromModelId Process model number
     * @param toProcessDefinitionId Process definition number
     */
    void copyTaskAssignRules(String fromModelId, String toProcessDefinitionId);

    /**
     * All task allocation rules of the verification process model have been configured
     * Purpose：If there are rules not configured，It will cause the process task to not find the person in charge，The process cannot proceed further！
     *
     * @param id Process model number
     */
    void checkTaskAssignRuleAllConfig(String id);

    /**
     * Calculate the person who is currently executing the task
     *
     * @param execution Execute the task
     * @return Processor number array
     */
    Set<Long> calculateTaskCandidateUsers(DelegateExecution execution);

}
