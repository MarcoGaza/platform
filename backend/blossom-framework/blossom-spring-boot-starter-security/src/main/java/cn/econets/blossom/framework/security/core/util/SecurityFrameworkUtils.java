package cn.econets.blossom.framework.security.core.util;

import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * Security Service Tools
 *
 */
public class SecurityFrameworkUtils {

    /**
     * HEADER Authentication header value Prefix
     */
    public static final String AUTHORIZATION_BEARER = "Bearer";

    private SecurityFrameworkUtils() {}

    /**
     * From the request，Get certification Token
     *
     * @param request Request
     * @param headerName Authentication Token Corresponding Header Name
     * @param parameterName Authentication Token Corresponding Parameter Name
     * @return Authentication Token
     */
    public static String obtainAuthorization(HttpServletRequest request,
                                             String headerName, String parameterName) {
        // 1. Get Token。Priority：Header > Parameter
        String token = request.getHeader(headerName);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(parameterName);
        }
        if (!StringUtils.hasText(token)) {
            return null;
        }
        // 2. Remove Token middle belt Bearer
        int index = token.indexOf(AUTHORIZATION_BEARER + " ");
        return index >= 0 ? token.substring(index + 7).trim() : token;
    }

    /**
     * Get current authentication information
     *
     * @return Authentication information
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    /**
     * Get the current user
     *
     * @return Current user
     */
    @Nullable
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof LoginUser ? (LoginUser) authentication.getPrincipal() : null;
    }

    /**
     * Get the current user's ID，From the context
     *
     * @return User Number
     */
    @Nullable
    public static Long getLoginUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser != null ? loginUser.getId() : null;
    }

    /**
     * Set current user
     *
     * @param loginUser Login user
     * @param request Request
     */
    public static void setLoginUser(LoginUser loginUser, HttpServletRequest request) {
        // Create Authentication，and set to context
        Authentication authentication = buildAuthentication(loginUser, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Additional settings to request Medium，Used for ApiAccessLogFilter You can get the user number；
        // The reason is，Spring Security of Filter In ApiAccessLogFilter Behind，When it records access logs，The online context no longer has information such as user ID
        WebFrameworkUtils.setLoginUserId(request, loginUser.getId());
        WebFrameworkUtils.setLoginUserType(request, loginUser.getUserType());
    }

    private static Authentication buildAuthentication(LoginUser loginUser, HttpServletRequest request) {
        // Create UsernamePasswordAuthenticationToken Object
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

}
