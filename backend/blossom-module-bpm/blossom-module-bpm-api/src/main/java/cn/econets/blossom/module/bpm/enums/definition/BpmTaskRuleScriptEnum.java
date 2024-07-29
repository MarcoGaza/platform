package cn.econets.blossom.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM Task rule script enumeration
 * Currently passed TODO Hard coded，Can be considered in the future Groovy Dynamic script method
 *
 */
@Getter
@AllArgsConstructor
public enum BpmTaskRuleScriptEnum {

    START_USER(10L, "Process initiator"),

    LEADER_X1(20L, "First-level leader of the process initiator"),
    LEADER_X2(21L, "Second-level leader of the process initiator");

    /**
     * Script number
     */
    private final Long id;
    /**
     * Script description
     */
    private final String desc;

}
