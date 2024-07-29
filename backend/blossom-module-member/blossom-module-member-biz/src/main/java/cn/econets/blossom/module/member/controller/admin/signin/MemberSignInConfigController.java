package cn.econets.blossom.module.member.controller.admin.signin;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigRespVO;
import cn.econets.blossom.module.member.controller.admin.signin.vo.config.MemberSignInConfigUpdateReqVO;
import cn.econets.blossom.module.member.convert.signin.MemberSignInConfigConvert;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInConfigDO;
import cn.econets.blossom.module.member.service.signin.MemberSignInConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

// TODO url
@Tag(name = "Management Backend - Sign-in rules")
@RestController
@RequestMapping("/member/sign-in/config")
@Validated
public class MemberSignInConfigController {

    @Resource
    private MemberSignInConfigService signInConfigService;

    @PostMapping("/create")
    @Operation(summary = "Create sign-in rules")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:create')")
    public CommonResult<Long> createSignInConfig(@Valid @RequestBody MemberSignInConfigCreateReqVO createReqVO) {
        return success(signInConfigService.createSignInConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update sign-in rules")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:update')")
    public CommonResult<Boolean> updateSignInConfig(@Valid @RequestBody MemberSignInConfigUpdateReqVO updateReqVO) {
        signInConfigService.updateSignInConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete sign-in rules")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:delete')")
    public CommonResult<Boolean> deleteSignInConfig(@RequestParam("id") Long id) {
        signInConfigService.deleteSignInConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get sign-in rules")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<MemberSignInConfigRespVO> getSignInConfig(@RequestParam("id") Long id) {
        MemberSignInConfigDO signInConfig = signInConfigService.getSignInConfig(id);
        return success(MemberSignInConfigConvert.INSTANCE.convert(signInConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the sign-in rules list")
    @PreAuthorize("@ss.hasPermission('point:sign-in-config:query')")
    public CommonResult<List<MemberSignInConfigRespVO>> getSignInConfigList() {
        List<MemberSignInConfigDO> list = signInConfigService.getSignInConfigList();
        return success(MemberSignInConfigConvert.INSTANCE.convertList(list));
    }

}
