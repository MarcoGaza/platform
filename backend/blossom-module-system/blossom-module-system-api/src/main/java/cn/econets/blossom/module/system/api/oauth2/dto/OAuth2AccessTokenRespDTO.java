package cn.econets.blossom.module.system.api.oauth2.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * OAuth2.0 Access token information Response DTO
 *
 */
@Data
@Accessors(chain = true)
public class OAuth2AccessTokenRespDTO implements Serializable {

    /**
     * Access Token
     */
    private String accessToken;
    /**
     * Refresh Token
     */
    private String refreshToken;
    /**
     * User Number
     */
    private Long userId;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * Expiration time
     */
    private LocalDateTime expiresTime;

}
