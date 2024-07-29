package cn.econets.blossom.framework.websocket.core.sender.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;

/**
 * {@link RabbitMQWebSocketMessage} Consumer of broadcast messages，Really send the message
 *
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        // In Queue Name，Use UUID Generate its suffix。Like this，Started Consumer of Queue Different，To achieve the purpose of broadcast consumption
                        name = "${application.websocket.sender-rabbitmq.queue}" + "-" + "#{T(java.util.UUID).randomUUID()}",
                        // Consumer When closed，The queue can be automatically deleted
                        autoDelete = "true"
                ),
                exchange = @Exchange(
                        name = "${application.websocket.sender-rabbitmq.exchange}",
                        type = ExchangeTypes.TOPIC,
                        declare = "false"
                )
        )
)
@RequiredArgsConstructor
public class RabbitMQWebSocketMessageConsumer {

    private final RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender;

    @RabbitHandler
    public void onMessage(RabbitMQWebSocketMessage message) {
        rabbitMQWebSocketMessageSender.send(message.getSessionId(),
                message.getUserType(), message.getUserId(),
                message.getMessageType(), message.getMessageContent());
    }

}
