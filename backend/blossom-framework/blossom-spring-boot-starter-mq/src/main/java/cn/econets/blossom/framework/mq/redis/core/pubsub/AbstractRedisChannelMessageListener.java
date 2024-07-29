package cn.econets.blossom.framework.mq.redis.core.pubsub;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import cn.hutool.core.util.TypeUtil;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Redis Pub/Sub Listener abstract class，Used to implement broadcast consumption
 *
 * @param <T> Message type。Must fill in，Otherwise it will report an error
 *
 */
public abstract class AbstractRedisChannelMessageListener<T extends AbstractRedisChannelMessage> implements MessageListener {

    /**
     * Message type
     */
    private final Class<T> messageType;
    /**
     * Redis Channel
     */
    private final String channel;
    /**
     * RedisMQTemplate
     */
    @Setter
    private RedisMQTemplate redisMQTemplate;

    @SneakyThrows
    protected AbstractRedisChannelMessageListener() {
        this.messageType = getMessageClass();
        this.channel = messageType.getDeclaredConstructor().newInstance().getChannel();
    }

    /**
     * Get Sub Subscribed Redis Channel Channel
     *
     * @return channel
     */
    public final String getChannel() {
        return channel;
    }

    @Override
    public final void onMessage(Message message, byte[] bytes) {
        T messageObj = JsonUtils.parseObject(message.getBody(), messageType);
        try {
            consumeMessageBefore(messageObj);
            // Consumption message
            this.onMessage(messageObj);
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
     * By resolving generics on the class，Get message type
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
