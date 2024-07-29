package cn.econets.blossom.module.system.util.oauth2;

import cn.econets.blossom.framework.common.util.http.HttpUtils;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * OAuth2 Related tools
 *
 */
public class OAuth2Utils {

    /**
     * Build authorization code mode，Redirected URI
     *
     * copy from Spring Security OAuth2 of AuthorizationEndpoint Class getSuccessfulRedirect Method
     *
     * @param redirectUri Redirect URI
     * @param authorizationCode Authorization code
     * @param state Status
     * @return Redirection in authorization code mode URI
     */
    public static String buildAuthorizationCodeRedirectUri(String redirectUri, String authorizationCode, String state) {
        Map<String, String> query = new LinkedHashMap<>();
        query.put("code", authorizationCode);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, false);
    }

    /**
     * Build in simplified mode，Redirected URI
     *
     * copy from Spring Security OAuth2 of AuthorizationEndpoint Class appendAccessToken Method
     *
     * @param redirectUri Redirect URI
     * @param accessToken Access Token
     * @param state Status
     * @param expireTime Expiration time
     * @param scopes Authorization scope
     * @param additionalInformation Additional information
     * @return Redirection in simplified authorization mode URI
     */
    public static String buildImplicitRedirectUri(String redirectUri, String accessToken, String state, LocalDateTime expireTime,
                                                  Collection<String> scopes, Map<String, Object> additionalInformation) {
        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        Map<String, String> keys = new HashMap<String, String>();
        vars.put("access_token", accessToken);
        vars.put("token_type", SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        if (state != null) {
            vars.put("state", state);
        }
        if (expireTime != null) {
            vars.put("expires_in", getExpiresIn(expireTime));
        }
        if (CollUtil.isNotEmpty(scopes)) {
            vars.put("scope", buildScopeStr(scopes));
        }
        if (CollUtil.isNotEmpty(additionalInformation)) {
            for (String key : additionalInformation.keySet()) {
                Object value = additionalInformation.get(key);
                if (value != null) {
                    keys.put("extra_" + key, key);
                    vars.put("extra_" + key, value);
                }
            }
        }
        // Do not include the refresh token (even if there is one)
        return HttpUtils.append(redirectUri, vars, keys, true);
    }

    public static String buildUnsuccessfulRedirect(String redirectUri, String responseType, String state,
                                                   String error, String description) {
        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("error", error);
        query.put("error_description", description);
        if (state != null) {
            query.put("state", state);
        }
        return HttpUtils.append(redirectUri, query, null, !responseType.contains("code"));
    }

    public static long getExpiresIn(LocalDateTime expireTime) {
        return LocalDateTimeUtil.between(LocalDateTime.now(), expireTime, ChronoUnit.SECONDS);
    }

    public static String buildScopeStr(Collection<String> scopes) {
        return CollUtil.join(scopes, " ");
    }

    public static List<String> buildScopes(String scope) {
        return StrUtil.split(scope, ' ');
    }

}
