package cn.econets.blossom.module.member.controller.admin.group;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.group.vo.*;
import cn.econets.blossom.module.member.convert.group.MemberGroupConvert;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;
import cn.econets.blossom.module.member.service.group.MemberGroupService;
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


@Tag(name = "Management Backend - User Grouping")
@RestController
@RequestMapping("/member/group")
@Validated
public class MemberGroupController {

    @Resource
    private MemberGroupService groupService;

    @PostMapping("/create")
    @Operation(summary = "Create user group")
    @PreAuthorize("@ss.hasPermission('member:group:create')")
    public CommonResult<Long> createGroup(@Valid @RequestBody MemberGroupCreateReqVO createReqVO) {
        return success(groupService.createGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update user group")
    @PreAuthorize("@ss.hasPermission('member:group:update')")
    public CommonResult<Boolean> updateGroup(@Valid @RequestBody MemberGroupUpdateReqVO updateReqVO) {
        groupService.updateGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user group")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('member:group:delete')")
    public CommonResult<Boolean> deleteGroup(@RequestParam("id") Long id) {
        groupService.deleteGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get user groups")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:group:query')")
    public CommonResult<MemberGroupRespVO> getGroup(@RequestParam("id") Long id) {
        MemberGroupDO group = groupService.getGroup(id);
        return success(MemberGroupConvert.INSTANCE.convert(group));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of member group information", description = "Only include enabled member groups，Mainly used for front-end drop-down options")
    public CommonResult<List<MemberGroupSimpleRespVO>> getSimpleGroupList() {
        // Get user list，As long as it is turned on
        List<MemberGroupDO> list = groupService.getEnableGroupList();
        return success(MemberGroupConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get user group paging")
    @PreAuthorize("@ss.hasPermission('member:group:query')")
    public CommonResult<PageResult<MemberGroupRespVO>> getGroupPage(@Valid MemberGroupPageReqVO pageVO) {
        PageResult<MemberGroupDO> pageResult = groupService.getGroupPage(pageVO);
        return success(MemberGroupConvert.INSTANCE.convertPage(pageResult));
    }

}
