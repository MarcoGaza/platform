package cn.econets.blossom.framework.websocket.core.sender.rabbitmq;

import cn.econets.blossom.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Based on RabbitMQ of {@link WebSocketMessageSender} Implementation class
 *
 */
@Slf4j
public class RabbitMQWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final RabbitTemplate rabbitTemplate;

    private final TopicExchange topicExchange;

    public RabbitMQWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                          RabbitTemplate rabbitTemplate,
                                          TopicExchange topicExchange) {
        super(sessionManager);
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendRabbitMQMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendRabbitMQMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendRabbitMQMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * Pass RabbitMQ Broadcast message
     *
     * @param sessionId Session Number
     * @param userId User Number
     * @param userType User Type
     * @param messageType Message type
     * @param messageContent Message content
     */
    private void sendRabbitMQMessage(String sessionId, Long userId, Integer userType,
                                     String messageType, String messageContent) {
        RabbitMQWebSocketMessage mqMessage = new RabbitMQWebSocketMessage()
                .setSessionId(sessionId).setUserId(userId).setUserType(userType)
                .setMessageType(messageType).setMessageContent(messageContent);
        rabbitTemplate.convertAndSend(topicExchange.getName(), null, mqMessage);
    }

}
