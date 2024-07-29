package cn.econets.blossom.framework.datasource.core.filter;

import com.alibaba.druid.util.Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Druid Bottom Ad Filter
 *
 */
public class DruidAdRemoveFilter extends OncePerRequestFilter {

    /**
     * common.js Path
     */
    private static final String COMMON_JS_ILE_PATH = "support/http/resources/js/common.js";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(request, response);
        // Reset buffer，Response headers will not be reset
        response.resetBuffer();
        // Get common.js
        String text = Utils.readFromResource(COMMON_JS_ILE_PATH);
        // Regular replacement banner, Remove the advertising information at the bottom
        text = text.replaceAll("<a.*?banner\"></a><br/>", "");
        text = text.replaceAll("powered.*?shrek.wang</a>", "");
        response.getWriter().write(text);
    }

}
