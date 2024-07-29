package cn.econets.blossom.module.system.service.auth;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.util.monitor.TracerUtils;
import cn.econets.blossom.framework.common.util.servlet.ServletUtils;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.econets.blossom.module.system.api.sms.SmsCodeApi;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserBindReqDTO;
import cn.econets.blossom.module.system.api.social.dto.SocialUserRespDTO;
import cn.econets.blossom.module.system.controller.admin.auth.vo.*;
import cn.econets.blossom.module.system.convert.auth.AuthConvert;
import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.enums.logger.LoginLogTypeEnum;
import cn.econets.blossom.module.system.enums.logger.LoginResultEnum;
import cn.econets.blossom.module.system.enums.oauth2.OAuth2ClientConstants;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import cn.econets.blossom.module.system.service.logger.LoginLogService;
import cn.econets.blossom.module.system.service.member.MemberService;
import cn.econets.blossom.module.system.service.oauth2.OAuth2TokenService;
import cn.econets.blossom.module.system.service.social.SocialUserService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service Implementation class
 *
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private SocialUserService socialUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;

    /**
     * Verification code switch，Default is true
     */
    @Value("${application.captcha.enable:true}")
    private Boolean captchaEnable;

    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // Check if the account exists
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // Check whether to disable
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // Verify the verification code
        validateCaptcha(reqVO);

        // Use account password，Log in
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // If socialType Not empty，It means that you need to bind a social user
        if (reqVO.getSocialType() != null) {
            socialUserService.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }
        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // Login scene，Verify if it exists
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // Send verification code
        SmsCodeSendReqDTO convert = AuthConvert.INSTANCE.convert(reqVO);
        convert.setCreateIp(ServletUtils.getClientIP());
        smsCodeApi.sendSmsCode(convert);
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // Verify the verification code
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), ServletUtils.getClientIP()));

        // Get user information
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // Insert login log
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // Update last login time
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @Override
    public AuthLoginRespVO socialLogin(AuthSocialLoginReqVO reqVO) {
        // Use code Authorization code，Log in。Then，Get the bound user ID
        SocialUserRespDTO socialUser = socialUserService.getSocialUserByCode(UserTypeEnum.ADMIN.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null) {
            throw exception(AUTH_THIRD_LOGIN_NOT_BIND);
        }

        // Get user
        AdminUserDO user = userService.getUser(socialUser.getUserId());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // Create Token Token，Record login log
        return createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.LOGIN_SOCIAL);
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        // If verification code is turned off，No verification is performed
        if (!captchaEnable) {
            return;
        }
        // Verify the verification code
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(reqVO.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        // Verification failed
        if (!response.isSuccess()) {
            // Create login failure log（Incorrect verification code)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR, response.getRepMsg());
        }
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // Insert login log
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // Create access token
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(userId, getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        // Build return result
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public AuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void logout(String token, Integer logType) {
        // Delete access token
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(token);
        if (accessTokenDO == null) {
            return;
        }
        // Deleted successfully，Record the logout log
        createLogoutLog(accessTokenDO.getUserId(), accessTokenDO.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

}
