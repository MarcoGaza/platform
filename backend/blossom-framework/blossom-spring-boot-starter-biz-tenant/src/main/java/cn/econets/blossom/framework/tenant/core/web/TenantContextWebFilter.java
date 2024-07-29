package cn.econets.blossom.framework.tenant.core.web;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Multi-tenant Context Web Filter
 * Will request Header In tenant-id Analysis，Add to {@link TenantContextHolder} Medium，This is the follow-up DB Wait for operation，You can get the tenant number。
 *
 */
public class TenantContextWebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Settings
        Long tenantId = WebFrameworkUtils.getTenantId(request);
        if (tenantId != null) {
            TenantContextHolder.setTenantId(tenantId);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            // Clean up
            TenantContextHolder.clear();
        }
    }

}
