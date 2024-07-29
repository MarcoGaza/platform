package cn.econets.blossom.module.member.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.user.vo.*;
import cn.econets.blossom.module.member.convert.user.MemberUserConvert;
import cn.econets.blossom.module.member.dal.dataobject.group.MemberGroupDO;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.member.dal.dataobject.tag.MemberTagDO;
import cn.econets.blossom.module.member.dal.dataobject.user.MemberUserDO;
import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;
import cn.econets.blossom.module.member.service.group.MemberGroupService;
import cn.econets.blossom.module.member.service.level.MemberLevelService;
import cn.econets.blossom.module.member.service.point.MemberPointRecordService;
import cn.econets.blossom.module.member.service.tag.MemberTagService;
import cn.econets.blossom.module.member.service.user.MemberUserService;
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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Member User")
@RestController
@RequestMapping("/member/user")
@Validated
public class MemberUserController {

    @Resource
    private MemberUserService memberUserService;
    @Resource
    private MemberTagService memberTagService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private MemberGroupService memberGroupService;
    @Resource
    private MemberPointRecordService memberPointRecordService;

    @PutMapping("/update")
    @Operation(summary = "Update member user")
    @PreAuthorize("@ss.hasPermission('member:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody MemberUserUpdateReqVO updateReqVO) {
        memberUserService.updateUser(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-level")
    @Operation(summary = "Update member user level")
    @PreAuthorize("@ss.hasPermission('member:user:update-level')")
    public CommonResult<Boolean> updateUserLevel(@Valid @RequestBody MemberUserUpdateLevelReqVO updateReqVO) {
        memberLevelService.updateUserLevel(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-point")
    @Operation(summary = "Update member user points")
    @PreAuthorize("@ss.hasPermission('member:user:update-point')")
    public CommonResult<Boolean> updateUserPoint(@Valid @RequestBody MemberUserUpdatePointReqVO updateReqVO) {
        memberPointRecordService.createPointRecord(updateReqVO.getId(), updateReqVO.getPoint(),
                MemberPointBizTypeEnum.ADMIN, String.valueOf(getLoginUserId()));
        return success(true);
    }

    @PutMapping("/update-balance")
    @Operation(summary = "Update member user balance")
    @PreAuthorize("@ss.hasPermission('member:user:update-balance')")
    public CommonResult<Boolean> updateUserBalance(@Valid @RequestBody Long id) {
        // todo ：Add one【Modify balance】
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get member user")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<MemberUserRespVO> getUser(@RequestParam("id") Long id) {
        MemberUserDO user = memberUserService.getUser(id);
        return success(MemberUserConvert.INSTANCE.convert03(user));
    }

    @GetMapping("/page")
    @Operation(summary = "Get member user paging")
    @PreAuthorize("@ss.hasPermission('member:user:query')")
    public CommonResult<PageResult<MemberUserRespVO>> getUserPage(@Valid MemberUserPageReqVO pageVO) {
        PageResult<MemberUserDO> pageResult = memberUserService.getUserPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty());
        }

        // Processing user tag display
        Set<Long> tagIds = pageResult.getList().stream()
                .map(MemberUserDO::getTagIds)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        List<MemberTagDO> tags = memberTagService.getTagList(tagIds);
        // Processing user level display
        List<MemberLevelDO> levels = memberLevelService.getLevelList(
                convertSet(pageResult.getList(), MemberUserDO::getLevelId));
        // Processing user grouping display
        List<MemberGroupDO> groups = memberGroupService.getGroupList(
                convertSet(pageResult.getList(), MemberUserDO::getGroupId));
        return success(MemberUserConvert.INSTANCE.convertPage(pageResult, tags, levels, groups));
    }

}
