package cn.econets.blossom.framework.websocket.core.sender.redis;

import cn.econets.blossom.framework.mq.redis.core.pubsub.AbstractRedisChannelMessageListener;
import lombok.RequiredArgsConstructor;

/**
 * {@link RedisWebSocketMessage} Consumer of broadcast messages，Really send the message
 *
 */
@RequiredArgsConstructor
public class RedisWebSocketMessageConsumer extends AbstractRedisChannelMessageListener<RedisWebSocketMessage> {

    private final RedisWebSocketMessageSender redisWebSocketMessageSender;

    @Override
    public void onMessage(RedisWebSocketMessage message) {
        redisWebSocketMessageSender.send(message.getSessionId(),
                message.getUserType(), message.getUserId(),
                message.getMessageType(), message.getMessageContent());
    }

}
