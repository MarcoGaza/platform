package cn.econets.blossom.module.bpm.framework.flowable.core.behavior.script;

import cn.econets.blossom.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import org.flowable.engine.delegate.DelegateExecution;

import java.util.Set;

/**
 * Bpm Customization of task assignment Script Script
 * Usage scenario：
 * 1. Set the approver as the initiator
 * 2. Set the approver as the initiator Leader
 * 3. Even the approver is the initiator Leader of Leader
 *
 */
public interface BpmTaskAssignScript {

    /**
     * Based on execution tasks，Candidate users who get the task
     *
     * @param execution Execute the task
     * @return Candidate user ID array
     */
    Set<Long> calculateTaskCandidateUsers(DelegateExecution execution);

    /**
     * Get enumeration value
     *
     * @return Enumeration value
     */
    BpmTaskRuleScriptEnum getEnum();
}

