package cn.econets.blossom.framework.test.core.ut;

import cn.econets.blossom.framework.datasource.config.BlossomDataSourceAutoConfiguration;
import cn.econets.blossom.framework.mybatis.config.MybatisAutoConfiguration;
import cn.econets.blossom.framework.redis.config.BlossomRedisAutoConfiguration;
import cn.econets.blossom.framework.test.config.RedisTestConfiguration;
import cn.econets.blossom.framework.test.config.SqlInitializationTestConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * Memory dependent DB + Redis Unit test
 *
 * Compared to {@link BaseDbUnitTest} Let's say，Additional memory added Redis
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseDbAndRedisUnitTest.Application.class)
@ActiveProfiles("unit-test") // Set usage application-unit-test Configuration file
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // After each unit test is completed，Clean up DB
public class BaseDbAndRedisUnitTest {

    @Import({
            // DB Configuration Class
            BlossomDataSourceAutoConfiguration.class, // Own DB Configuration Class
            DataSourceAutoConfiguration.class, // Spring DB Automatic configuration class
            DataSourceTransactionManagerAutoConfiguration.class, // Spring Transaction automatic configuration class
            DruidDataSourceAutoConfigure.class, // Druid Automatic configuration class
            SqlInitializationTestConfiguration.class, // SQL Initialization
            // MyBatis Configuration Class
            MybatisAutoConfiguration.class, // Own MyBatis Configuration Class
            MybatisPlusAutoConfiguration.class, // MyBatis Automatic configuration class

            // Redis Configuration Class
            RedisTestConfiguration.class, // Redis Test configuration class，For startup RedisServer
//            RedisAutoConfiguration.class, // Spring Redis Automatic configuration class
            BlossomRedisAutoConfiguration.class, // Own Redis Configuration Class
            RedissonAutoConfiguration.class, // Redisson Automatic high configuration class
    })
    public static class Application {
    }

}
