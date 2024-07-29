package cn.econets.blossom.module.system.service.auth;


import cn.econets.blossom.module.system.controller.admin.auth.vo.*;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;

import javax.validation.Valid;

/**
 * Authentication of management background Service Interface
 *
 * Provide user login、The ability to log out
 *
 */
public interface AdminAuthService {

    /**
     * Verify account + Password。If passed，Return to user
     *
     * @param username Account
     * @param password Password
     * @return User
     */
    AdminUserDO authenticate(String username, String password);

    /**
     * Account login
     *
     * @param reqVO Login information
     * @return Login result
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);

    /**
     * Based on token Log out
     *
     * @param token token
     * @param logType Logout type
     */
    void logout(String token, Integer logType);

    /**
     * Send SMS verification code
     *
     * @param reqVO Send request
     */
    void sendSmsCode(AuthSmsSendReqVO reqVO);

    /**
     * SMS login
     *
     * @param reqVO Login information
     * @return Login result
     */
    AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) ;

    /**
     * Social quick login，Use code Authorization code
     *
     * @param reqVO Login information
     * @return Login result
     */
    AuthLoginRespVO socialLogin(@Valid AuthSocialLoginReqVO reqVO);

    /**
     * Refresh access token
     *
     * @param refreshToken Refresh Token
     * @return Login result
     */
    AuthLoginRespVO refreshToken(String refreshToken);

}
