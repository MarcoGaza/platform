package cn.econets.blossom.module.mp.controller.admin.material;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.material.vo.*;
import cn.econets.blossom.module.mp.convert.material.MpMaterialConvert;
import cn.econets.blossom.module.mp.dal.dataobject.material.MpMaterialDO;
import cn.econets.blossom.module.mp.service.material.MpMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Public account material")
@RestController
@RequestMapping("/mp/material")
@Validated
public class MpMaterialController {

    @Resource
    private MpMaterialService mpMaterialService;

    @Operation(summary = "Upload temporary material")
    @PostMapping("/upload-temporary")
    @PreAuthorize("@ss.hasPermission('mp:material:upload-temporary')")
    public CommonResult<MpMaterialUploadRespVO> uploadTemporaryMaterial(
            @Valid MpMaterialUploadTemporaryReqVO reqVO) throws IOException {
        MpMaterialDO material = mpMaterialService.uploadTemporaryMaterial(reqVO);
        return success(MpMaterialConvert.INSTANCE.convert(material));
    }

    @Operation(summary = "Upload permanent materials")
    @PostMapping("/upload-permanent")
    @PreAuthorize("@ss.hasPermission('mp:material:upload-permanent')")
    public CommonResult<MpMaterialUploadRespVO> uploadPermanentMaterial(
            @Valid MpMaterialUploadPermanentReqVO reqVO) throws IOException {
        MpMaterialDO material = mpMaterialService.uploadPermanentMaterial(reqVO);
        return success(MpMaterialConvert.INSTANCE.convert(material));
    }

    @Operation(summary = "Delete material")
    @DeleteMapping("/delete-permanent")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:material:delete')")
    public CommonResult<Boolean> deleteMaterial(@RequestParam("id") Long id) {
        mpMaterialService.deleteMaterial(id);
        return success(true);
    }

    @Operation(summary = "Upload pictures in graphic content")
    @PostMapping("/upload-news-image")
    @PreAuthorize("@ss.hasPermission('mp:material:upload-news-image')")
    public CommonResult<String> uploadNewsImage(@Valid MpMaterialUploadNewsImageReqVO reqVO)
            throws IOException {
        return success(mpMaterialService.uploadNewsImage(reqVO));
    }

    @Operation(summary = "Get material paging")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('mp:material:query')")
    public CommonResult<PageResult<MpMaterialRespVO>> getMaterialPage(@Valid MpMaterialPageReqVO pageReqVO) {
        PageResult<MpMaterialDO> pageResult = mpMaterialService.getMaterialPage(pageReqVO);
        return success(MpMaterialConvert.INSTANCE.convertPage(pageResult));
    }

}
