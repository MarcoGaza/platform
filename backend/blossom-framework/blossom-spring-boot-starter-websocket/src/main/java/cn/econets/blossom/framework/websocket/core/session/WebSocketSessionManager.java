package cn.econets.blossom.framework.websocket.core.session;

import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

/**
 * {@link WebSocketSession} Manager interface
 *
 */
public interface WebSocketSessionManager {

    /**
     * Add Session
     *
     * @param session Session
     */
    void addSession(WebSocketSession session);

    /**
     * Remove Session
     *
     * @param session Session
     */
    void removeSession(WebSocketSession session);

    /**
     * Get the specified number Session
     *
     * @param id Session Number
     * @return Session
     */
    WebSocketSession getSession(String id);

    /**
     * Get the specified user type Session List
     *
     * @param userType User Type
     * @return Session List
     */
    Collection<WebSocketSession> getSessionList(Integer userType);

    /**
     * Get the specified user number Session List
     *
     * @param userType User Type
     * @param userId User Number
     * @return Session List
     */
    Collection<WebSocketSession> getSessionList(Integer userType, Long userId);

}