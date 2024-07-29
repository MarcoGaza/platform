package cn.econets.blossom.framework.swagger;

import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.*;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.providers.JavadocProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *Swagger Automatic configuration class，Based on OpenAPI + Springdoc Realization。
 */
@AutoConfiguration
@ConditionalOnClass({OpenAPI.class})
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = true) // Set to false Time，Disable
public class SwaggerAutoConfiguration {

    // ========== Global OpenAPI Configuration ==========
    @Bean
    public OpenAPI createApi(SwaggerProperties properties) {
        Map<String, SecurityScheme> securitySchemas = buildSecuritySchemes();
        OpenAPI openAPI = new OpenAPI()
                // Interface information
                .info(buildInfo(properties))
                // Interface security configuration
                .components(new Components().securitySchemes(securitySchemas))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
        securitySchemas.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));
        return openAPI;
    }

    /**
     * API Summary Information
     */
    private Info buildInfo(SwaggerProperties properties) {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact().name(properties.getAuthor()).url(properties.getUrl()).email(properties.getEmail()))
                .license(new License().name(properties.getLicense()).url(properties.getLicenseUrl()));
    }

    /**
     * Safe Mode，Configure this through the request header Authorization Transfer token Parameters
     */
    private Map<String, SecurityScheme> buildSecuritySchemes() {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY) // Type
                .name(HttpHeaders.AUTHORIZATION) // Request header name
                .in(SecurityScheme.In.HEADER); // token Location
        securitySchemes.put(HttpHeaders.AUTHORIZATION, securityScheme);
        return securitySchemes;
    }

    /**
     * Custom OpenAPI Processor
     */
    @Bean
    public OpenAPIService openApiBuilder(Optional<OpenAPI> openAPI,
                                         SecurityService securityParser,
                                         SpringDocConfigProperties springDocConfigProperties,
                                         PropertyResolverUtils propertyResolverUtils,
                                         Optional<List<OpenApiBuilderCustomizer>> openApiBuilderCustomizers,
                                         Optional<List<ServerBaseUrlCustomizer>> serverBaseUrlCustomizers,
                                         Optional<JavadocProvider> javadocProvider) {

        return new OpenAPIService(openAPI, securityParser, springDocConfigProperties,
                propertyResolverUtils, openApiBuilderCustomizers, serverBaseUrlCustomizers, javadocProvider);
    }


    // ========== Grouping OpenAPI Configuration ==========
    /**
     * All modules API Grouping
     */
    @Bean
    public GroupedOpenApi allGroupedOpenApi() {
        return buildGroupedOpenApi("all", "");
    }

    public static GroupedOpenApi buildGroupedOpenApi(String group) {
        return buildGroupedOpenApi(group, group);
    }

    public static GroupedOpenApi buildGroupedOpenApi(String group, String path) {
        return GroupedOpenApi.builder()
                .group(group)
                .pathsToMatch("/admin-api/" + path + "/**", "/app-api/" + path + "/**")
                .addOperationCustomizer((operation, handlerMethod) -> operation
                        .addParametersItem(buildTenantHeaderParameter())
                        .addParametersItem(buildSecurityHeaderParameter()))
                .build();
    }

    /**
     * Build Tenant Tenant ID request header parameter
     *
     * @return Multi-tenant parameters
     */
    private static Parameter buildTenantHeaderParameter() {
        return new Parameter()
                .name(WebFrameworkUtils.HEADER_TENANT_ID) // header Name
                .description("Tenant Number") // Description
                .in(String.valueOf(SecurityScheme.In.HEADER)) // Request header
                .schema(new IntegerSchema()._default(1L).name(WebFrameworkUtils.HEADER_TENANT_ID).description("Tenant Number")); // Default：Use tenant ID 1
    }

    /**
     * Build Authorization Authentication request header parameters
     *
     * Solved Knife4j <a href="https://gitee.com/xiaoym/knife4j/issues/I69QBU">Authorize Not effective，RequestheaderParameters not included</a>
     *
     * @return Authentication parameters
     */
    private static Parameter buildSecurityHeaderParameter() {
        return new Parameter()
                .name(HttpHeaders.AUTHORIZATION) // header Name
                .description("Authentication Token") // Description
                .in(String.valueOf(SecurityScheme.In.HEADER)) // Request header
                .schema(new StringSchema()._default("Bearer test1").name(WebFrameworkUtils.HEADER_TENANT_ID).description("Authentication Token")); // Default：Use user number 1
    }
}
