package cn.econets.blossom.module.system.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system Module API Grouping
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
