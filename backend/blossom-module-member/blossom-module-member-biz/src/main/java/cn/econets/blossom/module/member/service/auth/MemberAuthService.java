package cn.econets.blossom.module.member.service.auth;

import cn.econets.blossom.module.member.controller.app.auth.vo.*;

import javax.validation.Valid;

/**
 * Member's certification Service Interface
 *
 * Provide the user's account and password to log in、token Authentication and other authentication related functions
 *
 * 
 */
public interface MemberAuthService {

    /**
     * Mobile phone + Password login
     *
     * @param reqVO Login information
     * @return Login result
     */
    AppAuthLoginRespVO login(@Valid AppAuthLoginReqVO reqVO);

    /**
     * Based on token Log out
     *
     * @param token token
     */
    void logout(String token);

    /**
     * Mobile phone + Verification code login
     *
     * @param reqVO    Login information
     * @return Login result
     */
    AppAuthLoginRespVO smsLogin(@Valid AppAuthSmsLoginReqVO reqVO);

    /**
     * Social login，Use code Authorization code
     *
     * @param reqVO Login information
     * @return Login result
     */
    AppAuthLoginRespVO socialLogin(@Valid AppAuthSocialLoginReqVO reqVO);

    /**
     * One-click login for WeChat mini program
     *
     * @param reqVO Login information
     * @return Login result
     */
    AppAuthLoginRespVO weixinMiniAppLogin(AppAuthWeixinMiniAppLoginReqVO reqVO);

    /**
     * Get social certification URL
     *
     * @param type Social platform type
     * @param redirectUri Jump address
     * @return Authentication URL
     */
    String getSocialAuthorizeUrl(Integer type, String redirectUri);

    /**
     * Send SMS verification code to user
     *
     * @param userId User Number
     * @param reqVO Send message
     */
    void sendSmsCode(Long userId, AppAuthSmsSendReqVO reqVO);

    /**
     * Check whether the SMS verification code is correct
     *
     * @param userId User Number
     * @param reqVO Verification information
     */
    void validateSmsCode(Long userId, AppAuthSmsValidateReqVO reqVO);

    /**
     * Refresh access token
     *
     * @param refreshToken Refresh Token
     * @return Login result
     */
    AppAuthLoginRespVO refreshToken(String refreshToken);

}
