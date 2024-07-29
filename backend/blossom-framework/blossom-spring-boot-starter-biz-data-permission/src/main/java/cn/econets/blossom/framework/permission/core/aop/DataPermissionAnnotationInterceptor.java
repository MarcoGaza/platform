package cn.econets.blossom.framework.permission.core.aop;

import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import lombok.Getter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link DataPermission} Annotation interceptor
 * 1. Before executing the method，Will @DataPermission Annotation stack
 * 2. After executing the method，Will @DataPermission Annotation pop
 *
 */
@DataPermission // This annotation，Used for {@link DATA_PERMISSION_NULL} Empty object
public class DataPermissionAnnotationInterceptor implements MethodInterceptor {

    /**
     * DataPermission Empty object，For method None {@link DataPermission} Annotation，Use DATA_PERMISSION_NULL Placeholder
     */
    static final DataPermission DATA_PERMISSION_NULL = DataPermissionAnnotationInterceptor.class.getAnnotation(DataPermission.class);

    @Getter
    private final Map<MethodClassKey, DataPermission> dataPermissionCache = new ConcurrentHashMap<>();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // Push to stack
        DataPermission dataPermission = this.findAnnotation(methodInvocation);
        if (dataPermission != null) {
            DataPermissionContextHolder.add(dataPermission);
        }
        try {
            // Execution logic
            return methodInvocation.proceed();
        } finally {
            // Out of stack
            if (dataPermission != null) {
                DataPermissionContextHolder.remove();
            }
        }
    }

    private DataPermission findAnnotation(MethodInvocation methodInvocation) {
        // 1. Get from cache
        Method method = methodInvocation.getMethod();
        Object targetObject = methodInvocation.getThis();
        Class<?> clazz = targetObject != null ? targetObject.getClass() : method.getDeclaringClass();
        MethodClassKey methodClassKey = new MethodClassKey(method, clazz);
        DataPermission dataPermission = dataPermissionCache.get(methodClassKey);
        if (dataPermission != null) {
            return dataPermission != DATA_PERMISSION_NULL ? dataPermission : null;
        }

        // 2.1 Get from method
        dataPermission = AnnotationUtils.findAnnotation(method, DataPermission.class);
        // 2.2 Get from the class
        if (dataPermission == null) {
            dataPermission = AnnotationUtils.findAnnotation(clazz, DataPermission.class);
        }
        // 2.3 Add to cache
        dataPermissionCache.put(methodClassKey, dataPermission != null ? dataPermission : DATA_PERMISSION_NULL);
        return dataPermission;
    }

}
