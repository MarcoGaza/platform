package cn.econets.blossom.framework.redis.config;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis Configuration class
 */
@AutoConfiguration
public class BlossomRedisAutoConfiguration {
    /**
     * Create RedisTemplate Bean，Use JSON Serialization method
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // Create RedisTemplate Object
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Settings RedisConnection Factory。It is to achieve multiple Java Redis Secret factory accessed by the client。
        template.setConnectionFactory(factory);
        // Use String Serialization method，Serialization KEY 。
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // Use JSON Serialization method（Library is Jackson ），Serialization VALUE 。
        template.setValueSerializer(buildRedisSerializer());
        template.setHashValueSerializer(buildRedisSerializer());
        return template;
    }

    public static RedisSerializer<?> buildRedisSerializer() {
        RedisSerializer<Object> json = RedisSerializer.json();
        // Solved LocalDateTime Serialization
        ObjectMapper objectMapper = (ObjectMapper) ReflectUtil.getFieldValue(json, "mapper");
        objectMapper.registerModules(new JavaTimeModule());
        return json;
    }
}
