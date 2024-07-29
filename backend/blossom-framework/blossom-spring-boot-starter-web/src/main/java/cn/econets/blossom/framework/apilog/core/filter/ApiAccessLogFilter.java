package cn.econets.blossom.framework.apilog.core.filter;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.apilog.core.service.ApiAccessLog;
import cn.econets.blossom.framework.apilog.core.service.ApiAccessLogFrameworkService;
import cn.econets.blossom.framework.web.config.WebProperties;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.framework.web.core.filter.ApiRequestFilter;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * API Access log Filter
 *
 */
@Slf4j
public class ApiAccessLogFilter extends ApiRequestFilter {

    private final String applicationName;

    private final ApiAccessLogFrameworkService apiAccessLogFrameworkService;

    public ApiAccessLogFilter(WebProperties webProperties, String applicationName, ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        super(webProperties);
        this.applicationName = applicationName;
        this.apiAccessLogFrameworkService = apiAccessLogFrameworkService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get start time
        LocalDateTime beginTime = LocalDateTime.now();
        // Get parameters in advance，Avoid XssFilter Filtering
        Map<String, String> queryString = ServletUtils.getParamMap(request);
        String requestBody = ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;

        try {
            // Continue filter
            filterChain.doFilter(request, response);
            // Normal execution，Record log
            createApiAccessLog(request, beginTime, queryString, requestBody, null);
        } catch (Exception ex) {
            // Exception execution，Record log
            createApiAccessLog(request, beginTime, queryString, requestBody, ex);
            throw ex;
        }
    }

    private void createApiAccessLog(HttpServletRequest request, LocalDateTime beginTime,
                                    Map<String, String> queryString, String requestBody, Exception ex) {
        ApiAccessLog accessLog = new ApiAccessLog();
        try {
            this.buildApiAccessLogDTO(accessLog, request, beginTime, queryString, requestBody, ex);
            apiAccessLogFrameworkService.createApiAccessLog(accessLog);
        } catch (Throwable th) {
            log.error("[createApiAccessLog][url({}) log({}) Exception occurred]", request.getRequestURI(), JsonUtils.toJsonString(accessLog), th);
        }
    }

    private void buildApiAccessLogDTO(ApiAccessLog accessLog, HttpServletRequest request, LocalDateTime beginTime,
                                      Map<String, String> queryString, String requestBody, Exception ex) {
        // Processing user information
        accessLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
        accessLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
        // Set access result
        CommonResult<?> result = WebFrameworkUtils.getCommonResult(request);
        if (result != null) {
            accessLog.setResultCode(result.getCode());
            accessLog.setResultMsg(result.getMsg());
        } else if (ex != null) {
            accessLog.setResultCode(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode());
            accessLog.setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
        } else {
            accessLog.setResultCode(0);
            accessLog.setResultMsg("");
        }
        // Set other fields
        accessLog.setTraceId(TracerUtils.getTraceId());
        accessLog.setApplicationName(applicationName);
        accessLog.setRequestUrl(request.getRequestURI());
        Map<String, Object> requestParams = MapUtil.<String, Object>builder().put("query", queryString).put("body", requestBody).build();
        accessLog.setRequestParams(JsonUtils.toJsonString(requestParams));
        accessLog.setRequestMethod(request.getMethod());
        accessLog.setUserAgent(ServletUtils.getUserAgent(request));
        accessLog.setUserIp(ServletUtils.getClientIP(request));
        // Duration
        accessLog.setBeginTime(beginTime);
        accessLog.setEndTime(LocalDateTime.now());
        accessLog.setDuration((int) LocalDateTimeUtil.between(accessLog.getBeginTime(), accessLog.getEndTime(), ChronoUnit.MILLIS));
    }

}
