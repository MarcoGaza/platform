package cn.econets.blossom.framework.xss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

/**
 * Xss Configuration properties
 */
@ConfigurationProperties(prefix = "application.xss")
@Validated
@Data
public class XssProperties {
    /**
     * Is it enabled?，Default is true
     */
    private boolean enable = true;
    /**
     * Need to be excluded URL，Default is empty
     */
    private List<String> excludeUrls = Collections.emptyList();
}
