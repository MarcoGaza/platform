package cn.econets.blossom.framework.websocket.core.sender.kafka;

import cn.econets.blossom.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Based on Kafka of {@link WebSocketMessageSender} Implementation class
 *
 */
@Slf4j
public class KafkaWebSocketMessageSender extends AbstractWebSocketMessageSender {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final String topic;

    public KafkaWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                       KafkaTemplate<Object, Object> kafkaTemplate,
                                       String topic) {
        super(sessionManager);
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        sendKafkaMessage(null, userId, userType, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        sendKafkaMessage(null, null, userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        sendKafkaMessage(sessionId, null, null, messageType, messageContent);
    }

    /**
     * Pass Kafka Broadcast message
     *
     * @param sessionId Session Number
     * @param userId User Number
     * @param userType User Type
     * @param messageType Message type
     * @param messageContent Message content
     */
    private void sendKafkaMessage(String sessionId, Long userId, Integer userType,
                                  String messageType, String messageContent) {
        KafkaWebSocketMessage mqMessage = new KafkaWebSocketMessage()
                .setSessionId(sessionId).setUserId(userId).setUserType(userType)
                .setMessageType(messageType).setMessageContent(messageContent);
        try {
            kafkaTemplate.send(topic, mqMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("[sendKafkaMessage][Send message({}) To Kafka Failed]", mqMessage, e);
        }
    }

}
