package cn.econets.blossom.framework.redis.config;

import cn.econets.blossom.framework.redis.core.TimeoutRedisCacheManager;
import cn.hutool.core.util.StrUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

@AutoConfiguration
@EnableConfigurationProperties({CacheProperties.class, CachesProperties.class})
@EnableCaching
public class CacheAutoConfiguration {

    /**
     * RedisCacheConfiguration Bean
     *
     * Reference org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration of createConfiguration Method
     */
    @Bean
    @Primary
    public RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties){
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        config = config.computePrefixWith(cacheName -> {
            String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
            if (StringUtils.hasText(keyPrefix)) {
                keyPrefix = keyPrefix.lastIndexOf(StrUtil.COLON) == -1 ? keyPrefix + StrUtil.COLON : keyPrefix;
                return keyPrefix + cacheName + StrUtil.COLON;
            }
            return cacheName + StrUtil.COLON;
        });
        // Set usage JSON Serialization method
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(BlossomRedisAutoConfiguration.buildRedisSerializer()));

        // Settings CacheProperties.Redis Properties
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                               RedisCacheConfiguration redisCacheConfiguration,
                                               CachesProperties cachesProperties) {
        // Create RedisCacheWriter Object
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(cachesProperties.getRedisScanBatchSize()));
        // Create TenantRedisCacheManager Object
        return new TimeoutRedisCacheManager(cacheWriter, redisCacheConfiguration);
    }
}
