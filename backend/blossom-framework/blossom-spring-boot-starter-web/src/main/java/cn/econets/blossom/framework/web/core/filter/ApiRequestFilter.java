package cn.econets.blossom.framework.web.core.filter;

import cn.econets.blossom.framework.web.config.WebProperties;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * Filter /admin-api、/app-api Wait API Requested filter
 *
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {

    protected final WebProperties webProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Filter only API Requested address
        return !StrUtil.startWithAny(request.getRequestURI(), webProperties.getAdminApi().getPrefix(),
                webProperties.getAppApi().getPrefix());
    }

}
