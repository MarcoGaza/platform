package cn.econets.blossom.framework.idempotent.core.redis;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Idempotence Redis DAO
 *
 */
@AllArgsConstructor
public class IdempotentRedisDAO {

    /**
     * Idempotent operation
     *
     * KEY Format：idempotent:%s // Parameters are uuid
     * VALUE Format：String
     * Expiration time：Not fixed
     */
    private static final String IDEMPOTENT = "idempotent:%s";

    private final StringRedisTemplate redisTemplate;

    public Boolean setIfAbsent(String key, long timeout, TimeUnit timeUnit) {
        String redisKey = formatKey(key);
        return redisTemplate.opsForValue().setIfAbsent(redisKey, "", timeout, timeUnit);
    }

    private static String formatKey(String key) {
        return String.format(IDEMPOTENT, key);
    }

}
