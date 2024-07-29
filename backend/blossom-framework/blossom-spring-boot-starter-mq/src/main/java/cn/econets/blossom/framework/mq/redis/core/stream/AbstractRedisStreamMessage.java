package cn.econets.blossom.framework.mq.redis.core.stream;

import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Redis Stream Message Abstract class
 *
 */
public abstract class AbstractRedisStreamMessage extends AbstractRedisMessage {

    /**
     * Get Redis Stream Key，Use class name by default
     *
     * @return Channel
     */
    @JsonIgnore // Avoid serialization
    public String getStreamKey() {
        return getClass().getSimpleName();
    }

}
