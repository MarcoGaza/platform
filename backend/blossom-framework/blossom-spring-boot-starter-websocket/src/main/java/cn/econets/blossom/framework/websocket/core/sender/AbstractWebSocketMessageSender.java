package cn.econets.blossom.framework.websocket.core.sender;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.websocket.core.message.JsonWebSocketMessage;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * WebSocketMessageSender Implementation class
 *
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {

    private final WebSocketSessionManager sessionManager;

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        send(null, userType, userId, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        send(null, userType, null, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        send(sessionId, null, null, messageType, messageContent);
    }

    /**
     * Send message
     *
     * @param sessionId Session Number
     * @param userType User Type
     * @param userId User ID
     * @param messageType Message type
     * @param messageContent Message content
     */
    public void send(String sessionId, Integer userType, Long userId, String messageType, String messageContent) {
        // 1. Get Session List
        List<WebSocketSession> sessions = Collections.emptyList();
        if (StrUtil.isNotEmpty(sessionId)) {
            WebSocketSession session = sessionManager.getSession(sessionId);
            if (session != null) {
                sessions = Collections.singletonList(session);
            }
        } else if (userType != null && userId != null) {
            sessions = (List<WebSocketSession>) sessionManager.getSessionList(userType, userId);
        } else if (userType != null) {
            sessions = (List<WebSocketSession>) sessionManager.getSessionList(userType);
        }
        if (CollUtil.isEmpty(sessions)) {
            log.info("[send][sessionId({}) userType({}) userId({}) messageType({}) messageContent({}) No session matched]",
                    sessionId, userType, userId, messageType, messageContent);
        }
        // 2. Execute send
        doSend(sessions, messageType, messageContent);
    }

    /**
     * The specific implementation of sending messages
     *
     * @param sessions Session List
     * @param messageType Message type
     * @param messageContent Message content
     */
    public void doSend(Collection<WebSocketSession> sessions, String messageType, String messageContent) {
        JsonWebSocketMessage message = new JsonWebSocketMessage().setType(messageType).setContent(messageContent);
        String payload = JsonUtils.toJsonString(message); // Key，Use JSON Serialization
        sessions.forEach(session -> {
            // 1. Various verifications，Guarantee Session Can be sent
            if (session == null) {
                log.error("[doSend][session Empty, message({})]", message);
                return;
            }
            if (!session.isOpen()) {
                log.error("[doSend][session({}) Closed, message({})]", session.getId(), message);
                return;
            }
            // 2. Execute send
            try {
                session.sendMessage(new TextMessage(payload));
                log.info("[doSend][session({}) Message sent successfully，message({})]", session.getId(), message);
            } catch (IOException ex) {
                log.error("[doSend][session({}) Failed to send message，message({})]", session.getId(), message, ex);
            }
        });
    }

}
