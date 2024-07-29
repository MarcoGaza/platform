package cn.econets.blossom.framework.idempotent.core.aop;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.idempotent.core.annotation.Idempotent;
import cn.econets.blossom.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.econets.blossom.framework.idempotent.core.redis.IdempotentRedisDAO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Intercepted statement {@link Idempotent} Annotation method，Implement idempotent operations
 *
 *
 */
@Aspect
@Slf4j
public class IdempotentAspect {

    /**
     * IdempotentKeyResolver Collection
     */
    private final Map<Class<? extends IdempotentKeyResolver>, IdempotentKeyResolver> keyResolvers;

    private final IdempotentRedisDAO idempotentRedisDAO;

    public IdempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        this.keyResolvers = CollectionUtils.convertMap(keyResolvers, IdempotentKeyResolver::getClass);
        this.idempotentRedisDAO = idempotentRedisDAO;
    }

    @Before("@annotation(idempotent)")
    public void beforePointCut(JoinPoint joinPoint, Idempotent idempotent) {
        // Get IdempotentKeyResolver
        IdempotentKeyResolver keyResolver = keyResolvers.get(idempotent.keyResolver());
        Assert.notNull(keyResolver, "Cannot find the corresponding one IdempotentKeyResolver");
        // Analysis Key
        String key = keyResolver.resolver(joinPoint, idempotent);

        // Lock Key。
        boolean success = idempotentRedisDAO.setIfAbsent(key, idempotent.timeout(), idempotent.timeUnit());
        // Lock failed，Throws an exception
        if (!success) {
            log.info("[beforePointCut][Method({}) Parameters({}) There are duplicate requests]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            throw new ServiceException(GlobalErrorCodeConstants.REPEATED_REQUESTS.getCode(), idempotent.message());
        }
    }

}
