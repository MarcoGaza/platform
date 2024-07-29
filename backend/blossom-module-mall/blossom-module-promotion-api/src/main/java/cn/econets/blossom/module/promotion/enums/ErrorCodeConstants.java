package cn.econets.blossom.module.promotion.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Promotion Error code enumeration class
 * <p>
 * promotion System，Use 1-013-000-000 Section
 */
public interface ErrorCodeConstants {

    // ========== Promotion related 1-013-001-000 ============
    ErrorCode DISCOUNT_ACTIVITY_NOT_EXISTS = new ErrorCode(1_013_001_000, "Limited time discount event does not exist");
    ErrorCode DISCOUNT_ACTIVITY_SPU_CONFLICTS = new ErrorCode(1_013_001_001, "There are products participating in other limited-time discount activities");
    ErrorCode DISCOUNT_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_001_002, "Limited time discount has been closed，Cannot be modified");
    ErrorCode DISCOUNT_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED = new ErrorCode(1_013_001_003, "Limited time discount event is not closed，Cannot delete");
    ErrorCode DISCOUNT_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_001_004, "Limited time discount has been closed，Cannot be closed repeatedly");

    // ========== Banner Related 1-013-002-000 ============
    ErrorCode BANNER_NOT_EXISTS = new ErrorCode(1_013_002_000, "Banner Does not exist");

    // ========== Coupon Related 1-013-003-000 ============
    ErrorCode COUPON_NO_MATCH_SPU = new ErrorCode(1_013_003_000, "The coupon cannot be used for any product！");
    ErrorCode COUPON_NO_MATCH_MIN_PRICE = new ErrorCode(1_013_003_001, "The amount of the goods being settled that has not been used");

    // ========== Coupon Template 1-013-004-000 ==========
    ErrorCode COUPON_TEMPLATE_NOT_EXISTS = new ErrorCode(1_013_004_000, "Coupon template does not exist");
    ErrorCode COUPON_TEMPLATE_TOTAL_COUNT_TOO_SMALL = new ErrorCode(1_013_004_001, "The quantity issued cannot be less than the quantity received({})");
    ErrorCode COUPON_TEMPLATE_NOT_ENOUGH = new ErrorCode(1_013_004_002, "The current remaining quantity is insufficient to receive");
    ErrorCode COUPON_TEMPLATE_USER_ALREADY_TAKE = new ErrorCode(1_013_004_003, "The user has received this coupon");
    ErrorCode COUPON_TEMPLATE_EXPIRED = new ErrorCode(1_013_004_004, "Coupon has expired");
    ErrorCode COUPON_TEMPLATE_CANNOT_TAKE = new ErrorCode(1_013_004_005, "Incorrect collection method");

    // ========== Coupon 1-013-005-000 ==========
    ErrorCode COUPON_NOT_EXISTS = new ErrorCode(1_013_005_000, "Coupon does not exist");
    ErrorCode COUPON_DELETE_FAIL_USED = new ErrorCode(1_013_005_001, "Coupon recovery failed，The coupon has been used");
    ErrorCode COUPON_STATUS_NOT_UNUSED = new ErrorCode(1_013_005_002, "Coupon is not in the waiting state");
    ErrorCode COUPON_VALID_TIME_NOT_NOW = new ErrorCode(1_013_005_003, "The coupon is not within the usage period");
    ErrorCode COUPON_STATUS_NOT_USED = new ErrorCode(1_013_005_004, "The coupon is not in the used state");

    // ========== Save a lot of money and get a free gift 1-013-006-000 ==========
    ErrorCode REWARD_ACTIVITY_NOT_EXISTS = new ErrorCode(1_013_006_000, "The free gift promotion does not exist");
    ErrorCode REWARD_ACTIVITY_SPU_CONFLICTS = new ErrorCode(1_013_006_001, "There are products participating in other discount and free gift activities");
    ErrorCode REWARD_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_006_002, "The free gift promotion has been closed，Cannot be modified");
    ErrorCode REWARD_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED = new ErrorCode(1_013_006_003, "The free gift promotion has not been closed，Cannot delete");
    ErrorCode REWARD_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_006_004, "The free gift promotion has been closed，Cannot be closed repeatedly");
    ErrorCode REWARD_ACTIVITY_CLOSE_FAIL_STATUS_END = new ErrorCode(1_013_006_005, "The free gift promotion has ended，Cannot be closed");

    // ========== TODO Empty 1-013-007-000 ============

    // ========== Second-sale event 1-013-008-000 ==========
    ErrorCode SECKILL_ACTIVITY_NOT_EXISTS = new ErrorCode(1_013_008_000, "Second kill event does not exist");
    ErrorCode SECKILL_ACTIVITY_SPU_CONFLICTS = new ErrorCode(1_013_008_002, "There are products participating in other flash sales activities，Conflict during flash sale period");
    ErrorCode SECKILL_ACTIVITY_UPDATE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_008_003, "Second sale has been closed，Cannot be modified");
    ErrorCode SECKILL_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END = new ErrorCode(1_013_008_004, "Second sale has not been closed or ended，Cannot delete");
    ErrorCode SECKILL_ACTIVITY_CLOSE_FAIL_STATUS_CLOSED = new ErrorCode(1_013_008_005, "Second sale has been closed，Cannot be closed repeatedly");
    ErrorCode SECKILL_ACTIVITY_UPDATE_STOCK_FAIL = new ErrorCode(1_013_008_006, "Second kill failed，Reason：Insufficient stock for flash sale");
    ErrorCode SECKILL_JOIN_ACTIVITY_TIME_ERROR = new ErrorCode(1_013_008_007, "Second kill failed，Reason：Not within the activity time range");
    ErrorCode SECKILL_JOIN_ACTIVITY_STATUS_CLOSED = new ErrorCode(1_013_008_008, "Second kill failed，Reason：Second sale has been closed");
    ErrorCode SECKILL_JOIN_ACTIVITY_SINGLE_LIMIT_COUNT_EXCEED = new ErrorCode(1_013_008_009, "Second kill failed，Reason：Single purchase limit exceeded");
    ErrorCode SECKILL_JOIN_ACTIVITY_PRODUCT_NOT_EXISTS = new ErrorCode(1_013_008_010, "Second kill failed，Reason：The product does not exist");

    // ========== Second sale period 1-013-009-000 ==========
    ErrorCode SECKILL_CONFIG_NOT_EXISTS = new ErrorCode(1_013_009_000, "Second sale period does not exist");
    ErrorCode SECKILL_CONFIG_TIME_CONFLICTS = new ErrorCode(1_013_009_001, "Conflict during flash sale period");
    ErrorCode SECKILL_CONFIG_DISABLE = new ErrorCode(1_013_009_004, "Second sale period is closed");

    // ========== Group buying activity 1-013-010-000 ==========
    ErrorCode COMBINATION_ACTIVITY_NOT_EXISTS = new ErrorCode(1_013_010_000, "Group buying activity does not exist");
    ErrorCode COMBINATION_ACTIVITY_SPU_CONFLICTS = new ErrorCode(1_013_010_001, "The product has participated in other group buying activities");
    ErrorCode COMBINATION_ACTIVITY_STATUS_DISABLE_NOT_UPDATE = new ErrorCode(1_013_010_002, "The group buying activity has been closed and cannot be modified");
    ErrorCode COMBINATION_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END = new ErrorCode(1_013_010_003, "The group buying activity has not been closed or ended，Cannot delete");
    ErrorCode COMBINATION_ACTIVITY_STATUS_DISABLE = new ErrorCode(1_013_010_004, "Group buying failed，Reason：Group buying activity has been closed");
    ErrorCode COMBINATION_JOIN_ACTIVITY_PRODUCT_NOT_EXISTS = new ErrorCode(1_013_010_005, "Group buying failed，Reason：The group purchase product does not exist");
    ErrorCode COMBINATION_ACTIVITY_UPDATE_STOCK_FAIL = new ErrorCode(1_013_010_006, "Group buying failed，Reason：Insufficient inventory for group buying activities");

    // ========== Group buying record 1-013-011-000 ==========
    ErrorCode COMBINATION_RECORD_NOT_EXISTS = new ErrorCode(1_013_011_000, "Group buying does not exist");
    ErrorCode COMBINATION_RECORD_EXISTS = new ErrorCode(1_013_011_001, "Group buying failed，Has participated in this group buy");
    ErrorCode COMBINATION_RECORD_HEAD_NOT_EXISTS = new ErrorCode(1_013_011_002, "Group buying failed，The parent group does not exist");
    ErrorCode COMBINATION_RECORD_USER_FULL = new ErrorCode(1_013_011_003, "Group buying failed，The group is full");
    ErrorCode COMBINATION_RECORD_FAILED_HAVE_JOINED = new ErrorCode(1_013_011_004, "Group buying failed，Reason：There is a record of group buying for this activity in progress");
    ErrorCode COMBINATION_RECORD_FAILED_TIME_NOT_START = new ErrorCode(1_013_011_005, "Group buying failed，The activity has not started");
    ErrorCode COMBINATION_RECORD_FAILED_TIME_END = new ErrorCode(1_013_011_006, "Group buying failed，The event has ended");
    ErrorCode COMBINATION_RECORD_FAILED_SINGLE_LIMIT_COUNT_EXCEED = new ErrorCode(1_013_011_007, "Group buying failed，Reason：Single purchase limit exceeded");
    ErrorCode COMBINATION_RECORD_FAILED_TOTAL_LIMIT_COUNT_EXCEED = new ErrorCode(1_013_011_008, "Group buying failed，Reason：Total number of purchases exceeded");
    ErrorCode COMBINATION_RECORD_FAILED_ORDER_STATUS_UNPAID = new ErrorCode(1_013_011_009, "Group buying failed，Reason：There are unpaid orders，Please pay first");

    // ========== Bargaining activity 1-013-012-000 ==========
    ErrorCode BARGAIN_ACTIVITY_NOT_EXISTS = new ErrorCode(1_013_012_000, "Bargaining activity does not exist");
    ErrorCode BARGAIN_ACTIVITY_SPU_CONFLICTS = new ErrorCode(1_013_012_001, "There are products that have participated in other bargaining activities");
    ErrorCode BARGAIN_ACTIVITY_STATUS_DISABLE = new ErrorCode(1_013_012_002, "Bargaining activity has been closed，Cannot be modified");
    ErrorCode BARGAIN_ACTIVITY_DELETE_FAIL_STATUS_NOT_CLOSED_OR_END = new ErrorCode(1_013_012_003, "The bargaining activity has not been closed or has not ended，Cannot delete");
    ErrorCode BARGAIN_ACTIVITY_STOCK_NOT_ENOUGH = new ErrorCode(1_013_012_004, "Insufficient stock for bargaining activity");
    ErrorCode BARGAIN_ACTIVITY_STATUS_CLOSED = new ErrorCode(1_013_012_005, "Bargaining activity has been closed");
    ErrorCode BARGAIN_ACTIVITY_TIME_END = new ErrorCode(1_013_012_006, "The bargaining activity has ended");

    // ========== Bargaining Record 1-013-013-000 ==========
    ErrorCode BARGAIN_RECORD_NOT_EXISTS = new ErrorCode(1_013_013_000, "Bargaining record does not exist");
    ErrorCode BARGAIN_RECORD_CREATE_FAIL_EXISTS = new ErrorCode(1_013_013_001, "Participation failed，You have participated in the current bargaining activity");
    ErrorCode BARGAIN_RECORD_CREATE_FAIL_LIMIT = new ErrorCode(1_013_013_002, "Participation failed，You have reached the participation limit of the current bargaining activity");
    ErrorCode BARGAIN_JOIN_RECORD_NOT_SUCCESS = new ErrorCode(1_013_013_004, "Order failed，Bargaining failed");
    ErrorCode BARGAIN_JOIN_RECORD_ALREADY_ORDER = new ErrorCode(1_013_013_005, "Order failed，The order has been placed");

    // ========== Bargaining assistance 1-013-014-000 ==========
    ErrorCode BARGAIN_HELP_CREATE_FAIL_RECORD_NOT_IN_PROCESS = new ErrorCode(1_013_014_000, "Assistance failed，Bargaining record is not in progress");
    ErrorCode BARGAIN_HELP_CREATE_FAIL_RECORD_SELF = new ErrorCode(1_013_014_001, "Assistance failed，Can't help myself");
    ErrorCode BARGAIN_HELP_CREATE_FAIL_LIMIT = new ErrorCode(1_013_014_002, "Assistance failed，You have reached the upper limit of the current bargaining activity");
    ErrorCode BARGAIN_HELP_CREATE_FAIL_CONFLICT = new ErrorCode(1_013_014_003, "Assistance failed，Please try again");
    ErrorCode BARGAIN_HELP_CREATE_FAIL_HELP_EXISTS = new ErrorCode(1_013_014_004, "Assistance failed，You have already helped");

    // ========== Article Category 1-013-015-000 ==========
    ErrorCode ARTICLE_CATEGORY_NOT_EXISTS = new ErrorCode(1_013_015_000, "The article category does not exist");
    ErrorCode ARTICLE_CATEGORY_DELETE_FAIL_HAVE_ARTICLES = new ErrorCode(1_013_015_001, "Article category deletion failed，There are related articles");

    // ========== Article Management 1-013-016-000 ==========
    ErrorCode ARTICLE_NOT_EXISTS = new ErrorCode(1_013_016_000, "The article does not exist");

    // ========== Decoration template 1-013-017-000 ==========
    ErrorCode DIY_TEMPLATE_NOT_EXISTS = new ErrorCode(1_013_017_000, "Decoration template does not exist");
    ErrorCode DIY_TEMPLATE_NAME_USED = new ErrorCode(1_013_017_001, "Decoration template name({})Already in use");
    ErrorCode DIY_TEMPLATE_USED_CANNOT_DELETE = new ErrorCode(1_013_017_002, "Cannot delete the decoration template in use");

    // ========== Decoration page 1-013-018-000 ==========
    ErrorCode DIY_PAGE_NOT_EXISTS = new ErrorCode(1_013_018_000, "The decoration page does not exist");
    ErrorCode DIY_PAGE_NAME_USED = new ErrorCode(1_013_018_001, "Decoration page name({})Already in use");

}
