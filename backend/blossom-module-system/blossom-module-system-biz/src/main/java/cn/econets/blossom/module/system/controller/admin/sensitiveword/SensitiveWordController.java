package cn.econets.blossom.module.system.controller.admin.sensitiveword;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordPageReqVO;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordRespVO;
import cn.econets.blossom.module.system.controller.admin.sensitiveword.vo.SensitiveWordSaveVO;
import cn.econets.blossom.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import cn.econets.blossom.module.system.service.sensitiveword.SensitiveWordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Sensitive words")
@RestController
@RequestMapping("/system/sensitive-word")
@Validated
public class SensitiveWordController {

    @Resource
    private SensitiveWordService sensitiveWordService;

    @PostMapping("/create")
    @Operation(summary = "Create sensitive words")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:create')")
    public CommonResult<Long> createSensitiveWord(@Valid @RequestBody SensitiveWordSaveVO createReqVO) {
        return success(sensitiveWordService.createSensitiveWord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update sensitive words")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:update')")
    public CommonResult<Boolean> updateSensitiveWord(@Valid @RequestBody SensitiveWordSaveVO updateReqVO) {
        sensitiveWordService.updateSensitiveWord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete sensitive words")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:delete')")
    public CommonResult<Boolean> deleteSensitiveWord(@RequestParam("id") Long id) {
        sensitiveWordService.deleteSensitiveWord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get sensitive words")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<SensitiveWordRespVO> getSensitiveWord(@RequestParam("id") Long id) {
        SensitiveWordDO sensitiveWord = sensitiveWordService.getSensitiveWord(id);
        return success(BeanUtils.toBean(sensitiveWord, SensitiveWordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get sensitive word paging")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<PageResult<SensitiveWordRespVO>> getSensitiveWordPage(@Valid SensitiveWordPageReqVO pageVO) {
        PageResult<SensitiveWordDO> pageResult = sensitiveWordService.getSensitiveWordPage(pageVO);
        return success(BeanUtils.toBean(pageResult, SensitiveWordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export sensitive words Excel")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:export')")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void exportSensitiveWordExcel(@Valid SensitiveWordPageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SensitiveWordDO> list = sensitiveWordService.getSensitiveWordPage(exportReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Sensitive words.xls", "Data", SensitiveWordRespVO.class,
                BeanUtils.toBean(list, SensitiveWordRespVO.class));
    }

    @GetMapping("/get-tags")
    @Operation(summary = "Get the label array of all sensitive words")
    @PreAuthorize("@ss.hasPermission('system:sensitive-word:query')")
    public CommonResult<Set<String>> getSensitiveWordTagSet() {
        return success(sensitiveWordService.getSensitiveWordTagSet());
    }

    @GetMapping("/validate-text")
    @Operation(summary = "Get the array of illegal sensitive words contained in the text")
    public CommonResult<List<String>> validateText(@RequestParam("text") String text,
                                                   @RequestParam(value = "tags", required = false) List<String> tags) {
        return success(sensitiveWordService.validateText(text, tags));
    }

}
