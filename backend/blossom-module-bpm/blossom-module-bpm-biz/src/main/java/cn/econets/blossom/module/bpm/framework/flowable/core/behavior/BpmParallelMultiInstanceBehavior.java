package cn.econets.blossom.module.bpm.framework.flowable.core.behavior;

import cn.econets.blossom.framework.flowable.core.util.FlowableUtils;
import cn.econets.blossom.module.bpm.service.definition.BpmTaskAssignRuleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.Activity;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;

import java.util.Set;

/**
 * Customized【Parallel】of【Multiple】Process Task assignee Assignment of responsible persons
 * First step，Based on allocation rules，Calculate the assigned tasks【Multiple】Candidates。
 * Step 2，Will【Multiple】Mission Candidates，Set to DelegateExecution of collectionVariable In variables，So that BpmUserTaskActivityBehavior Use it
 *
 */
@Slf4j
public class BpmParallelMultiInstanceBehavior extends ParallelMultiInstanceBehavior {

    @Setter
    private BpmTaskAssignRuleService bpmTaskRuleService;

    public BpmParallelMultiInstanceBehavior(Activity activity,
                                            AbstractBpmnActivityBehavior innerActivityBehavior) {
        super(activity, innerActivityBehavior);
    }

    /**
     * Rewrite this method，Mainly realizes two functions：
     * 1. Ignore the original collectionVariable、collectionElementVariable Expression，Instead, we use our own definition
     * 2. Get the person who handles the task，and set to collectionVariable Medium，Used for BpmUserTaskActivityBehavior You can get the person who handles the task from it
     *
     * Attention，Multiple task instances，Each task instance corresponds to a handler，So the number returned is the number of people handling the task
     *
     * @param execution Execute the task
     * @return Quantity
     */
    @Override
    protected int resolveNrOfInstances(DelegateExecution execution) {
        // First step，Settings collectionVariable Japanese CollectionVariable
        // From  execution.getVariable() Read all task handlers key
        super.collectionExpression = null; // collectionExpression Japanese collectionVariable are mutually exclusive
        super.collectionVariable = FlowableUtils.formatCollectionVariable(execution.getCurrentActivityId());
        // From execution.getVariable() Read all current task processing people key
        super.collectionElementVariable = FlowableUtils.formatCollectionElementVariable(execution.getCurrentActivityId());

        // Step 2，Get all task handlers
        Set<Long> assigneeUserIds = bpmTaskRuleService.calculateTaskCandidateUsers(execution);
        execution.setVariable(super.collectionVariable, assigneeUserIds);
        return assigneeUserIds.size();
    }

}
