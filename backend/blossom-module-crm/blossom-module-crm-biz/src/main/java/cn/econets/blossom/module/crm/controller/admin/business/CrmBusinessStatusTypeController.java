package cn.econets.blossom.module.crm.controller.admin.business;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusRespVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeRespVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeSaveReqVO;
import cn.econets.blossom.module.crm.convert.business.CrmBusinessStatusConvert;
import cn.econets.blossom.module.crm.convert.business.CrmBusinessStatusTypeConvert;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusTypeDO;
import cn.econets.blossom.module.crm.service.business.CrmBusinessStatusService;
import cn.econets.blossom.module.crm.service.business.CrmBusinessStatusTypeService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - CRM Opportunity status type")
@RestController
@RequestMapping("/crm/business-status-type")
@Validated
public class CrmBusinessStatusTypeController {

    @Resource
    private CrmBusinessStatusTypeService businessStatusTypeService;

    @Resource
    private CrmBusinessStatusService businessStatusService;

    @Resource
    private DeptApi deptApi;

    @PostMapping("/create")
    @Operation(summary = "Create opportunity status type")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:create')")
    public CommonResult<Long> createBusinessStatusType(@Valid @RequestBody CrmBusinessStatusTypeSaveReqVO createReqVO) {
        return success(businessStatusTypeService.createBusinessStatusType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update opportunity status type")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:update')")
    public CommonResult<Boolean> updateBusinessStatusType(@Valid @RequestBody CrmBusinessStatusTypeSaveReqVO updateReqVO) {
        businessStatusTypeService.updateBusinessStatusType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete opportunity status type")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:delete')")
    public CommonResult<Boolean> deleteBusinessStatusType(@RequestParam("id") Long id) {
        businessStatusTypeService.deleteBusinessStatusType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the business opportunity status type")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:query')")
    public CommonResult<CrmBusinessStatusTypeRespVO> getBusinessStatusType(@RequestParam("id") Long id) {
        CrmBusinessStatusTypeDO statusType = businessStatusTypeService.getBusinessStatusType(id);
        // Processing status echo
        // TODO @lzxhqs：Can be in businessStatusService Add one getBusinessStatusListByTypeId Method，Return directly List<CrmBusinessStatusDO> Ha，Commonly used，Try to encapsulate a simple and easy-to-understand method，No need to pursue absolute universality；
        CrmBusinessStatusQueryVO queryVO = new CrmBusinessStatusQueryVO();
        queryVO.setTypeId(id);
        List<CrmBusinessStatusDO> statusList = businessStatusService.selectList(queryVO);
        return success(CrmBusinessStatusTypeConvert.INSTANCE.convert(statusType, statusList));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the opportunity status type page")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:query')")
    public CommonResult<PageResult<CrmBusinessStatusTypeRespVO>> getBusinessStatusTypePage(@Valid CrmBusinessStatusTypePageReqVO pageReqVO) {
        PageResult<CrmBusinessStatusTypeDO> pageResult = businessStatusTypeService.getBusinessStatusTypePage(pageReqVO);
        // Processing department echo
        Set<Long> deptIds = CollectionUtils.convertSetByFlatMap(pageResult.getList(), CrmBusinessStatusTypeDO::getDeptIds,Collection::stream);
        List<DeptRespDTO> deptList = deptApi.getDeptList(deptIds);
        return success(CrmBusinessStatusTypeConvert.INSTANCE.convertPage(pageResult, deptList));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export opportunity status type Excel")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:export')")
    @OperateLog(type = EXPORT)
    public void exportBusinessStatusTypeExcel(@Valid CrmBusinessStatusTypePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CrmBusinessStatusTypeDO> list = businessStatusTypeService.getBusinessStatusTypePage(pageReqVO).getList();
        // Export Excel
        ExcelUtils.write(response, "Opportunity Status Type.xls", "Data", CrmBusinessStatusTypeRespVO.class,
                        BeanUtils.toBean(list, CrmBusinessStatusTypeRespVO.class));
    }

    @GetMapping("/get-simple-list")
    @Operation(summary = "Get the list of business opportunity status types")
    @PreAuthorize("@ss.hasPermission('crm:business-status-type:query')")
    public CommonResult<List<CrmBusinessStatusTypeRespVO>> getBusinessStatusTypeList() {
        CrmBusinessStatusTypeQueryVO queryVO = new CrmBusinessStatusTypeQueryVO();
        queryVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<CrmBusinessStatusTypeDO> list = businessStatusTypeService.selectList(queryVO);
        return success(BeanUtils.toBean(list, CrmBusinessStatusTypeRespVO.class));
    }

    // TODO @ljlleo This interface，Is it possible to be with getBusinessStatusTypeList Merge into one？
    @GetMapping("/get-status-list")
    @Operation(summary = "Get the opportunity status list")
    @PreAuthorize("@ss.hasPermission('crm:business-status:query')")
    public CommonResult<List<CrmBusinessStatusRespVO>> getBusinessStatusListByTypeId(@RequestParam("typeId") Long typeId) {
        CrmBusinessStatusQueryVO queryVO = new CrmBusinessStatusQueryVO();
        queryVO.setTypeId(typeId);
        List<CrmBusinessStatusDO> list = businessStatusService.selectList(queryVO);
        return success(CrmBusinessStatusConvert.INSTANCE.convertList(list));
    }

}
