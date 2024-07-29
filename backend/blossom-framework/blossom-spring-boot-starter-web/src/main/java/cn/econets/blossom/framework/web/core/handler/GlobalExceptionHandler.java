package cn.econets.blossom.framework.web.core.handler;

import cn.econets.blossom.framework.apilog.core.service.ApiErrorLogFrameworkService;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.apilog.core.service.ApiErrorLog;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


/**
 * Global exception handler，Will Exception Translate into CommonResult + Corresponding exception number
 *
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final String applicationName;

    private final ApiErrorLogFrameworkService apiErrorLogFrameworkService;

    /**
     * Handle all exceptions，Mainly provided to Filter Use
     * Because Filter Don't go SpringMVC Process，But we need to handle exceptions as well，So here is a complete exception handling process，Keep the logic consistent。
     *
     * @param request Request
     * @param ex Abnormal
     * @return General return
     */
    public CommonResult<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof ValidationException) {
            return validationException((ValidationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler(request, (NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return defaultExceptionHandler(request, ex);
    }

    /**
     * Processing SpringMVC Request parameters missing
     *
     * For example，Set on the interface @RequestParam("xx") Parameters，Result not delivered xx Parameters
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("Request parameters missing:%s", ex.getParameterName()));
    }

    /**
     * Processing SpringMVC Request parameter type error
     *
     * For example，Set on the interface @RequestParam("xx") Parameters are Integer，Result delivery xx Parameter type is String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("Request parameter type error:%s", ex.getMessage()));
    }

    /**
     * Processing SpringMVC Parameter verification is incorrect
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // Assertion，Avoid warning
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("Incorrect request parameters:%s", fieldError.getDefaultMessage()));
    }

    /**
     * Processing SpringMVC Parameter binding is incorrect，Essentially also through Validator Verification
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindExceptionHandler(BindException ex) {
        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null; // Assertion，Avoid warning
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("Incorrect request parameters:%s", fieldError.getDefaultMessage()));
    }

    /**
     * Processing Validator Exception caused by verification failure
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), String.format("Incorrect request parameters:%s", constraintViolation.getMessage()));
    }

    /**
     * Processing Dubbo Consumer Local parameter verification，Thrown ValidationException Abnormal
     */
    @ExceptionHandler(value = ValidationException.class)
    public CommonResult<?> validationException(ValidationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        // Error message of unable to splice details，Because Dubbo Consumer Throw ValidationException Abnormality，It is direct string information，and not human readable
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST);
    }

    /**
     * Processing SpringMVC The requested address does not exist
     *
     * Attention，It needs to set the following two configuration items：
     * 1. spring.mvc.throw-exception-if-no-handler-found for true
     * 2. spring.mvc.static-path-pattern for /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<?> noHandlerFoundExceptionHandler(HttpServletRequest req, NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return CommonResult.error(GlobalErrorCodeConstants.NOT_FOUND.getCode(), String.format("The requested address does not exist:%s", ex.getRequestURL()));
    }

    /**
     * Processing SpringMVC Incorrect request method
     *
     * For example，A The interface method is GET Method，The result request method is POST Method，results in mismatch
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CommonResult.error(GlobalErrorCodeConstants.METHOD_NOT_ALLOWED.getCode(), String.format("Incorrect request method:%s", ex.getMessage()));
    }

    /**
     * Processing Resilience4j Exception thrown by current limiting
     */
    public CommonResult<?> requestNotPermittedExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.warn("[requestNotPermittedExceptionHandler][url({}) Access too frequently]", req.getRequestURL(), ex);
        return CommonResult.error(GlobalErrorCodeConstants.TOO_MANY_REQUESTS);
    }

    /**
     * Processing Spring Security Insufficient permissions exception
     *
     * The source is，Use @PreAuthorize Annotation，AOP Perform permission interception
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("[accessDeniedExceptionHandler][userId({}) Unable to access url({})]", WebFrameworkUtils.getLoginUserId(req),
                req.getRequestURL(), ex);
        return CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN);
    }

    /**
     * Handling business exceptions ServiceException
     *
     * For example，Insufficient product inventory，The user's phone number already exists。
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceExceptionHandler(ServiceException ex) {
        log.info("[serviceExceptionHandler]", ex);
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * Handling system exceptions，Handle everything at the end
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        // Situation 1：Handle exception when table does not exist
        CommonResult<?> tableNotExistsResult = handleTableNotExists(ex);
        if (tableNotExistsResult != null) {
            return tableNotExistsResult;
        }

        // Case 2：Processing of some special libraries
        if (Objects.equals("io.github.resilience4j.ratelimiter.RequestNotPermitted", ex.getClass().getName())) {
            return requestNotPermittedExceptionHandler(req, ex);
        }

        // Case 3：Handling exceptions
        log.error("[defaultExceptionHandler]", ex);
        // Insert exception log
        this.createExceptionLog(req, ex);
        // Return ERROR CommonResult
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getMsg());
    }

    private void createExceptionLog(HttpServletRequest req, Throwable e) {
        // Insert error log
        ApiErrorLog errorLog = new ApiErrorLog();
        try {
            // Initialization errorLog
            initExceptionLog(errorLog, req, e);
            // Execute insert errorLog
            apiErrorLogFrameworkService.createApiErrorLog(errorLog);
        } catch (Throwable th) {
            log.error("[createExceptionLog][url({}) log({}) Exception occurred]", req.getRequestURI(),  JsonUtils.toJsonString(errorLog), th);
        }
    }

    private void initExceptionLog(ApiErrorLog errorLog, HttpServletRequest request, Throwable e) {
        // Processing user information
        errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
        errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
        // Set exception fields
        errorLog.setExceptionName(e.getClass().getName());
        errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
        errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
        errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        Assert.notEmpty(stackTraceElements, "Abnormal stackTraceElements Cannot be empty");
        StackTraceElement stackTraceElement = stackTraceElements[0];
        errorLog.setExceptionClassName(stackTraceElement.getClassName());
        errorLog.setExceptionFileName(stackTraceElement.getFileName());
        errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
        errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
        // Set other fields
        errorLog.setTraceId(TracerUtils.getTraceId());
        errorLog.setApplicationName(applicationName);
        errorLog.setRequestUrl(request.getRequestURI());
        Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                .put("query", ServletUtils.getParamMap(request))
                .put("body", ServletUtils.getBody(request)).build();
        errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
        errorLog.setRequestMethod(request.getMethod());
        errorLog.setUserAgent(ServletUtils.getUserAgent(request));
        errorLog.setUserIp(ServletUtils.getClientIP(request));
        errorLog.setExceptionTime(LocalDateTime.now());
    }

    /**
     * Processing Table Non-existent abnormality
     *
     * @param ex Abnormal
     * @return If yes Table Non-existent exception，Then return the corresponding CommonResult
     */
    private CommonResult<?> handleTableNotExists(Throwable ex) {
        String message = ExceptionUtil.getRootCauseMessage(ex);
        if (!message.contains("doesn't exist")) {
            return null;
        }
        // 1. Data Report
        if (message.contains("report_")) {
            log.error("[Report Module blossom-module-report - Table structure not imported]");
            return CommonResult.error(GlobalErrorCodeConstants.NOT_IMPLEMENTED.getCode(),
                    "[Report Module blossom-module-report - Table structure not imported]");
        }
        // 2. Workflow
        if (message.contains("bpm_")) {
            log.error("[Workflow Module blossom-module-bpm - Table structure not imported]");
            return CommonResult.error(GlobalErrorCodeConstants.NOT_IMPLEMENTED.getCode(),
                    "[Workflow Module blossom-module-bpm - Table structure not imported]");
        }
        // 3. WeChat public account
        if (message.contains("mp_")) {
            log.error("[WeChat public account blossom-module-mp - Table structure not imported]");
            return CommonResult.error(GlobalErrorCodeConstants.NOT_IMPLEMENTED.getCode(),
                    "[WeChat public account blossom-module-mp - Table structure not imported]");
        }
        // 4. Mall System
        if (StrUtil.containsAny(message, "product_", "promotion_", "trade_")) {
            log.error("[Mall System blossom-module-mall - Disabled]");
            return CommonResult.error(GlobalErrorCodeConstants.NOT_IMPLEMENTED.getCode(),
                    "[Mall System blossom-module-mall - Disabled]");
        }
        // 5. Payment Platform
        if (message.contains("pay_")) {
            log.error("[Payment Module blossom-module-pay - Table structure not imported]");
            return CommonResult.error(GlobalErrorCodeConstants.NOT_IMPLEMENTED.getCode(),
                    "[Payment Module blossom-module-pay - Table structure not imported]");
        }
        return null;
    }

}
