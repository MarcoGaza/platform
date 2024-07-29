package cn.econets.blossom.framework.web.core.handler;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.apilog.core.filter.ApiAccessLogFilter;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Global response result（ResponseBody）Processor
 *
 * Different from many articles seen on the Internet，Will choose to automatically Controller Return result package {@link CommonResult}，
 * In onemall Medium，Yes Controller On return，Take the initiative to pack it yourself {@link CommonResult}。
 * The reason is，GlobalResponseBodyHandler Essentially AOP，It should not change Controller Returned data structure
 *
 * Currently，GlobalResponseBodyHandler The main function is，Record Controller The returned result，
 * Convenient {@link ApiAccessLogFilter} Record access log
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    @SuppressWarnings("NullableProblems") // Avoid IDEA Warning
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        // Only intercept the returned result CommonResult Type
        return returnType.getMethod().getReturnType() == CommonResult.class;
    }

    @Override
    @SuppressWarnings("NullableProblems") // Avoid IDEA Warning
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // Record Controller Results
        WebFrameworkUtils.setCommonResult(((ServletServerHttpRequest) request).getServletRequest(), (CommonResult<?>) body);
        return body;
    }

}
