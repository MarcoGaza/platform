package cn.econets.blossom.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "application.security")
@Validated
@Data
public class SecurityProperties {

    /**
     * HTTP When requesting，Access token request Header
     */
    @NotEmpty(message = "Token Header Cannot be empty")
    private String tokenHeader = "Authorization";
    /**
     * HTTP When requesting，Access token request parameters
     *
     * Initial Purpose：Solved WebSocket Unable to pass header Passing parameters，Can only pass token Parameter splicing
     */
    @NotEmpty(message = "Token Parameter Cannot be empty")
    private String tokenParameter = "token";

    /**
     * mock Mode switch
     */
    @NotNull(message = "mock The mode switch cannot be empty")
    private Boolean mockEnable = false;
    /**
     * mock Key of the mode
     * Must configure the key，Ensure safety
     */
    @NotEmpty(message = "mock The key of the pattern cannot be empty") // A default value is set here，Because in fact only mockEnable for true Configuration is only required。
    private String mockSecret = "test";

    /**
     * No login required URL List
     */
    private List<String> permitAllUrls = Collections.emptyList();

    /**
     * PasswordEncoder Encryption complexity，The higher the cost, the greater the cost
     */
    private Integer passwordEncoderLength = 4;
}
