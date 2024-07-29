package cn.econets.blossom.module.system.api.oauth2.dto;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * OAuth2.0 Access token creation Request DTO
 *
 */
@Data
public class OAuth2AccessTokenCreateReqDTO implements Serializable {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     */
    @NotNull(message = "User type cannot be empty")
    @InEnum(value = UserTypeEnum.class, message = "User type must be {value}")
    private Integer userType;
    /**
     * Client ID
     */
    @NotNull(message = "Client ID cannot be empty")
    private String clientId;
    /**
     * Authorization scope
     */
    private List<String> scopes;

}
