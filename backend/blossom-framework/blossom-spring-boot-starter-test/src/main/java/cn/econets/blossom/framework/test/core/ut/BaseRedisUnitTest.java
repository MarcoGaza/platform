package cn.econets.blossom.framework.test.core.ut;

import cn.econets.blossom.framework.redis.config.BlossomRedisAutoConfiguration;
import cn.econets.blossom.framework.test.config.RedisTestConfiguration;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Memory dependent Redis Unit tests
 *
 * Compared to {@link BaseDbUnitTest} Let's say，From memory DB Changed to memory Redis
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseRedisUnitTest.Application.class)
@ActiveProfiles("unit-test") // Set usage application-unit-test Configuration file
public class BaseRedisUnitTest {

    @Import({
            // Redis Configuration Class
            RedisTestConfiguration.class, // Redis Test configuration class，For startup RedisServer
            RedisAutoConfiguration.class, // Spring Redis Automatic configuration class
            BlossomRedisAutoConfiguration.class, // Own Redis Configuration Class
            RedissonAutoConfiguration.class, // Redisson Automatic high configuration class
    })
    public static class Application {
    }

}
