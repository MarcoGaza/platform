package cn.econets.blossom.framework.websocket.core.listener;

import cn.econets.blossom.framework.websocket.core.message.JsonWebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocket Message listener interface
 *
 * Purpose：After the front end sends a message to the back end，Processing response {@link #getType()} Type of message
 *
 * @param <T> Generic，Message type
 */
public interface WebSocketMessageListener<T> {

    /**
     * Processing message
     *
     * @param session Session
     * @param message Message
     */
    void onMessage(WebSocketSession session, T message);

    /**
     * Get message type
     *
     * @see JsonWebSocketMessage#getType()
     * @return Message type
     */
    String getType();

}
