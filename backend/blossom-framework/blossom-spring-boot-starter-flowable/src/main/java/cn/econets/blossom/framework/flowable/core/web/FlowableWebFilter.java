package cn.econets.blossom.framework.flowable.core.web;

import cn.econets.blossom.framework.flowable.core.util.FlowableUtils;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * flowable Web Filter，Will userId Set to {@link org.flowable.common.engine.impl.identity.Authentication} Medium
 *
 */
public class FlowableWebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            // Set the user of the workflow
            Long userId = SecurityFrameworkUtils.getLoginUserId();
            if (userId != null) {
                FlowableUtils.setAuthenticatedUserId(userId);
            }
            // Filter
            chain.doFilter(request, response);
        } finally {
            // Clean up
            FlowableUtils.clearAuthenticatedUserId();
        }
    }
}
