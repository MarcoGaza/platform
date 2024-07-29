package cn.econets.blossom.module.infrastructure.framework.security.config;

import cn.econets.blossom.framework.security.config.AuthorizeRequestsCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Infra Module Security Configuration
 */
@Configuration(proxyBeanMethods = false, value = "infraSecurityConfiguration")
public class SecurityConfiguration {

    @Value("${spring.boot.admin.context-path:''}")
    private String adminSeverContextPath;

    @Bean("infraAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger Interface Documentation
                registry.antMatchers("/v3/api-docs/**").permitAll()
                        .antMatchers("/swagger-ui.html").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll()
                        .antMatchers("/swagger-resources/**").anonymous()
                        .antMatchers("/webjars/**").anonymous()
                        .antMatchers("/*/api-docs").anonymous();
                // Spring Boot Actuator Security Configuration
                registry.antMatchers("/actuator").anonymous()
                        .antMatchers("/actuator/**").anonymous();
                // Druid Monitoring
                registry.antMatchers("/druid/**").anonymous();
                // Spring Boot Admin Server Security Configuration
                registry.antMatchers(adminSeverContextPath).anonymous()
                        .antMatchers(adminSeverContextPath + "/**").anonymous();
                // File read
                registry.antMatchers(buildAdminApi("/infra/file/*/get/**")).permitAll();
            }

        };
    }

}
