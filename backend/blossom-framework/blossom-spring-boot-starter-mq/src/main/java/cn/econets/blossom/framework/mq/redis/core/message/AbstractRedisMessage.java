package cn.econets.blossom.framework.mq.redis.core.message;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis Message abstract base class
 *
 *
 */
@Data
public abstract class AbstractRedisMessage {

    /**
     * Head
     */
    private Map<String, String> headers = new HashMap<>();

    public String getHeader(String key) {
        return headers.get(key);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

}
