package cn.econets.blossom.module.system.controller.admin.socail;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserBindReqVO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserPageReqVO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserRespVO;
import cn.econets.blossom.module.system.controller.admin.socail.vo.user.SocialUserUnbindReqVO;
import cn.econets.blossom.module.system.convert.social.SocialUserConvert;
import cn.econets.blossom.module.system.dal.dataobject.social.SocialUserDO;
import cn.econets.blossom.module.system.service.social.SocialUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Social user")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class SocialUserController {

    @Resource
    private SocialUserService socialUserService;

    @PostMapping("/bind")
    @Operation(summary = "Social Binding，Use code Authorization code")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid SocialUserBindReqVO reqVO) {
        socialUserService.bindSocialUser(SocialUserConvert.INSTANCE.convert(
                SecurityFrameworkUtils.getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO));
        return success(true);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "Cancel social binding")
    public CommonResult<Boolean> socialUnbind(@RequestBody SocialUserUnbindReqVO reqVO) {
        socialUserService.unbindSocialUser(SecurityFrameworkUtils.getLoginUserId(), UserTypeEnum.ADMIN.getValue(), reqVO.getType(), reqVO.getOpenid());
        return success(true);
    }

    // ==================== Social user CRUD ====================

    @GetMapping("/get")
    @Operation(summary = "Get social users")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:social-user:query')")
    public CommonResult<SocialUserRespVO> getSocialUser(@RequestParam("id") Long id) {
        SocialUserDO socialUser = socialUserService.getSocialUser(id);
        return success(BeanUtils.toBean(socialUser, SocialUserRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get social user paging")
    @PreAuthorize("@ss.hasPermission('system:social-user:query')")
    public CommonResult<PageResult<SocialUserRespVO>> getSocialUserPage(@Valid SocialUserPageReqVO pageVO) {
        PageResult<SocialUserDO> pageResult = socialUserService.getSocialUserPage(pageVO);
        return success(BeanUtils.toBean(pageResult, SocialUserRespVO.class));
    }

}
