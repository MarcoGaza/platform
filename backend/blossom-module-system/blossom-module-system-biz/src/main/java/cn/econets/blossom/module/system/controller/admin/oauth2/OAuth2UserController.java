package cn.econets.blossom.module.system.controller.admin.oauth2;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.user.OAuth2UserInfoRespVO;
import cn.econets.blossom.module.system.controller.admin.oauth2.vo.user.OAuth2UserUpdateReqVO;
import cn.econets.blossom.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.dataobject.dept.PostDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.service.dept.DeptService;
import cn.econets.blossom.module.system.service.dept.PostService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * Mainly provided for external application calls
 *
 * 1. In getUserInfo Method，Add @PreAuthorize("@ss.hasScope('user.read')") Annotation，Statement needs to be satisfied scope = user.read
 * 2. In updateUserInfo Method，Add @PreAuthorize("@ss.hasScope('user.write')") Annotation，Statement needs to be satisfied scope = user.write
 *
 */
@Tag(name = "Management Backend - OAuth2.0 User")
@RestController
@RequestMapping("/system/oauth2/user")
@Validated
@Slf4j
public class OAuth2UserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;

    @GetMapping("/get")
    @Operation(summary = "Get basic user information")
    @PreAuthorize("@ss.hasScope('user.read')") //
    public CommonResult<OAuth2UserInfoRespVO> getUserInfo() {
        // Get basic user information
        AdminUserDO user = userService.getUser(getLoginUserId());
        OAuth2UserInfoRespVO resp = BeanUtils.toBean(user, OAuth2UserInfoRespVO.class);
        // Get department information
        if (user.getDeptId() != null) {
            DeptDO dept = deptService.getDept(user.getDeptId());
            resp.setDept(BeanUtils.toBean(dept, OAuth2UserInfoRespVO.Dept.class));
        }
        // Get job information
        if (CollUtil.isNotEmpty(user.getPostIds())) {
            List<PostDO> posts = postService.getPostList(user.getPostIds());
            resp.setPosts(BeanUtils.toBean(posts, OAuth2UserInfoRespVO.Post.class));
        }
        return success(resp);
    }

    @PutMapping("/update")
    @Operation(summary = "Update basic user information")
    @PreAuthorize("@ss.hasScope('user.write')")
    public CommonResult<Boolean> updateUserInfo(@Valid @RequestBody OAuth2UserUpdateReqVO reqVO) {
        // Here UserProfileUpdateReqVO =》UserProfileUpdateReqVO Object，Realize interface reuse。
        // Mainly，AdminUserService No one of my own BO Object，So this is the only way to reuse
        userService.updateUserProfile(getLoginUserId(), BeanUtils.toBean(reqVO, UserProfileUpdateReqVO.class));
        return success(true);
    }

}
