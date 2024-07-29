package cn.econets.blossom.module.member.controller.admin.tag;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagCreateReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagRespVO;
import cn.econets.blossom.module.member.controller.admin.tag.vo.MemberTagUpdateReqVO;
import cn.econets.blossom.module.member.convert.tag.MemberTagConvert;
import cn.econets.blossom.module.member.dal.dataobject.tag.MemberTagDO;
import cn.econets.blossom.module.member.service.tag.MemberTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Member Tag")
@RestController
@RequestMapping("/member/tag")
@Validated
public class MemberTagController {

    @Resource
    private MemberTagService tagService;

    @PostMapping("/create")
    @Operation(summary = "Create member tag")
    @PreAuthorize("@ss.hasPermission('member:tag:create')")
    public CommonResult<Long> createTag(@Valid @RequestBody MemberTagCreateReqVO createReqVO) {
        return success(tagService.createTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update member tags")
    @PreAuthorize("@ss.hasPermission('member:tag:update')")
    public CommonResult<Boolean> updateTag(@Valid @RequestBody MemberTagUpdateReqVO updateReqVO) {
        tagService.updateTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete member tag")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('member:tag:delete')")
    public CommonResult<Boolean> deleteTag(@RequestParam("id") Long id) {
        tagService.deleteTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get member tag")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:tag:query')")
    public CommonResult<MemberTagRespVO> getMemberTag(@RequestParam("id") Long id) {
        MemberTagDO tag = tagService.getTag(id);
        return success(MemberTagConvert.INSTANCE.convert(tag));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a list of simplified member tag information", description = "Only include enabled member tags，Mainly used for front-end drop-down options")
    public CommonResult<List<MemberTagRespVO>> getSimpleTagList() {
        // Get user list，As long as it is turned on
        List<MemberTagDO> list = tagService.getTagList();
        // After sorting，Return to the front end
        return success(MemberTagConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list")
    @Operation(summary = "Get member tag list")
    @Parameter(name = "ids", description = "Numbered list", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('member:tag:query')")
    public CommonResult<List<MemberTagRespVO>> getMemberTagList(@RequestParam("ids") Collection<Long> ids) {
        List<MemberTagDO> list = tagService.getTagList(ids);
        return success(MemberTagConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get member tag paging")
    @PreAuthorize("@ss.hasPermission('member:tag:query')")
    public CommonResult<PageResult<MemberTagRespVO>> getTagPage(@Valid MemberTagPageReqVO pageVO) {
        PageResult<MemberTagDO> pageResult = tagService.getTagPage(pageVO);
        return success(MemberTagConvert.INSTANCE.convertPage(pageResult));
    }

}
