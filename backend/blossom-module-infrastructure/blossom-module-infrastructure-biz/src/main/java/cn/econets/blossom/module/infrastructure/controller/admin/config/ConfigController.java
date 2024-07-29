package cn.econets.blossom.module.infrastructure.controller.admin.config;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.infrastructure.controller.admin.config.vo.*;
import cn.econets.blossom.module.infrastructure.convert.config.ConfigConvert;
import cn.econets.blossom.module.infrastructure.dal.dataobject.config.ConfigDO;
import cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants;
import cn.econets.blossom.module.infrastructure.service.config.ConfigService;
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

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Parameter configuration")
@RestController
@RequestMapping("/infra/config")
@Validated
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PostMapping("/create")
    @Operation(summary = "Create parameter configuration")
    @PreAuthorize("@ss.hasPermission('infra:config:create')")
    public CommonResult<Long> createConfig(@Valid @RequestBody ConfigSaveReqVO createReqVO) {
        return success(configService.createConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Modify parameter configuration")
    @PreAuthorize("@ss.hasPermission('infra:config:update')")
    public CommonResult<Boolean> updateConfig(@Valid @RequestBody ConfigSaveReqVO updateReqVO) {
        configService.updateConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete parameter configuration")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:config:delete')")
    public CommonResult<Boolean> deleteConfig(@RequestParam("id") Long id) {
        configService.deleteConfig(id);
        return success(true);
    }

    @GetMapping(value = "/get")
    @Operation(summary = "Get parameter configuration")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<ConfigRespVO> getConfig(@RequestParam("id") Long id) {
        return success(ConfigConvert.INSTANCE.convert(configService.getConfig(id)));
    }

    @GetMapping(value = "/get-value-by-key")
    @Operation(summary = "Query parameter value based on parameter key name", description = "Invisible configuration，Not allowed to return to the front end")
    @Parameter(name = "key", description = "Parameter key", required = true, example = "ximu.biz.username")
    public CommonResult<String> getConfigKey(@RequestParam("key") String key) {
        ConfigDO config = configService.getConfigByKey(key);
        if (config == null) {
            return success(null);
        }
        if (!config.getVisible()) {
            throw exception(ErrorCodeConstants.CONFIG_GET_VALUE_ERROR_IF_VISIBLE);
        }
        return success(config.getValue());
    }

    @GetMapping("/page")
    @Operation(summary = "Get parameter configuration paging")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<PageResult<ConfigRespVO>> getConfigPage(@Valid ConfigPageReqVO pageReqVO) {
        PageResult<ConfigDO> page = configService.getConfigPage(pageReqVO);
        return success(ConfigConvert.INSTANCE.convertPage(page));
    }

    @GetMapping("/export")
    @Operation(summary = "Export parameter configuration")
    @PreAuthorize("@ss.hasPermission('infra:config:export')")
    @OperateLog(type = EXPORT)
    public void exportConfig(@Valid ConfigPageReqVO exportReqVO,
                             HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ConfigDO> list = configService.getConfigPage(exportReqVO).getList();
        // Output
        ExcelUtils.write(response, "Parameter configuration.xls", "Data", ConfigRespVO.class,
                ConfigConvert.INSTANCE.convertList(list));
    }

}
