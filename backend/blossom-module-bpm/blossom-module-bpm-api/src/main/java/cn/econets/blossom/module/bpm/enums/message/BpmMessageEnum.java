package cn.econets.blossom.module.bpm.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bpm Message enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BpmMessageEnum {

    PROCESS_INSTANCE_APPROVE("bpm_process_instance_approve"), // When the process task is approved，Send to applicant
    PROCESS_INSTANCE_REJECT("bpm_process_instance_reject"), // When the process task is not approved，Send to applicant
    TASK_ASSIGNED("bpm_task_assigned"); // When the task is assigned，Send to approver

    /**
     * SMS template identifier
     *
     * Relationship SmsTemplateDO of code Properties
     */
    private final String smsTemplateCode;

}
