package cn.econets.blossom.framework.file.core.client.db;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

/**
 * Based on DB Configuration class for the stored file client
 *
 */
@Data
public class DBFileClientConfig implements FileClientConfig {

    /**
     * Custom domain name
     */
    @NotEmpty(message = "domain Cannot be empty")
    @URL(message = "domain Must be URL Format")
    private String domain;

}
