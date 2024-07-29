package cn.econets.blossom.module.promotion.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * promotion Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class PromotionWebConfiguration {

    /**
     * promotion Module API Grouping
     */
    @Bean
    public GroupedOpenApi promotionGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("promotion");
    }

}
