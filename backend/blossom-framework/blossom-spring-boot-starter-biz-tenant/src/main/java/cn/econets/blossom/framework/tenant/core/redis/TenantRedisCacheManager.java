package cn.econets.blossom.framework.tenant.core.redis;

import cn.econets.blossom.framework.redis.core.TimeoutRedisCacheManager;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * Multi-tenant {@link RedisCacheManager} Implementation class
 *
 * Operation specification name of {@link Cache} Time，Automatically concatenate tenant suffixes，The format is name + ":" + tenantId + Suffix
 *
 */
@Slf4j
public class TenantRedisCacheManager extends TimeoutRedisCacheManager {

    public TenantRedisCacheManager(RedisCacheWriter cacheWriter,
                                   RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    public Cache getCache(String name) {
        // If multi-tenancy is enabled，Then name Concatenate tenant suffixes
        if (!TenantContextHolder.isIgnore()
            && TenantContextHolder.getTenantId() != null) {
            name = name + ":" + TenantContextHolder.getTenantId();
        }

        // Continue based on the parent method
        return super.getCache(name);
    }

}
