package cn.econets.blossom.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM Task assignment rule type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum BpmTaskAssignRuleTypeEnum {

    ROLE(10, "Role"),
    DEPT_MEMBER(20, "Members of the department"), // Including responsible persons
    DEPT_LEADER(21, "Head of department"),
    POST(22, "Position"),
    USER(30, "User"),
    USER_GROUP(40, "User Group"),
    SCRIPT(50, "Custom script"), // For example，The leader of the sponsor's department、The leader of the leader of the sponsor's department
    ;

    /**
     * Type
     */
    private final Integer type;
    /**
     * Description
     */
    private final String desc;

}
