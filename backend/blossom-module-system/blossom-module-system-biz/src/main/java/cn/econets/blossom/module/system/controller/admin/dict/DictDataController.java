package cn.econets.blossom.module.system.controller.admin.dict;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataPageReqVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataRespVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataSaveReqVO;
import cn.econets.blossom.module.system.controller.admin.dict.vo.data.DictDataSimpleRespVO;
import cn.econets.blossom.module.system.dal.dataobject.dict.DictDataDO;
import cn.econets.blossom.module.system.service.dict.DictDataService;
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
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Dictionary data")
@RestController
@RequestMapping("/system/dict-data")
@Validated
public class DictDataController {

    @Resource
    private DictDataService dictDataService;

    @PostMapping("/create")
    @Operation(summary = "Add new dictionary data")
    @PreAuthorize("@ss.hasPermission('system:dict:create')")
    public CommonResult<Long> createDictData(@Valid @RequestBody DictDataSaveReqVO createReqVO) {
        Long dictDataId = dictDataService.createDictData(createReqVO);
        return success(dictDataId);
    }

    @PutMapping("/update")
    @Operation(summary = "Modify dictionary data")
    @PreAuthorize("@ss.hasPermission('system:dict:update')")
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody DictDataSaveReqVO updateReqVO) {
        dictDataService.updateDictData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete dictionary data")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dict:delete')")
    public CommonResult<Boolean> deleteDictData(Long id) {
        dictDataService.deleteDictData(id);
        return success(true);
    }

    @GetMapping(value = {"/list-all-simple", "simple-list"})
    @Operation(summary = "Get the list of all dictionary data", description = "Generally used to manage background cache dictionary data locally")
    // No need to add permission authentication，Because the front end needs it globally
    public CommonResult<List<DictDataSimpleRespVO>> getSimpleDictDataList() {
        List<DictDataDO> list = dictDataService.getDictDataList(
                CommonStatusEnum.ENABLE.getStatus(), null);
        return success(BeanUtils.toBean(list, DictDataSimpleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "/Get the paginated list of dictionary type")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<PageResult<DictDataRespVO>> getDictTypePage(@Valid DictDataPageReqVO pageReqVO) {
        PageResult<DictDataDO> pageResult = dictDataService.getDictDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DictDataRespVO.class));
    }

    @GetMapping(value = "/get")
    @Operation(summary = "/Query dictionary data details")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:dict:query')")
    public CommonResult<DictDataRespVO> getDictData(@RequestParam("id") Long id) {
        DictDataDO dictData = dictDataService.getDictData(id);
        return success(BeanUtils.toBean(dictData, DictDataRespVO.class));
    }

    @GetMapping("/export")
    @Operation(summary = "Export dictionary data")
    @PreAuthorize("@ss.hasPermission('system:dict:export')")
    @OperateLog(type = EXPORT)
    public void export(HttpServletResponse response, @Valid DictDataPageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DictDataDO> list = dictDataService.getDictDataPage(exportReqVO).getList();
        // Output
        ExcelUtils.write(response, "Dictionary data.xls", "Data", DictDataRespVO.class,
                BeanUtils.toBean(list, DictDataRespVO.class));
    }

}
