package cn.econets.blossom.framework.monitor.core.filter;

import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Trace Filter，Print traceId To header Return in
 *
 */
public class TraceFilter extends OncePerRequestFilter {

    /**
     * Header Name - Link tracking number
     */
    private static final String HEADER_NAME_TRACE_ID = "trace-id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Set response traceId
        response.addHeader(HEADER_NAME_TRACE_ID, TracerUtils.getTraceId());
        // Continue filtering
        chain.doFilter(request, response);
    }

}
