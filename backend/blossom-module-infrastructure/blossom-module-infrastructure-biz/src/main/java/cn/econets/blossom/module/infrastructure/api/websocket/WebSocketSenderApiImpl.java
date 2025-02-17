package cn.econets.blossom.module.infrastructure.api.websocket;

import cn.econets.blossom.framework.websocket.core.sender.WebSocketMessageSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * WebSocket Transmitter API Implementation class
 *
 */
@Component
public class WebSocketSenderApiImpl implements WebSocketSenderApi {

    @Resource
    private WebSocketMessageSender webSocketMessageSender;

    @Override
    public void send(Integer userType, Long userId, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, userId, messageType, messageContent);
    }

    @Override
    public void send(Integer userType, String messageType, String messageContent) {
        webSocketMessageSender.send(userType, messageType, messageContent);
    }

    @Override
    public void send(String sessionId, String messageType, String messageContent) {
        webSocketMessageSender.send(sessionId, messageType, messageContent);
    }

}
