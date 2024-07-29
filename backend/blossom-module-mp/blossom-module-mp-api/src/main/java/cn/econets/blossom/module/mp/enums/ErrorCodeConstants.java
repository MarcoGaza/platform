package cn.econets.blossom.module.mp.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Mp Error code enumeration class
 *
 * mp System，Use 1-006-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== Public Account 1-006-000-000 ============
    ErrorCode ACCOUNT_NOT_EXISTS = new ErrorCode(1_006_000_000, "The public account does not exist");
    ErrorCode ACCOUNT_GENERATE_QR_CODE_FAIL = new ErrorCode(1_006_000_001, "Failed to generate the public account QR code，Reason：{}");
    ErrorCode ACCOUNT_CLEAR_QUOTA_FAIL = new ErrorCode(1_006_000_002, "Clear public account API Quota failed，Reason：{}");

    // ========== Public Account Statistics 1-006-001-000 ============
    ErrorCode STATISTICS_GET_USER_SUMMARY_FAIL = new ErrorCode(1_006_001_000, "Failed to obtain the increase and decrease data of fans，Reason：{}");
    ErrorCode STATISTICS_GET_USER_CUMULATE_FAIL = new ErrorCode(1_006_001_001, "Failed to obtain cumulative fan data，Reason：{}");
    ErrorCode STATISTICS_GET_UPSTREAM_MESSAGE_FAIL = new ErrorCode(1_006_001_002, "Failed to obtain message sending profile data，Reason：{}");
    ErrorCode STATISTICS_GET_INTERFACE_SUMMARY_FAIL = new ErrorCode(1_006_001_003, "Failed to obtain interface analysis data，Reason：{}");

    // ========== Public account tag 1-006-002-000 ============
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_006_002_000, "Tag does not exist");
    ErrorCode TAG_CREATE_FAIL = new ErrorCode(1_006_002_001, "Failed to create tag，Reason：{}");
    ErrorCode TAG_UPDATE_FAIL = new ErrorCode(1_006_002_002, "Failed to update label，Reason：{}");
    ErrorCode TAG_DELETE_FAIL = new ErrorCode(1_006_002_003, "Failed to delete tag，Reason：{}");
    ErrorCode TAG_GET_FAIL = new ErrorCode(1_006_002_004, "Failed to get label，Reason：{}");

    // ========== Public Account Fans 1-006-003-000 ============
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_006_003_000, "Fans do not exist");
    ErrorCode USER_UPDATE_TAG_FAIL = new ErrorCode(1_006_003_001, "Failed to update fan tags，Reason：{}");

    // ========== Public account material 1-006-004-000 ============
    ErrorCode MATERIAL_NOT_EXISTS = new ErrorCode(1_006_004_000, "Material does not exist");
    ErrorCode MATERIAL_UPLOAD_FAIL = new ErrorCode(1_006_004_001, "Upload material failed，Reason：{}");
    ErrorCode MATERIAL_IMAGE_UPLOAD_FAIL = new ErrorCode(1_006_004_002, "Failed to upload image，Reason：{}");
    ErrorCode MATERIAL_DELETE_FAIL = new ErrorCode(1_006_004_003, "Failed to delete material，Reason：{}");

    // ========== Public account news 1-006-005-000 ============
    ErrorCode MESSAGE_SEND_FAIL = new ErrorCode(1_006_005_000, "Failed to send message，Reason：{}");

    // ========== Public account publishing capabilities 1-006-006-000 ============
    ErrorCode FREE_PUBLISH_LIST_FAIL = new ErrorCode(1_006_006_000, "Failed to obtain the list of successfully published content，Reason：{}");
    ErrorCode FREE_PUBLISH_SUBMIT_FAIL = new ErrorCode(1_006_006_001, "Submission failed，Reason：{}");
    ErrorCode FREE_PUBLISH_DELETE_FAIL = new ErrorCode(1_006_006_002, "Failed to delete the release，Reason：{}");

    // ========== Draft of Public Account 1-006-007-000 ============
    ErrorCode DRAFT_LIST_FAIL = new ErrorCode(1_006_007_000, "Failed to obtain the draft list，Reason：{}");
    ErrorCode DRAFT_CREATE_FAIL = new ErrorCode(1_006_007_001, "Failed to create draft，Reason：{}");
    ErrorCode DRAFT_UPDATE_FAIL = new ErrorCode(1_006_007_002, "Failed to update draft，Reason：{}");
    ErrorCode DRAFT_DELETE_FAIL = new ErrorCode(1_006_007_003, "Failed to delete draft，Reason：{}");

    // ========== Official Account Menu 1-006-008-000 ============
    ErrorCode MENU_SAVE_FAIL = new ErrorCode(1_006_008_000, "Failed to create menu，Reason：{}");
    ErrorCode MENU_DELETE_FAIL = new ErrorCode(1_006_008_001, "Failed to delete menu，Reason：{}");

    // ========== Automatic reply to public account 1-006-009-000 ============
    ErrorCode AUTO_REPLY_NOT_EXISTS = new ErrorCode(1_006_009_000, "Automatic reply does not exist");
    ErrorCode AUTO_REPLY_ADD_SUBSCRIBE_FAIL_EXISTS = new ErrorCode(1_006_009_001, "Operation failed，Reason：Reply when there is already a follow");
    ErrorCode AUTO_REPLY_ADD_MESSAGE_FAIL_EXISTS = new ErrorCode(1_006_009_002, "Operation failed，Reason：A reply of this message type already exists");
    ErrorCode AUTO_REPLY_ADD_KEYWORD_FAIL_EXISTS = new ErrorCode(1_006_009_003, "Operation failed，Reason：The reply to this keyword has been closed");

}
