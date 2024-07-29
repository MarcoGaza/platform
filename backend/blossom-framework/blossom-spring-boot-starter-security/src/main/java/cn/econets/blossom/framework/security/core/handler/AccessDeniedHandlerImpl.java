package cn.econets.blossom.framework.security.core.handler;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Access a page that requires authentication URL Resources，Already authenticated（Login）But without permission，Return {@link GlobalErrorCodeConstants#FORBIDDEN} Error code。
 *
 * Supplement：Spring Security Passed {@link ExceptionTranslationFilter#handleAccessDeniedException(HttpServletRequest, HttpServletResponse, FilterChain, AccessDeniedException)} Method，Call the current class
 *
 */
@Slf4j
@SuppressWarnings("JavadocReference")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        // Print warn The reason is，Irregular merger warn，See if there is any malicious damage
        log.warn("[commence][Visit URL({}) Time，User({}) Insufficient permissions]", request.getRequestURI(),
                SecurityFrameworkUtils.getLoginUserId(), e);
        // Return 403
        ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN));
    }

}
