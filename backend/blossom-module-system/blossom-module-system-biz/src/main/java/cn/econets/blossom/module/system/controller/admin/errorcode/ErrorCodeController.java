package cn.econets.blossom.module.system.controller.admin.errorcode;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.module.system.controller.admin.errorcode.vo.ErrorCodePageReqVO;
import cn.econets.blossom.module.system.controller.admin.errorcode.vo.ErrorCodeRespVO;
import cn.econets.blossom.module.system.controller.admin.errorcode.vo.ErrorCodeSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import cn.econets.blossom.module.system.service.errorcode.ErrorCodeService;
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

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;


@Tag(name = "Management Backend - Error code")
@RestController
@RequestMapping("/system/error-code")
@Validated
public class ErrorCodeController {

    @Resource
    private ErrorCodeService errorCodeService;

    @PostMapping("/create")
    @Operation(summary = "Create error code")
    @PreAuthorize("@ss.hasPermission('system:error-code:create')")
    public CommonResult<Long> createErrorCode(@Valid @RequestBody ErrorCodeSaveReqVO createReqVO) {
        return success(errorCodeService.createErrorCode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update error code")
    @PreAuthorize("@ss.hasPermission('system:error-code:update')")
    public CommonResult<Boolean> updateErrorCode(@Valid @RequestBody ErrorCodeSaveReqVO updateReqVO) {
        errorCodeService.updateErrorCode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete error code")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('system:error-code:delete')")
    public CommonResult<Boolean> deleteErrorCode(@RequestParam("id") Long id) {
        errorCodeService.deleteErrorCode(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get error code")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:error-code:query')")
    public CommonResult<ErrorCodeRespVO> getErrorCode(@RequestParam("id") Long id) {
        ErrorCodeDO errorCode = errorCodeService.getErrorCode(id);
        return success(BeanUtils.toBean(errorCode, ErrorCodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "Get error code paging")
    @PreAuthorize("@ss.hasPermission('system:error-code:query')")
    public CommonResult<PageResult<ErrorCodeRespVO>> getErrorCodePage(@Valid ErrorCodePageReqVO pageVO) {
        PageResult<ErrorCodeDO> pageResult = errorCodeService.getErrorCodePage(pageVO);
        return success(BeanUtils.toBean(pageResult, ErrorCodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export error code Excel")
    @PreAuthorize("@ss.hasPermission('system:error-code:export')")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void exportErrorCodeExcel(@Valid ErrorCodePageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ErrorCodeDO> list = errorCodeService.getErrorCodePage(exportReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Error code.xls", "Data", ErrorCodeRespVO.class,
                BeanUtils.toBean(list, ErrorCodeRespVO.class));
    }

}
