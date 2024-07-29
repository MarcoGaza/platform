package cn.econets.blossom.framework.websocket.core.session;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.websocket.core.util.WebSocketFrameworkUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Default {@link WebSocketSessionManager} Implementation class
 *
 */
public class WebSocketSessionManagerImpl implements WebSocketSessionManager {

    /**
     * id with WebSocketSession Mapping
     *
     * key：Session Number
     */
    private final ConcurrentMap<String, WebSocketSession> idSessions = new ConcurrentHashMap<>();

    /**
     * user with WebSocketSession Mapping
     *
     * key1：User Type
     * key2：User Number
     */
    private final ConcurrentMap<Integer, ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>>> userSessions
            = new ConcurrentHashMap<>();

    @Override
    public void addSession(WebSocketSession session) {
        // Add to idSessions Medium
        idSessions.put(session.getId(), session);
        // Add to userSessions Medium
        LoginUser user = WebSocketFrameworkUtils.getLoginUser(session);
        if (user == null) {
            return;
        }
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(user.getUserType());
        if (userSessionsMap == null) {
            userSessionsMap = new ConcurrentHashMap<>();
            if (userSessions.putIfAbsent(user.getUserType(), userSessionsMap) != null) {
                userSessionsMap = userSessions.get(user.getUserType());
            }
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(user.getId());
        if (sessions == null) {
            sessions = new CopyOnWriteArrayList<>();
            if (userSessionsMap.putIfAbsent(user.getId(), sessions) != null) {
                sessions = userSessionsMap.get(user.getId());
            }
        }
        sessions.add(session);
    }

    @Override
    public void removeSession(WebSocketSession session) {
        // Remove from idSessions Medium
        idSessions.remove(session.getId(), session);
        // Remove from idSessions Medium
        LoginUser user = WebSocketFrameworkUtils.getLoginUser(session);
        if (user == null) {
            return;
        }
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(user.getUserType());
        if (userSessionsMap == null) {
            return;
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(user.getId());
        sessions.removeIf(session0 -> session0.getId().equals(session.getId()));
        if (CollUtil.isEmpty(sessions)) {
            userSessionsMap.remove(user.getId(), sessions);
        }
    }

    @Override
    public WebSocketSession getSession(String id) {
        return idSessions.get(id);
    }

    @Override
    public Collection<WebSocketSession> getSessionList(Integer userType) {
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(userType);
        if (CollUtil.isEmpty(userSessionsMap)) {
            return new ArrayList<>();
        }
        LinkedList<WebSocketSession> result = new LinkedList<>(); // Avoid capacity expansion
        Long contextTenantId = TenantContextHolder.getTenantId();
        for (List<WebSocketSession> sessions : userSessionsMap.values()) {
            if (CollUtil.isEmpty(sessions)) {
                continue;
            }
            // Special：If tenant does not match，Directly exclude
            if (contextTenantId != null) {
                Long userTenantId = WebSocketFrameworkUtils.getTenantId(sessions.get(0));
                if (!contextTenantId.equals(userTenantId)) {
                    continue;
                }
            }
            result.addAll(sessions);
        }
        return result;
    }

    @Override
    public Collection<WebSocketSession> getSessionList(Integer userType, Long userId) {
        ConcurrentMap<Long, CopyOnWriteArrayList<WebSocketSession>> userSessionsMap = userSessions.get(userType);
        if (CollUtil.isEmpty(userSessionsMap)) {
            return new ArrayList<>();
        }
        CopyOnWriteArrayList<WebSocketSession> sessions = userSessionsMap.get(userId);
        return CollUtil.isNotEmpty(sessions) ? new ArrayList<>(sessions) : new ArrayList<>();
    }

}
