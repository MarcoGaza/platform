package cn.econets.blossom.framework.websocket.core.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.framework.websocket.core.listener.WebSocketMessageListener;
import cn.econets.blossom.framework.websocket.core.message.JsonWebSocketMessage;
import cn.econets.blossom.framework.websocket.core.util.WebSocketFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * JSON Format {@link WebSocketHandler} Implementation class
 *
 * Based on {@link JsonWebSocketMessage#getType()} Message type，Dispatched to the corresponding {@link WebSocketMessageListener} Listener。
 *
 */
@Slf4j
public class JsonWebSocketMessageHandler extends TextWebSocketHandler {

    /**
     * type with WebSocketMessageListener Mapping
     */
    private final Map<String, WebSocketMessageListener<Object>> listeners = new HashMap<>();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public JsonWebSocketMessageHandler(List<? extends WebSocketMessageListener> listenersList) {
        listenersList.forEach((Consumer<WebSocketMessageListener>)
                listener -> listeners.put(listener.getType(), listener));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1.1 Empty message，Skip
        if (message.getPayloadLength() == 0) {
            return;
        }
        // 1.2 ping Heartbeat message，Return directly pong Message。
        if (message.getPayloadLength() == 4 && Objects.equals(message.getPayload(), "ping")) {
            session.sendMessage(new TextMessage("pong"));
            return;
        }

        // 2.1 Parsing message
        try {
            JsonWebSocketMessage jsonMessage = JsonUtils.parseObject(message.getPayload(), JsonWebSocketMessage.class);
            if (jsonMessage == null) {
                log.error("[handleTextMessage][session({}) message({}) The resolution is empty]", session.getId(), message.getPayload());
                return;
            }
            if (StrUtil.isEmpty(jsonMessage.getType())) {
                log.error("[handleTextMessage][session({}) message({}) Type is empty]", session.getId(), message.getPayload());
                return;
            }
            // 2.2 Get the corresponding WebSocketMessageListener
            WebSocketMessageListener<Object> messageListener = listeners.get(jsonMessage.getType());
            if (messageListener == null) {
                log.error("[handleTextMessage][session({}) message({}) The listener is empty]", session.getId(), message.getPayload());
                return;
            }
            // 2.3 Processing message
            Type type = TypeUtil.getTypeArgument(messageListener.getClass(), 0);
            Object messageObj = JsonUtils.parseObject(jsonMessage.getContent(), type);
            Long tenantId = WebSocketFrameworkUtils.getTenantId(session);
            TenantUtils.execute(tenantId, () -> messageListener.onMessage(session, messageObj));
        } catch (Throwable ex) {
            log.error("[handleTextMessage][session({}) message({}) Handling exceptions]", session.getId(), message.getPayload());
        }
    }

}
