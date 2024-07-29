package cn.econets.blossom.module.member.controller.app.auth;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.security.config.SecurityProperties;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.module.member.controller.app.auth.vo.*;
import cn.econets.blossom.module.member.convert.auth.AuthConvert;
import cn.econets.blossom.module.member.service.auth.MemberAuthService;
import cn.econets.blossom.module.system.api.social.SocialClientApi;
import cn.econets.blossom.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
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
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - Authentication")
@RestController
@RequestMapping("/member/auth")
@Validated
@Slf4j
public class AppAuthController {

    @Resource
    private MemberAuthService authService;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @Operation(summary = "Use mobile phone + Password login")
    public CommonResult<AppAuthLoginRespVO> login(@RequestBody @Valid AppAuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "Log out of the system")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Token")
    @Parameter(name = "refreshToken", description = "Refresh Token", required = true)
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<AppAuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    // ========== SMS login related ==========

    @PostMapping("/sms-login")
    @Operation(summary = "Use mobile phone + Verification code login")
    public CommonResult<AppAuthLoginRespVO> smsLogin(@RequestBody @Valid AppAuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    @PostMapping("/send-sms-code")
    @Operation(summary = "Send mobile verification code")
    public CommonResult<Boolean> sendSmsCode(@RequestBody @Valid AppAuthSmsSendReqVO reqVO) {
        authService.sendSmsCode(getLoginUserId(), reqVO);
        return success(true);
    }

    @PostMapping("/validate-sms-code")
    @Operation(summary = "Verify mobile phone verification code")
    public CommonResult<Boolean> validateSmsCode(@RequestBody @Valid AppAuthSmsValidateReqVO reqVO) {
        authService.validateSmsCode(getLoginUserId(), reqVO);
        return success(true);
    }

    // ========== Social login related ==========

    @GetMapping("/social-auth-redirect")
    @Operation(summary = "Social authorization jump")
    @Parameters({
            @Parameter(name = "type", description = "Social Type", required = true),
            @Parameter(name = "redirectUri", description = "Callback path")
    })
    public CommonResult<String> socialAuthRedirect(@RequestParam("type") Integer type,
                                                   @RequestParam("redirectUri") String redirectUri) {
        return CommonResult.success(authService.getSocialAuthorizeUrl(type, redirectUri));
    }

    @PostMapping("/social-login")
    @Operation(summary = "Social quick login，Use code Authorization code", description = "For users who are not logged in，But the social account has been bound to the user")
    public CommonResult<AppAuthLoginRespVO> socialLogin(@RequestBody @Valid AppAuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

    @PostMapping("/weixin-mini-app-login")
    @Operation(summary = "One-click login for WeChat mini program")
    public CommonResult<AppAuthLoginRespVO> weixinMiniAppLogin(@RequestBody @Valid AppAuthWeixinMiniAppLoginReqVO reqVO) {
        return success(authService.weixinMiniAppLogin(reqVO));
    }

    @PostMapping("/create-weixin-jsapi-signature")
    @Operation(summary = "Create WeChat JS SDK Signatures required for initialization",
            description = "Reference https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html Document")
    public CommonResult<SocialWxJsapiSignatureRespDTO> createWeixinMpJsapiSignature(@RequestParam("url") String url) {
        SocialWxJsapiSignatureRespDTO signature = socialClientApi.createWxMpJsapiSignature(
                UserTypeEnum.MEMBER.getValue(), url);
        return success(AuthConvert.INSTANCE.convert(signature));
    }

}
