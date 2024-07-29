package cn.econets.blossom.framework.websocket.core.sender.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * RabbitMQ Broadcast WebSocket Message
 *
 */
@Data
public class RabbitMQWebSocketMessage implements Serializable {

    /**
     * Session Number
     */
    private String sessionId;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * User Number
     */
    private Long userId;

    /**
     * Message type
     */
    private String messageType;
    /**
     * Message content
     */
    private String messageContent;

}
