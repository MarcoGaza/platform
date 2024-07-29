package cn.econets.blossom.framework.tenant.core.security;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.framework.tenant.config.TenantProperties;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.tenant.core.service.TenantFrameworkService;
import cn.econets.blossom.framework.web.config.WebProperties;
import cn.econets.blossom.framework.web.core.filter.ApiRequestFilter;
import cn.econets.blossom.framework.web.core.handler.GlobalExceptionHandler;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Multi-tenant Security Web Filter
 * 1. If it is a logged in user，Check whether you have permission to access the tenant，Avoid exceeding authority issues。
 * 2. If the request does not include the tenant ID，Check if it is ignored URL，Otherwise, access is not allowed。
 * 3. Verify that the tenant is legal，For example, it is disabled、Expired
 *
 */
@Slf4j
public class TenantSecurityWebFilter extends ApiRequestFilter {

    private final TenantProperties tenantProperties;

    private final AntPathMatcher pathMatcher;

    private final GlobalExceptionHandler globalExceptionHandler;
    private final TenantFrameworkService tenantFrameworkService;

    public TenantSecurityWebFilter(TenantProperties tenantProperties,
                                   WebProperties webProperties,
                                   GlobalExceptionHandler globalExceptionHandler,
                                   TenantFrameworkService tenantFrameworkService) {
        super(webProperties);
        this.tenantProperties = tenantProperties;
        this.pathMatcher = new AntPathMatcher();
        this.globalExceptionHandler = globalExceptionHandler;
        this.tenantFrameworkService = tenantFrameworkService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Long tenantId = TenantContextHolder.getTenantId();
        // 1. Logged in user，Check whether you have permission to access the tenant，Avoid exceeding authority issues。
        LoginUser user = SecurityFrameworkUtils.getLoginUser();
        if (user != null) {
            // If the tenant number cannot be obtained，Then try to use the tenant ID of the logged-in user
            if (tenantId == null) {
                tenantId = user.getTenantId();
                TenantContextHolder.setTenantId(tenantId);
            // If a tenant ID is passed，Compare tenant numbers，Avoid exceeding authority issues
            } else if (!Objects.equals(user.getTenantId(), TenantContextHolder.getTenantId())) {
                log.error("[doFilterInternal][Tenant({}) User({}/{}) Unauthorized access to tenants({}) URL({}/{})]",
                        user.getTenantId(), user.getId(), user.getUserType(),
                        TenantContextHolder.getTenantId(), request.getRequestURI(), request.getMethod());
                ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN.getCode(),
                        "You do not have permission to access this tenant's data"));
                return;
            }
        }

        // If it is not allowed to ignore the tenant URL，Verify whether the tenant is legal
        if (!isIgnoreUrl(request)) {
            // 2. If the request does not include the tenant ID，Access not allowed。
            if (tenantId == null) {
                log.error("[doFilterInternal][URL({}/{}) Tenant ID not passed]", request.getRequestURI(), request.getMethod());
                ServletUtils.writeJSON(response, CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(),
                        "The requested tenant ID was not passed，Please conduct investigation"));
                return;
            }
            // 3. Verify that the tenant is legal，For example, it is disabled、Expired
            try {
                tenantFrameworkService.validTenant(tenantId);
            } catch (Throwable ex) {
                CommonResult<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                ServletUtils.writeJSON(response, result);
                return;
            }
        } else { // If ignoring tenants is allowed URL，If tenant ID is not passed，The tenant number is ignored by default，Avoid errors
            if (tenantId == null) {
                TenantContextHolder.setIgnore(true);
            }
        }

        // Continue filtering
        chain.doFilter(request, response);
    }

    private boolean isIgnoreUrl(HttpServletRequest request) {
        // Quick Match，Guaranteed performance
        if (CollUtil.contains(tenantProperties.getIgnoreUrls(), request.getRequestURI())) {
            return true;
        }
        // One by one Ant Path matching
        for (String url : tenantProperties.getIgnoreUrls()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

}
