package cn.econets.blossom.module.system.service.oauth2;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.util.List;

/**
 * OAuth2.0 Token Service Interface
 *
 * Functionally，Japanese Spring Security OAuth of DefaultTokenServices + JdbcTokenStore Function，Provide access token、Operation of refreshing token
 *
 */
public interface OAuth2TokenService {

    /**
     * Create access token
     * Attention：In this process，Will include the creation of refresh tokens
     *
     * Reference DefaultTokenServices of createAccessToken Method
     *
     * @param userId User Number
     * @param userType User Type
     * @param clientId Client ID
     * @param scopes Authorization scope
     * @return Access token information
     */
    OAuth2AccessTokenDO createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    /**
     * Refresh access token
     *
     * Reference DefaultTokenServices of refreshAccessToken Method
     *
     * @param refreshToken Refresh Token
     * @param clientId Client ID
     * @return Access token information
     */
    OAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId);

    /**
     * Get access token
     *
     * Reference DefaultTokenServices of getAccessToken Method
     *
     * @param accessToken Access Token
     * @return Access token information
     */
    OAuth2AccessTokenDO getAccessToken(String accessToken);

    /**
     * Verify access token
     *
     * @param accessToken Access Token
     * @return Access token information
     */
    OAuth2AccessTokenDO checkAccessToken(String accessToken);

    /**
     * Remove access token
     * Attention：In this process，Will remove the associated refresh token
     *
     * Reference DefaultTokenServices of revokeToken Method
     *
     * @param accessToken Refresh Token
     * @return Access token information
     */
    OAuth2AccessTokenDO removeAccessToken(String accessToken);

    /**
     * Get access token paging
     *
     * @param reqVO Request
     * @return Access token paging
     */
    PageResult<OAuth2AccessTokenDO> getAccessTokenPage(OAuth2AccessTokenPageReqVO reqVO);

}
