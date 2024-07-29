package cn.econets.blossom.module.infrastructure.controller.admin.db;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.db.vo.DataSourceConfigRespVO;
import cn.econets.blossom.module.infrastructure.controller.admin.db.vo.DataSourceConfigSaveReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.db.DataSourceConfigDO;
import cn.econets.blossom.module.infrastructure.service.db.DataSourceConfigService;
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

@Tag(name = "Management Backend - Data source configuration")
@RestController
@RequestMapping("/infra/data-source-config")
@Validated
public class DataSourceConfigController {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @PostMapping("/create")
    @Operation(summary = "Create data source configuration")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:create')")
    public CommonResult<Long> createDataSourceConfig(@Valid @RequestBody DataSourceConfigSaveReqVO createReqVO) {
        return success(dataSourceConfigService.createDataSourceConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update data source configuration")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:update')")
    public CommonResult<Boolean> updateDataSourceConfig(@Valid @RequestBody DataSourceConfigSaveReqVO updateReqVO) {
        dataSourceConfigService.updateDataSourceConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete data source configuration")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:delete')")
    public CommonResult<Boolean> deleteDataSourceConfig(@RequestParam("id") Long id) {
        dataSourceConfigService.deleteDataSourceConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get data source configuration")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:query')")
    public CommonResult<DataSourceConfigRespVO> getDataSourceConfig(@RequestParam("id") Long id) {
        DataSourceConfigDO config = dataSourceConfigService.getDataSourceConfig(id);
        return success(BeanUtils.toBean(config, DataSourceConfigRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the data source configuration list")
    @PreAuthorize("@ss.hasPermission('infra:data-source-config:query')")
    public CommonResult<List<DataSourceConfigRespVO>> getDataSourceConfigList() {
        List<DataSourceConfigDO> list = dataSourceConfigService.getDataSourceConfigList();
        return success(BeanUtils.toBean(list, DataSourceConfigRespVO.class));
    }

}
