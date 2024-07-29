package cn.econets.blossom.module.infrastructure.api.websocket;

import cn.econets.blossom.framework.common.util.json.JsonUtils;

/**
 * WebSocket Transmitter API Interface
 *
 * Yes WebSocketMessageSender Encapsulate，Provided for use by other modules
 *
 */
public interface WebSocketSenderApi {

    /**
     * Send a message to a specified user
     *
     * @param userType User Type
     * @param userId User Number
     * @param messageType Message type
     * @param messageContent Message content，JSON Format
     */
    void send(Integer userType, Long userId, String messageType, String messageContent);

    /**
     * Send a message to the specified user type
     *
     * @param userType User Type
     * @param messageType Message type
     * @param messageContent Message content，JSON Format
     */
    void send(Integer userType, String messageType, String messageContent);

    /**
     * Send a message to the specified person Session
     *
     * @param sessionId Session Number
     * @param messageType Message type
     * @param messageContent Message content，JSON Format
     */
    void send(String sessionId, String messageType, String messageContent);

    default void sendObject(Integer userType, Long userId, String messageType, Object messageContent) {
        send(userType, userId, messageType, JsonUtils.toJsonString(messageContent));
    }

    default void sendObject(Integer userType, String messageType, Object messageContent) {
        send(userType, messageType, JsonUtils.toJsonString(messageContent));
    }

    default void sendObject(String sessionId, String messageType, Object messageContent) {
        send(sessionId, messageType, JsonUtils.toJsonString(messageContent));
    }

}
