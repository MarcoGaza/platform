package cn.econets.blossom.module.bpm.framework.flowable.core.behavior.script.impl;

import cn.econets.blossom.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Second level assigned to the initiator Leader Approved Script Implementation class
 *
 */
@Component
public class BpmTaskAssignLeaderX2Script extends BpmTaskAssignLeaderAbstractScript {

    @Override
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        return calculateTaskCandidateUsers(execution, 2);
    }

    @Override
    public BpmTaskRuleScriptEnum getEnum() {
        return BpmTaskRuleScriptEnum.LEADER_X2;
    }

}
