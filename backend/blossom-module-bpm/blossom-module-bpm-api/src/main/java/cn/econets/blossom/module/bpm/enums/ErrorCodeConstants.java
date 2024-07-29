package cn.econets.blossom.module.bpm.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Bpm Error code enumeration class
 * <p>
 * bpm System，Use 1-009-000-000 Section
 */
public interface ErrorCodeConstants {

    // ==========  General process processing Module 1-009-000-000 ==========
    ErrorCode HIGHLIGHT_IMG_ERROR = new ErrorCode(1_009_000_002, "Abnormality in obtaining the highlighted flowchart");

    // ========== OA Process Module 1-009-001-000 ==========
    ErrorCode OA_LEAVE_NOT_EXISTS = new ErrorCode(1_009_001_001, "Leave application does not exist");
    ErrorCode OA_PM_POST_NOT_EXISTS = new ErrorCode(1_009_001_002, "Project manager position is not set");
    ErrorCode OA_DEPART_PM_POST_NOT_EXISTS = new ErrorCode(1_009_001_009, "The project manager of the department does not exist");
    ErrorCode OA_BM_POST_NOT_EXISTS = new ErrorCode(1_009_001_004, "Department manager position is not set");
    ErrorCode OA_DEPART_BM_POST_NOT_EXISTS = new ErrorCode(1_009_001_005, "The department manager of the department does not exist");
    ErrorCode OA_HR_POST_NOT_EXISTS = new ErrorCode(1_009_001_006, "HRPosition not set");
    ErrorCode OA_DAY_LEAVE_ERROR = new ErrorCode(1_009_001_007, "The number of days of leave required>=1");

    // ========== Process Model 1-009-002-000 ==========
    ErrorCode MODEL_KEY_EXISTS = new ErrorCode(1_009_002_000, "There is already a process with the ID【{}】Process");
    ErrorCode MODEL_NOT_EXISTS = new ErrorCode(1_009_002_001, "The process model does not exist");
    ErrorCode MODEL_KEY_VALID = new ErrorCode(1_009_002_002, "The process identifier format is incorrect，Needs to start with a letter or underscore，followed by any letter、Number、Dash、Underline、Period！");
    ErrorCode MODEL_DEPLOY_FAIL_FORM_NOT_CONFIG = new ErrorCode(1_009_002_003, "Deployment process failed，Reason：Process form is not configured，Please click【Modification process】Button to configure");
    ErrorCode MODEL_DEPLOY_FAIL_TASK_ASSIGN_RULE_NOT_CONFIG = new ErrorCode(1_009_002_004, "Deployment process failed，" +
            "Reason：User tasks({})Allocation rules are not configured，Please click【Modification process】Button to configure");
    ErrorCode MODEL_DEPLOY_FAIL_TASK_INFO_EQUALS = new ErrorCode(1_009_003_005, "Process definition deployment failed，Reason：The information has not changed");

    // ========== Process definition 1-009-003-000 ==========
    ErrorCode PROCESS_DEFINITION_KEY_NOT_MATCH = new ErrorCode(1_009_003_000, "The process definition identifier is expected to be({})，Currently({})，Please modify BPMN Flowchart");
    ErrorCode PROCESS_DEFINITION_NAME_NOT_MATCH = new ErrorCode(1_009_003_001, "The expected name of the process definition is({})，Currently({})，Please modify BPMN Flowchart");
    ErrorCode PROCESS_DEFINITION_NOT_EXISTS = new ErrorCode(1_009_003_002, "Process definition does not exist");
    ErrorCode PROCESS_DEFINITION_IS_SUSPENDED = new ErrorCode(1_009_003_003, "The process definition is in a suspended state");
    ErrorCode PROCESS_DEFINITION_BPMN_MODEL_NOT_EXISTS = new ErrorCode(1_009_003_004, "The process definition model does not exist");

