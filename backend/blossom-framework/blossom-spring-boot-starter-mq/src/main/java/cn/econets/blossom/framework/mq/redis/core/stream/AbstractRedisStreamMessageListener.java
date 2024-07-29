package cn.econets.blossom.framework.mq.redis.core.stream;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import cn.hutool.core.util.TypeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Redis Stream Listener abstract class，Used to achieve cluster consumption
 *
 * @param <T> Message type。Must fill in，Otherwise it will report an error
 *
 */
public abstract class AbstractRedisStreamMessageListener<T extends AbstractRedisStreamMessage>
        implements StreamListener<String, ObjectRecord<String, String>> {

    /**
     * Message type
     */
    private final Class<T> messageType;
    /**
     * Redis Channel
     */
    @Getter
    private final String streamKey;

    /**
     * Redis Consumer Grouping，Default use spring.application.name Name
     */
    @Value("${spring.application.name}")
    @Getter
    private String group;
    /**
     * RedisMQTemplate
     */
    @Setter
    private RedisMQTemplate redisMQTemplate;

    @SneakyThrows
    protected AbstractRedisStreamMessageListener() {
        this.messageType = getMessageClass();
        this.streamKey = messageType.getDeclaredConstructor().newInstance().getStreamKey();
    }

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        // Consumption message
        T messageObj = JsonUtils.parseObject(message.getValue(), messageType);
        try {
            consumeMessageBefore(messageObj);
            // Consumption message
            this.onMessage(messageObj);
            // ack Message consumption completed
            redisMQTemplate.getRedisTemplate().opsForStream().acknowledge(group, message);
            // TODO The following points need to be considered additionally：
            // 1. Handling abnormal situations
            // 2. Send log；And the combination of transactions
            // 3. Consumption log；And general idempotence
            // 4. Retry after consumption failure，https://zhuanlan.zhihu.com/p/60501638
        } finally {
            consumeMessageAfter(messageObj);
        }
    }

    /**
     * Processing message
     *
     * @param message Message
     */
    public abstract void onMessage(T message);

    /**
     * By resolving generics on a class，Get message type
     *
     * @return Message type
     */
    @SuppressWarnings("unchecked")
    private Class<T> getMessageClass() {
        Type type = TypeUtil.getTypeArgument(getClass(), 0);
        if (type == null) {
            throw new IllegalStateException(String.format("Type(%s) The message type needs to be set", getClass().getName()));
        }
        return (Class<T>) type;
    }

    private void consumeMessageBefore(AbstractRedisMessage message) {
        assert redisMQTemplate != null;
        List<RedisMessageInterceptor> interceptors = redisMQTemplate.getInterceptors();
        // Positive sequence
        interceptors.forEach(interceptor -> interceptor.consumeMessageBefore(message));
    }

    private void consumeMessageAfter(AbstractRedisMessage message) {
        assert redisMQTemplate != null;
        List<RedisMessageInterceptor> interceptors = redisMQTemplate.getInterceptors();
        // Reverse order
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).consumeMessageAfter(message);
        }
    }

}
