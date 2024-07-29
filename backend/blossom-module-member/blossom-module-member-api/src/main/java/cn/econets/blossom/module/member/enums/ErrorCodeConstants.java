package cn.econets.blossom.module.member.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Member Error code enumeration class
 * <p>
 * member System，Use 1-004-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== User related  1-004-001-000 ============
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_004_001_000, "User does not exist");
    ErrorCode USER_MOBILE_NOT_EXISTS = new ErrorCode(1_004_001_001, "Mobile phone number is not registered user");
    ErrorCode USER_MOBILE_USED = new ErrorCode(1_004_001_002, "Failed to modify the phone number，This phone number({})Already in use");
    ErrorCode USER_POINT_NOT_ENOUGH = new ErrorCode(1_004_001_003, "Insufficient user points balance");

    // ========== AUTH Module 1-004-003-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_004_003_000, "Login failed，The account password is incorrect");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_004_003_001, "Login failed，Account disabled");
    ErrorCode AUTH_SOCIAL_USER_NOT_FOUND = new ErrorCode(1_004_003_005, "Login failed，Unable to parse third-party login information");
    ErrorCode AUTH_MOBILE_USED = new ErrorCode(1_004_003_007, "The phone number has been used");

    // ========== User's mailing address 1-004-004-000 ==========
    ErrorCode ADDRESS_NOT_EXISTS = new ErrorCode(1_004_004_000, "The user's recipient address does not exist");

    //========== User tags 1-004-006-000 ==========
    ErrorCode TAG_NOT_EXISTS = new ErrorCode(1_004_006_000, "User tag does not exist");
    ErrorCode TAG_NAME_EXISTS = new ErrorCode(1_004_006_001, "The user tag already exists");
    ErrorCode TAG_HAS_USER = new ErrorCode(1_004_006_002, "Users exist under the user tag，Cannot delete");

    //========== Points Configuration 1-004-007-000 ==========

    //========== Points record 1-004-008-000 ==========
    ErrorCode POINT_RECORD_BIZ_NOT_SUPPORT = new ErrorCode(1_004_008_000, "User points record business type is not supported");

    //========== Sign-in configuration 1-004-009-000 ==========
    ErrorCode SIGN_IN_CONFIG_NOT_EXISTS = new ErrorCode(1_004_009_000, "Sign-in days rule does not exist");
    ErrorCode SIGN_IN_CONFIG_EXISTS = new ErrorCode(1_004_009_001, "Sign-in days rule already exists");

    //========== Sign-in Configuration 1-004-010-000 ==========
    ErrorCode SIGN_IN_RECORD_TODAY_EXISTS = new ErrorCode(1_004_010_000, "Signed in today，Please do not sign in again");

    //========== User Level 1-004-011-000 ==========
    ErrorCode LEVEL_NOT_EXISTS = new ErrorCode(1_004_011_000, "User level does not exist");
    ErrorCode LEVEL_NAME_EXISTS = new ErrorCode(1_004_011_001, "User level name[{}]Already used");
    ErrorCode LEVEL_VALUE_EXISTS = new ErrorCode(1_004_011_002, "User level value[{}]Has been[{}]Use");
    ErrorCode LEVEL_EXPERIENCE_MIN = new ErrorCode(1_004_011_003, "Upgrade experience must be greater than the previous level[{}]Set the upgrade experience[{}]");
    ErrorCode LEVEL_EXPERIENCE_MAX = new ErrorCode(1_004_011_004, "Upgrade experience must be less than the next level[{}]Settings for upgrade experience[{}]");
    ErrorCode LEVEL_HAS_USER = new ErrorCode(1_004_011_005, "Users exist under this user level，Cannot delete");

    ErrorCode EXPERIENCE_BIZ_NOT_SUPPORT = new ErrorCode(1_004_011_201, "User experience service type is not supported");

    //========== User Grouping 1-004-012-000 ==========
    ErrorCode GROUP_NOT_EXISTS = new ErrorCode(1_004_012_000, "User group does not exist");
    ErrorCode GROUP_HAS_USER = new ErrorCode(1_004_012_001, "Users exist under this user group，Cannot delete");

}
