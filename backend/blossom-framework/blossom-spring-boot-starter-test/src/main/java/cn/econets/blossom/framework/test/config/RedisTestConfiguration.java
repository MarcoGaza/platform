package cn.econets.blossom.framework.test.config;

import com.github.fppt.jedismock.RedisServer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

/**
 * Redis Test Configuration，Mainly implements embedding Redis Startup
 *
 */
@Configuration(proxyBeanMethods = false)
@Lazy(false) // Disable delayed loading
@EnableConfigurationProperties(RedisProperties.class)
public class RedisTestConfiguration {

    /**
     * Create a simulation Redis Server Server
     */
    @Bean
    public RedisServer redisServer(RedisProperties properties) throws IOException {
        RedisServer redisServer = new RedisServer(properties.getPort());
        // When executing multiple unit tests at once，It seems to create multiple spring Container，results in non-conduct stop。Like this，The port is occupied，Unable to start。。。
        try {
            redisServer.start();
        } catch (Exception ignore) {}
        return redisServer;
    }

}
