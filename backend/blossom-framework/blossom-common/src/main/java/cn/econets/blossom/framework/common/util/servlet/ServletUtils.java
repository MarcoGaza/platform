package cn.econets.blossom.framework.common.util.servlet;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Client Tools
 *
 */
public class ServletUtils {

    /**
     * Return JSON String
     *
     * @param response Response
     * @param object   Object，Will be serialized into JSON String
     */
    @SuppressWarnings("deprecation") // Must use APPLICATION_JSON_UTF8_VALUE，Otherwise the characters will be garbled
    public static void writeJSON(HttpServletResponse response, Object object) {
        String content = JsonUtils.toJsonString(object);
        ServletUtil.write(response, content, MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * Return attachment
     *
     * @param response Response
     * @param filename File name
     * @param content  Attachment content
     */
    public static void writeAttachment(HttpServletResponse response, String filename, byte[] content) throws IOException {
        // Settings header Japanese contentType
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        // Output attachment
        IoUtil.write(response.getOutputStream(), false, content);
    }

    /**
     * @param request Request
     * @return ua
     */
    public static String getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return ua != null ? ua : "";
    }

    /**
     * Get request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static String getUserAgent() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return getUserAgent(request);
    }

    public static String getClientIP() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return ServletUtil.getClientIP(request);
    }

    // TODO terminal Still from ServletUtils Rina，Easier to govern globally；

    public static boolean isJsonRequest(ServletRequest request) {
        return StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE);
    }

    public static String getBody(HttpServletRequest request) {
        return ServletUtil.getBody(request);
    }

    public static byte[] getBodyBytes(HttpServletRequest request) {
        return ServletUtil.getBodyBytes(request);
    }

    public static String getClientIP(HttpServletRequest request) {
        return ServletUtil.getClientIP(request);
    }

    public static Map<String, String> getParamMap(HttpServletRequest request) {
        return ServletUtil.getParamMap(request);
    }

}
