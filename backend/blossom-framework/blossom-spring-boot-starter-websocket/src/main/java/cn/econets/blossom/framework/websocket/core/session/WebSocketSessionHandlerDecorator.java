package cn.econets.blossom.framework.websocket.core.session;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * {@link WebSocketHandler} Decoration category，The following functions have been implemented：
 *
 * 1. {@link WebSocketSession} When connecting or closing，Use {@link #sessionManager} Manage
 * 2. Packaging {@link WebSocketSession} Support concurrent operations
 *
 */
public class WebSocketSessionHandlerDecorator extends WebSocketHandlerDecorator {

    /**
     * Sending time limit，Unit：Milliseconds
     */
    private static final Integer SEND_TIME_LIMIT = 1000 * 5;
    /**
     * Send message buffer online，Unit：bytes
     */
    private static final Integer BUFFER_SIZE_LIMIT = 1024 * 100;

    private final WebSocketSessionManager sessionManager;

    public WebSocketSessionHandlerDecorator(WebSocketHandler delegate,
                                            WebSocketSessionManager sessionManager) {
        super(delegate);
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Realization session Support concurrency，For reference https://blog.csdn.net/abu935009066/article/details/131218149
        session = new ConcurrentWebSocketSessionDecorator(session, SEND_TIME_LIMIT, BUFFER_SIZE_LIMIT);
        // Add to WebSocketSessionManager Medium
        sessionManager.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        sessionManager.removeSession(session);
    }

}
