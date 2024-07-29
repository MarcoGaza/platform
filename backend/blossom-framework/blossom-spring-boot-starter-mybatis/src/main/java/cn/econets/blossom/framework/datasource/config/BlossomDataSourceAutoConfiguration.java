package cn.econets.blossom.framework.datasource.config;

import cn.econets.blossom.framework.datasource.core.filter.DruidAdRemoveFilter;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration class
 */
@AutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true) // Start transaction management
@EnableConfigurationProperties(DruidStatProperties.class)
public class BlossomDataSourceAutoConfiguration {

    /**
     * Create DruidAdRemoveFilter Filter，Filter common.js Advertisement
     */
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.druid.web-stat-filter.enabled", havingValue = "true")
    public FilterRegistrationBean<DruidAdRemoveFilter> druidAdRemoveFilterFilter(DruidStatProperties properties) {
        // Get druid web Parameters of the monitoring page
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        // Extract common.js Configuration path
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        // Create DruidAdRemoveFilter Bean
        FilterRegistrationBean<DruidAdRemoveFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DruidAdRemoveFilter());
        registrationBean.addUrlPatterns(commonJsPattern);
        return registrationBean;
    }
}
