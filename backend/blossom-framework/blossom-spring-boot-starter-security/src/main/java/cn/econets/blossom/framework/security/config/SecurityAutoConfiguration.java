package cn.econets.blossom.framework.security.config;

import cn.econets.blossom.framework.security.core.aop.PreAuthenticatedAspect;
import cn.econets.blossom.framework.security.core.context.TransmittableThreadLocalSecurityContextHolderStrategy;
import cn.econets.blossom.framework.security.core.filter.TokenAuthenticationFilter;
import cn.econets.blossom.framework.security.core.handler.AccessDeniedHandlerImpl;
import cn.econets.blossom.framework.security.core.handler.AuthenticationEntryPointImpl;
import cn.econets.blossom.framework.security.core.service.SecurityFrameworkService;
import cn.econets.blossom.framework.security.core.service.SecurityFrameworkServiceImpl;
import cn.econets.blossom.framework.web.core.handler.GlobalExceptionHandler;
import cn.econets.blossom.module.system.api.oauth2.OAuth2TokenApi;
import cn.econets.blossom.module.system.api.permission.PermissionApi;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

/**
 * Spring Security Automatic configuration class，Mainly used for configuration of related components
 *
 * Attention，Cannot be together {@link WebSecurityConfigurerAdapter} Use one，The reason is that it will cause initialization errors。
 * See https://stackoverflow.com/questions/53847050/spring-boot-delegatebuilder-cannot-be-null-on-autowiring-authenticationmanager Document。
 *
 */

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {
    @Resource
    private SecurityProperties securityProperties;

    /**
     * Processing the interception of user not logged in Bean
     */
    @Bean
    public PreAuthenticatedAspect preAuthenticatedAspect() {
        return new PreAuthenticatedAspect();
    }

    /**
     * Authentication failure processing class Bean
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    /**
     * Insufficient permissions for the processor Bean
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    /**
     * Spring Security Encryptor
     * Considering security，Used here BCryptPasswordEncoder Encryptor
     *
     * @see <a href="http://stackabuse.com/password-encoding-with-spring-security/">Password Encoding with Spring Security</a>
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    /**
     * Token Authentication filter Bean
     */
    @Bean
    public TokenAuthenticationFilter authenticationTokenFilter(GlobalExceptionHandler globalExceptionHandler,
                                                               OAuth2TokenApi oauth2TokenApi) {
        return new TokenAuthenticationFilter(securityProperties, globalExceptionHandler, oauth2TokenApi);
    }

    @Bean("ss") // Use Spring Security abbreviation of，Easy to use
    public SecurityFrameworkService securityFrameworkService(PermissionApi permissionApi) {
        return new SecurityFrameworkServiceImpl(permissionApi);
    }

    /**
     * Declaration call {@link SecurityContextHolder#setStrategyName(String)} Method，
     * Set usage {@link TransmittableThreadLocalSecurityContextHolderStrategy} As Security Context strategy
     */
    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }
}
