package cn.econets.blossom.framework.websocket.core.sender.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * {@link KafkaWebSocketMessage} Consumer of broadcast messages，Really send the message
 *
 */
@RequiredArgsConstructor
public class KafkaWebSocketMessageConsumer {

    private final KafkaWebSocketMessageSender rabbitMQWebSocketMessageSender;

    @RabbitHandler
    @KafkaListener(
            topics = "${application.websocket.sender-kafka.topic}",
            // In Group Top，Use UUID Generate its suffix。Like this，Started Consumer of Group Different，To achieve the purpose of broadcast consumption
            groupId = "${application.websocket.sender-kafka.consumer-group}" + "-" + "#{T(java.util.UUID).randomUUID()}")
    public void onMessage(KafkaWebSocketMessage message) {
        rabbitMQWebSocketMessageSender.send(message.getSessionId(),
                message.getUserType(), message.getUserId(),
                message.getMessageType(), message.getMessageContent());
    }

}
