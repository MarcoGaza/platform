package cn.econets.blossom.framework.security.core.filter;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.security.config.SecurityProperties;
import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.framework.web.core.handler.GlobalExceptionHandler;
import cn.econets.blossom.module.system.api.oauth2.OAuth2TokenApi;
import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token Filter，Verification token Validity
 * After verification，Get {@link LoginUser} Information，and added to Spring Security Context
 *
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    private final GlobalExceptionHandler globalExceptionHandler;

    private final OAuth2TokenApi oauth2TokenApi;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotEmpty(token)) {
            Integer userType = WebFrameworkUtils.getLoginUserType(request);
            try {
                // 1.1 Based on token Build login user
                LoginUser loginUser = buildLoginUserByToken(token, userType);
                // 1.2 Simulation Login Function，Convenient for daily development and debugging
                if (loginUser == null) {
                    loginUser = mockLoginUser(request, token, userType);
                }

                // 2. Set current user
                if (loginUser != null) {
                    SecurityFrameworkUtils.setLoginUser(loginUser, request);
                }
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        }

        // Continue filtering chain
        chain.doFilter(request, response);
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        try {
            OAuth2AccessTokenCheckRespDTO accessToken = oauth2TokenApi.checkAccessToken(token);
            if (accessToken == null) {
                return null;
            }
            // User type does not match，No permission
            // Attention：Only /admin-api/* Japanese /app-api/* Yes userType，Only user type comparison is required
            // Similar WebSocket of /ws/* Connection address，There is no need to compare user types
            if (userType != null
                    && ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
                throw new AccessDeniedException("Wrong user type");
            }
            // Build login user
            LoginUser loginUser = new LoginUser();
            loginUser.setId(accessToken.getUserId());
            loginUser.setUserType(accessToken.getUserType());
            loginUser.setTenantId(accessToken.getTenantId());
            loginUser.setScopes(accessToken.getScopes());
            return loginUser;
        } catch (ServiceException serviceException) {
            // Verification Token When not passed，Considering that some interfaces do not require login，So return directly null That's it
            return null;
        }
    }

    /**
     * Simulate login user，Convenient for daily development and debugging
     *
     * Attention，In an online environment，Be sure to turn off this function！！！
     *
     * @param request Request
     * @param token Simulated token，Format is {@link SecurityProperties#getMockSecret()} + User Number
     * @param userType User Type
     * @return Simulated LoginUser
     */
    private LoginUser mockLoginUser(HttpServletRequest request, String token, Integer userType) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        // Must mockSecret Beginning
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        // Build a simulated user
        Long userId = Long.valueOf(token.substring(securityProperties.getMockSecret().length()));
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUserType(userType);
        loginUser.setTenantId(WebFrameworkUtils.getTenantId(request));
        return loginUser;
    }

}
