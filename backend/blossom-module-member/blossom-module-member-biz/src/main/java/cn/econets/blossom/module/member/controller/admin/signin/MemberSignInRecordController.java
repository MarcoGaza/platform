package cn.econets.blossom.module.member.controller.admin.signin;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import cn.econets.blossom.module.member.controller.admin.signin.vo.record.MemberSignInRecordRespVO;
import cn.econets.blossom.module.member.convert.signin.MemberSignInRecordConvert;
import cn.econets.blossom.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.service.signin.MemberSignInRecordService;
import cn.econets.blossom.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Sign-in record")
@RestController
@RequestMapping("/member/sign-in/record")
@Validated
public class MemberSignInRecordController {

    @Resource
    private MemberSignInRecordService signInRecordService;

    @Resource
    private MemberUserService memberUserService;

    @GetMapping("/page")
    @Operation(summary = "Get the sign-in record page")
    @PreAuthorize("@ss.hasPermission('point:sign-in-record:query')")
    public CommonResult<PageResult<MemberSignInRecordRespVO>> getSignInRecordPage(@Valid MemberSignInRecordPageReqVO pageVO) {
        // Execute paging query
        PageResult<MemberSignInRecordDO> pageResult = signInRecordService.getSignInRecordPage(pageVO);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // The splicing result is returned
        List<MemberUserDO> users = memberUserService.getUserList(
                convertSet(pageResult.getList(), MemberSignInRecordDO::getUserId));
        return success(MemberSignInRecordConvert.INSTANCE.convertPage(pageResult, users));
    }
}
