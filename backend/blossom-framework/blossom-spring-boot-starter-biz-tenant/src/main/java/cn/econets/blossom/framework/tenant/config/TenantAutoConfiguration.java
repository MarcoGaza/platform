package cn.econets.blossom.framework.tenant.config;

import cn.econets.blossom.framework.common.enums.WebFilterOrderEnum;
import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.framework.tenant.core.aop.TenantIgnoreAspect;
import cn.econets.blossom.framework.tenant.core.db.TenantDatabaseInterceptor;
import cn.econets.blossom.framework.tenant.core.job.TenantJobAspect;
import cn.econets.blossom.framework.tenant.core.mq.rabbitmq.TenantRabbitMQInitializer;
import cn.econets.blossom.framework.tenant.core.mq.redis.TenantRedisMessageInterceptor;
import cn.econets.blossom.framework.tenant.core.mq.rocketmq.TenantRocketMQInitializer;
import cn.econets.blossom.framework.tenant.core.redis.TenantRedisCacheManager;
import cn.econets.blossom.framework.tenant.core.security.TenantSecurityWebFilter;
import cn.econets.blossom.framework.tenant.core.service.TenantFrameworkService;
import cn.econets.blossom.framework.tenant.core.service.TenantFrameworkServiceImpl;
import cn.econets.blossom.framework.tenant.core.web.TenantContextWebFilter;
import cn.econets.blossom.framework.web.config.WebProperties;
import cn.econets.blossom.framework.web.core.handler.GlobalExceptionHandler;
import cn.econets.blossom.module.system.api.tenant.TenantApi;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import cn.econets.blossom.framework.redis.config.CachesProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 *
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "application.tenant", value = "enable", matchIfMissing = true) // Allow use application.tenant.enable=false Disable multi-tenancy
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfiguration {

    @Bean
    public TenantFrameworkService tenantFrameworkService(TenantApi tenantApi) {
        return new TenantFrameworkServiceImpl(tenantApi);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // Add to interceptor Medium
        // Need to be added to the first one，Mainly for the paging plugin。This is MyBatis Plus Regulations
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // ========== WEB ==========

    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // ========== Security ==========

    @Bean
    public FilterRegistrationBean<TenantSecurityWebFilter> tenantSecurityWebFilter(TenantProperties tenantProperties,
                                                                                   WebProperties webProperties,
                                                                                   GlobalExceptionHandler globalExceptionHandler,
                                                                                   TenantFrameworkService tenantFrameworkService) {
        FilterRegistrationBean<TenantSecurityWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantSecurityWebFilter(tenantProperties, webProperties,
                globalExceptionHandler, tenantFrameworkService));
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_SECURITY_FILTER);
        return registrationBean;
    }

    // ========== MQ ==========

    @Bean
    public TenantRedisMessageInterceptor tenantRedisMessageInterceptor() {
        return new TenantRedisMessageInterceptor();
    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
    public TenantRabbitMQInitializer tenantRabbitMQInitializer() {
        return new TenantRabbitMQInitializer();
    }

    @Bean
    @ConditionalOnClass(name = "org.apache.rocketmq.spring.core.RocketMQTemplate")
    public TenantRocketMQInitializer tenantRocketMQInitializer() {
        return new TenantRocketMQInitializer();
    }

    // ========== Job ==========

    @Bean
    public TenantJobAspect tenantJobAspect(TenantFrameworkService tenantFrameworkService) {
        return new TenantJobAspect(tenantFrameworkService);
    }

    // ========== Redis ==========

    @Bean
    @Primary // When introducing tenants，tenantRedisCacheManager Main Bean
    public RedisCacheManager tenantRedisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                                     RedisCacheConfiguration redisCacheConfiguration,
                                                     CachesProperties cacheProperties) {
        // Create RedisCacheWriter Object
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(cacheProperties.getRedisScanBatchSize()));
        // Create TenantRedisCacheManager Object
        return new TenantRedisCacheManager(cacheWriter, redisCacheConfiguration);
    }

}
