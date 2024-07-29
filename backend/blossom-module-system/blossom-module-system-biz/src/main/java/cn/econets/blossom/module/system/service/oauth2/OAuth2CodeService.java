package cn.econets.blossom.module.system.service.oauth2;


import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2CodeDO;

import java.util.List;

/**
 * OAuth2.0 Authorization code Service Interface
 *
 * Functionally，Japanese Spring Security OAuth of JdbcAuthorizationCodeServices Function，Operation of providing authorization code
 *
 */
public interface OAuth2CodeService {

    /**
     * Create authorization code
     *
     * Reference JdbcAuthorizationCodeServices of createAuthorizationCode Method
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @param redirectUri Redirect URI
     * @param state Status
     * @return Authorization code information
     */
    OAuth2CodeDO createAuthorizationCode(Long userId, Integer userType, String clientId,
                                         List<String> scopes, String redirectUri, String state);

    /**
     * Use authorization code
     *
     * @param code Authorization code
     */
    OAuth2CodeDO consumeAuthorizationCode(String code);

}
