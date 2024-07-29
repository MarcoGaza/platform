package cn.econets.blossom.module.member.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * member Module web Component Configuration
 *
 *
 */
@Configuration(proxyBeanMethods = false)
public class MemberWebConfiguration {

    /**
     * member Module API Grouping
     */
    @Bean
    public GroupedOpenApi memberGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("member");
    }

}
