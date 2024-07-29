package cn.econets.blossom.module.crm.controller.admin.business;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessRespVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessTransferReqVO;
import cn.econets.blossom.module.crm.convert.business.CrmBusinessConvert;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusTypeDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import cn.econets.blossom.module.crm.service.business.CrmBusinessStatusService;
import cn.econets.blossom.module.crm.service.business.CrmBusinessStatusTypeService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
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
import static cn.econets.blossom.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.CUSTOMER_NOT_EXISTS;

@Tag(name = "Management Backend - CRM Business Opportunities")
@RestController
@RequestMapping("/crm/business")
@Validated
public class CrmBusinessController {

    @Resource
    private CrmBusinessService businessService;
    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmBusinessStatusTypeService businessStatusTypeService;
    @Resource
    private CrmBusinessStatusService businessStatusService;

    @PostMapping("/create")
    @Operation(summary = "Create business opportunity")
    @PreAuthorize("@ss.hasPermission('crm:business:create')")
    public CommonResult<Long> createBusiness(@Valid @RequestBody CrmBusinessSaveReqVO createReqVO) {
        return success(businessService.createBusiness(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update business opportunities")
    @PreAuthorize("@ss.hasPermission('crm:business:update')")
    public CommonResult<Boolean> updateBusiness(@Valid @RequestBody CrmBusinessSaveReqVO updateReqVO) {
        businessService.updateBusiness(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete business opportunity")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:business:delete')")
    public CommonResult<Boolean> deleteBusiness(@RequestParam("id") Long id) {
        businessService.deleteBusiness(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Obtain business opportunities")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:business:query')")
    public CommonResult<CrmBusinessRespVO> getBusiness(@RequestParam("id") Long id) {
        CrmBusinessDO business = businessService.getBusiness(id);
        return success(BeanUtils.toBean(business, CrmBusinessRespVO.class));
    }

    @GetMapping("/list-by-ids")
    @Operation(summary = "Get a list of business opportunities")
    @Parameter(name = "ids", description = "Number", required = true, example = "[1024]")
    @PreAuthorize("@ss.hasPermission('crm:business:query')")
    public CommonResult<List<CrmBusinessRespVO>> getContactListByIds(@RequestParam("ids") List<Long> ids) {
        return success(BeanUtils.toBean(businessService.getBusinessList(ids, getLoginUserId()), CrmBusinessRespVO.class));
    }

    @GetMapping("/simple-all-list")
    @Operation(summary = "Get a simplified list of contacts")
    @PreAuthorize("@ss.hasPermission('crm:contact:query')")
    public CommonResult<List<CrmBusinessRespVO>> getSimpleContactList() {
        CrmBusinessPageReqVO reqVO = new CrmBusinessPageReqVO();
        reqVO.setPageSize(PAGE_SIZE_NONE); // No paging
        PageResult<CrmBusinessDO> pageResult = businessService.getBusinessPage(reqVO, getLoginUserId());
        return success(convertList(pageResult.getList(), business -> // Return only id、name Field
                new CrmBusinessRespVO().setId(business.getId()).setName(business.getName())));
    }

    @GetMapping("/page")
    @Operation(summary = "Get business opportunity paging")
    @PreAuthorize("@ss.hasPermission('crm:business:query')")
    public CommonResult<PageResult<CrmBusinessRespVO>> getBusinessPage(@Valid CrmBusinessPageReqVO pageVO) {
        PageResult<CrmBusinessDO> pageResult = businessService.getBusinessPage(pageVO, getLoginUserId());
        return success(buildBusinessDetailPageResult(pageResult));
    }

    @GetMapping("/page-by-customer")
    @Operation(summary = "Get business opportunity paging，Based on specified customers")
    public CommonResult<PageResult<CrmBusinessRespVO>> getBusinessPageByCustomer(@Valid CrmBusinessPageReqVO pageReqVO) {
        if (pageReqVO.getCustomerId() == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        PageResult<CrmBusinessDO> pageResult = businessService.getBusinessPageByCustomerId(pageReqVO);
        return success(buildBusinessDetailPageResult(pageResult));
    }

    @GetMapping("/page-by-contact")
    @Operation(summary = "Get the contact's business opportunity paging")
    @PreAuthorize("@ss.hasPermission('crm:business:query')")
    public CommonResult<PageResult<CrmBusinessRespVO>> getBusinessContactPage(@Valid CrmBusinessPageReqVO pageReqVO) {
        PageResult<CrmBusinessDO> pageResult = businessService.getBusinessPageByContact(pageReqVO);
        return success(buildBusinessDetailPageResult(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export Opportunities Excel")
    @PreAuthorize("@ss.hasPermission('crm:business:export')")
    @OperateLog(type = EXPORT)
    public void exportBusinessExcel(@Valid CrmBusinessPageReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PAGE_SIZE_NONE);
        PageResult<CrmBusinessDO> pageResult = businessService.getBusinessPage(exportReqVO, getLoginUserId());
        // Export Excel
        ExcelUtils.write(response, "Business Opportunities.xls", "Data", CrmBusinessRespVO.class,
                buildBusinessDetailPageResult(pageResult).getList());
    }

    /**
     * Build detailed business opportunity pagination results
     *
     * @param pageResult Simple business opportunity pagination results
     * @return Detailed business opportunity pagination results
     */
    private PageResult<CrmBusinessRespVO> buildBusinessDetailPageResult(PageResult<CrmBusinessDO> pageResult) {
        if (CollUtil.isEmpty(pageResult.getList())) {
            return PageResult.empty(pageResult.getTotal());
        }
        List<CrmBusinessStatusTypeDO> statusTypeList = businessStatusTypeService.getBusinessStatusTypeList(
                convertSet(pageResult.getList(), CrmBusinessDO::getStatusTypeId));
        List<CrmBusinessStatusDO> statusList = businessStatusService.getBusinessStatusList(
                convertSet(pageResult.getList(), CrmBusinessDO::getStatusId));
        List<CrmCustomerDO> customerList = customerService.getCustomerList(
                convertSet(pageResult.getList(), CrmBusinessDO::getCustomerId));
        return CrmBusinessConvert.INSTANCE.convertPage(pageResult, customerList, statusTypeList, statusList);
    }

    @PutMapping("/transfer")
    @Operation(summary = "Business Opportunity Transfer")
    @PreAuthorize("@ss.hasPermission('crm:business:update')")
    public CommonResult<Boolean> transferBusiness(@Valid @RequestBody CrmBusinessTransferReqVO reqVO) {
        businessService.transferBusiness(reqVO, getLoginUserId());
        return success(true);
    }

}
