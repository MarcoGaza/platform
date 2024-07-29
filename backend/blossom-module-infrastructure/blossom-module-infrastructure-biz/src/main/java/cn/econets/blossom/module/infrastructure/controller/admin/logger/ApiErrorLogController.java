package cn.econets.blossom.module.infrastructure.controller.admin.logger;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiErrorLogDO;
import cn.econets.blossom.module.infrastructure.service.logger.ApiErrorLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "Management Backend - API Error log")
@RestController
@RequestMapping("/infra/api-error-log")
@Validated
public class ApiErrorLogController {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @PutMapping("/update-status")
    @Operation(summary = "Update API Error log status")
    @Parameters({
            @Parameter(name = "id", description = "Number", required = true, example = "1024"),
            @Parameter(name = "processStatus", description = "Processing status", required = true, example = "1")
    })
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:update-status')")
    public CommonResult<Boolean> updateApiErrorLogProcess(@RequestParam("id") Long id,
                                                          @RequestParam("processStatus") Integer processStatus) {
        apiErrorLogService.updateApiErrorLogProcess(id, processStatus, WebFrameworkUtils.getLoginUserId());
        return CommonResult.success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "Get API Error log paging")
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:query')")
    public CommonResult<PageResult<ApiErrorLogRespVO>> getApiErrorLogPage(@Valid ApiErrorLogPageReqVO pageReqVO) {
        PageResult<ApiErrorLogDO> pageResult = apiErrorLogService.getApiErrorLogPage(pageReqVO);
        return CommonResult.success(BeanUtils.toBean(pageResult, ApiErrorLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export API Error log Excel")
    @PreAuthorize("@ss.hasPermission('infra:api-error-log:export')")
    @OperateLog(type = OperateTypeEnum.EXPORT)
    public void exportApiErrorLogExcel(@Valid ApiErrorLogPageReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ApiErrorLogDO> list = apiErrorLogService.getApiErrorLogPage(exportReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "API Error log.xls", "Data", ApiErrorLogRespVO.class,
                BeanUtils.toBean(list, ApiErrorLogRespVO.class));
    }

}
