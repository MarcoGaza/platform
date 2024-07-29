package cn.econets.blossom.framework.websocket.core.sender.rocketmq;

import cn.econets.blossom.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * Based on RocketMQ of {@link WebSocketMessageSender} Implementation class
 *
 */
@Slf4j
public class RocketMQWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final RocketMQTemplate rocketMQTemplate;

    private final String topic;

    public RocketMQWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                          RocketMQTemplate rocketMQTemplate,
                                          String topic) {
        super(sessionManager);
        this.rocketMQTemplate = rocketMQTemplate;
        this.topic = topic;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendRocketMQMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendRocketMQMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendRocketMQMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * Passed RocketMQ Broadcast message
     *
     * @param sessionId Session Number
     * @param userId User Number
     * @param userType User Type
     * @param messageType Message type
     * @param messageContent Message content
     */
    private void sendRocketMQMessage(String sessionId, Long userId, Integer userType,
                                     String messageType, String messageContent) {
        RocketMQWebSocketMessage mqMessage = new RocketMQWebSocketMessage()
                .setSessionId(sessionId).setUserId(userId).setUserType(userType)
                .setMessageType(messageType).setMessageContent(messageContent);
        rocketMQTemplate.syncSend(topic, mqMessage);
    }

}
