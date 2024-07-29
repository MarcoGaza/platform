package cn.econets.blossom.framework.websocket.config;

import cn.econets.blossom.framework.mq.redis.config.RedisMQConsumerAutoConfiguration;
import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.websocket.core.handler.JsonWebSocketMessageHandler;
import cn.econets.blossom.framework.websocket.core.listener.WebSocketMessageListener;
import cn.econets.blossom.framework.websocket.core.security.LoginUserHandshakeInterceptor;
import cn.econets.blossom.framework.websocket.core.sender.kafka.KafkaWebSocketMessageConsumer;
import cn.econets.blossom.framework.websocket.core.sender.kafka.KafkaWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.local.LocalWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.rabbitmq.RabbitMQWebSocketMessageConsumer;
import cn.econets.blossom.framework.websocket.core.sender.rabbitmq.RabbitMQWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.redis.RedisWebSocketMessageConsumer;
import cn.econets.blossom.framework.websocket.core.sender.redis.RedisWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.sender.rocketmq.RocketMQWebSocketMessageConsumer;
import cn.econets.blossom.framework.websocket.core.sender.rocketmq.RocketMQWebSocketMessageSender;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionHandlerDecorator;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManager;
import cn.econets.blossom.framework.websocket.core.session.WebSocketSessionManagerImpl;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;

/**
 * WebSocket Automatic configuration
 *
 */
@AutoConfiguration(before = RedisMQConsumerAutoConfiguration.class) // before RedisMQConsumerAutoConfiguration The reason is，Guarantee required RedisWebSocketMessageConsumer Create first，Can create RedisMessageListenerContainer
@EnableWebSocket // Open websocket
@ConditionalOnProperty(prefix = "application.websocket", value = "enable", matchIfMissing = true) // Allow use application.websocket.enable=false Disable websocket
@EnableConfigurationProperties(WebSocketProperties.class)
public class WebSocketAutoConfiguration {

    @Bean
    public WebSocketConfigurer webSocketConfigurer(HandshakeInterceptor[] handshakeInterceptors,
                                                   WebSocketHandler webSocketHandler,
                                                   WebSocketProperties webSocketProperties) {
        return registry -> registry
                // Add WebSocketHandler
                .addHandler(webSocketHandler, webSocketProperties.getPath())
                .addInterceptors(handshakeInterceptors)
                // Allow cross-domain，Otherwise the front-end connection will be disconnected directly
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new LoginUserHandshakeInterceptor();
    }

    @Bean
    public WebSocketHandler webSocketHandler(WebSocketSessionManager sessionManager,
                                             List<? extends WebSocketMessageListener<?>> messageListeners) {
        // 1. Create JsonWebSocketMessageHandler Object，Processing message
        JsonWebSocketMessageHandler messageHandler = new JsonWebSocketMessageHandler(messageListeners);
        // 2. Create WebSocketSessionHandlerDecorator Object，Handling connections
        return new WebSocketSessionHandlerDecorator(messageHandler, sessionManager);
    }

    @Bean
    public WebSocketSessionManager webSocketSessionManager() {
        return new WebSocketSessionManagerImpl();
    }

    // ==================== Sender Related ====================

    @Configuration
    @ConditionalOnProperty(prefix = "application.websocket", name = "sender-type", havingValue = "local", matchIfMissing = true)
    public class LocalWebSocketMessageSenderConfiguration {

        @Bean
        public LocalWebSocketMessageSender localWebSocketMessageSender(WebSocketSessionManager sessionManager) {
            return new LocalWebSocketMessageSender(sessionManager);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "application.websocket", name = "sender-type", havingValue = "redis", matchIfMissing = true)
    public class RedisWebSocketMessageSenderConfiguration {

        @Bean
        public RedisWebSocketMessageSender redisWebSocketMessageSender(WebSocketSessionManager sessionManager,
                                                                       RedisMQTemplate redisMQTemplate) {
            return new RedisWebSocketMessageSender(sessionManager, redisMQTemplate);
        }

        @Bean
        public RedisWebSocketMessageConsumer redisWebSocketMessageConsumer(
                RedisWebSocketMessageSender redisWebSocketMessageSender) {
            return new RedisWebSocketMessageConsumer(redisWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "application.websocket", name = "sender-type", havingValue = "rocketmq", matchIfMissing = true)
    public class RocketMQWebSocketMessageSenderConfiguration {

        @Bean
        public RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RocketMQTemplate rocketMQTemplate,
                @Value("${application.websocket.sender-rocketmq.topic}") String topic) {
            return new RocketMQWebSocketMessageSender(sessionManager, rocketMQTemplate, topic);
        }

        @Bean
        public RocketMQWebSocketMessageConsumer rocketMQWebSocketMessageConsumer(
                RocketMQWebSocketMessageSender rocketMQWebSocketMessageSender) {
            return new RocketMQWebSocketMessageConsumer(rocketMQWebSocketMessageSender);
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "application.websocket", name = "sender-type", havingValue = "rabbitmq", matchIfMissing = true)
    public class RabbitMQWebSocketMessageSenderConfiguration {

        @Bean
        public RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender(
                WebSocketSessionManager sessionManager, RabbitTemplate rabbitTemplate,
                TopicExchange websocketTopicExchange) {
            return new RabbitMQWebSocketMessageSender(sessionManager, rabbitTemplate, websocketTopicExchange);
        }

        @Bean
        public RabbitMQWebSocketMessageConsumer rabbitMQWebSocketMessageConsumer(
                RabbitMQWebSocketMessageSender rabbitMQWebSocketMessageSender) {
            return new RabbitMQWebSocketMessageConsumer(rabbitMQWebSocketMessageSender);
        }

        /**
         * Create Topic Exchange
         */
        @Bean
        public TopicExchange websocketTopicExchange(@Value("${application.websocket.sender-rabbitmq.exchange}") String exchange) {
            return new TopicExchange(exchange,
                    true,  // durable: Whether to persist
                    false);  // exclusive: Is it exclusive?
        }

    }

    @Configuration
    @ConditionalOnProperty(prefix = "application.websocket", name = "sender-type", havingValue = "kafka", matchIfMissing = true)
    public class KafkaWebSocketMessageSenderConfiguration {

        @Bean
        public KafkaWebSocketMessageSender kafkaWebSocketMessageSender(
                WebSocketSessionManager sessionManager, KafkaTemplate<Object, Object> kafkaTemplate,
                @Value("${application.websocket.sender-kafka.topic}") String topic) {
            return new KafkaWebSocketMessageSender(sessionManager, kafkaTemplate, topic);
        }

        @Bean
        public KafkaWebSocketMessageConsumer kafkaWebSocketMessageConsumer(
                KafkaWebSocketMessageSender kafkaWebSocketMessageSender) {
            return new KafkaWebSocketMessageConsumer(kafkaWebSocketMessageSender);
        }

    }

}