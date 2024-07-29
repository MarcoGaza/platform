package cn.econets.blossom.framework.web.config;

import cn.econets.blossom.framework.common.enums.WebFilterOrderEnum;
import cn.econets.blossom.framework.apilog.core.service.ApiErrorLogFrameworkService;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.framework.web.core.filter.CacheRequestBodyFilter;
import cn.econets.blossom.framework.web.core.handler.GlobalExceptionHandler;
import cn.econets.blossom.framework.web.core.handler.GlobalResponseBodyHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 *
 */
@AutoConfiguration
@EnableConfigurationProperties(WebProperties.class)
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private WebProperties webProperties;
    /**
     * Application Name
     */
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurePathMatch(configurer, webProperties.getAdminApi());
        configurePathMatch(configurer, webProperties.getAppApi());
    }

    /**
     * Settings API Prefix，Match only controller Packed
     *
     * @param configurer Configuration
     * @param api        API Configuration
     */
    private void configurePathMatch(PathMatchConfigurer configurer, WebProperties.Api api) {
        AntPathMatcher antPathMatcher = new AntPathMatcher(".");
        configurer.addPathPrefix(api.getPrefix(), clazz -> clazz.isAnnotationPresent(RestController.class)
                && antPathMatcher.match(api.getController(), clazz.getPackage().getName())); // Match only controller Package
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(ApiErrorLogFrameworkService ApiErrorLogFrameworkService) {
        return new GlobalExceptionHandler(applicationName, ApiErrorLogFrameworkService);
    }

    @Bean
    public GlobalResponseBodyHandler globalResponseBodyHandler() {
        return new GlobalResponseBodyHandler();
    }

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public WebFrameworkUtils webFrameworkUtils(WebProperties webProperties) {
        // Because WebFrameworkUtils Need to use webProperties Properties，So register as one Bean
        return new WebFrameworkUtils(webProperties);
    }


    // ========== Filter Related ==========
    /**
     * Create CorsFilter Bean，Solve cross-domain issues
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        // Create CorsConfiguration Object
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // Set the access source address
        config.addAllowedHeader("*"); // Set the access source request header
        config.addAllowedMethod("*"); // Set the access source request method
        // Create UrlBasedCorsConfigurationSource Object
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Configure cross-domain settings for interfaces
        return createFilterBean(new CorsFilter(source), WebFilterOrderEnum.CORS_FILTER);
    }

    /**
     * Create RequestBodyCacheFilter Bean，Can read request content repeatedly
     */
    @Bean
    public FilterRegistrationBean<CacheRequestBodyFilter> requestBodyCacheFilter() {
        return createFilterBean(new CacheRequestBodyFilter(), WebFilterOrderEnum.REQUEST_BODY_CACHE_FILTER);
    }

    public static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    /**
     * Create RestTemplate Example
     *
     * @param restTemplateBuilder {@link RestTemplateAutoConfiguration#restTemplateBuilder}
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
