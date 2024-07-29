package cn.econets.blossom.module.bpm.enums.task;

import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Results of process instance
 *
 */
@Getter
@AllArgsConstructor
public enum BpmProcessInstanceResultEnum {

    PROCESS(1, "Processing"),
    APPROVE(2, "Passed"),
    REJECT(3, "Not passed"),
    CANCEL(4, "Cancelled"),

    // ========== Unique status of process tasks ==========

    BACK(5, "Rejected"), // Return
    DELEGATE(6, "Delegation"),
    /**
     * 【Add signature】The source task has been approved and completed，But it uses post-signature，The task of adding signature later is not completed，The source task will be in this state
     * Equivalent to Passed APPROVE Special status
     * For example：AApproval， A Added signature later B，And the task was approved，But B Not yet approved，The current task status is“Wait for the signature task to be completed”
     */
    SIGN_AFTER(7, "Wait for the signature task to be completed"),
    /**
     * 【Add signature】Source task not approved，But it was signed forward，So the source task status becomes“Wait for the previous signing task to be completed”
     * Equivalent to Processing PROCESS Special status
     * For example：A Approval， A Added signature before B，B Not reviewed yet
     */
    SIGN_BEFORE(8, "Wait for the previous signing task to be completed"),
    /**
     * 【Add signature】The initial state of the post-signature task when it is created
     * Equivalent to Processing PROCESS Special status
     * Because the source task needs to be completed first，Only those who can sign later can come to approve，So a state distinction is added
     */
    WAIT_BEFORE_TASK(9, "Wait for the previous task to be completed");

    /**
     * The status that can be reduced
     */
    public static final List<Integer> CAN_SUB_SIGN_STATUS_LIST = Arrays.asList(PROCESS.result, WAIT_BEFORE_TASK.result);

    /**
     * Results
     * <p>
     * If adding，Attention {@link #isEndResult(Integer)} Does it need to be changed?
     */
    private final Integer result;
    /**
     * Description
     */
    private final String desc;

    /**
     * Judge whether the result is already in End Final result
     * <p>
     * Mainly used for some result update logic，If it is already the final result，No more updates
     *
     * @param result Results
     * @return Yes
     */
    public static boolean isEndResult(Integer result) {
        return ObjectUtils.equalsAny(result, APPROVE.getResult(), REJECT.getResult(),
                CANCEL.getResult(), BACK.getResult(),
                SIGN_AFTER.getResult());
    }

}
