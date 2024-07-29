package cn.econets.blossom.module.pay.dal.redis.no;

import cn.hutool.core.date.DatePattern;import cn.hutool.core.date.DateUtil;
import cn.econets.blossom.module.pay.dal.redis.RedisKeyConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Payment serial number Redis DAO
 *
 *
 */
@Repository
public class PayNoRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Generate serial number
     *
     * @param prefix Prefix
     * @return Serial number
     */
    public String generate(String prefix) {
        // Incremental sequence number
        String noPrefix = prefix + DateUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_PATTERN);
        String key = RedisKeyConstants.PAY_NO + noPrefix;
        Long no = stringRedisTemplate.opsForValue().increment(key);
        // Set expiration time
        stringRedisTemplate.expire(key, Duration.ofMinutes(1L));
        return noPrefix + no;
    }

}
