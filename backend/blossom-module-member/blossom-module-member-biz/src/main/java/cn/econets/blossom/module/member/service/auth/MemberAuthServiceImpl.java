package cn.econets.blossom.module.member.service.auth;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.module.member.controller.app.auth.vo.*;
import cn.econets.blossom.module.member.convert.auth.AuthConvert;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import cn.econets.blossom.module.system.api.logger.LoginLogApi;
import cn.econets.blossom.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.econets.blossom.module.system.api.oauth2.OAuth2TokenApi;
import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import cn.econets.blossom.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import cn.econets.blossom.module.system.api.sms.SmsCodeApi;
import cn.econets.blossom.module.system.api.social.SocialClientApi;
import cn.econets.blossom.module.system.api.social.SocialUserApi;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserRespDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import cn.econets.blossom.module.system.enums.logger.LoginLogTypeEnum;
import cn.econets.blossom.module.system.enums.logger.LoginResultEnum;
import cn.econets.blossom.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getTerminal;
import static cn.econets.blossom.module.member.enums.ErrorCodeConstants.*;

/**
 * Member's certification Service Interface
 *
 *
 */
@Service
@Slf4j
public class MemberAuthServiceImpl implements MemberAuthService {

    @Resource
    private MemberUserService userService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private LoginLogApi loginLogApi;
    @Resource
    private SocialUserApi socialUserApi;
    @Resource
    private SocialClientApi socialClientApi;
    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Override
    public AppAuthLoginRespVO login(AppAuthLoginReqVO reqVO) {
        // Use mobile phone + Password，Log in。
        MemberUserDO user = login0(reqVO.getMobile(), reqVO.getPassword());

        // If socialType Not empty，It means that you need to bind a social user
        String openid = null;
        if (reqVO.getSocialType() != null) {
            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE, openid);
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO smsLogin(AppAuthSmsLoginReqVO reqVO) {
        // Verify verification code
        String userIp = getClientIP();
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_LOGIN.getScene(), userIp));

        // Get registered users
        MemberUserDO user = userService.createUserIfAbsent(reqVO.getMobile(), userIp, getTerminal());
        Assert.notNull(user, "Failed to obtain user，The result is empty");

        // If socialType Not empty，It means that you need to bind a social user
        String openid = null;
        if (reqVO.getSocialType() != null) {
            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_SMS, openid);
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO socialLogin(AppAuthSocialLoginReqVO reqVO) {
        // Use code Authorization code，Log in。Then，Get the bound user ID
        SocialUserRespDTO socialUser = socialUserApi.getSocialUserByCode(UserTypeEnum.MEMBER.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null) {
            throw exception(AUTH_SOCIAL_USER_NOT_FOUND);
        }

        // Situation 1：Already bound，Read user information directly
        MemberUserDO user;
        if (socialUser.getUserId() != null) {
            user = userService.getUser(socialUser.getUserId());
        // Case 2：Not bound，Registered User + Bind user
        } else {
            user = userService.createUser(socialUser.getNickname(), socialUser.getAvatar(), getClientIP(), getTerminal());
            socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getType(), reqVO.getCode(), reqVO.getState()));
        }
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, socialUser.getOpenid());
    }

    @Override
    public AppAuthLoginRespVO weixinMiniAppLogin(AppAuthWeixinMiniAppLoginReqVO reqVO) {
        // Get the corresponding mobile phone number information
        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
                UserTypeEnum.MEMBER.getValue(), reqVO.getPhoneCode());
        Assert.notNull(phoneNumberInfo, "Failed to obtain mobile phone information，The result is empty");

        // Get registered users
        MemberUserDO user = userService.createUserIfAbsent(phoneNumberInfo.getPurePhoneNumber(),
                getClientIP(), TerminalEnum.WECHAT_MINI_PROGRAM.getTerminal());
        Assert.notNull(user, "Failed to obtain user，The result is empty");

        // Bind social user
        String openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                SocialTypeEnum.WECHAT_MINI_APP.getType(), reqVO.getLoginCode(), reqVO.getState()));

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, openid);
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(MemberUserDO user, String mobile,
                                                            LoginLogTypeEnum logType, String openid) {
        // Insert login log
        createLoginLog(user.getId(), mobile, logType, LoginResultEnum.SUCCESS);
        // Create Token Token
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
                .setUserId(user.getId()).setUserType(getUserType().getValue())
                .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));
        // Build return result
        return AuthConvert.INSTANCE.convert(accessTokenRespDTO, openid);
    }

    @Override
    public String getSocialAuthorizeUrl(Integer type, String redirectUri) {
        return socialClientApi.getAuthorizeUrl(type, UserTypeEnum.MEMBER.getValue(), redirectUri);
    }

    private MemberUserDO login0(String mobile, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_MOBILE;
        // Check if the account exists
        MemberUserDO user = userService.getUserByMobile(mobile);
        if (user == null) {
            createLoginLog(null, mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // Check whether to disable
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void createLoginLog(Long userId, String mobile, LoginLogTypeEnum logType, LoginResultEnum loginResult) {
        // Insert login log
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(mobile);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogApi.createLoginLog(reqDTO);
        // Update last login time
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, getClientIP());
        }
    }

    @Override
    public void logout(String token) {
        // Delete access token
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.removeAccessToken(token);
        if (accessTokenRespDTO == null) {
            return;
        }
        // Deleted successfully，Record the logout log
        createLogoutLog(accessTokenRespDTO.getUserId());
    }

    @Override
    public void sendSmsCode(Long userId, AppAuthSmsSendReqVO reqVO) {
        // Situation 1：If you want to modify the mobile phone scene，Need to verify whether the new mobile phone number has been registered，This means that the phone cannot be used anymore
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene())) {
            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
            if (user != null && !Objects.equals(user.getId(), userId)) {
                throw exception(AUTH_MOBILE_USED);
            }
        }
        // Situation 2：If it is a password reset scenario，Need to verify that the mobile phone number exists
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_RESET_PASSWORD.getScene())) {
            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
            if (user == null) {
                throw exception(USER_MOBILE_NOT_EXISTS);
            }
        }
        // Situation 3：If it is a password modification scenario，Need to check the mobile phone number，No front-end transfer required
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene())) {
            MemberUserDO user = userService.getUser(userId);
            // TODO Follow-up member user Mobile phone is not strongly bound，This needs some adjustments；
            reqVO.setMobile(user.getMobile());
        }

        // Execute send
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public void validateSmsCode(Long userId, AppAuthSmsValidateReqVO reqVO) {
        smsCodeApi.validateSmsCode(AuthConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public AppAuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenRespDTO accessTokenDO = oauth2TokenApi.refreshAccessToken(refreshToken,
                OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO, null);
    }

    private void createLogoutLog(Long userId) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_SELF.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(getMobile(userId));
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogApi.createLoginLog(reqDTO);
    }

    private String getMobile(Long userId) {
        if (userId == null) {
            return null;
        }
        MemberUserDO user = userService.getUser(userId);
        return user != null ? user.getMobile() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
