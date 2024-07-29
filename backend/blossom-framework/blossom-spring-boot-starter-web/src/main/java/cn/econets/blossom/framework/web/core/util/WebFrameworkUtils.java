package cn.econets.blossom.framework.web.core.util;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.web.config.WebProperties;
import cn.hutool.core.util.NumberUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Exclusively for web Package tool class
 */
public class WebFrameworkUtils {

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    public static final String HEADER_TENANT_ID = "tenant-id";

    /**
     * Terminal Header
     *
     * @see TerminalEnum
     */
    public static final String HEADER_TERMINAL = "terminal";

    private static WebProperties webProperties;


    public WebFrameworkUtils(WebProperties properties) {
        WebFrameworkUtils.webProperties = properties;
    }


    /**
     * Get tenant number，From header Medium
     * Taking other into consideration framework The components will also use the tenant ID，So I have to put it in WebFrameworkUtils Uniform provision
     *
     * @param request Request
     * @return Tenant Number
     */
    public static Long getTenantId(HttpServletRequest request) {
        String tenantId = request.getHeader(HEADER_TENANT_ID);
        return NumberUtil.isNumber(tenantId) ? Long.valueOf(tenantId) : null;
    }

    public static void setLoginUserId(ServletRequest request, Long userId) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, userId);
    }

    /**
     * Set user type
     *
     * @param request Request
     * @param userType User Type
     */
    public static void setLoginUserType(ServletRequest request, Integer userType) {
        request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE, userType);
    }

    public static Integer getTerminal() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return TerminalEnum.UNKNOWN.getTerminal();
        }
        String terminalValue = request.getHeader(HEADER_TERMINAL);
        return NumberUtil.parseInt(terminalValue, TerminalEnum.UNKNOWN.getTerminal());
    }

    /**
     * Get the current user's ID，From the request
     * Attention：This method is limited to framework Framework usage！！！
     *
     * @param request Request
     * @return User Number
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
    }

    /**
     * Get the type of the current user
     * Attention：This method is limited to web Related framework Component usage！！！
     *
     * @param request Request
     * @return User Number
     */
    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 1. Priority，From Attribute Get
        Integer userType = (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
        if (userType != null) {
            return userType;
        }
        // 2. Secondly，Based on URL Prefix convention
        if (request.getServletPath().startsWith(webProperties.getAdminApi().getPrefix())) {
            return UserTypeEnum.ADMIN.getValue();
        }
        if (request.getServletPath().startsWith(webProperties.getAppApi().getPrefix())) {
            return UserTypeEnum.MEMBER.getValue();
        }
        return null;
    }

    public static Integer getLoginUserType() {
        HttpServletRequest request = getRequest();
        return getLoginUserType(request);
    }

    public static Long getLoginUserId() {
        HttpServletRequest request = getRequest();
        return getLoginUserId(request);
    }

    public static void setCommonResult(ServletRequest request, CommonResult<?> result) {
        request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
    }

    public static CommonResult<?> getCommonResult(ServletRequest request) {
        return (CommonResult<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

}
