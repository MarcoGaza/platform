package cn.econets.blossom.framework.xss.config;

import cn.econets.blossom.framework.common.enums.WebFilterOrderEnum;
import cn.econets.blossom.framework.web.config.WebAutoConfiguration;
import cn.econets.blossom.framework.xss.core.XssStringJsonDeserializer;
import cn.econets.blossom.framework.xss.core.clean.JsoupXssCleaner;
import cn.econets.blossom.framework.xss.core.clean.XssCleaner;
import cn.econets.blossom.framework.xss.core.filter.XssFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.PathMatcher;

/**
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(XssProperties.class)
// Set to false Time，Disable
@ConditionalOnProperty(prefix = "application.xss", name = "enable", havingValue = "true", matchIfMissing = true)
public class XssAutoConfiguration {
    /**
     * Xss Cleaner
     *
     * @return XssCleaner
     */
    @Bean
    @ConditionalOnMissingBean(XssCleaner.class)
    public XssCleaner xssCleaner() {
        return new JsoupXssCleaner();
    }

    /**
     * Register Jackson Serializer，For processing json Type parameter xss Filter
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    @ConditionalOnMissingBean(name = "xssJacksonCustomizer")
    @ConditionalOnBean(ObjectMapper.class)
    @ConditionalOnProperty(value = "application.xss.enable", havingValue = "true")
    public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(XssCleaner xssCleaner) {
        // During deserialization xss Filter，Can be used instead XssStringJsonSerializer，Processed during serialization
        return builder -> builder.deserializerByType(String.class, new XssStringJsonDeserializer(xssCleaner));
    }

    /**
     * Create XssFilter Bean，Solved Xss Security Issues
     */
    @Bean
    @ConditionalOnBean(XssCleaner.class)
    public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties, PathMatcher pathMatcher, XssCleaner xssCleaner) {
        return WebAutoConfiguration.createFilterBean(new XssFilter(properties, pathMatcher, xssCleaner), WebFilterOrderEnum.XSS_FILTER);
    }
}
