package cn.econets.blossom.framework.websocket.core.sender.rocketmq;

import lombok.Data;

/**
 * RocketMQ Broadcast WebSocket Message
 *
 */
@Data
public class RocketMQWebSocketMessage {

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
