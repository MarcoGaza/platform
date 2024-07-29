package cn.econets.blossom.framework.security.core.handler;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Access a page that requires authentication URL Resources，But I have not yet authenticated myself（Login）In the case of，Return {@link GlobalErrorCodeConstants#UNAUTHORIZED} Error code，So that the front end redirects to the login page
 *
 * Supplement：Spring Security Passed {@link ExceptionTranslationFilter#sendStartAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, AuthenticationException)} Method，Call the current class
 *
 */
@Slf4j
@SuppressWarnings("JavadocReference") // Ignore document reference errors
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.debug("[commence][Visit URL({}) Time，Not logged in]", request.getRequestURI(), e);
        // Return 401
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED));
    }

}
