package cn.econets.blossom.framework.common.exception.enums;

import cn.econets.blossom.framework.common.exception.ErrorCode;

/**
 * Global error code enumeration
 * 200-999 System exception code reserved
 *
 * Under normal circumstances，Use HTTP Response status code https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Status
 * Although，HTTP Response status code is weak in expressing business needs，But it is still very good to use it at the system level
 *
 */
public interface GlobalErrorCodeConstants {

    ErrorCode SUCCESS = new ErrorCode(200, "Success");

    // ========== Client error segment ==========

    ErrorCode BAD_REQUEST = new ErrorCode(400, "Incorrect request parameters");
    ErrorCode UNAUTHORIZED = new ErrorCode(401, "Account not logged in");
    ErrorCode FORBIDDEN = new ErrorCode(403, "No permission for this operation");
    ErrorCode NOT_FOUND = new ErrorCode(404, "Request not found");
    ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "Incorrect request method");
    ErrorCode LOCKED = new ErrorCode(423, "Request failed，Please try again later"); // Concurrent requests，Not allowed
    ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "Too frequent requests，Please try again later");

    // ========== Server error segment ==========

    ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "System abnormality");
    ErrorCode NOT_IMPLEMENTED = new ErrorCode(501, "Function not implemented/Not enabled");
    ErrorCode ERROR_CONFIGURATION = new ErrorCode(502, "Wrong configuration item");

    // ========== Custom error segment ==========
    ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "Repeat request，Please try again later"); // Repeat request
    ErrorCode DEMO_DENY = new ErrorCode(901, "Demo mode，Write operation prohibited");

    ErrorCode UNKNOWN = new ErrorCode(999, "Unknown error");

}
