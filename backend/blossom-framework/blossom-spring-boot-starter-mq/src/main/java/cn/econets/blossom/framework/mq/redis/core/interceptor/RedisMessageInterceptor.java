package cn.econets.blossom.framework.mq.redis.core.interceptor;


import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;

/**
 * {@link AbstractRedisMessage} Message Interceptor
 * Through the interceptor，As a plugin mechanism，Realize expansion。
 * For example，Multi-tenant scenario MQ Message processing
 *
 */
public interface RedisMessageInterceptor {

    default void sendMessageBefore(AbstractRedisMessage message) {
    }

    default void sendMessageAfter(AbstractRedisMessage message) {
    }

    default void consumeMessageBefore(AbstractRedisMessage message) {
    }

    default void consumeMessageAfter(AbstractRedisMessage message) {
    }

}
