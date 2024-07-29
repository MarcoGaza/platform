package cn.econets.blossom.module.system.controller.admin.oauth2;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.http.HttpUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAccessTokenRespVO;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAuthorizeInfoRespVO;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.open.OAuth2OpenCheckTokenRespVO;
import cn.econets.blossom.module.system.convert.oauth2.OAuth2OpenConvert;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import cn.econets.blossom.module.system.enums.oauth2.OAuth2GrantTypeEnum;
import cn.econets.blossom.module.system.service.oauth2.OAuth2ApproveService;
import cn.econets.blossom.module.system.service.oauth2.OAuth2ClientService;
import cn.econets.blossom.module.system.service.oauth2.OAuth2GrantService;
import cn.econets.blossom.module.system.service.oauth2.OAuth2TokenService;
import cn.econets.blossom.module.system.util.oauth2.OAuth2Utils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * Mainly provided for external application calls
 *
 * Generally speaking，Management backend /system-api/* Not directly provided to external applications，The data and interfaces that external applications can access are limited，And the management background RBAC Cannot be well controlled。
 * References to a large number of open platforms，They are all independent sets OpenAPI，Corresponding to【This system】Right here Controller Create a new project open Package，Realization /open-api/* Interface，Then pass scope Control。
 * In addition，If a company has multiple management backends，They client_id Generated access token They cannot communicate with each other，That is, they cannot access their systems API Interface，Until two client_id Generate trust authorization。
 *
 * Taking into account【This system】I don't want to make it too complicated for now，Only get by default access token After，Can access【This system】Management backend /system-api/* All interfaces，Unless added manually scope Control。
 * scope Usage examples，Visible {@link OAuth2UserController} Class
 *
 */
@Tag(name = "Management Backend - OAuth2.0 Authorization")
@RestController
@RequestMapping("/system/oauth2")
@Validated
@Slf4j
public class OAuth2OpenController {

    @Resource
    private OAuth2GrantService oauth2GrantService;
    @Resource
    private OAuth2ClientService oauth2ClientService;
    @Resource
    private OAuth2ApproveService oauth2ApproveService;
    @Resource
    private OAuth2TokenService oauth2TokenService;

    /**
     * Corresponding Spring Security OAuth of TokenEndpoint Similar postAccessToken Method
     *
     * Authorization code authorization_code Mode：code + redirectUri + state Parameters
     * Password password Mode：username + password + scope Parameters
     * Refresh refresh_token Mode：refreshToken Parameters
     * Client client_credentials Mode：scope Parameters
     * Simplify implicit Mode：Not supported
     *
     * Attention，Required to be passed by default client_id + client_secret Parameters
     */
    @PostMapping("/token")
    @PermitAll
    @Operation(summary = "Get access token", description = "Suitable for code Authorization code mode，Or implicit Simplified Mode；In sso.vue Single sign-on interface is【Get】Call")
    @Parameters({
            @Parameter(name = "grant_type", required = true, description = "Authorization type", example = "code"),
            @Parameter(name = "code", description = "Authorization scope", example = "userinfo.read"),
            @Parameter(name = "redirect_uri", description = "Redirect URI", example = "https://www.econets.cn"),
            @Parameter(name = "state", description = "Status", example = "1"),
            @Parameter(name = "username", example = "tudou"),
            @Parameter(name = "password", example = "cai"), // Use spaces to separate multiple items
            @Parameter(name = "scope", example = "user_info"),
            @Parameter(name = "refresh_token", example = "123424233"),
    })
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<OAuth2OpenAccessTokenRespVO> postAccessToken(HttpServletRequest request,
                                                                     @RequestParam("grant_type") String grantType,
                                                                     @RequestParam(value = "code", required = false) String code, // Authorization code mode
                                                                     @RequestParam(value = "redirect_uri", required = false) String redirectUri, // Authorization code mode
                                                                     @RequestParam(value = "state", required = false) String state, // Authorization code mode
                                                                     @RequestParam(value = "username", required = false) String username, // Password Mode
                                                                     @RequestParam(value = "password", required = false) String password, // Password Mode
                                                                     @RequestParam(value = "scope", required = false) String scope, // Password Mode
                                                                     @RequestParam(value = "refresh_token", required = false) String refreshToken) { // Refresh Mode
        List<String> scopes = OAuth2Utils.buildScopes(scope);
        // 1.1 Verify authorization type
        OAuth2GrantTypeEnum grantTypeEnum = OAuth2GrantTypeEnum.getByGranType(grantType);
        if (grantTypeEnum == null) {
            throw exception0(BAD_REQUEST.getCode(), StrUtil.format("Unknown authorization type({})", grantType));
        }
        if (grantTypeEnum == OAuth2GrantTypeEnum.IMPLICIT) {
            throw exception0(BAD_REQUEST.getCode(), "Token Interface not supported implicit Authorization Mode");
        }

        // 1.2 Verify client
        String[] clientIdAndSecret = obtainBasicAuthorization(request);
        OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                grantType, scopes, redirectUri);

