package cn.econets.blossom.framework.operatelog.core.aop;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.framework.operatelog.core.service.OperateLog;
import cn.econets.blossom.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.SUCCESS;

/**
 * Intercept usage @OperateLog Annotation，If the conditions are met，Generate operation log。
 * Meet any of the following conditions，Will be recorded：
 * 1. Use @ApiOperation + Non @GetMapping
 * 2. Use @OperateLog Annotation
 * <p>
 * But，If statement @OperateLog Annotation，Will enable The property is set to false Time，Force not to record。
 *
 */
@Aspect
@Slf4j
public class OperateLogAspect {

    /**
     * Context used to record operation content
     *
     * @see OperateLog#getContent()
     */
    private static final ThreadLocal<String> CONTENT = new ThreadLocal<>();
    /**
     * Context used to record extended fields
     *
     * @see OperateLog#getExts()
     */
    private static final ThreadLocal<Map<String, Object>> EXTS = new ThreadLocal<>();

    @Resource
    private OperateLogFrameworkService operateLogFrameworkService;

    @Around("@annotation(operation)")
    public Object around(ProceedingJoinPoint joinPoint, Operation operation) throws Throwable {
        // May also be added @ApiOperation Annotation
        cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog = getMethodAnnotation(joinPoint,
                cn.econets.blossom.framework.operatelog.core.annotations.OperateLog.class);
        return around0(joinPoint, operateLog, operation);
    }

    @Around("!@annotation(io.swagger.v3.oas.annotations.Operation) && @annotation(operateLog)")
    // Compatible processing，Add only @OperateLog Annotation situation
    public Object around(ProceedingJoinPoint joinPoint,
                         cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog) throws Throwable {
        return around0(joinPoint, operateLog, null);
    }

    private Object around0(ProceedingJoinPoint joinPoint,
                           cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog,
                           Operation operation) throws Throwable {
        // Currently，Only administrators，Only then record the operation log！So non-administrator，Direct call，Do not record
        Integer userType = WebFrameworkUtils.getLoginUserType();
        if (!Objects.equals(userType, UserTypeEnum.ADMIN.getValue())) {
            return joinPoint.proceed();
        }

        // Record start time
        LocalDateTime startTime = LocalDateTime.now();
        try {
            // Execute the original method
            Object result = joinPoint.proceed();
            // Record the operation log during normal execution
            this.log(joinPoint, operateLog, operation, startTime, result, null);
            return result;
        } catch (Throwable exception) {
            this.log(joinPoint, operateLog, operation, startTime, null, exception);
            throw exception;
        } finally {
            clearThreadLocal();
        }
    }

    public static void setContent(String content) {
        CONTENT.set(content);
    }

    public static void addExt(String key, Object value) {
        if (EXTS.get() == null) {
            EXTS.set(new HashMap<>());
        }
        EXTS.get().put(key, value);
    }

    private static void clearThreadLocal() {
        CONTENT.remove();
        EXTS.remove();
    }

    private void log(ProceedingJoinPoint joinPoint,
                     cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog,
                     Operation operation,
                     LocalDateTime startTime, Object result, Throwable exception) {
        try {
            // Judge the situation of not recording
            if (!isLogEnable(joinPoint, operateLog)) {
                return;
            }
            // Really record the operation log
            this.log0(joinPoint, operateLog, operation, startTime, result, exception);
        } catch (Throwable ex) {
            log.error("[log][When recording operation logs，Exception occurred，The parameters are joinPoint({}) operateLog({}) apiOperation({}) result({}) exception({}) ]",
                    joinPoint, operateLog, operation, result, exception, ex);
        }
    }

    private void log0(ProceedingJoinPoint joinPoint,
                      cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog,
                      Operation operation,
                      LocalDateTime startTime, Object result, Throwable exception) {
        OperateLog operateLogObj = new OperateLog();
        // Complete common fields
        operateLogObj.setTraceId(TracerUtils.getTraceId());
        operateLogObj.setStartTime(startTime);
        // Add user information
        fillUserFields(operateLogObj);
        // Complete module information
        fillModuleFields(operateLogObj, joinPoint, operateLog, operation);
        // Complete request information
        fillRequestFields(operateLogObj);
        // Completion method information
        fillMethodFields(operateLogObj, joinPoint, operateLog, startTime, result, exception);

        // Asynchronous logging
        operateLogFrameworkService.createOperateLog(operateLogObj);
    }

