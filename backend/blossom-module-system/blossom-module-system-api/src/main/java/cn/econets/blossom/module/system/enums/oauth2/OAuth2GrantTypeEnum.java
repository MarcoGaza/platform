package cn.econets.blossom.module.system.enums.oauth2;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OAuth2 Authorization type（Mode）Enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum OAuth2GrantTypeEnum {

    PASSWORD("password"), // Password Mode
    AUTHORIZATION_CODE("authorization_code"), // Authorization code mode
    IMPLICIT("implicit"), // Simplified mode
    CLIENT_CREDENTIALS("client_credentials"), // Client Mode
    REFRESH_TOKEN("refresh_token"), // Refresh Mode
    ;

    private final String grantType;

    public static OAuth2GrantTypeEnum getByGranType(String grantType) {
        return ArrayUtil.firstMatch(o -> o.getGrantType().equals(grantType), values());
    }

}
