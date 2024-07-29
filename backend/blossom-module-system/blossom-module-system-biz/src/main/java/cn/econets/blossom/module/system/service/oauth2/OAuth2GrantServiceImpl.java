package cn.econets.blossom.module.system.service.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2CodeDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.service.auth.AdminAuthService;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * OAuth2 Grant Service Implementation class
 *
 */
@Service
public class OAuth2GrantServiceImpl implements OAuth2GrantService {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private OAuth2CodeService oauth2CodeService;
    @Resource
    private AdminAuthService adminAuthService;

    @Override
    public OAuth2AccessTokenDO grantImplicit(Long userId, Integer userType,
                                             String clientId, List<String> scopes) {
        return oauth2TokenService.createAccessToken(userId, userType, clientId, scopes);
    }

    @Override
    public String grantAuthorizationCodeForCode(Long userId, Integer userType,
                                                String clientId, List<String> scopes,
                                                String redirectUri, String state) {
        return oauth2CodeService.createAuthorizationCode(userId, userType, clientId, scopes,
                redirectUri, state).getCode();
    }

    @Override
    public OAuth2AccessTokenDO grantAuthorizationCodeForAccessToken(String clientId, String code,
                                                                    String redirectUri, String state) {
        OAuth2CodeDO codeDO = oauth2CodeService.consumeAuthorizationCode(code);
        Assert.notNull(codeDO, "Authorization code cannot be empty"); // Defensive Programming
        // Verification clientId Whether it matches
        if (!StrUtil.equals(clientId, codeDO.getClientId())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_CLIENT_ID_MISMATCH);
        }
        // Verification redirectUri Whether it matches
        if (!StrUtil.equals(redirectUri, codeDO.getRedirectUri())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_REDIRECT_URI_MISMATCH);
        }
        // Verification state Whether it matches
        state = StrUtil.nullToDefault(state, ""); // Database state for null Time，Will be set to "" Empty string
        if (!StrUtil.equals(state, codeDO.getState())) {
            throw exception(ErrorCodeConstants.OAUTH2_GRANT_STATE_MISMATCH);
        }

        // Create access token
        return oauth2TokenService.createAccessToken(codeDO.getUserId(), codeDO.getUserType(),
                codeDO.getClientId(), codeDO.getScopes());
    }

    @Override
    public OAuth2AccessTokenDO grantPassword(String username, String password, String clientId, List<String> scopes) {
        // Use account + Log in with password
        AdminUserDO user = adminAuthService.authenticate(username, password);
        Assert.notNull(user, "User cannot be empty！"); // Defensive Programming

        // Create access token
        return oauth2TokenService.createAccessToken(user.getId(), UserTypeEnum.ADMIN.getValue(), clientId, scopes);
    }

    @Override
    public OAuth2AccessTokenDO grantRefreshToken(String refreshToken, String clientId) {
        return oauth2TokenService.refreshAccessToken(refreshToken, clientId);
    }

    @Override
    public OAuth2AccessTokenDO grantClientCredentials(String clientId, List<String> scopes) {
        // TODO Used in the project OAuth2 It solves the authorization of third-party applications，Internal SSO And other issues，So I won't consider it for now client_credentials This scene
        throw new UnsupportedOperationException("Not supported yet client_credentials Authorization Mode");
    }

    @Override
    public boolean revokeToken(String clientId, String accessToken) {
        // Query first，Guarantee clientId Matched
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.getAccessToken(accessToken);
        if (accessTokenDO == null || ObjectUtil.notEqual(clientId, accessTokenDO.getClientId())) {
            return false;
        }
        // Delete again
        return oauth2TokenService.removeAccessToken(accessToken) != null;
    }

}
