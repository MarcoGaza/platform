package cn.econets.blossom.framework.common.util.cache;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Cache Tools
 *
 */
public class CacheUtils {

    public static <K, V> LoadingCache<K, V> buildAsyncReloadingCache(Duration duration, CacheLoader<K, V> loader) {
        Executor executor = Executors.newCachedThreadPool( TtlExecutors.getDefaultDisableInheritableThreadFactory()); // TTL Guarantee ThreadLocal Can be transparently transmitted

        return CacheBuilder.newBuilder()
                // Only block the current data loading thread，Other threads return old values
                .refreshAfterWrite(duration)
                // Passed asyncReloading Implement full asynchronous loading，Include refreshAfterWrite Blocked loading thread
                .build(CacheLoader.asyncReloading(loader, executor));
    }

}
