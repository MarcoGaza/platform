package cn.econets.blossom.module.system.framework.sms.core.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Error code enumeration of SMS framework
 *
 * SMS Framework，Use 2-001-000-000 Section
 *
 */
public interface SmsFrameworkErrorCodeConstants {

    ErrorCode SMS_UNKNOWN = new ErrorCode(2_001_000_000, "Unknown error，Need to be parsed");

    // ========== Permissions / Current limiting and other related issues 2-001-000-100 ==========

    ErrorCode SMS_PERMISSION_DENY = new ErrorCode(2_001_000_100, "No permission to send SMS messages");
    ErrorCode SMS_IP_DENY = new ErrorCode(2_001_000_100, "IP Not allowed to send SMS");

    // Alibaba Cloud：Limit the SMS sending frequency to within the normal business flow limit range。Default SMS verification code：Use the same signature，Verification code for the same mobile phone number，Support 1 Article / Minutes，5 Article / Hours，Accumulated 10 Article / Sky。
    ErrorCode SMS_SEND_BUSINESS_LIMIT_CONTROL = new ErrorCode(2_001_000_102, "Specify the sending limit of the mobile phone");
    // Alibaba Cloud：The daily SMS sending limit you set in the console has been reached。In domestic message settings > Security Settings，Modify the total sending amount threshold。
    ErrorCode SMS_SEND_DAY_LIMIT_CONTROL = new ErrorCode(2_001_000_103, "Daily sending limit");

    ErrorCode SMS_SEND_CONTENT_INVALID = new ErrorCode(2_001_000_104, "The text message contains sensitive words");

    // Tencent Cloud：To avoid harassing users，Marketing SMS is only allowed in8Click here22Click to send。
    ErrorCode SMS_SEND_MARKET_LIMIT_CONTROL = new ErrorCode(2_001_000_105, "Marketing SMS sending time limit");

    // ========== Template related 2-001-000-200 ==========
    ErrorCode SMS_TEMPLATE_INVALID = new ErrorCode(2_001_000_200, "The SMS template is illegal"); // The SMS template does not exist
    ErrorCode SMS_TEMPLATE_PARAM_ERROR = new ErrorCode(2_001_000_201, "Template parameters are incorrect");

    // ========== Signature related 2-001-000-300 ==========
    ErrorCode SMS_SIGN_INVALID = new ErrorCode(2_001_000_300, "SMS signature is unavailable");

    // ========== Account related 2-001-000-400 ==========
    ErrorCode SMS_ACCOUNT_MONEY_NOT_ENOUGH = new ErrorCode(2_001_000_400, "Insufficient account balance");
    ErrorCode SMS_ACCOUNT_INVALID = new ErrorCode(2_001_000_401, "apiKey Does not exist");

    // ========== Other related 2-001-000-900 Beginning ==========
    ErrorCode SMS_API_PARAM_ERROR = new ErrorCode(2_001_000_900, "Request parameters missing");
    ErrorCode SMS_MOBILE_INVALID = new ErrorCode(2_001_000_901, "Incorrect phone format");
    ErrorCode SMS_MOBILE_BLACK = new ErrorCode(2_001_000_902, "The phone number is in the blacklist");
    ErrorCode SMS_APP_ID_INVALID = new ErrorCode(2_001_000_903, "SdkAppIdIllegal");

    ErrorCode EXCEPTION = new ErrorCode(2_001_000_999, "Call exception");

}
