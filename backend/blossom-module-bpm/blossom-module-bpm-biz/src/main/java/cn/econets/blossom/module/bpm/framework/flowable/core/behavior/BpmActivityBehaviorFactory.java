package cn.econets.blossom.module.bpm.framework.flowable.core.behavior;

import cn.econets.blossom.module.bpm.service.definition.BpmTaskAssignRuleService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;

/**
 * Custom ActivityBehaviorFactory Implementation class，The purpose is as follows：
 * 1. Custom {@link #createUserTaskActivityBehavior(UserTask)}：Implementing custom process tasks assignee Assignment of responsible persons
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmActivityBehaviorFactory extends DefaultActivityBehaviorFactory {

    @Setter
    private BpmTaskAssignRuleService bpmTaskRuleService;

    @Override
    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask) {
        return new BpmUserTaskActivityBehavior(userTask)
                .setBpmTaskRuleService(bpmTaskRuleService);
    }

    @Override
    public ParallelMultiInstanceBehavior createParallelMultiInstanceBehavior(Activity activity,
                                                                             AbstractBpmnActivityBehavior innerActivityBehavior) {
        return new BpmParallelMultiInstanceBehavior(activity, innerActivityBehavior)
                .setBpmTaskRuleService(bpmTaskRuleService);
    }

    // TODO ke：SequentialMultiInstanceBehavior You can also take a look at this when you have time

}
