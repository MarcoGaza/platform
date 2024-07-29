package cn.econets.blossom.module.system.api.oauth2.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * OAuth2.0 Access token verification Response DTO
 *
 */
@Data
public class OAuth2AccessTokenCheckRespDTO implements Serializable {

    /**
     * User Number
     */
    private Long userId;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * Tenant Number
     */
    private Long tenantId;
    /**
     * Array of authorization scopes
     */
    private List<String> scopes;

}
