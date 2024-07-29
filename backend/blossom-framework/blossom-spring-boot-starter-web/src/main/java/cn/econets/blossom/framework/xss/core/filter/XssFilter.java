package cn.econets.blossom.framework.xss.core.filter;

import cn.econets.blossom.framework.xss.config.XssProperties;
import cn.econets.blossom.framework.xss.core.clean.XssCleaner;
import lombok.AllArgsConstructor;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Xss Filter
 *
 */
@AllArgsConstructor
public class XssFilter extends OncePerRequestFilter {

    /**
     * Properties
     */
    private final XssProperties properties;
    /**
     * Path Matcher
     */
    private final PathMatcher pathMatcher;

    private final XssCleaner xssCleaner;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(new XssRequestWrapper(request, xssCleaner), response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // If closed，Do not filter
        if (!properties.isEnable()) {
            return true;
        }

        // If matched, no filtering is required，Do not filter
        String uri = request.getRequestURI();
        return properties.getExcludeUrls().stream().anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, uri));
    }

}
