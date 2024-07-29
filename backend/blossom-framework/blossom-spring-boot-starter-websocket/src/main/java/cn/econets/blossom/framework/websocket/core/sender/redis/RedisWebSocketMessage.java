package cn.econets.blossom.framework.websocket.core.sender.redis;

import cn.econets.blossom.framework.mq.redis.core.pubsub.AbstractRedisChannelMessage;
import lombok.Data;

/**
 * Redis Broadcast WebSocket Message
 */
@Data
public class RedisWebSocketMessage extends AbstractRedisChannelMessage {

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
