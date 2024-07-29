package cn.econets.blossom.framework.security.config;

import cn.econets.blossom.framework.security.core.filter.TokenAuthenticationFilter;
import cn.econets.blossom.framework.web.config.WebProperties;
import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
@AutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfigurerAdapter {

    @Resource
    private WebProperties webProperties;
    @Resource
    private SecurityProperties securityProperties;

    /**
     * Authentication failure processing class Bean
     */
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    /**
     * Insufficient permissions for the processor Bean
     */
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    /**
     * Token Authentication filter Bean
     */
    @Resource
    private TokenAuthenticationFilter authenticationTokenFilter;

    /**
     * Customized permission mapping Bean We
     *
     * @see #filterChain(HttpSecurity)
     */
    @Resource
    private List<AuthorizeRequestsCustomizer> authorizeRequestsCustomizers;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * Because Spring Security Create AuthenticationManager Object，No statement @Bean Annotation，resulting in inability to be injected
     * By overriding the method of the parent class，Add @Bean Annotation，Solve this problem
     */
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configuration URL Security Configuration
     *
     * anyRequest          |   Match all request paths
     * access              |   SpringElThe result of the expression istrueAccessible
     * anonymous           |   Anonymous access possible
     * denyAll             |   User cannot access
     * fullyAuthenticated  |   Users with full authentication can access（Nonremember-meAutomatically log in）
     * hasAnyAuthority     |   If there are parameters，Parameters represent permissions，Any one of these permissions can access
     * hasAnyRole          |   If there are parameters，Parameter represents role，Any role can access
     * hasAuthority        |   If there are parameters，Parameters represent permissions，Then its permissions can access
     * hasIpAddress        |   If there are parameters，Parameter representationIPAddress，If the userIPMatches with parameters，You can access
     * hasRole             |   If there are parameters，Parameter represents role，The role can access
     * permitAll           |   Users can access it at will
     * rememberMe          |   Allowed to passremember-meLogged-in user access
     * authenticated       |   Users can access after logging in
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Log out
        httpSecurity
                // Enable cross-domain
                .cors().and()
                // CSRF Disable，Because it is not used Session
                .csrf().disable()
                // Based on token Mechanism，So it is not necessary Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()
                // A bunch of custom ones Spring Security Processor
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        // Login、Login is temporarily unavailable Spring Security Expansion points，Mainly consider expanding multiple users、Multiple login methods are relatively complex，On the one hand, the learning cost for users is high

        // Get @PermitAll Brought URL List，No login required
        Multimap<HttpMethod, String> permitAllUrls = getPermitAllUrlsFromAnnotations();
        // Set permissions for each request
        httpSecurity
                // ①：Global shared rules
                .authorizeRequests()
                // 1.1 Static resources，Anonymous access possible
                .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // 1.2 Settings @PermitAll No authentication required
                .antMatchers(HttpMethod.GET, permitAllUrls.get(HttpMethod.GET).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, permitAllUrls.get(HttpMethod.POST).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, permitAllUrls.get(HttpMethod.PUT).toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, permitAllUrls.get(HttpMethod.DELETE).toArray(new String[0])).permitAll()
                // 1.3 Based on application.security.permit-all-urls No authentication required
                .antMatchers(securityProperties.getPermitAllUrls().toArray(new String[0])).permitAll()
                // 1.4 Settings App API No authentication required
                .antMatchers(buildAppApi("/**")).permitAll()
                // 1.5 Verification codecaptcha Allow anonymous access
                .antMatchers("/captcha/get", "/captcha/check").permitAll()
                // ②：Custom rules for each project
                .and().authorizeRequests(registry -> // Below，Set custom rules in a loop
                authorizeRequestsCustomizers.forEach(customizer -> customizer.customize(registry)))
                // ③：Bottom line rule，Authentication required
                .authorizeRequests()
                .anyRequest().authenticated()
        ;

        // Add Token Filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private String buildAppApi(String url) {
        return webProperties.getAppApi().getPrefix() + url;
    }

    private Multimap<HttpMethod, String> getPermitAllUrlsFromAnnotations() {
        Multimap<HttpMethod, String> result = HashMultimap.create();
        // Get the corresponding interface HandlerMethod Collection
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // Obtained @PermitAll Annotated interface
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(PermitAll.class)) {
                continue;
            }
            if (entry.getKey().getPatternsCondition() == null) {
                continue;
            }
            Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();
            // Special：Use @RequestMapping Annotation，and not written method Properties，At this time, we think that no login is required
            Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();
            if (CollUtil.isEmpty(methods)) { //
                result.putAll(HttpMethod.GET, urls);
                result.putAll(HttpMethod.POST, urls);
                result.putAll(HttpMethod.PUT, urls);
                result.putAll(HttpMethod.DELETE, urls);
                continue;
            }
            // According to the request method，Add to result Results
            entry.getKey().getMethodsCondition().getMethods().forEach(requestMethod -> {
                switch (requestMethod) {
                    case GET:
                        result.putAll(HttpMethod.GET, urls);
                        break;
                    case POST:
                        result.putAll(HttpMethod.POST, urls);
                        break;
                    case PUT:
                        result.putAll(HttpMethod.PUT, urls);
                        break;
                    case DELETE:
                        result.putAll(HttpMethod.DELETE, urls);
                        break;
                }
            });
        }
        return result;
    }
}
