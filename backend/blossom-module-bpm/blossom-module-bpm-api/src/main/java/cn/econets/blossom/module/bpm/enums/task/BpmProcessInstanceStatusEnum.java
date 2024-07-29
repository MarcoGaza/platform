package cn.econets.blossom.module.bpm.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The status of the process instance
 *
 */
@Getter
@AllArgsConstructor
public enum BpmProcessInstanceStatusEnum {

    RUNNING(1, "In progress"),
    FINISH(2, "Completed");

    /**
     * Status
     */
    private final Integer status;
    /**
     * Description
     */
    private final String desc;

}
