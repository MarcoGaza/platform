package cn.econets.blossom.framework.mq.redis.config;

import cn.econets.blossom.framework.mq.redis.core.RedisMQTemplate;
import cn.econets.blossom.framework.mq.redis.core.job.RedisPendingMessageResendJob;
import cn.econets.blossom.framework.mq.redis.core.pubsub.AbstractRedisChannelMessageListener;
import cn.econets.blossom.framework.mq.redis.core.stream.AbstractRedisStreamMessageListener;
import cn.econets.blossom.framework.redis.config.BlossomRedisAutoConfiguration;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.Properties;

/**
 * Redis Message Queue Consumer Configuration Class
 *
 */
@Slf4j
@EnableScheduling // Enable scheduled tasks，Used for RedisPendingMessageResendJob Resend message
@AutoConfiguration(after = BlossomRedisAutoConfiguration.class)
public class RedisMQConsumerAutoConfiguration {

    /**
     * Create Redis Pub/Sub Broadcast consumption container
     */
    @Bean
    @ConditionalOnBean(AbstractRedisChannelMessageListener.class) // Only AbstractChannelMessageListener When it exists，Registration is required Redis pubsub Monitor
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisMQTemplate redisMQTemplate, List<AbstractRedisChannelMessageListener<?>> listeners) {
        // Create RedisMessageListenerContainer Object
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // Settings RedisConnection Factory。
        container.setConnectionFactory(redisMQTemplate.getRedisTemplate().getRequiredConnectionFactory());
        // Add listener
        listeners.forEach(listener -> {
            listener.setRedisMQTemplate(redisMQTemplate);
            container.addMessageListener(listener, new ChannelTopic(listener.getChannel()));
            log.info("[redisMessageListenerContainer][Register Channel({}) Corresponding listener({})]",
                    listener.getChannel(), listener.getClass().getName());
        });
        return container;
    }

    /**
     * Create Redis Stream Re-consumption task
     */
    @Bean
    @ConditionalOnBean(AbstractRedisStreamMessageListener.class) // Only AbstractStreamMessageListener When it exists，Registration is required Redis pubsub Monitor
    public RedisPendingMessageResendJob redisPendingMessageResendJob(List<AbstractRedisStreamMessageListener<?>> listeners,
                                                                     RedisMQTemplate redisTemplate,
                                                                     @Value("${spring.application.name}") String groupName,
                                                                     RedissonClient redissonClient) {
        return new RedisPendingMessageResendJob(listeners, redisTemplate, groupName, redissonClient);
    }

    /**
     * Create Redis Stream Container for cluster consumption
     *
     * Basic knowledge：<a href="https://www.geek-book.com/src/docs/redis/redis/redis.io/commands/xreadgroup.html">Redis Stream of xreadgroup Command</a>
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnBean(AbstractRedisStreamMessageListener.class) // Only AbstractStreamMessageListener When it exists，Registration is required Redis pubsub Monitor
    public StreamMessageListenerContainer<String, ObjectRecord<String, String>> redisStreamMessageListenerContainer(
            RedisMQTemplate redisMQTemplate, List<AbstractRedisStreamMessageListener<?>> listeners) {
        RedisTemplate<String, ?> redisTemplate = redisMQTemplate.getRedisTemplate();
        checkRedisVersion(redisTemplate);
        // First step，Create StreamMessageListenerContainer Container
        // Create options Configuration
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> containerOptions =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
                        .batchSize(10) // How many messages can be pulled at one time at most
                        .targetType(String.class) // Target type。Use uniformly String，Through self-encapsulation AbstractStreamMessageListener Deserialize
                        .build();
        // Create container Object
        StreamMessageListenerContainer<String, ObjectRecord<String, String>> container =
                StreamMessageListenerContainer.create(redisMQTemplate.getRedisTemplate().getRequiredConnectionFactory(), containerOptions);

        // Step 2，Register Listener，Consumption corresponding to Stream Topic
        String consumerName = buildConsumerName();
        listeners.parallelStream().forEach(listener -> {
            log.info("[redisStreamMessageListenerContainer][Start registration StreamKey({}) Corresponding listener({})]",
                    listener.getStreamKey(), listener.getClass().getName());
            // Create listener Corresponding consumer groups
            try {
                redisTemplate.opsForStream().createGroup(listener.getStreamKey(), listener.getGroup());
            } catch (Exception ignore) {
            }
            // Settings listener Corresponding redisTemplate
            listener.setRedisMQTemplate(redisMQTemplate);
            // Create Consumer Object
            Consumer consumer = Consumer.from(listener.getGroup(), consumerName);
            // Settings Consumer Consumption progress，Subject to the minimum consumption progress
            StreamOffset<String> streamOffset = StreamOffset.create(listener.getStreamKey(), ReadOffset.lastConsumed());
            // Settings Consumer Monitor
            StreamMessageListenerContainer.StreamReadRequestBuilder<String> builder = StreamMessageListenerContainer.StreamReadRequest
                    .builder(streamOffset).consumer(consumer)
                    .autoAcknowledge(false) // Not automatic ack
                    .cancelOnError(throwable -> false); // Default Configuration，Cancel consumption if an exception occurs，Obviously not meeting expectations；Therefore，We set it to false
            container.register(builder.build(), listener);
            log.info("[redisStreamMessageListenerContainer][Complete registration StreamKey({}) Corresponding listener({})]",
                    listener.getStreamKey(), listener.getClass().getName());
        });
        return container;
    }

    /**
     * Construct consumer name，Use local IP + Process numbering method。
     * Reference from RocketMQ clientId Realization
     *
     * @return Consumer Name
     */
    private static String buildConsumerName() {
        return String.format("%s@%d", SystemUtil.getHostInfo().getAddress(), SystemUtil.getCurrentPID());
    }

    /**
     * Verification Redis Version number，Whether the minimum version number requirement is met！
     */
    private static void checkRedisVersion(RedisTemplate<String, ?> redisTemplate) {
        // Get Redis Version
        Properties info = redisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        String version = MapUtil.getStr(info, "redis_version");
        // The minimum version to be verified must be greater than or equal to 5.0.0
        int majorVersion = Integer.parseInt(StrUtil.subBefore(version, '.', false));
        if (majorVersion < 5) {
            throw new IllegalStateException(StrUtil.format("Your current Redis Version is {}，Less than the minimum requirement 5.0.0 Version！", version));
        }
    }

}
