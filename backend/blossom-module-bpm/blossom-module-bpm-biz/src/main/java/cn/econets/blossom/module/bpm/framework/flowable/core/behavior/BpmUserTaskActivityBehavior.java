package cn.econets.blossom.module.bpm.framework.flowable.core.behavior;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.econets.blossom.module.bpm.service.definition.BpmTaskAssignRuleService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import java.util.List;
import java.util.Set;

/**
 * Customized【Single】Process Task assignee Assignment of responsible persons
 * First step，Based on allocation rules，Calculate the assigned tasks【Single】Candidate。If not found，Directly report business exceptions，Do not continue the subsequent process；
 * Step 2，Randomly select a candidate，Select as assignee Person in charge。
 *
 */
@Slf4j
public class BpmUserTaskActivityBehavior extends UserTaskActivityBehavior {

    @Setter
    private BpmTaskAssignRuleService bpmTaskRuleService;

    public BpmUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner,
        List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager,
        DelegateExecution execution, ProcessEngineConfigurationImpl processEngineConfiguration) {
        // First step，Get candidate users for the task
        Long assigneeUserId = calculateTaskCandidateUsers(execution);
        Assert.notNull(assigneeUserId, "The task handler cannot be empty");
        // Step 2，Set as the person in charge
        TaskHelper.changeTaskAssignee(task, String.valueOf(assigneeUserId));
    }

    private Long calculateTaskCandidateUsers(DelegateExecution execution) {
        // Situation 1，If it is a multi-instance task，For example, co-signing、Or signing, etc.，Follow Variable Get。Its task handler is BpmParallelMultiInstanceBehavior has been allocated
        if (super.multiInstanceActivityBehavior != null) {
            return execution.getVariable(super.multiInstanceActivityBehavior.getCollectionElementVariable(), Long.class);
        }

        // Situation 2，If it is not a multi-instance task，Then calculate the task handler
        // First step，First calculate the people who can handle the task
        Set<Long> candidateUserIds = bpmTaskRuleService.calculateTaskCandidateUsers(execution);
        // Step 2，Then randomly select a task handler
        // Question：Why do we have to choose a task handler?？
        // Answer：Project pair bpm The mission is to assign responsibilities to individuals，So each task has only one handler。
        //      If you want a task to be processed by multiple people at the same time，You can consider using BpmParallelMultiInstanceBehavior Realized countersignature or Or sign。
        int index = RandomUtil.randomInt(candidateUserIds.size());
        return CollUtil.get(candidateUserIds, index);
    }

}
