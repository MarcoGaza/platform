package cn.econets.blossom.module.mp.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mp Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class MpWebConfiguration {

    /**
     * mp Module API Grouping
     */
    @Bean
    public GroupedOpenApi mpGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("mp");
    }

}
