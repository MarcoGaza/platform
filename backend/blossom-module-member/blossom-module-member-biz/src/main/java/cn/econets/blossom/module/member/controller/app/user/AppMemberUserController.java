package cn.econets.blossom.module.member.controller.app.user;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.member.controller.app.user.vo.*;
import cn.econets.blossom.module.member.convert.user.MemberUserConvert;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.service.level.MemberLevelService;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User APP - User Personal Center")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class AppMemberUserController {

    @Resource
    private MemberUserService userService;
    @Resource
    private MemberLevelService levelService;

    @GetMapping("/get")
    @Operation(summary = "Get basic information")
    @PreAuthenticated
    public CommonResult<AppMemberUserInfoRespVO> getUserInfo() {
        MemberUserDO user = userService.getUser(getLoginUserId());
        MemberLevelDO level = levelService.getLevel(user.getLevelId());
        return success(MemberUserConvert.INSTANCE.convert(user, level));
    }

    @PutMapping("/update")
    @Operation(summary = "Modify basic information")
    @PreAuthenticated
    public CommonResult<Boolean> updateUser(@RequestBody @Valid AppMemberUserUpdateReqVO reqVO) {
        userService.updateUser(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile")
    @Operation(summary = "Modify user's mobile phone number")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobile(@RequestBody @Valid AppMemberUserUpdateMobileReqVO reqVO) {
        userService.updateUserMobile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile-by-weixin")
    @Operation(summary = "Authorization code based on WeChat applet，Modify user's mobile phone number")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobileByWeixin(@RequestBody @Valid AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        userService.updateUserMobileByWeixin(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "Change user password", description = "Used when the user changes the password")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid AppMemberUserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Used when the user forgets the password")
    public CommonResult<Boolean> resetUserPassword(@RequestBody @Valid AppMemberUserResetPasswordReqVO reqVO) {
        userService.resetUserPassword(reqVO);
        return success(true);
    }

}