    private static void fillUserFields(OperateLog operateLogObj) {
        operateLogObj.setUserId(WebFrameworkUtils.getLoginUserId());
        operateLogObj.setUserType(WebFrameworkUtils.getLoginUserType());
    }

    private static void fillModuleFields(OperateLog operateLogObj,
                                         ProceedingJoinPoint joinPoint,
                                         cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog,
                                         Operation operation) {
        // module Attributes
        if (operateLog != null) {
            operateLogObj.setModule(operateLog.module());
        }
        if (StrUtil.isEmpty(operateLogObj.getModule())) {
            Tag tag = getClassAnnotation(joinPoint, Tag.class);
            if (tag != null) {
                // Read first @Tag of name Properties
                if (StrUtil.isNotEmpty(tag.name())) {
                    operateLogObj.setModule(tag.name());
                }
                // If not，Read @API of description Properties
                if (StrUtil.isEmpty(operateLogObj.getModule()) && ArrayUtil.isNotEmpty(tag.description())) {
                    operateLogObj.setModule(tag.description());
                }
            }
        }
        // name Attributes
        if (operateLog != null) {
            operateLogObj.setName(operateLog.name());
        }
        if (StrUtil.isEmpty(operateLogObj.getName()) && operation != null) {
            operateLogObj.setName(operation.summary());
        }
        // type Attributes
        if (operateLog != null && ArrayUtil.isNotEmpty(operateLog.type())) {
            operateLogObj.setType(operateLog.type()[0].getType());
        }
        if (operateLogObj.getType() == null) {
            RequestMethod requestMethod = obtainFirstMatchRequestMethod(obtainRequestMethod(joinPoint));
            OperateTypeEnum operateLogType = convertOperateLogType(requestMethod);
            operateLogObj.setType(operateLogType != null ? operateLogType.getType() : null);
        }
        // content Japanese exts Attributes
        operateLogObj.setContent(CONTENT.get());
        operateLogObj.setExts(EXTS.get());
    }

