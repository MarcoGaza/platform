package cn.econets.blossom.framework.mq.redis.core.pubsub;

import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Redis Channel Message Abstract class
 *
 */
public abstract class AbstractRedisChannelMessage extends AbstractRedisMessage {

    /**
     * Get Redis Channel，Use class name by default
     *
     * @return Channel
     */
    @JsonIgnore // Avoid serialization。The reason is，Redis Publish Channel When the message is received，Already know how to specify。
    public String getChannel() {
        return getClass().getSimpleName();
    }

}