        // 2. According to the authorization mode，Get access token
        OAuth2AccessTokenDO accessTokenDO;
        switch (grantTypeEnum) {
            case AUTHORIZATION_CODE:
                accessTokenDO = oauth2GrantService.grantAuthorizationCodeForAccessToken(client.getClientId(), code, redirectUri, state);
                break;
            case PASSWORD:
                accessTokenDO = oauth2GrantService.grantPassword(username, password, client.getClientId(), scopes);
                break;
            case CLIENT_CREDENTIALS:
                accessTokenDO = oauth2GrantService.grantClientCredentials(client.getClientId(), scopes);
                break;
            case REFRESH_TOKEN:
                accessTokenDO = oauth2GrantService.grantRefreshToken(refreshToken, client.getClientId());
                break;
            default:
                throw new IllegalArgumentException("Unknown authorization type：" + grantType);
        }
        Assert.notNull(accessTokenDO, "Access token cannot be empty"); // Defensive Check
        return success(OAuth2OpenConvert.INSTANCE.convert(accessTokenDO));
    }

    @DeleteMapping("/token")
    @PermitAll
    @Operation(summary = "Delete access token")
    @Parameter(name = "token", required = true, description = "Access Token", example = "biu")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<Boolean> revokeToken(HttpServletRequest request,
                                             @RequestParam("token") String token) {
        // Verify client
        String[] clientIdAndSecret = obtainBasicAuthorization(request);
        OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                null, null, null);

        // Delete access token
        return success(oauth2GrantService.revokeToken(client.getClientId(), token));
    }

    /**
     * Corresponding Spring Security OAuth of CheckTokenEndpoint Similar checkToken Method
     */
    @PostMapping("/check-token")
    @PermitAll
    @Operation(summary = "Verify access token")
    @Parameter(name = "token", required = true, description = "Access Token", example = "biu")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<OAuth2OpenCheckTokenRespVO> checkToken(HttpServletRequest request,
                                                               @RequestParam("token") String token) {
        // Verify client
        String[] clientIdAndSecret = obtainBasicAuthorization(request);
        oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                null, null, null);

        // Verify token
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.checkAccessToken(token);
        Assert.notNull(accessTokenDO, "Access token cannot be empty"); // Defensive Check
        return success(OAuth2OpenConvert.INSTANCE.convert2(accessTokenDO));
    }

    /**
     * Corresponding Spring Security OAuth of AuthorizationEndpoint Class authorize Method
     */
    @GetMapping("/authorize")
    @Operation(summary = "Get authorization information", description = "Suitable for code Authorization code mode，Or implicit Simplified Mode；In sso.vue Single sign-on interface is【Get】Call")
    @Parameter(name = "clientId", required = true, description = "Client ID", example = "tudou")
    public CommonResult<OAuth2OpenAuthorizeInfoRespVO> authorize(@RequestParam("clientId") String clientId) {
        // 0. Verify that the user is logged in。Passed Spring Security Realization

        // 1. Get Client Client information
        OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 2. Get the user's authorized information
        List<OAuth2ApproveDO> approves = oauth2ApproveService.getApproveList(getLoginUserId(), getUserType(), clientId);
        // Splicing returns
        return success(OAuth2OpenConvert.INSTANCE.convert(client, approves));
    }

    /**
     * Corresponding Spring Security OAuth of AuthorizationEndpoint Class approveOrDeny Method
     *
     * Scene 1：【Automatic authorization autoApprove = true】
     *      Just entered sso.vue Interface，Call this interface，The user has already authorized the application accordingly，Or OAuth2Client Support this scope Automatic authorization
     * Scene 2：【Manual authorization autoApprove = false】
     *      In sso.vue Interface，User selection is good scope Authorization scope，Call this interface，Authorize。At this time，approved for true Or false
     *
     * Because the front-end and back-end are separated，Axios Cannot be handled well 302 Redirect，So and Spring Security OAuth Slightly different，The return result is a redirect URL，The rest will be handled by the front end
     */
    @PostMapping("/authorize")
    @Operation(summary = "Apply for authorization", description = "Suitable for code Authorization code mode，Or implicit Simplified Mode；In sso.vue Single sign-on interface is【Submit】Call")
    @Parameters({
            @Parameter(name = "response_type", required = true, description = "Response type", example = "code"),
            @Parameter(name = "client_id", required = true, description = "Client ID", example = "tudou"),
            @Parameter(name = "scope", description = "Authorization scope", example = "userinfo.read"), // Use Map<String, Boolean> Format，Spring MVC This method of receiving parameters is not supported at the moment
            @Parameter(name = "redirect_uri", required = true, description = "Redirect URI", example = "https://www.econets.cn"),
            @Parameter(name = "auto_approve", required = true, description = "Does the user accept?", example = "true"),
            @Parameter(name = "state", example = "1")
    })
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<String> approveOrDeny(@RequestParam("response_type") String responseType,
                                              @RequestParam("client_id") String clientId,
                                              @RequestParam(value = "scope", required = false) String scope,
                                              @RequestParam("redirect_uri") String redirectUri,
                                              @RequestParam(value = "auto_approve") Boolean autoApprove,
                                              @RequestParam(value = "state", required = false) String state) {
        @SuppressWarnings("unchecked")
        Map<String, Boolean> scopes = JsonUtils.parseObject(scope, Map.class);
        scopes = ObjectUtil.defaultIfNull(scopes, Collections.emptyMap());
        // 0. Verify that the user is logged in。Passed Spring Security Realization

        // 1.1 Verification responseType Satisfied code Or token Value
        OAuth2GrantTypeEnum grantTypeEnum = getGrantTypeEnum(responseType);
        // 1.2 Verification redirectUri Is the redirected domain name legal? + Verification scope Are you there? Client Within the scope of authorization
        OAuth2ClientDO client = oauth2ClientService.validOAuthClientFromCache(clientId, null,
                grantTypeEnum.getGrantType(), scopes.keySet(), redirectUri);

        // 2.1 Assumption approved for null，This is scene 1
        if (Boolean.TRUE.equals(autoApprove)) {
            // If automatic authorization cannot be passed，Returns empty url，The front end does not jump
            if (!oauth2ApproveService.checkForPreApproval(getLoginUserId(), getUserType(), clientId, scopes.keySet())) {
                return success(null);
            }
        } else { // 2.2 Assumption approved Non null，This is scene 2
            // If the calculation fails，Then jump to an error link
            if (!oauth2ApproveService.updateAfterApproval(getLoginUserId(), getUserType(), clientId, scopes)) {
                return success(OAuth2Utils.buildUnsuccessfulRedirect(redirectUri, responseType, state,
                        "access_denied", "User denied access"));
            }
        }

        // 3.1 If yes code Authorization code mode，Then issue code Authorization code，and redirect
        List<String> approveScopes = convertList(scopes.entrySet(), Map.Entry::getKey, Map.Entry::getValue);
        if (grantTypeEnum == OAuth2GrantTypeEnum.AUTHORIZATION_CODE) {
            return success(getAuthorizationCodeRedirect(getLoginUserId(), client, approveScopes, redirectUri, state));
        }
        // 3.2 If yes token Then implicit Simplified Mode，Send accessToken Access Token，and redirect
        return success(getImplicitGrantRedirect(getLoginUserId(), client, approveScopes, redirectUri, state));
    }

    private static OAuth2GrantTypeEnum getGrantTypeEnum(String responseType) {
        if (StrUtil.equals(responseType, "code")) {
            return OAuth2GrantTypeEnum.AUTHORIZATION_CODE;
        }
        if (StrUtil.equalsAny(responseType, "token")) {
            return OAuth2GrantTypeEnum.IMPLICIT;
        }
        throw exception0(BAD_REQUEST.getCode(), "response_type Parameter value is only allowed code Japanese token");
    }

    private String getImplicitGrantRedirect(Long userId, OAuth2ClientDO client,
                                            List<String> scopes, String redirectUri, String state) {
        // 1. Create access token Access Token
        OAuth2AccessTokenDO accessTokenDO = oauth2GrantService.grantImplicit(userId, getUserType(), client.getClientId(), scopes);
        Assert.notNull(accessTokenDO, "Access token cannot be empty"); // Defensive Check
        // 2. Splicing redirects URL
        // noinspection unchecked
        return OAuth2Utils.buildImplicitRedirectUri(redirectUri, accessTokenDO.getAccessToken(), state, accessTokenDO.getExpiresTime(),
                scopes, JsonUtils.parseObject(client.getAdditionalInformation(), Map.class));
    }

    private String getAuthorizationCodeRedirect(Long userId, OAuth2ClientDO client,
                                                List<String> scopes, String redirectUri, String state) {
        // 1. Create code Authorization code
        String authorizationCode = oauth2GrantService.grantAuthorizationCodeForCode(userId, getUserType(), client.getClientId(), scopes,
                redirectUri, state);
        // 2. Splicing redirection URL
        return OAuth2Utils.buildAuthorizationCodeRedirectUri(redirectUri, authorizationCode, state);
    }

    private Integer getUserType() {
        return UserTypeEnum.ADMIN.getValue();
    }

    private String[] obtainBasicAuthorization(HttpServletRequest request) {
        String[] clientIdAndSecret = HttpUtils.obtainBasicAuthorization(request);
        if (ArrayUtil.isEmpty(clientIdAndSecret) || clientIdAndSecret.length != 2) {
            throw exception0(BAD_REQUEST.getCode(), "client_id or client_secret Not delivered correctly");
        }
        return clientIdAndSecret;
    }

}
