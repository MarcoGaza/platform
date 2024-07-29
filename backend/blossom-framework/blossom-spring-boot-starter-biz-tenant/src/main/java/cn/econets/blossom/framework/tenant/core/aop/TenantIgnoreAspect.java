package cn.econets.blossom.framework.tenant.core.aop;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Ignore multi-tenancy Aspect，Based on {@link TenantIgnore} Annotation implementation，Used for some global logic。
 * For example，A scheduled task，Read all data，Processing。
 * For example，Read all data，Cache。
 *
 * Implementation of overall logic，Japanese {@link TenantUtils#executeIgnore(Runnable)} Need to be consistent
 *
 */
@Aspect
@Slf4j
public class TenantIgnoreAspect {

    @Around("@annotation(tenantIgnore)")
    public Object around(ProceedingJoinPoint joinPoint, TenantIgnore tenantIgnore) throws Throwable {
        Boolean oldIgnore = TenantContextHolder.isIgnore();
        try {
            TenantContextHolder.setIgnore(true);
            // Execution logic
            return joinPoint.proceed();
        } finally {
            TenantContextHolder.setIgnore(oldIgnore);
        }
    }

}
