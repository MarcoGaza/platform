package cn.econets.blossom.framework.file.core.client.local;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

/**
 * Local file client configuration class
 *
 */
@Data
public class LocalFileClientConfig implements FileClientConfig {

    /**
     * Basic path
     */
    @NotEmpty(message = "The base path cannot be empty")
    private String basePath;

    /**
     * Custom domain name
     */
    @NotEmpty(message = "domain Cannot be empty")
    @URL(message = "domain Must be URL Format")
    private String domain;

}
