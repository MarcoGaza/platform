package cn.econets.blossom.framework.file.core.client.ftp;

import cn.econets.blossom.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Ftp File client configuration class
 *
 */
@Data
public class FtpFileClientConfig implements FileClientConfig {

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
    /**
     * Connection Mode
     *
     * Use {@link  cn.hutool.extra.ftp.FtpMode} Corresponding string
     */
    @NotEmpty(message = "Connection mode cannot be empty")
    private String mode;

}
