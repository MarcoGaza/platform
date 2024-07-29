package cn.econets.blossom.module.crm.controller.admin.receivable;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanRespVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanUpdateReqVO;
import cn.econets.blossom.module.crm.convert.receivable.CrmReceivablePlanConvert;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivableDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivablePlanDO;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.crm.service.receivable.CrmReceivablePlanService;
import cn.econets.blossom.module.crm.service.receivable.CrmReceivableService;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertListByFlatMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - CRM Payment Refund Plan")
@RestController
@RequestMapping("/crm/receivable-plan")
@Validated
public class CrmReceivablePlanController {

    @Resource
    private CrmReceivablePlanService receivablePlanService;
    @Resource
    private CrmReceivableService receivableService;
    @Resource
    @Lazy
    private CrmContractService contractService;
    @Resource
    private CrmCustomerService customerService;

    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create a payment collection plan")
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:create')")
    public CommonResult<Long> createReceivablePlan(@Valid @RequestBody CrmReceivablePlanCreateReqVO createReqVO) {
        return success(receivablePlanService.createReceivablePlan(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update payment plan")
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:update')")
    public CommonResult<Boolean> updateReceivablePlan(@Valid @RequestBody CrmReceivablePlanUpdateReqVO updateReqVO) {
        receivablePlanService.updateReceivablePlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete payment plan")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:delete')")
    public CommonResult<Boolean> deleteReceivablePlan(@RequestParam("id") Long id) {
        receivablePlanService.deleteReceivablePlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the payment plan")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:query')")
    public CommonResult<CrmReceivablePlanRespVO> getReceivablePlan(@RequestParam("id") Long id) {
        CrmReceivablePlanDO receivablePlan = receivablePlanService.getReceivablePlan(id);
        return success(CrmReceivablePlanConvert.INSTANCE.convert(receivablePlan));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the payment plan page")
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:query')")
    public CommonResult<PageResult<CrmReceivablePlanRespVO>> getReceivablePlanPage(@Valid CrmReceivablePlanPageReqVO pageReqVO) {
        PageResult<CrmReceivablePlanDO> pageResult = receivablePlanService.getReceivablePlanPage(pageReqVO, getLoginUserId());
        return success(convertDetailReceivablePlanPage(pageResult));
    }

    @GetMapping("/page-by-customer")
    @Operation(summary = "Get the payment plan page，Based on specified customers")
    public CommonResult<PageResult<CrmReceivablePlanRespVO>> getReceivablePlanPageByCustomer(@Valid CrmReceivablePlanPageReqVO pageReqVO) {
        Assert.notNull(pageReqVO.getCustomerId(), "Customer number cannot be empty");
        PageResult<CrmReceivablePlanDO> pageResult = receivablePlanService.getReceivablePlanPageByCustomerId(pageReqVO);
        return success(convertDetailReceivablePlanPage(pageResult));
    }

    // TODO The export will be optimized later
    @GetMapping("/export-excel")
    @Operation(summary = "Export payment collection plan Excel")
    @PreAuthorize("@ss.hasPermission('crm:receivable-plan:export')")
    @OperateLog(type = EXPORT)
    public void exportReceivablePlanExcel(@Valid CrmReceivablePlanPageReqVO exportReqVO,
                                          HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PAGE_SIZE_NONE);
        PageResult<CrmReceivablePlanDO> pageResult = receivablePlanService.getReceivablePlanPage(exportReqVO, getLoginUserId());
        // Export Excel
        ExcelUtils.write(response, "Payment Refund Plan.xls", "Data", CrmReceivablePlanRespVO.class,
                convertDetailReceivablePlanPage(pageResult).getList());
    }

    /**
     * Build detailed payment collection plan pagination results
     *
     * @param pageResult Simple payment collection plan pagination results
     * @return Detailed payment collection plan pagination results
     */
    private PageResult<CrmReceivablePlanRespVO> convertDetailReceivablePlanPage(PageResult<CrmReceivablePlanDO> pageResult) {
        List<CrmReceivablePlanDO> receivablePlanList = pageResult.getList();
        if (CollUtil.isEmpty(receivablePlanList)) {
            return PageResult.empty(pageResult.getTotal());
        }
        // 1. Get customer list
        List<CrmCustomerDO> customerList = customerService.getCustomerList(
                convertSet(receivablePlanList, CrmReceivablePlanDO::getCustomerId));
        // 2. Get the creator、List of responsible persons
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(receivablePlanList,
                contact -> Stream.of(NumberUtils.parseLong(contact.getCreator()), contact.getOwnerUserId())));
        // 3. Get the contract list
        List<CrmContractDO> contractList = contractService.getContractList(
                convertSet(receivablePlanList, CrmReceivablePlanDO::getContractId));
        // 4. Get repayment list
        List<CrmReceivableDO> receivableList = receivableService.getReceivableList(
                convertSet(receivablePlanList, CrmReceivablePlanDO::getReceivableId));
        return CrmReceivablePlanConvert.INSTANCE.convertPage(pageResult, userMap, customerList, contractList, receivableList);
    }

}
