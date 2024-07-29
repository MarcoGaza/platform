package cn.econets.blossom.module.promotion.controller.admin.diy;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.diy.vo.template.*;
import cn.econets.blossom.module.promotion.convert.diy.DiyTemplateConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyPageDO;
import cn.econets.blossom.module.promotion.dal.dataobject.diy.DiyTemplateDO;
import cn.econets.blossom.module.promotion.service.diy.DiyPageService;
import cn.econets.blossom.module.promotion.service.diy.DiyTemplateService;
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

@Tag(name = "Management Backend - Decoration template")
@RestController
@RequestMapping("/promotion/diy-template")
@Validated
public class DiyTemplateController {

    @Resource
    private DiyTemplateService diyTemplateService;
    @Resource
    private DiyPageService diyPageService;

    @PostMapping("/create")
    @Operation(summary = "Create decoration template")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:create')")
    public CommonResult<Long> createDiyTemplate(@Valid @RequestBody DiyTemplateCreateReqVO createReqVO) {
        return success(diyTemplateService.createDiyTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update decoration template")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:update')")
    public CommonResult<Boolean> updateDiyTemplate(@Valid @RequestBody DiyTemplateUpdateReqVO updateReqVO) {
        diyTemplateService.updateDiyTemplate(updateReqVO);
        return success(true);
    }

    @PutMapping("/use")
    @Operation(summary = "Use decoration template")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:use')")
    public CommonResult<Boolean> useDiyTemplate(@RequestParam("id") Long id) {
        diyTemplateService.useDiyTemplate(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete decoration template")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:delete')")
    public CommonResult<Boolean> deleteDiyTemplate(@RequestParam("id") Long id) {
        diyTemplateService.deleteDiyTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get decoration template")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:query')")
    public CommonResult<DiyTemplateRespVO> getDiyTemplate(@RequestParam("id") Long id) {
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(id);
        return success(DiyTemplateConvert.INSTANCE.convert(diyTemplate));
    }

    @GetMapping("/page")
    @Operation(summary = "Get decoration template paging")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:query')")
    public CommonResult<PageResult<DiyTemplateRespVO>> getDiyTemplatePage(@Valid DiyTemplatePageReqVO pageVO) {
        PageResult<DiyTemplateDO> pageResult = diyTemplateService.getDiyTemplatePage(pageVO);
        return success(DiyTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    // TODO This is not right getDiyTemplate Merge，Then DiyTemplateRespVO Just put it inside DiyPagePropertyRespVO Also add。Reduce VO OK，Management Backend get Return more data，Not a big problem。Purpose，I still want to reduce everyone's understanding cost as much as possible；
    @GetMapping("/get-property")
    @Operation(summary = "Get decoration template properties")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:query')")
    public CommonResult<DiyTemplatePropertyRespVO> getDiyTemplateProperty(@RequestParam("id") Long id) {
        DiyTemplateDO diyTemplate = diyTemplateService.getDiyTemplate(id);
        List<DiyPageDO> pages = diyPageService.getDiyPageByTemplateId(id);
        return success(DiyTemplateConvert.INSTANCE.convertPropertyVo(diyTemplate, pages));
    }

    // TODO This interface，Don't be in harmony useDiyTemplate Merge into one，Then VO Change to our new one VO Specification。Unchanged fields，Do not pass it on。
    @PutMapping("/update-property")
    @Operation(summary = "Update decoration template properties")
    @PreAuthorize("@ss.hasPermission('promotion:diy-template:update')")
    public CommonResult<Boolean> updateDiyTemplateProperty(@Valid @RequestBody DiyTemplatePropertyUpdateRequestVO updateReqVO) {
        diyTemplateService.updateDiyTemplateProperty(updateReqVO);
        return success(true);
    }

}
