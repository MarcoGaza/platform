package cn.econets.blossom.module.bpm.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Process Task -- commentType enumeration
 */
@Getter
@AllArgsConstructor
public enum BpmCommentTypeEnum {

    APPROVE(1, "Passed", ""),
    REJECT(2, "Not passed", ""),
    CANCEL(3, "Cancelled", ""),
    BACK(4, "Return", ""),
    DELEGATE(5, "Delegation", ""),
    ADD_SIGN(6, "Add signature", "[{}]{}gave[{}]，Reason：{}"),
    SUB_SIGN(7, "Reduce signature", "[{}]Operated【Reduce signature】,Approver[{}]The task was cancelled"),
    ;

    /**
     * Operation type
     */
    private final Integer type;
    /**
     * Operation name
     */
    private final String name;
    /**
     * Operation description
     */
    private final String comment;

}
