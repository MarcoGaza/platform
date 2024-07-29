package cn.econets.blossom.module.pay.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * pay Module web Component Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class PayWebConfiguration {

    /**
     * pay Module API Grouping
     */
    @Bean
    public GroupedOpenApi payGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("pay");
    }

}
