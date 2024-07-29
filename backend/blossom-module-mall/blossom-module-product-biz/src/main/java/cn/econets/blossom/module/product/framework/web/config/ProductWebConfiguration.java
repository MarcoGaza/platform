package cn.econets.blossom.module.product.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * product Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class ProductWebConfiguration {

    /**
     * product Module API Grouping
     */
    @Bean
    public GroupedOpenApi productGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("product");
    }

}
