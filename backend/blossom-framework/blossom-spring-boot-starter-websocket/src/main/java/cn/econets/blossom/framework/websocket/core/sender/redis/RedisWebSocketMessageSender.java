package cn.econets.blossom.framework.websocket.core.sender.redis;

import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Based on Redis of {@link WebSocketMessageSender} Implementation class
 *
 */
@Slf4j
public class RedisWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final RedisMQTemplate redisMQTemplate;

    public RedisWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                       RedisMQTemplate redisMQTemplate) {
        super(sessionManager);
        this.redisMQTemplate = redisMQTemplate;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendRedisMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendRedisMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendRedisMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * Passed Redis Broadcast message
     *
     * @param sessionId Session Number
     * @param userId User Number
     * @param userType User Type
     * @param messageType Message type
     * @param messageContent Message content
     */
    private void sendRedisMessage(String sessionId, Long userId, Integer userType,
                                  String messageType, String messageContent) {
        RedisWebSocketMessage mqMessage = new RedisWebSocketMessage()
                .setSessionId(sessionId).setUserId(userId).setUserType(userType)
                .setMessageType(messageType).setMessageContent(messageContent);
        redisMQTemplate.send(mqMessage);
    }

}
