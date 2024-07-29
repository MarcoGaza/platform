package cn.econets.blossom.framework.tenant.core.job;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.tenant.core.service.TenantFrameworkService;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Multi-tenancy JobHandler AOP
 * When executing the task，Will be executed one by one according to the tenants Job Logic
 *
 * Attention，Guarantee required JobHandler Idempotence。Because Job When a tenant fails to execute and retry，The tenants who have successfully executed before will also execute again。
 *
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class TenantJobAspect {

    private final TenantFrameworkService tenantFrameworkService;

    @Around("@annotation(tenantJob)")
    public String around(ProceedingJoinPoint joinPoint, TenantJob tenantJob) {
        // Get tenant list
        List<Long> tenantIds = tenantFrameworkService.getTenantIds();
        if (CollUtil.isEmpty(tenantIds)) {
            return null;
        }

        // Tenant by tenant，Execute Job
        Map<Long, String> results = new ConcurrentHashMap<>();
        tenantIds.parallelStream().forEach(tenantId -> {
            // TODO Pass first parallel Achieve parallelism；1）Multiple tenants，This is an execution log；2）Abnormal situation
            TenantUtils.execute(tenantId, () -> {
                try {
                    joinPoint.proceed();
                } catch (Throwable e) {
                    results.put(tenantId, ExceptionUtil.getRootCauseMessage(e));
                }
            });
        });
        return JsonUtils.toJsonString(results);
    }

}
