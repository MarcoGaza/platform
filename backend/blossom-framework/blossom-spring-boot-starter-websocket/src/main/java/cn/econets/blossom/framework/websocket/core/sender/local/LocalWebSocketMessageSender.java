package cn.econets.blossom.framework.websocket.core.sender.local;

import cn.econets.blossom.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;

/**
 * Local {@link WebSocketMessageSender} Implementation class
 *
 * Attention：Only suitable for single machine scenarios！！！
 *
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
