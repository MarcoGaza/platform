package cn.econets.blossom.module.trade.framework.web.config;

import cn.econets.blossom.framework.swagger.SwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * trade Module web Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class TradeWebConfiguration {

    /**
     * trade Module API Grouping
     */
    @Bean
    public GroupedOpenApi tradeGroupedOpenApi() {
        return SwaggerAutoConfiguration.buildGroupedOpenApi("trade");
    }

}
