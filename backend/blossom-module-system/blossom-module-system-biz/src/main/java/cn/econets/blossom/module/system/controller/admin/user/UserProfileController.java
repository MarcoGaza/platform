package cn.econets.blossom.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.econets.blossom.module.system.convert.user.UserConvert;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.dataobject.dept.PostDO;
import cn.econets.blossom.module.system.dal.dataobject.permission.RoleDO;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialUserDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.service.dept.DeptService;
import cn.econets.blossom.module.system.service.dept.PostService;
import cn.econets.blossom.module.system.service.permission.PermissionService;
import cn.econets.blossom.module.system.service.permission.RoleService;
import cn.econets.blossom.module.system.service.social.SocialUserService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "Management Backend - User Personal Center")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;

    @GetMapping("/get")
    @Operation(summary = "Get logged-in user information")
    @DataPermission(enable = false) // Close data permissions，Avoid viewing only yourself，No department found。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // Get basic user information
        AdminUserDO user = userService.getUser(getLoginUserId());
        // Get user role
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        // Get department information
        DeptDO dept = user.getDeptId() != null ? deptService.getDept(user.getDeptId()) : null;
        // Get job information
        List<PostDO> posts = CollUtil.isNotEmpty(user.getPostIds()) ? postService.getPostList(user.getPostIds()) : null;
        // Get social user information
        List<SocialUserDO> socialUsers = socialService.getSocialUserList(user.getId(), UserTypeEnum.ADMIN.getValue());
        return success(UserConvert.INSTANCE.convert(user, userRoles, dept, posts, socialUsers));
    }

    @PutMapping("/update")
    @Operation(summary = "Modify user personal information")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "Modify user's personal password")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar",
            method = {RequestMethod.POST, RequestMethod.PUT}) // Solved uni-app Not supported Put Problem with uploading files
    @Operation(summary = "Upload user profile picture")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
