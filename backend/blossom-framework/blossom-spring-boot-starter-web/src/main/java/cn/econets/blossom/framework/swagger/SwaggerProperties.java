package cn.econets.blossom.framework.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * Swagger Configuration properties
 *
 */
@ConfigurationProperties("application.swagger")
@Data
public class SwaggerProperties {

    /**
     * Title
     */
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    /**
     * Description
     */
    @NotEmpty(message = "Description cannot be empty")
    private String description;
    /**
     * Author
     */
    @NotEmpty(message = "Author cannot be empty")
    private String author;
    /**
     * Version
     */
    @NotEmpty(message = "Version cannot be empty")
    private String version;
    /**
     * url
     */
    @NotEmpty(message = "Scanned package Cannot be empty")
    private String url;
    /**
     * email
     */
    @NotEmpty(message = "Scanned email Cannot be empty")
    private String email;

    /**
     * license
     */
    @NotEmpty(message = "Scanned license Cannot be empty")
    private String license;

    /**
     * license-url
     */
    @NotEmpty(message = "Scanned license-url Cannot be empty")
    private String licenseUrl;

}
