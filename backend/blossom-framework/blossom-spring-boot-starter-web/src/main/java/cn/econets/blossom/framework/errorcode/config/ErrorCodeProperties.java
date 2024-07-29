package cn.econets.blossom.framework.errorcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Error code configuration property class
 *
 */
@ConfigurationProperties(prefix = "application.error-code")
@Data
@Validated
public class ErrorCodeProperties {

    /**
     * Is it enabled?
     */
    private Boolean enable = true;
    /**
     * Error code enumeration class
     */
    @NotNull(message = "Error code enumeration class cannot be empty")
    private List<String> constantsClassList;

}
