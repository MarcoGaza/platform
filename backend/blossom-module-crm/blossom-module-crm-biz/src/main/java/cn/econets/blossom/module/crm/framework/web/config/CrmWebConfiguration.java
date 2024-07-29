package cn.econets.blossom.module.crm.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * crm Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class CrmWebConfiguration {

    /**
     * crm Module API Grouping
     */
    @Bean
    public GroupedOpenApi crmGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("crm");
    }

}
