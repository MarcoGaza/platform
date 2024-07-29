package cn.econets.blossom.module.system.api.oauth2;


import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;

import javax.validation.Valid;

/**
 * OAuth2.0 Token API Interface
 *
 */
public interface OAuth2TokenApi {

    /**
     * Create access token
     *
     * @param reqDTO Access token creation information
     * @return Access token information
     */
    OAuth2AccessTokenRespDTO createAccessToken(@Valid OAuth2AccessTokenCreateReqDTO reqDTO);

    /**
     * Verify access token
     *
     * @param accessToken Access Token
     * @return Access token information
     */
    OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);

    /**
     * Remove access token
     *
     * @param accessToken Access Token
     * @return Access token information
     */
    OAuth2AccessTokenRespDTO removeAccessToken(String accessToken);

    /**
     * Refresh access token
     *
     * @param refreshToken Refresh Token
     * @param clientId Client ID
     * @return Access token information
     */
    OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId);

}
