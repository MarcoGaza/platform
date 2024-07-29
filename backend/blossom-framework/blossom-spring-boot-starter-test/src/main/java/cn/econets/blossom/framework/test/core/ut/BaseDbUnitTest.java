package cn.econets.blossom.framework.test.core.ut;

import cn.econets.blossom.framework.datasource.config.BlossomDataSourceAutoConfiguration;
import cn.econets.blossom.framework.mybatis.config.MybatisAutoConfiguration;
import cn.econets.blossom.framework.test.config.SqlInitializationTestConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.github.yulichang.autoconfigure.MybatisPlusJoinAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * Memory dependent DB Unit test
 *
 * Attention，Service The same applies to layers。For Service Unit tests for layers，We target our own modules Mapper The one who left is H2 Memory database，For other modules Service The one who left is Mock Method
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BaseDbUnitTest.Application.class)
@ActiveProfiles("unit-test") // Set usage application-unit-test Configuration file
@Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // After each unit test is completed，Clean up DB
public class BaseDbUnitTest {

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
            MybatisPlusJoinAutoConfiguration.class, // MyBatis ofJoinConfiguration Class
    })
    public static class Application {
    }

}
