package cn.econets.blossom.module.bpm.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bpm Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class BpmWebConfiguration {

    /**
     * bpm Module API Grouping
     */
    @Bean
    public GroupedOpenApi bpmGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("bpm");
    }

}
