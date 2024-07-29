package cn.econets.blossom.framework.websocket.core.sender.kafka;

import lombok.Data;

/**
 * Kafka Broadcast WebSocket Message
 *
 */
@Data
public class KafkaWebSocketMessage {

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
