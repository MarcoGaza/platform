package cn.econets.blossom.module.mp.controller.admin.tag;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.tag.vo.*;
import cn.econets.blossom.module.mp.convert.tag.MpTagConvert;
import cn.econets.blossom.module.mp.dal.dataobject.tag.MpTagDO;
import cn.econets.blossom.module.mp.service.tag.MpTagService;
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

@Tag(name = "Management Backend - Public account tag")
@RestController
@RequestMapping("/mp/tag")
@Validated
public class MpTagController {

    @Resource
    private MpTagService mpTagService;

    @PostMapping("/create")
    @Operation(summary = "Create a public account tag")
    @PreAuthorize("@ss.hasPermission('mp:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody MpTagCreateReqVO createReqVO) {
        return success(mpTagService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update public account tags")
    @PreAuthorize("@ss.hasPermission('mp:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody MpTagUpdateReqVO updateReqVO) {
        mpTagService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the public account tag")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        mpTagService.deleteTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get public account tag details")
    @PreAuthorize("@ss.hasPermission('mp:tag:query')")
    public CommonResult<MpTagRespVO> get(@RequestParam("id") Long id) {
        MpTagDO mpTagDO = mpTagService.get(id);
        return success(MpTagConvert.INSTANCE.convert(mpTagDO));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the public account tag paging")
    @PreAuthorize("@ss.hasPermission('mp:tag:query')")
    public CommonResult<PageResult<MpTagRespVO>> getTagPage(MpTagPageReqVO pageReqVO) {
        PageResult<MpTagDO> pageResult = mpTagService.getTagPage(pageReqVO);
        return success(MpTagConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of public account information")
    @PreAuthorize("@ss.hasPermission('mp:account:query')")
    public CommonResult<List<MpTagSimpleRespVO>> getSimpleTags() {
        List<MpTagDO> list = mpTagService.getTagList();
        return success(MpTagConvert.INSTANCE.convertList02(list));
    }

    @PostMapping("/sync")
    @Operation(summary = "Synchronize public account tags")
    @Parameter(name = "accountId", description = "The public account number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:tag:sync')")
    public CommonResult<Boolean> syncTag(@RequestParam("accountId") Long accountId) {
        mpTagService.syncTag(accountId);
        return success(true);
    }

}
