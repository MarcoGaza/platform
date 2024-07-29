package cn.econets.blossom.module.crm.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * CRM Error code enumeration class
 * <p>
 * crm System，Use 1-020-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== Contract Management 1-020-000-000 ==========
    ErrorCode CONTRACT_NOT_EXISTS = new ErrorCode(1_020_000_000, "The contract does not exist");
    ErrorCode CONTRACT_UPDATE_FAIL_EDITING_PROHIBITED = new ErrorCode(1_020_000_001, "Failed to update contract，Reason：Editing prohibited");
    ErrorCode CONTRACT_SUBMIT_FAIL_NOT_DRAFT = new ErrorCode(1_020_000_002, "Contract submission review failed，Reason：The contract is not in the unsubmitted state");

    // ========== Lead Management 1-020-001-000 ==========
    ErrorCode CLUE_NOT_EXISTS = new ErrorCode(1_020_001_000, "The clue does not exist");
    ErrorCode CLUE_ANY_CLUE_NOT_EXISTS = new ErrorCode(1_020_001_001, "Clues【{}】Does not exist");
    ErrorCode CLUE_ANY_CLUE_ALREADY_TRANSLATED = new ErrorCode(1_020_001_002, "Clues【{}】Already converted，Please do not repeat conversion");

    // ========== Business Opportunity Management 1-020-002-000 ==========
    ErrorCode BUSINESS_NOT_EXISTS = new ErrorCode(1_020_002_000, "Business opportunity does not exist");
    ErrorCode BUSINESS_CONTRACT_EXISTS = new ErrorCode(1_020_002_001, "Business opportunity has been associated with a contract，Cannot delete");

    // TODO @lilleo：Opportunity Status、Opportunity Type，Each is a separate error code segment


    // ========== Contact Management 1-020-003-000 ==========
    ErrorCode CONTACT_NOT_EXISTS = new ErrorCode(1_020_003_000, "Contact does not exist");
    ErrorCode CONTACT_BUSINESS_LINK_NOT_EXISTS = new ErrorCode(1_020_003_001, "Contact opportunity association does not exist");
    ErrorCode CONTACT_DELETE_FAIL_CONTRACT_LINK_EXISTS = new ErrorCode(1_020_003_002, "Contact has been associated with a contract，Cannot delete");

    // ========== Payback 1-020-004-000 ==========
    ErrorCode RECEIVABLE_NOT_EXISTS = new ErrorCode(1_020_004_000, "Payment does not exist");

    // ========== Contract Management 1-020-005-000 ==========
    ErrorCode RECEIVABLE_PLAN_NOT_EXISTS = new ErrorCode(1_020_005_000, "The payment plan does not exist");

    // ========== Customer Management 1_020_006_000 ==========
    ErrorCode CUSTOMER_NOT_EXISTS = new ErrorCode(1_020_006_000, "The customer does not exist");
    ErrorCode CUSTOMER_OWNER_EXISTS = new ErrorCode(1_020_006_001, "Customer【{}】The person in charge already exists");
    ErrorCode CUSTOMER_LOCKED = new ErrorCode(1_020_006_002, "Customer【{}】Status is locked");
    ErrorCode CUSTOMER_ALREADY_DEAL = new ErrorCode(1_020_006_003, "The customer has traded");
    ErrorCode CUSTOMER_IN_POOL = new ErrorCode(1_020_006_004, "Customer【{}】Failed to put into the high seas，Reason：Already a high seas customer");
    ErrorCode CUSTOMER_LOCKED_PUT_POOL_FAIL = new ErrorCode(1_020_006_005, "Customer【{}】Failed to put into the high seas，Reason：Customer has been locked");
    ErrorCode CUSTOMER_UPDATE_OWNER_USER_FAIL = new ErrorCode(1_020_006_006, "Update Customer【{}】The person in charge failed, Reason：System abnormality");
    ErrorCode CUSTOMER_LOCK_FAIL_IS_LOCK = new ErrorCode(1_020_006_007, "Failed to lock the client，It is already locked");
    ErrorCode CUSTOMER_UNLOCK_FAIL_IS_UNLOCK = new ErrorCode(1_020_006_008, "Unlocking the client failed，It is already unlocked");
    ErrorCode CUSTOMER_LOCK_EXCEED_LIMIT = new ErrorCode(1_020_006_009, "Failed to lock the client，Exceeded the upper limit of the lock rule");
    ErrorCode CUSTOMER_OWNER_EXCEED_LIMIT = new ErrorCode(1_020_006_010, "Operation failed，The number of customers has exceeded the upper limit");
    ErrorCode CUSTOMER_DELETE_FAIL_HAVE_REFERENCE = new ErrorCode(1_020_006_011, "Failed to delete customer，Related{}");
    ErrorCode CUSTOMER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_020_006_012, "Imported customer data cannot be empty！");
    ErrorCode CUSTOMER_CREATE_NAME_NOT_NULL = new ErrorCode(1_020_006_013, "Customer name cannot be empty！");
    ErrorCode CUSTOMER_NAME_EXISTS = new ErrorCode(1_020_006_014, "There is already an entity named【{}】Customer！");

    // ========== Permission Management 1_020_007_000 ==========
    ErrorCode CRM_PERMISSION_NOT_EXISTS = new ErrorCode(1_020_007_000, "Data permission does not exist");
    ErrorCode CRM_PERMISSION_DENIED = new ErrorCode(1_020_007_001, "{}Operation failed，Reason：No permission");
    ErrorCode CRM_PERMISSION_MODEL_NOT_EXISTS = new ErrorCode(1_020_007_002, "{}Does not exist");
    ErrorCode CRM_PERMISSION_MODEL_TRANSFER_FAIL_OWNER_USER_EXISTS = new ErrorCode(1_020_007_003, "{}Operation failed，Reason：The transferee is already the person in charge");
    ErrorCode CRM_PERMISSION_DELETE_FAIL = new ErrorCode(1_020_007_004, "Failed to delete data permissions，Reason：When deleting permissions in batches，Can only belong to the same person bizId Next");
    ErrorCode CRM_PERMISSION_DELETE_FAIL_EXIST_OWNER = new ErrorCode(1_020_007_005, "Failed to delete data permissions，Reason：There is a person in charge");
    ErrorCode CRM_PERMISSION_DELETE_DENIED = new ErrorCode(1_020_007_006, "Failed to delete data permissions，Reason：No permission");
    ErrorCode CRM_PERMISSION_DELETE_SELF_PERMISSION_FAIL_EXIST_OWNER = new ErrorCode(1_020_007_007, "Failed to delete data permissions，Reason：Cannot delete the person in charge");
    ErrorCode CRM_PERMISSION_CREATE_FAIL = new ErrorCode(1_020_007_008, "Failed to create data permissions，Reason：The added user already has permissions");

    // ========== Products 1_020_008_000 ==========
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1_020_008_000, "Product does not exist");
    ErrorCode PRODUCT_NO_EXISTS = new ErrorCode(1_020_008_001, "Product number already exists");

    // ========== Product Category 1_020_009_000 ==========
    ErrorCode PRODUCT_CATEGORY_NOT_EXISTS = new ErrorCode(1_020_009_000, "Product category does not exist");
    ErrorCode PRODUCT_CATEGORY_EXISTS = new ErrorCode(1_020_009_001, "Product category already exists");
    ErrorCode PRODUCT_CATEGORY_USED = new ErrorCode(1_020_009_002, "Product category has associated products");
    ErrorCode PRODUCT_CATEGORY_PARENT_NOT_EXISTS = new ErrorCode(1_020_009_003, "The parent category does not exist");
    ErrorCode PRODUCT_CATEGORY_PARENT_NOT_FIRST_LEVEL = new ErrorCode(1_020_009_004, "The parent category cannot be a secondary category");
    ErrorCode product_CATEGORY_EXISTS_CHILDREN = new ErrorCode(1_020_009_005, "Subcategories exist，Cannot delete");

    // ========== Opportunity Status Type 1_020_010_000 ==========
    ErrorCode BUSINESS_STATUS_TYPE_NOT_EXISTS = new ErrorCode(1_020_010_000, "Opportunity status type does not exist");
    ErrorCode BUSINESS_STATUS_TYPE_NAME_EXISTS = new ErrorCode(1_020_010_001, "Opportunity status type name already exists");

    // ========== Opportunity Status 1_020_011_000 ==========
    ErrorCode BUSINESS_STATUS_NOT_EXISTS = new ErrorCode(1_020_011_000, "Opportunity status does not exist");

    // ========== Customer high seas rules settings 1_020_012_000 ==========
    ErrorCode CUSTOMER_POOL_CONFIG_NOT_EXISTS_OR_DISABLED = new ErrorCode(1_020_012_000, "The client's high seas configuration does not exist or is not enabled");
    ErrorCode CUSTOMER_LIMIT_CONFIG_NOT_EXISTS = new ErrorCode(1_020_012_001, "Customer restriction configuration does not exist");

    // ========== Follow-up records 1_020_013_000 ==========
    ErrorCode FOLLOW_UP_RECORD_NOT_EXISTS = new ErrorCode(1_020_013_000, "Follow-up record does not exist");
    ErrorCode FOLLOW_UP_RECORD_DELETE_DENIED = new ErrorCode(1_020_013_001, "Failed to delete follow-up records，Reason：No permission");

    // ========== To-do messages 1_020_014_000 ==========

}
