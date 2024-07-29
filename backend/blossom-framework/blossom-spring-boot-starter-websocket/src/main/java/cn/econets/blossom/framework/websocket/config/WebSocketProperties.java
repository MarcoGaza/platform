package cn.econets.blossom.framework.websocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * WebSocket Configuration item
 *
 */
@ConfigurationProperties("application.websocket")
@Data
@Validated
public class WebSocketProperties {

    /**
     * WebSocket Connection path
     */
    @NotEmpty(message = "WebSocket The connection path cannot be empty")
    private String path = "/ws";

    /**
     * Type of message sender
     *
     * Optional value：local、redis、rocketmq、kafka、rabbitmq
     */
    @NotNull(message = "WebSocket The message sender cannot be empty")
    private String senderType = "local";

}
