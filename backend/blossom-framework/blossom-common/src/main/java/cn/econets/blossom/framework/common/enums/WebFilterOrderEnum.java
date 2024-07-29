package cn.econets.blossom.framework.common.enums;

/**
 * Web Filter order enumeration class，Ensure that the filter works as expected
 *
 *  Taking into account each starter This tool class is required，So put it in common Under the module enums Book now
 *
 */
public interface WebFilterOrderEnum {

    int CORS_FILTER = Integer.MIN_VALUE;

    int TRACE_FILTER = CORS_FILTER + 1;

    int REQUEST_BODY_CACHE_FILTER = Integer.MIN_VALUE + 500;

    // OrderedRequestContextFilter Default is -105，Used in internationalization contexts, etc.

    int TENANT_CONTEXT_FILTER = - 104; // Need to ensure ApiAccessLogFilter Front

    int API_ACCESS_LOG_FILTER = -103; // Need to ensure RequestBodyCacheFilter Behind

    int XSS_FILTER = -102;  // Need to ensure RequestBodyCacheFilter Behind

    // Spring Security Filter Default is -100，Visible org.springframework.boot.autoconfigure.security.SecurityProperties Configuration property class

    int TENANT_SECURITY_FILTER = -99; // Need to ensure Spring Security Behind the filter

    int FLOWABLE_FILTER = -98; // Need to ensure Spring Security After filtering

    int DEMO_FILTER = Integer.MAX_VALUE;

}
