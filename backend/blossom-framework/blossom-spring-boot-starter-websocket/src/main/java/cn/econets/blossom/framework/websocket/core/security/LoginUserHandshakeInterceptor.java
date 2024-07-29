package cn.econets.blossom.framework.websocket.core.security;

import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.security.core.filter.TokenAuthenticationFilter;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.framework.websocket.core.util.WebSocketFrameworkUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Logged-in user {@link HandshakeInterceptor} Implementation class
 *
 * The process is as follows：
 * 1. Front-end connection websocket Time，Will be spliced ?token={token} To ws:// After connecting，So that it can be {@link TokenAuthenticationFilter} Certified
 * 2. {@link LoginUserHandshakeInterceptor} Responsible for {@link LoginUser} Add to {@link WebSocketSession} Medium
 *
 */
public class LoginUserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            WebSocketFrameworkUtils.setLoginUser(loginUser, attributes);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // do nothing
    }

}
