package cn.econets.blossom.framework.mq.redis.core;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import cn.econets.blossom.framework.mq.redis.core.pubsub.AbstractRedisChannelMessage;
import cn.econets.blossom.framework.mq.redis.core.stream.AbstractRedisStreamMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis MQ Operation template class
 *
 */
@AllArgsConstructor
public class RedisMQTemplate {

    @Getter
    private final RedisTemplate<String, ?> redisTemplate;
    /**
     * Interceptor array
     */
    @Getter
    private final List<RedisMessageInterceptor> interceptors = new ArrayList<>();

    /**
     * Send Redis Message，Based on Redis pub/sub Realization
     *
     * @param message Message
     */
    public <T extends AbstractRedisChannelMessage> void send(T message) {
        try {
            sendMessageBefore(message);
            // Send message
            redisTemplate.convertAndSend(message.getChannel(), JsonUtils.toJsonString(message));
        } finally {
            sendMessageAfter(message);
        }
    }

    /**
     * Send Redis Message，Based on Redis Stream Realization
     *
     * @param message Message
     * @return The message record number object
     */
    public <T extends AbstractRedisStreamMessage> RecordId send(T message) {
        try {
            sendMessageBefore(message);
            // Send message
            return redisTemplate.opsForStream().add(StreamRecords.newRecord()
                    .ofObject(JsonUtils.toJsonString(message)) // Setting content
                    .withStreamKey(message.getStreamKey())); // Settings stream key
        } finally {
            sendMessageAfter(message);
        }
    }

    /**
     * Add interceptor
     *
     * @param interceptor Interceptor
     */
    public void addInterceptor(RedisMessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    private void sendMessageBefore(AbstractRedisMessage message) {
        // Positive sequence
        interceptors.forEach(interceptor -> interceptor.sendMessageBefore(message));
    }

    private void sendMessageAfter(AbstractRedisMessage message) {
        // Reverse order
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).sendMessageAfter(message);
        }
    }

}
