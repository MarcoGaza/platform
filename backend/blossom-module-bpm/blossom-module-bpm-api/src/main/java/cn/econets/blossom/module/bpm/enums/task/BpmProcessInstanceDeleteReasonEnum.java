package cn.econets.blossom.module.bpm.enums.task;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Reason for deleting the process instance
 *
 */
@Getter
@AllArgsConstructor
public enum BpmProcessInstanceDeleteReasonEnum {

    REJECT_TASK("Failed the task，Reason：{}"), // When modifying the text，Attention required isRejectReason Method
    CANCEL_TASK("Actively cancel the task，Reason：{}"),

    // ========== Unique reason for process task ==========
    MULTI_TASK_END("System automatically canceled，Reason：Multi-task approval has met the conditions，No need to approve this task"), // Multiple instances satisfy condition At the end，Other task instance tasks will be cancelled，The corresponding deletion reason is MI_END

    ;

    private final String reason;

    /**
     * Formatting reason
     *
     * @param args Parameters
     * @return Reason
     */
    public String format(Object... args) {
        return StrUtil.format(reason, args);
    }

    // ========== Logic ==========

    public static boolean isRejectReason(String reason) {
        return StrUtil.startWith(reason, "Failed the task，Reason：");
    }

    /**
     * Will Flowable Reason for deletion，Translated into the corresponding Chinese reason
     *
     * @param reason Original reason
     * @return Reason
     */
    public static String translateReason(String reason) {
        if (StrUtil.isEmpty(reason)) {
            return reason;
        }
        switch (reason) {
            case "MI_END": return MULTI_TASK_END.getReason();
            default: return reason;
        }
    }

}