    // ========== Process instance 1-009-004-000 ==========
    ErrorCode PROCESS_INSTANCE_NOT_EXISTS = new ErrorCode(1_009_004_000, "The process instance does not exist");
    ErrorCode PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS = new ErrorCode(1_009_004_001, "Process cancellation failed，The process is not running");
    ErrorCode PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF = new ErrorCode(1_009_004_002, "Process cancellation failed，This process was not initiated by you");

    // ========== Process Task 1-009-005-000 ==========
    ErrorCode TASK_OPERATE_FAIL_ASSIGN_NOT_SELF = new ErrorCode(1_009_005_001, "Operation failed，Reason：The approver of this task is not you");
    ErrorCode TASK_NOT_EXISTS = new ErrorCode(1_009_005_002, "Process task does not exist");
    ErrorCode TASK_IS_PENDING = new ErrorCode(1_009_005_003, "The current task is in suspended state，Cannot operate");
    ErrorCode TASK_TARGET_NODE_NOT_EXISTS = new ErrorCode(1_009_005_004, " The target node does not exist");
    ErrorCode TASK_RETURN_FAIL_SOURCE_TARGET_ERROR = new ErrorCode(1_009_005_006, "Rewind task failed，The target node is on a parallel gateway or not on the same route，Cannot jump");
    ErrorCode TASK_DELEGATE_FAIL_USER_REPEAT = new ErrorCode(1_009_005_007, "Task delegation failed，The delegator and the current approver are the same person");
    ErrorCode TASK_DELEGATE_FAIL_USER_NOT_EXISTS = new ErrorCode(1_009_005_008, "Task delegation failed，The delegate does not exist");
    ErrorCode TASK_ADD_SIGN_USER_NOT_EXIST = new ErrorCode(1_009_005_009, "Task Signature：The selected user does not exist");
    ErrorCode TASK_ADD_SIGN_TYPE_ERROR = new ErrorCode(1_009_005_010, "Task Signature：The current task has already been{}，No{}");
    ErrorCode TASK_ADD_SIGN_USER_REPEAT = new ErrorCode(1_009_005_011, "Task signing failed，Additional signatories and existing approvers[{}]Repeat");
    ErrorCode TASK_SUB_SIGN_NO_PARENT = new ErrorCode(1_009_005_011, "Task reduction failed，The task to be reduced must be the task generated by adding signatures");

    // ========== Process task allocation rules 1-009-006-000 ==========
    ErrorCode TASK_ASSIGN_RULE_EXISTS = new ErrorCode(1_009_006_000, "Process({}) Mission({}) Allocation rules already exist");
    ErrorCode TASK_ASSIGN_RULE_NOT_EXISTS = new ErrorCode(1_009_006_001, "Process task allocation rule does not exist");
    ErrorCode TASK_UPDATE_FAIL_NOT_MODEL = new ErrorCode(1_009_006_002, "Only the task allocation rules of the process model，Only allowed to be modified");
    ErrorCode TASK_CREATE_FAIL_NO_CANDIDATE_USER = new ErrorCode(1_009_006_003, "Operation failed，Reason：Cannot find the task approver！");
    ErrorCode TASK_ASSIGN_SCRIPT_NOT_EXISTS = new ErrorCode(1_009_006_004, "Operation failed，Reason：Task assignment script({}) Does not exist");

    // ========== Dynamic form module 1-009-010-000 ==========
    ErrorCode FORM_NOT_EXISTS = new ErrorCode(1_009_010_000, "Dynamic form does not exist");
    ErrorCode FORM_FIELD_REPEAT = new ErrorCode(1_009_010_001, "Form item({}) Japanese ({}) The same field name is used({})");

    // ========== User Group Module 1-009-011-000 ==========
    ErrorCode USER_GROUP_NOT_EXISTS = new ErrorCode(1_009_011_000, "User group does not exist");
    ErrorCode USER_GROUP_IS_DISABLE = new ErrorCode(1_009_011_001, "Name is【{}】The user group has been disabled");

}
