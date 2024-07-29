package cn.econets.blossom.framework.websocket.core.message;

import cn.econets.blossom.framework.websocket.core.listener.WebSocketMessageListener;
import lombok.Data;

import java.io.Serializable;

/**
 * JSON Format WebSocket Message frame
 *
 */
@Data
public class JsonWebSocketMessage implements Serializable {

    /**
     * Message type
     *
     * Purpose：Used to distribute to corresponding {@link WebSocketMessageListener} Implementation class
     */
    private String type;
    /**
     * Message content
     *
     * Requirements JSON Object
     */
    private String content;

}
