package cn.econets.blossom.module.member.controller.admin.level;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.member.controller.admin.level.vo.level.*;
import cn.econets.blossom.module.member.convert.level.MemberLevelConvert;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelDO;
import cn.econets.blossom.module.member.service.level.MemberLevelService;
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

@Tag(name = "Management Backend - Member Level")
@RestController
@RequestMapping("/member/level")
@Validated
public class MemberLevelController {

    @Resource
    private MemberLevelService levelService;

    @PostMapping("/create")
    @Operation(summary = "Create membership level")
    @PreAuthorize("@ss.hasPermission('member:level:create')")
    public CommonResult<Long> createLevel(@Valid @RequestBody MemberLevelCreateReqVO createReqVO) {
        return success(levelService.createLevel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update membership level")
    @PreAuthorize("@ss.hasPermission('member:level:update')")
    public CommonResult<Boolean> updateLevel(@Valid @RequestBody MemberLevelUpdateReqVO updateReqVO) {
        levelService.updateLevel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete membership level")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('member:level:delete')")
    public CommonResult<Boolean> deleteLevel(@RequestParam("id") Long id) {
        levelService.deleteLevel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get membership level")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:level:query')")
    public CommonResult<MemberLevelRespVO> getLevel(@RequestParam("id") Long id) {
        MemberLevelDO level = levelService.getLevel(id);
        return success(MemberLevelConvert.INSTANCE.convert(level));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a list of simplified member level information", description = "Only include enabled membership levels，Mainly used for front-end drop-down options")
    public CommonResult<List<MemberLevelSimpleRespVO>> getSimpleLevelList() {
        // Get user list，As long as it is turned on
        List<MemberLevelDO> list = levelService.getEnableLevelList();
        // After sorting，Return to the front end
        return success(MemberLevelConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/list")
    @Operation(summary = "Get member level list")
    @PreAuthorize("@ss.hasPermission('member:level:query')")
    public CommonResult<List<MemberLevelRespVO>> getLevelList(@Valid MemberLevelListReqVO listReqVO) {
        List<MemberLevelDO> result = levelService.getLevelList(listReqVO);
        return success(MemberLevelConvert.INSTANCE.convertList(result));
    }

}
