package cn.econets.blossom.framework.tenant.core.service;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.util.cache.CacheUtils;
import cn.econets.blossom.module.system.api.tenant.TenantApi;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.Duration;
import java.util.List;

/**
 * Tenant Framework Service Implementation class
 *
 */
@RequiredArgsConstructor
public class TenantFrameworkServiceImpl implements TenantFrameworkService {

    private static final ServiceException SERVICE_EXCEPTION_NULL = new ServiceException();

    private final TenantApi tenantApi;

    /**
     * Targeting {@link #getTenantIds()} Cache
     */
    private final LoadingCache<Object, List<Long>> getTenantIdsCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // Expiration time 1 Minutes
            new CacheLoader<Object, List<Long>>() {

                @Override
                public List<Long> load(Object key) {
                    return tenantApi.getTenantIdList();
                }

            });

    /**
     * Targeting {@link #validTenant(Long)} Cache
     */
    private final LoadingCache<Long, ServiceException> validTenantCache = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // Expiration time 1 Minutes
            new CacheLoader<Long, ServiceException>() {

                @Override
                public ServiceException load(Long id) {
                    try {
                        tenantApi.validateTenant(id);
                        return SERVICE_EXCEPTION_NULL;
                    } catch (ServiceException ex) {
                        return ex;
                    }
                }

            });

    @Override
    @SneakyThrows
    public List<Long> getTenantIds() {
        return getTenantIdsCache.get(Boolean.TRUE);
    }

    @Override
    public void validTenant(Long id) {
        ServiceException serviceException = validTenantCache.getUnchecked(id);
        if (serviceException != SERVICE_EXCEPTION_NULL) {
            throw serviceException;
        }
    }

}
