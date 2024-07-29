package cn.econets.blossom.module.system.enums;


import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * System Error code enumeration class
 *
 * system System，Use 1-002-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== AUTH Module 1-002-000-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_002_000_000, "Login failed，The account password is incorrect");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_002_000_001, "Login failed，Account disabled");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1_002_000_004, "Incorrect verification code，Reason：{}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1_002_000_005, "Account not bound，Need to bind");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1_002_000_006, "Token Expired");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1_002_000_007, "The phone number does not exist");

    // ========== Menu module 1-002-001-000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1_002_001_000, "A menu with this name already exists");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1_002_001_001, "The parent menu does not exist");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1_002_001_002, "Cannot set itself as parent menu");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1_002_001_003, "Menu does not exist");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1_002_001_004, "Submenu exists，Cannot delete");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1_002_001_005, "The parent menu type must be directory or menu");

    // ========== Role Module 1-002-002-000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1_002_002_000, "Role does not exist");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1_002_002_001, "There is already an entity named【{}】Role");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1_002_002_002, "Encoding already exists【{}】Role");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1_002_002_003, "Cannot operate on a role that is built into the system");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1_002_002_004, "Name is【{}】The character has been disabled");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1_002_002_005, "Encoding【{}】Cannot be used");

    // ========== User Module 1-002-003-000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1_002_003_000, "The user account already exists");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1_002_003_001, "The phone number already exists");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1_002_003_002, "The mailbox already exists");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_003_003, "User does not exist");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_002_003_004, "Imported user data cannot be empty！");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1_002_003_005, "User password verification failed");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1_002_003_006, "Name is【{}】The user has been disabled");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1_002_003_008, "Failed to create user，Reason：Exceeded the maximum tenant quota({})！");

    // ========== Department Module 1-002-004-000 ==========
    ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1_002_004_000, "A department with this name already exists");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1_002_004_001,"The parent department does not exist");
    ErrorCode DEPT_NOT_FOUND = new ErrorCode(1_002_004_002, "The current department does not exist");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1_002_004_003, "Sub-department exists，Cannot delete");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1_002_004_004, "Cannot set itself as parent department");
    ErrorCode DEPT_EXISTS_USER = new ErrorCode(1_002_004_005, "There are employees in the department，Cannot delete");
    ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1_002_004_006, "Department({})Not in open state，Selection not allowed");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1_002_004_007, "Cannot set own sub-department as parent department");

    // ========== Position Module 1-002-005-000 ==========
    ErrorCode POST_NOT_FOUND = new ErrorCode(1_002_005_000, "The current position does not exist");
    ErrorCode POST_NOT_ENABLE = new ErrorCode(1_002_005_001, "Position({}) Not in open state，Selection not allowed");
    ErrorCode POST_NAME_DUPLICATE = new ErrorCode(1_002_005_002, "A position with this name already exists");
    ErrorCode POST_CODE_DUPLICATE = new ErrorCode(1_002_005_003, "A position with this ID already exists");

    // ========== Dictionary type 1-002-006-000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1_002_006_001, "The current dictionary type does not exist");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1_002_006_002, "The dictionary type is not enabled，Selection not allowed");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1_002_006_003, "A dictionary type with this name already exists");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1_002_006_004, "A dictionary of this type already exists");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1_002_006_005, "Cannot delete，This dictionary type also has dictionary data");

    // ========== Dictionary data 1-002-007-000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1_002_007_001, "The current dictionary data does not exist");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1_002_007_002, "Dictionary data({})Not in open state，Selection not allowed");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1_002_007_003, "The dictionary data for this value already exists");

    // ========== Notices and Announcements 1-002-008-000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1_002_008_001, "The current notification does not exist");

    // ========== SMS channel 1-002-011-000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1_002_011_000, "SMS channel does not exist");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1_002_011_001, "The SMS channel is not open，Selection not allowed");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1_002_011_002, "Cannot delete，This SMS channel also has SMS templates");

    // ========== SMS template 1-002-012-000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_012_000, "SMS template does not exist");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_012_001, "Encoding already exists【{}】SMS template");
    ErrorCode SMS_TEMPLATE_API_ERROR = new ErrorCode(1_002_012_002, "SMS API Template call failed，The reason is：{}");
    ErrorCode SMS_TEMPLATE_API_AUDIT_CHECKING = new ErrorCode(1_002_012_003, "SMS API Template cannot be used，Reason：Under review");
    ErrorCode SMS_TEMPLATE_API_AUDIT_FAIL = new ErrorCode(1_002_012_004, "SMS API Template cannot be used，Reason：Approval failed，{}");
    ErrorCode SMS_TEMPLATE_API_NOT_FOUND = new ErrorCode(1_002_012_005, "SMS API Template cannot be used，Reason：Template does not exist");

    // ========== Send SMS 1-002-013-000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1_002_013_000, "The phone number does not exist");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_013_001, "Template parameters({})Missing");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_013_002, "SMS template does not exist");

    // ========== SMS verification code 1-002-014-000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1_002_014_000, "Verification code does not exist");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1_002_014_001, "Verification code has expired");
    ErrorCode SMS_CODE_USED = new ErrorCode(1_002_014_002, "Verification code has been used");
    ErrorCode SMS_CODE_NOT_CORRECT = new ErrorCode(1_002_014_003, "Incorrect verification code");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1_002_014_004, "Exceeded the daily number of SMS messages sent");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1_002_014_005, "SMS messages are sent too frequently");
    ErrorCode SMS_CODE_IS_EXISTS = new ErrorCode(1_002_014_006, "The phone number has been used");
    ErrorCode SMS_CODE_IS_UNUSED = new ErrorCode(1_002_014_007, "Verification code not used");

    // ========== Tenant Information 1-002-015-000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1_002_015_000, "Tenant does not exist");
    ErrorCode TENANT_DISABLE = new ErrorCode(1_002_015_001, "Name is【{}】The tenant has been disabled");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1_002_015_002, "Name is【{}】The tenant has expired");
    ErrorCode TENANT_CAN_NOT_UPDATE_SYSTEM = new ErrorCode(1_002_015_003, "System tenants cannot be modified、Delete and other operations！");
    ErrorCode TENANT_NAME_DUPLICATE = new ErrorCode(1_002_015_004, "Name is【{}】The tenant already exists");
    ErrorCode TENANT_WEBSITE_DUPLICATE = new ErrorCode(1_002_015_005, "Domain name is【{}】The tenant already exists");

    // ========== Tenant Package 1-002-016-000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1_002_016_000, "Tenant package does not exist");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1_002_016_001, "Tenant is using this package，Please reset the package for the tenant and try to delete it again");
    ErrorCode TENANT_PACKAGE_DISABLE = new ErrorCode(1_002_016_002, "Name is【{}】The tenant package has been disabled");

    // ========== Error code module 1-002-017-000 ==========
    ErrorCode ERROR_CODE_NOT_EXISTS = new ErrorCode(1_002_017_000, "Error code does not exist");
    ErrorCode ERROR_CODE_DUPLICATE = new ErrorCode(1_002_017_001, "Encoding already exists【{}】Error code");

    // ========== Social user 1-002-018-000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1_002_018_000, "Social authorization failed，The reason is：{}");
    ErrorCode SOCIAL_USER_NOT_FOUND = new ErrorCode(1_002_018_001, "Social authorization failed，Cannot find the corresponding user");

    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_PHONE_CODE_ERROR = new ErrorCode(1_002_018_200, "Failed to obtain mobile phone number");
    ErrorCode SOCIAL_CLIENT_NOT_EXISTS = new ErrorCode(1_002_018_201, "Social client does not exist");
    ErrorCode SOCIAL_CLIENT_UNIQUE = new ErrorCode(1_002_018_201, "The social client already has a configuration");

    // ========== System sensitive words 1-002-019-000 =========
    ErrorCode SENSITIVE_WORD_NOT_EXISTS = new ErrorCode(1_002_019_000, "System sensitive words do not exist in all tags");
    ErrorCode SENSITIVE_WORD_EXISTS = new ErrorCode(1_002_019_001, "System sensitive words already exist in the tag");

    // ========== OAuth2 Client 1-002-020-000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1_002_020_000, "OAuth2 Client does not exist");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1_002_020_001, "OAuth2 Client ID already exists");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1_002_020_002, "OAuth2 Client disabled");
    ErrorCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ErrorCode(1_002_020_003, "This authorization type is not supported");
    ErrorCode OAUTH2_CLIENT_SCOPE_OVER = new ErrorCode(1_002_020_004, "Authorization scope is too large");
    ErrorCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ErrorCode(1_002_020_005, "Invalid redirect_uri: {}");
    ErrorCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ErrorCode(1_002_020_006, "Invalid client_secret: {}");

    // ========== OAuth2 Authorization 1-002-021-000 =========
    ErrorCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ErrorCode(1_002_021_000, "client_id No match");
    ErrorCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ErrorCode(1_002_021_001, "redirect_uri No match");
    ErrorCode OAUTH2_GRANT_STATE_MISMATCH = new ErrorCode(1_002_021_002, "state No match");
    ErrorCode OAUTH2_GRANT_CODE_NOT_EXISTS = new ErrorCode(1_002_021_003, "code Does not exist");

    // ========== OAuth2 Authorization 1-002-022-000 =========
    ErrorCode OAUTH2_CODE_NOT_EXISTS = new ErrorCode(1_002_022_000, "code Does not exist");
    ErrorCode OAUTH2_CODE_EXPIRE = new ErrorCode(1_002_022_001, "code Expired");

    // ========== Email Account 1-002-023-000 ==========
    ErrorCode MAIL_ACCOUNT_NOT_EXISTS = new ErrorCode(1_002_023_000, "The email account does not exist");
    ErrorCode MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS = new ErrorCode(1_002_023_001, "Cannot delete，This email account also has email templates");

    // ========== Mail Template 1-002-024-000 ==========
    ErrorCode MAIL_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_024_000, "The email template does not exist");
    ErrorCode MAIL_TEMPLATE_CODE_EXISTS = new ErrorCode(1_002_024_001, "Mail Template code({}) Already exists");

    // ========== Email sent 1-002-025-000 ==========
    ErrorCode MAIL_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_025_000, "Template parameters({})Missing");
    ErrorCode MAIL_SEND_MAIL_NOT_EXISTS = new ErrorCode(1_002_025_001, "The mailbox does not exist");

    // ========== Internal message template 1-002-026-000 ==========
    ErrorCode NOTIFY_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_026_000, "The internal message template does not exist");
    ErrorCode NOTIFY_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_026_001, "Encoding already exists【{}】Internal message template");

    // ========== Internal message template 1-002-027-000 ==========

    // ========== Send internal message 1-002-028-000 ==========
    ErrorCode NOTIFY_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_028_000, "Template parameters({})Missing");

}
