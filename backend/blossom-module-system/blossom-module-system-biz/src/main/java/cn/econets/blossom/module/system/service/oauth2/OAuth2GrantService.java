package cn.econets.blossom.module.system.service.oauth2;


import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

/**
 * OAuth2 Granted Service Interface
 *
 * Functionally，Japanese Spring Security OAuth of TokenGranter Function，Provide access token、Operation of refreshing token
 *
 * Change its own AdminUser User，Authorize to third-party applications，Adopt OAuth2.0 Protocol。
 *
 * Question：Why is it also a third-party application?，Also follow this process？
 * Reply：Of course you can do this，Adopt password Mode。Considering that most developers cannot use this feature，OAuth2.0 After all, there is a certain learning cost，So this approach is not being adopted for the time being。
 *
 */
public interface OAuth2GrantService {

    /**
     * Simplified mode
     *
     * Corresponding Spring Security OAuth2 of ImplicitTokenGranter Function
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @return Access Token
     */
    OAuth2AccessTokenDO grantImplicit(Long userId, Integer userType,
                                      String clientId, List<String> scopes);

    /**
     * Authorization code mode，Phase 1，Get code Authorization code
     *
     * Corresponding Spring Security OAuth2 of AuthorizationEndpoint of generateCode Method
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @param redirectUri Redirect URI
     * @param state Status
     * @return Authorization code
     */
    String grantAuthorizationCodeForCode(Long userId, Integer userType,
                                         String clientId, List<String> scopes,
                                         String redirectUri, String state);

    /**
     * Authorization code mode，Phase 2，Get accessToken Access Token
     *
     * Corresponding Spring Security OAuth2 of AuthorizationCodeTokenGranter Function
     *
     * @param clientId Client ID
     * @param code Authorization code
     * @param redirectUri Redirect URI
     * @param state Status
     * @return Access Token
     */
    OAuth2AccessTokenDO grantAuthorizationCodeForAccessToken(String clientId, String code,
                                                             String redirectUri, String state);

    /**
     * Password Mode
     *
     * Corresponding Spring Security OAuth2 of ResourceOwnerPasswordTokenGranter Function
     *
     * @param username Account
     * @param password Password
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @return Access Token
     */
    OAuth2AccessTokenDO grantPassword(String username, String password,
                                      String clientId, List<String> scopes);

    /**
     * Refresh Mode
     *
     * Corresponding Spring Security OAuth2 of ResourceOwnerPasswordTokenGranter Function
     *
     * @param refreshToken Refresh Token
     * @param clientId Client ID
     * @return Access Token
     */
    OAuth2AccessTokenDO grantRefreshToken(String refreshToken, String clientId);

    /**
     * Client Mode
     *
     * Corresponding Spring Security OAuth2 of ClientCredentialsTokenGranter Function
     *
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @return Access Token
     */
    OAuth2AccessTokenDO grantClientCredentials(String clientId, List<String> scopes);

    /**
     * Remove access token
     *
     * Corresponding Spring Security OAuth2 of ConsumerTokenServices of revokeToken Method
     *
     * @param accessToken Access Token
     * @param clientId Client ID
     * @return Remove to
     */
    boolean revokeToken(String clientId, String accessToken);

}
