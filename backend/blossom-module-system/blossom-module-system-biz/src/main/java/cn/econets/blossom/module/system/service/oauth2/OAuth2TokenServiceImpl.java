package cn.econets.blossom.module.system.service.oauth2;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenPageReqVO;
import cn.econets.blossom.module.system.dal.mysql.oauth2.OAuth2AccessTokenMapper;
import cn.econets.blossom.module.system.dal.mysql.oauth2.OAuth2RefreshTokenMapper;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2RefreshTokenDO;
import cn.econets.blossom.module.system.dal.redis.oauth2.OAuth2AccessTokenRedisDAO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception0;

/**
 * OAuth2.0 Token Service Implementation class
 *
 */
@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Resource
    private OAuth2AccessTokenMapper oauth2AccessTokenMapper;
    @Resource
    private OAuth2RefreshTokenMapper oauth2RefreshTokenMapper;

    @Resource
    private OAuth2AccessTokenRedisDAO oauth2AccessTokenRedisDAO;

    @Resource
    private OAuth2ClientService oauth2ClientService;

    @Override
    @Transactional
    public OAuth2AccessTokenDO createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {
        OAuth2ClientDO clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        // Create refresh token
        OAuth2RefreshTokenDO refreshTokenDO = createOAuth2RefreshToken(userId, userType, clientDO, scopes);
        // Create access token
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public OAuth2AccessTokenDO refreshAccessToken(String refreshToken, String clientId) {
        // Query access token
        OAuth2RefreshTokenDO refreshTokenDO = oauth2RefreshTokenMapper.selectByRefreshToken(refreshToken);
        if (refreshTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "Invalid refresh token");
        }

        // Verification Client Match
        OAuth2ClientDO clientDO = oauth2ClientService.validOAuthClientFromCache(clientId);
        if (ObjectUtil.notEqual(clientId, refreshTokenDO.getClientId())) {
            throw exception0(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "The client ID of the refresh token is incorrect");
        }

        // Remove related access tokens
        List<OAuth2AccessTokenDO> accessTokenDOs = oauth2AccessTokenMapper.selectListByRefreshToken(refreshToken);
        if (CollUtil.isNotEmpty(accessTokenDOs)) {
            oauth2AccessTokenMapper.deleteBatchIds(CollectionUtils.convertSet(accessTokenDOs, OAuth2AccessTokenDO::getId));
            oauth2AccessTokenRedisDAO.deleteList(CollectionUtils.convertSet(accessTokenDOs, OAuth2AccessTokenDO::getAccessToken));
        }

        // Expired，Delete refresh token
        if (DateUtils.isExpired(refreshTokenDO.getExpiresTime())) {
            oauth2RefreshTokenMapper.deleteById(refreshTokenDO.getId());
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "The refresh token has expired");
        }

        // Create access token
        return createOAuth2AccessToken(refreshTokenDO, clientDO);
    }

    @Override
    public OAuth2AccessTokenDO getAccessToken(String accessToken) {
        // Prioritize from Redis Get
        OAuth2AccessTokenDO accessTokenDO = oauth2AccessTokenRedisDAO.get(accessToken);
        if (accessTokenDO != null) {
            return accessTokenDO;
        }

        // Unable to obtain，From MySQL Get
        accessTokenDO = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
        // If in MySQL Exists，Then go Redis Write in
        if (accessTokenDO != null && !DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            oauth2AccessTokenRedisDAO.set(accessTokenDO);
        }
        return accessTokenDO;
    }

    @Override
    public OAuth2AccessTokenDO checkAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO = getAccessToken(accessToken);
        if (accessTokenDO == null) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "Access token does not exist");
        }
        if (DateUtils.isExpired(accessTokenDO.getExpiresTime())) {
            throw exception0(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "Access token has expired");
        }
        return accessTokenDO;
    }

    @Override
    public OAuth2AccessTokenDO removeAccessToken(String accessToken) {
        // Delete access token
        OAuth2AccessTokenDO accessTokenDO = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (accessTokenDO == null) {
            return null;
        }
        oauth2AccessTokenMapper.deleteById(accessTokenDO.getId());
        oauth2AccessTokenRedisDAO.delete(accessToken);
        // Delete refresh token
        oauth2RefreshTokenMapper.deleteByRefreshToken(accessTokenDO.getRefreshToken());
        return accessTokenDO;
    }

    @Override
    public PageResult<OAuth2AccessTokenDO> getAccessTokenPage(OAuth2AccessTokenPageReqVO reqVO) {
        return oauth2AccessTokenMapper.selectPage(reqVO);
    }

    private OAuth2AccessTokenDO createOAuth2AccessToken(OAuth2RefreshTokenDO refreshTokenDO, OAuth2ClientDO clientDO) {
        OAuth2AccessTokenDO accessTokenDO = new OAuth2AccessTokenDO();
        accessTokenDO.setAccessToken(generateAccessToken());
        accessTokenDO.setUserId(refreshTokenDO.getUserId());
        accessTokenDO.setUserType(refreshTokenDO.getUserType());
        accessTokenDO.setClientId(clientDO.getClientId());
        accessTokenDO.setScopes(refreshTokenDO.getScopes());
        accessTokenDO.setRefreshToken(refreshTokenDO.getRefreshToken());
        accessTokenDO.setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.getAccessTokenValiditySeconds()));
        accessTokenDO.setTenantId(TenantContextHolder.getTenantId()); // Manually set tenant ID，Avoid caching Redis When，No corresponding tenant number
        oauth2AccessTokenMapper.insert(accessTokenDO);
        // Recorded Redis Medium
        oauth2AccessTokenRedisDAO.set(accessTokenDO);
        return accessTokenDO;
    }

    private OAuth2RefreshTokenDO createOAuth2RefreshToken(Long userId, Integer userType, OAuth2ClientDO clientDO, List<String> scopes) {
        OAuth2RefreshTokenDO refreshToken = new OAuth2RefreshTokenDO().setRefreshToken(generateRefreshToken())
                .setUserId(userId).setUserType(userType)
                .setClientId(clientDO.getClientId()).setScopes(scopes)
                .setExpiresTime(LocalDateTime.now().plusSeconds(clientDO.getRefreshTokenValiditySeconds()));
        oauth2RefreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

}
