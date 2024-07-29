package cn.econets.blossom.module.system.controller.admin.auth;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.security.config.SecurityProperties;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.module.system.controller.admin.auth.vo.*;
import cn.econets.blossom.module.system.convert.auth.AuthConvert;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.enums.logger.LoginLogTypeEnum;
import cn.econets.blossom.module.system.service.auth.AdminAuthService;
import cn.econets.blossom.module.system.service.permission.MenuService;
import cn.econets.blossom.module.system.service.permission.PermissionService;
import cn.econets.blossom.module.system.service.permission.RoleService;
import cn.econets.blossom.module.system.service.social.SocialClientService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
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
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Authentication")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SocialClientService socialClientService;

    @Resource
    private SecurityProperties securityProperties;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "Log in using your account and password")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "Log out of the system")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @Operation(summary = "Refresh Token")
    @Parameter(name = "refreshToken", description = "Refresh Token", required = true)
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @Operation(summary = "Get the logged-in user's permission information")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo() {
        // 1.1 Get user information
        AdminUserDO user = userService.getUser(SecurityFrameworkUtils.getLoginUserId());
        if (user == null) {
            return null;
        }

        // 1.2 Get the role list
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(SecurityFrameworkUtils.getLoginUserId());
        if (CollUtil.isEmpty(roleIds)) {
            return success(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList()));
        }
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // Remove disabled roles

        // 1.3 Get the menu list
        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(CollectionUtils.convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList.removeIf(menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus())); // Remove disabled menu

        // 2. The splicing result is returned
        return success(AuthConvert.INSTANCE.convert(user, roles, menuList));
    }

    // ========== SMS login related ==========

    @PostMapping("/sms-login")
    @PermitAll
    @Operation(summary = "Log in using SMS verification code")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<AuthLoginRespVO> smsLogin(@RequestBody @Valid AuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    @PostMapping("/send-sms-code")
    @PermitAll
    @Operation(summary = "Send mobile verification code")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<Boolean> sendLoginSmsCode(@RequestBody @Valid AuthSmsSendReqVO reqVO) {
        authService.sendSmsCode(reqVO);
        return success(true);
    }

    // ========== Social login related ==========

    @GetMapping("/social-auth-redirect")
    @PermitAll
    @Operation(summary = "Social authorization jump")
    @Parameters({
            @Parameter(name = "type", description = "Social Type", required = true),
            @Parameter(name = "redirectUri", description = "Callback path")
    })
    public CommonResult<String> socialLogin(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return success(socialClientService.getAuthorizeUrl(
                type, UserTypeEnum.ADMIN.getValue(), redirectUri));
    }

    @PostMapping("/social-login")
    @PermitAll
    @Operation(summary = "Social quick login，Use code Authorization code", description = "For users who are not logged in，But the social account has been bound to the user")
    @OperateLog(enable = false) // Avoid Post Request to record operation log
    public CommonResult<AuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

}
