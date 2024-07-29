package cn.econets.blossom.framework.websocket.core.util;

import cn.econets.blossom.framework.security.core.LoginUser;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * Exclusively for web Package tool class
 *
 */
public class WebSocketFrameworkUtils {

    public static final String ATTRIBUTE_LOGIN_USER = "LOGIN_USER";

    /**
     * Set current user
     *
     * @param loginUser Logged in user
     * @param attributes Session
     */
    public static void setLoginUser(LoginUser loginUser, Map<String, Object> attributes) {
        attributes.put(ATTRIBUTE_LOGIN_USER, loginUser);
    }

    /**
     * Get the current user
     *
     * @return Current user
     */
    public static LoginUser getLoginUser(WebSocketSession session) {
        return (LoginUser) session.getAttributes().get(ATTRIBUTE_LOGIN_USER);
    }

    /**
     * Get the current user's ID
     *
     * @return User Number
     */
    public static Long getLoginUserId(WebSocketSession session) {
        LoginUser loginUser = getLoginUser(session);
        return loginUser != null ? loginUser.getId() : null;
    }

    /**
     * Get the type of the current user
     *
     * @return User Number
     */
    public static Integer getLoginUserType(WebSocketSession session) {
        LoginUser loginUser = getLoginUser(session);
        return loginUser != null ? loginUser.getUserType() : null;
    }

    /**
     * Get the tenant ID of the current user
     *
     * @param session Session
     * @return Tenant Number
     */
    public static Long getTenantId(WebSocketSession session) {
        LoginUser loginUser = getLoginUser(session);
        return loginUser != null ? loginUser.getTenantId() : null;
    }

}
