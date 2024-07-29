package cn.econets.blossom.framework.redis.core;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * Supports custom expiration time {@link RedisCacheManager} Implementation class
 *
 * In {@link Cacheable#cacheNames()} The format is "key#ttl" Time，# Behind ttl is the expiration time。
 * The unit is the last letter（Supported units include：d Sky，h Hours，m minutes，s seconds），The default unit is s seconds
 *
 */
public class TimeoutRedisCacheManager extends RedisCacheManager {

    private static final String SPLIT = "#";

    public TimeoutRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (StrUtil.isEmpty(name)) {
            return super.createRedisCache(name, cacheConfig);
        }
        // If used # Separation，Size is not equal 2，This means that a custom expiration time is not used
        String[] names = StrUtil.splitToArray(name, SPLIT);
        if (names.length != 2) {
            return super.createRedisCache(name, cacheConfig);
        }

        // Core：Through modification cacheConfig Expiration time，Implement custom expiration time
        if (cacheConfig != null) {
            // Remove # Behind : And the following content，Avoid affecting parsing
            names[1] = StrUtil.subBefore(names[1], StrUtil.COLON, false);
            // Parsing time
            Duration duration = parseDuration(names[1]);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    /**
     * Parsing expiration time Duration
     *
     * @param ttlStr Expiration time string
     * @return Expiration time Duration
     */
    private Duration parseDuration(String ttlStr) {
        String timeUnit = StrUtil.subSuf(ttlStr, -1);
        switch (timeUnit) {
            case "d":
                return Duration.ofDays(removeDurationSuffix(ttlStr));
            case "h":
                return Duration.ofHours(removeDurationSuffix(ttlStr));
            case "m":
                return Duration.ofMinutes(removeDurationSuffix(ttlStr));
            case "s":
                return Duration.ofSeconds(removeDurationSuffix(ttlStr));
            default:
                return Duration.ofSeconds(Long.parseLong(ttlStr));
        }
    }

    /**
     * Remove extra suffixes，Return the specific time
     *
     * @param ttlStr Expiration time string
     * @return Time
     */
    private Long removeDurationSuffix(String ttlStr) {
        return NumberUtil.parseLong(StrUtil.sub(ttlStr, 0, ttlStr.length() - 1));
    }

}
