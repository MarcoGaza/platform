package cn.econets.blossom.framework.websocket.core.security;

import cn.econets.blossom.framework.security.config.AuthorizeRequestsCustomizer;
import cn.econets.blossom.framework.websocket.config.WebSocketProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * WebSocket Customized permissions
 *
 */
@RequiredArgsConstructor
public class WebSocketAuthorizeRequestsCustomizer extends AuthorizeRequestsCustomizer {

    private final WebSocketProperties webSocketProperties;

    @Override
    public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry.antMatchers(webSocketProperties.getPath()).permitAll();
    }

}
