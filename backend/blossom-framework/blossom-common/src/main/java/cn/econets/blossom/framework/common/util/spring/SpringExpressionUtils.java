package cn.econets.blossom.framework.common.util.spring;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Spring EL Expression tool class
 *
 */
public class SpringExpressionUtils {

    /**
     * Spring EL Expression parser
     */
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    /**
     * Parameter name finder
     */
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    private SpringExpressionUtils() {
    }

    /**
     * From the section，Single analysis EL The result of the expression
     *
     * @param joinPoint        Cut point
     * @param expressionString EL Expression array
     * @return Execution interface
     */
    public static Object parseExpression(JoinPoint joinPoint, String expressionString) {
        Map<String, Object> result = parseExpressions(joinPoint, Collections.singletonList(expressionString));
        return result.get(expressionString);
    }

    /**
     * From the section，Batch parsing EL The result of the expression
     *
     * @param joinPoint         Cut point
     * @param expressionStrings EL Expression array
     * @return Results，key is an expression，value is the corresponding value
     */
    public static Map<String, Object> parseExpressions(JoinPoint joinPoint, List<String> expressionStrings) {
        // If empty，No parsing is performed
        if (CollUtil.isEmpty(expressionStrings)) {
            return MapUtil.newHashMap();
        }

        // First step，Build parsing context EvaluationContext
        // Passed joinPoint Get the annotated method
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // Use spring of ParameterNameDiscoverer Get method parameter name array
        String[] paramNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
        // Spring The expression context object
        EvaluationContext context = new StandardEvaluationContext();
        // Assign value to context
        if (ArrayUtil.isNotEmpty(paramNames)) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }

        // Step 2，Parse each parameter one by one
        Map<String, Object> result = MapUtil.newHashMap(expressionStrings.size(), true);
        expressionStrings.forEach(key -> {
            Object value = EXPRESSION_PARSER.parseExpression(key).getValue(context);
            result.put(key, value);
        });
        return result;
    }

}
