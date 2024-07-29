package cn.econets.blossom.framework.websocket.core.sender.rocketmq;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * {@link RocketMQWebSocketMessage} Consumer of broadcast messages，Really send the message
 *
 */
@RocketMQMessageListener( // Key Points：Add @RocketMQMessageListener Annotation，Declaration of consumption topic
        topic = "${application.websocket.sender-rocketmq.topic}",
        consumerGroup = "${application.websocket.sender-rocketmq.consumer-group}",
        messageModel = MessageModel.BROADCASTING // Set to broadcast mode，Ensure that each instance can receive the message
)
@RequiredArgsConstructor
public class RocketMQWebSocketMessageConsumer implements RocketMQListener<RocketMQWebSocketMessage> {

    private final RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender;

    @Override
    public void onMessage(RocketMQWebSocketMessage message) {
        rocketMQWebSocketMessageSender.send(message.getSessionId(),
                message.getUserType(), message.getUserId(),
                message.getMessageType(), message.getMessageContent());
    }

}
