package cn.econets.blossom.module.member.controller.admin.level;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.controller.admin.level.vo.record.MemberLevelRecordPageReqVO;
import cn.econets.blossom.module.member.controller.admin.level.vo.record.MemberLevelRecordRespVO;
import cn.econets.blossom.module.member.convert.level.MemberLevelRecordConvert;
import cn.econets.blossom.module.member.dal.dataobject.level.MemberLevelRecordDO;
import cn.econets.blossom.module.member.service.level.MemberLevelRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Member level record")
@RestController
@RequestMapping("/member/level-record")
@Validated
public class MemberLevelRecordController {

    @Resource
    private MemberLevelRecordService levelLogService;

    @GetMapping("/get")
    @Operation(summary = "Get member level record")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:level-record:query')")
    public CommonResult<MemberLevelRecordRespVO> getLevelRecord(@RequestParam("id") Long id) {
        MemberLevelRecordDO levelLog = levelLogService.getLevelRecord(id);
        return success(MemberLevelRecordConvert.INSTANCE.convert(levelLog));
    }

    @GetMapping("/page")
    @Operation(summary = "Get member level record paging")
    @PreAuthorize("@ss.hasPermission('member:level-record:query')")
    public CommonResult<PageResult<MemberLevelRecordRespVO>> getLevelRecordPage(
            @Valid MemberLevelRecordPageReqVO pageVO) {
        PageResult<MemberLevelRecordDO> pageResult = levelLogService.getLevelRecordPage(pageVO);
        return success(MemberLevelRecordConvert.INSTANCE.convertPage(pageResult));
    }

}
