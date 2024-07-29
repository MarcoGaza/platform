package cn.econets.blossom.framework.file.core.client.sftp;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Sftp File client configuration class
 *
 */
@Data
public class SftpFileClientConfig implements FileClientConfig {

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

    /**
     * Host address
     */
    @NotEmpty(message = "host Cannot be empty")
    private String host;
    /**
     * Host port
     */
    @NotNull(message = "port Cannot be empty")
    private Integer port;
    /**
     * Username
     */
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    /**
     * Password
     */
    @NotEmpty(message = "The password cannot be empty")
    private String password;

}
