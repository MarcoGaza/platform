package cn.econets.blossom.module.mp.controller.admin.message;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyCreateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyRespVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply.MpAutoReplyUpdateReqVO;
import cn.econets.blossom.module.mp.controller.admin.message.vo.message.MpMessagePageReqVO;
import cn.econets.blossom.module.mp.convert.message.MpAutoReplyConvert;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpAutoReplyDO;
import cn.econets.blossom.module.mp.service.message.MpAutoReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Automatic reply to public account")
@RestController
@RequestMapping("/mp/auto-reply")
@Validated
public class MpAutoReplyController {

    @Resource
    private MpAutoReplyService mpAutoReplyService;

    @GetMapping("/page")
    @Operation(summary = "Get the automatic reply paging of the public account")
    @PreAuthorize("@ss.hasPermission('mp:auto-reply:query')")
    public CommonResult<PageResult<MpAutoReplyRespVO>> getAutoReplyPage(@Valid MpMessagePageReqVO pageVO) {
        PageResult<MpAutoReplyDO> pageResult = mpAutoReplyService.getAutoReplyPage(pageVO);
        return success(MpAutoReplyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get")
    @Operation(summary = "Get automatic reply from the public account")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:auto-reply:query')")
    public CommonResult<MpAutoReplyRespVO> getAutoReply(@RequestParam("id") Long id) {
        MpAutoReplyDO autoReply = mpAutoReplyService.getAutoReply(id);
        return success(MpAutoReplyConvert.INSTANCE.convert(autoReply));
    }

    @PostMapping("/create")
    @Operation(summary = "Create an automatic reply to a public account")
    @PreAuthorize("@ss.hasPermission('mp:auto-reply:create')")
    public CommonResult<Long> createAutoReply(@Valid @RequestBody MpAutoReplyCreateReqVO createReqVO) {
        return success(mpAutoReplyService.createAutoReply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update the automatic reply of the public account")
    @PreAuthorize("@ss.hasPermission('mp:auto-reply:update')")
    public CommonResult<Boolean> updateAutoReply(@Valid @RequestBody MpAutoReplyUpdateReqVO updateReqVO) {
        mpAutoReplyService.updateAutoReply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the automatic reply of the public account")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:auto-reply:delete')")
    public CommonResult<Boolean> deleteAutoReply(@RequestParam("id") Long id) {
        mpAutoReplyService.deleteAutoReply(id);
        return success(true);
    }

}
