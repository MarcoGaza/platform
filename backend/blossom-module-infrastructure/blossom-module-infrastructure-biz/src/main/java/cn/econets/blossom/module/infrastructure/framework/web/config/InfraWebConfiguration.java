package cn.econets.blossom.module.infrastructure.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infra Module web Component Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class InfraWebConfiguration {

    /**
     * infra Module API Grouping
     */
    @Bean
    public GroupedOpenApi infraGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("infra");
    }

}
