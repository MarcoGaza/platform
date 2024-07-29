package cn.econets.blossom.framework.monitor.core.aop;

import cn.econets.blossom.framework.common.util.spring.SpringExpressionUtils;
import cn.econets.blossom.framework.monitor.core.annotation.BizTrace;
import cn.econets.blossom.framework.monitor.core.util.TracerFrameworkUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Map;

import static java.util.Arrays.asList;

/**
 * {@link BizTrace} Cut surface，Record business link
 *
 */
@Aspect
@AllArgsConstructor
@Slf4j
public class BizTraceAspect {

    private static final String BIZ_OPERATION_NAME_PREFIX = "Biz/";

    private final Tracer tracer;

    @Around(value = "@annotation(trace)")
    public Object around(ProceedingJoinPoint joinPoint, BizTrace trace) throws Throwable {
        // Create span
        String operationName = getOperationName(joinPoint, trace);
        Span span = tracer.buildSpan(operationName)
                .withTag(Tags.COMPONENT.getKey(), "biz")
                .start();
        try {
            // Execute the original method
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            TracerFrameworkUtils.onError(throwable, span);
            throw throwable;
        } finally {
            // Settings Span of biz Properties
            setBizTag(span, joinPoint, trace);
            // Completed Span
            span.finish();
        }
    }

    private String getOperationName(ProceedingJoinPoint joinPoint, BizTrace trace) {
        // Custom operation name
        if (StrUtil.isNotEmpty(trace.operationName())) {
            return BIZ_OPERATION_NAME_PREFIX + trace.operationName();
        }
        // Default operation name，Usage method name
        return BIZ_OPERATION_NAME_PREFIX
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "/" + joinPoint.getSignature().getName();
    }

    private void setBizTag(Span span, ProceedingJoinPoint joinPoint, BizTrace trace) {
        try {
            Map<String, Object> result = SpringExpressionUtils.parseExpressions(joinPoint, asList(trace.type(), trace.id()));
            span.setTag(BizTrace.TYPE_TAG, MapUtil.getStr(result, trace.type()));
            span.setTag(BizTrace.ID_TAG, MapUtil.getStr(result, trace.id()));
        } catch (Exception ex) {
            log.error("[setBizTag][Analysis bizType with bizId Exception occurred]", ex);
        }
    }

}