    private static void fillRequestFields(OperateLog operateLogObj) {
        // Get Request Object
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return;
        }
        // Complete request information
        operateLogObj.setRequestMethod(request.getMethod());
        operateLogObj.setRequestUrl(request.getRequestURI());
        operateLogObj.setUserIp(ServletUtils.getClientIP(request));
        operateLogObj.setUserAgent(ServletUtils.getUserAgent(request));
    }

    private static void fillMethodFields(OperateLog operateLogObj,
                                         ProceedingJoinPoint joinPoint,
                                         cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog,
                                         LocalDateTime startTime, Object result, Throwable exception) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        operateLogObj.setJavaMethod(methodSignature.toString());
        if (operateLog == null || operateLog.logArgs()) {
            operateLogObj.setJavaMethodArgs(obtainMethodArgs(joinPoint));
        }
        if (operateLog == null || operateLog.logResultData()) {
            operateLogObj.setResultData(obtainResultData(result));
        }
        operateLogObj.setDuration((int) (LocalDateTimeUtil.between(startTime, LocalDateTime.now()).toMillis()));
        // （Normal）Processing resultCode Japanese resultMsg Field
        if (result instanceof CommonResult) {
            CommonResult<?> commonResult = (CommonResult<?>) result;
            operateLogObj.setResultCode(commonResult.getCode());
            operateLogObj.setResultMsg(commonResult.getMsg());
        } else {
            operateLogObj.setResultCode(SUCCESS.getCode());
        }
        // （Abnormal）Processing resultCode Japanese resultMsg Field
        if (exception != null) {
            operateLogObj.setResultCode(INTERNAL_SERVER_ERROR.getCode());
            operateLogObj.setResultMsg(ExceptionUtil.getRootCauseMessage(exception));
        }
    }

    private static boolean isLogEnable(ProceedingJoinPoint joinPoint,
                                       cn.econets.blossom.framework.operatelog.core.annotations.OperateLog operateLog) {
        // Yes @OperateLog In case of annotation
        if (operateLog != null) {
            return operateLog.enable();
        }
        // No @ApiOperation In case of annotation，Record only POST、PUT、DELETE Situation
        return obtainFirstLogRequestMethod(obtainRequestMethod(joinPoint)) != null;
    }

    private static RequestMethod obtainFirstLogRequestMethod(RequestMethod[] requestMethods) {
        if (ArrayUtil.isEmpty(requestMethods)) {
            return null;
        }
        return Arrays.stream(requestMethods).filter(requestMethod ->
                requestMethod == RequestMethod.POST
                        || requestMethod == RequestMethod.PUT
                        || requestMethod == RequestMethod.DELETE)
                .findFirst().orElse(null);
    }

    private static RequestMethod obtainFirstMatchRequestMethod(RequestMethod[] requestMethods) {
        if (ArrayUtil.isEmpty(requestMethods)) {
            return null;
        }
        // Priority，Best match POST、PUT、DELETE
        RequestMethod result = obtainFirstLogRequestMethod(requestMethods);
        if (result != null) {
            return result;
        }
        // Then，Suboptimal match GET
        result = Arrays.stream(requestMethods).filter(requestMethod -> requestMethod == RequestMethod.GET)
                .findFirst().orElse(null);
        if (result != null) {
            return result;
        }
        // Backstop，Get the first one
        return requestMethods[0];
    }

    private static OperateTypeEnum convertOperateLogType(RequestMethod requestMethod) {
        if (requestMethod == null) {
            return null;
        }
        switch (requestMethod) {
            case GET:
                return OperateTypeEnum.GET;
            case POST:
                return OperateTypeEnum.CREATE;
            case PUT:
                return OperateTypeEnum.UPDATE;
            case DELETE:
                return OperateTypeEnum.DELETE;
            default:
                return OperateTypeEnum.OTHER;
        }
    }

    private static RequestMethod[] obtainRequestMethod(ProceedingJoinPoint joinPoint) {
        RequestMapping requestMapping = AnnotationUtils.getAnnotation( // Use Spring Tools，Can be processed @RequestMapping Alias ​​annotation
                ((MethodSignature) joinPoint.getSignature()).getMethod(), RequestMapping.class);
        return requestMapping != null ? requestMapping.method() : new RequestMethod[]{};
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(annotationClass);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Annotation> T getClassAnnotation(ProceedingJoinPoint joinPoint, Class<T> annotationClass) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(annotationClass);
    }

    private static String obtainMethodArgs(ProceedingJoinPoint joinPoint) {
        // TODO Improvement：Parameter desensitization and ignoring
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] argNames = methodSignature.getParameterNames();
        Object[] argValues = joinPoint.getArgs();
        // Splicing parameters
        Map<String, Object> args = Maps.newHashMapWithExpectedSize(argValues.length);
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            Object argValue = argValues[i];
            // When ignored，Mark as ignore String，Avoid and null Mixed together
            args.put(argName, !isIgnoreArgs(argValue) ? argValue : "[ignore]");
        }
        return JsonUtils.toJsonString(args);
    }

    private static String obtainResultData(Object result) {
        // TODO Promotion：Result desensitization and ignoring
        if (result instanceof CommonResult) {
            result = ((CommonResult<?>) result).getData();
        }
        return JsonUtils.toJsonString(result);
    }

    private static boolean isIgnoreArgs(Object object) {
        Class<?> clazz = object.getClass();
        // Processing arrays
        if (clazz.isArray()) {
            return IntStream.range(0, Array.getLength(object))
                    .anyMatch(index -> isIgnoreArgs(Array.get(object, index)));
        }
        // Recursion，Processing arrays、Collection、Map Situation
        if (Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) object).stream()
                    .anyMatch((Predicate<Object>) OperateLogAspect::isIgnoreArgs);
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return isIgnoreArgs(((Map<?, ?>) object).values());
        }
        // obj
        return object instanceof MultipartFile
                || object instanceof HttpServletRequest
                || object instanceof HttpServletResponse
                || object instanceof BindingResult;
    }

}
