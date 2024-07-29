package cn.econets.blossom.module.crm.controller.admin.contract;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractRespVO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractTransferReqVO;
import cn.econets.blossom.module.crm.convert.contract.CrmContractConvert;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractProductDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import cn.econets.blossom.module.crm.service.contact.CrmContactService;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.crm.service.product.CrmProductService;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static java.util.Collections.singletonList;

@Tag(name = "Management Backend - CRM Contract")
@RestController
@RequestMapping("/crm/contract")
@Validated
public class CrmContractController {

    @Resource
    private CrmContractService contractService;
    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmContactService contactService;
    @Resource
    private CrmBusinessService businessService;
    @Resource
    private CrmProductService productService;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create a contract")
    @PreAuthorize("@ss.hasPermission('crm:contract:create')")
    public CommonResult<Long> createContract(@Valid @RequestBody CrmContractSaveReqVO createReqVO) {
        return success(contractService.createContract(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Contract")
    @PreAuthorize("@ss.hasPermission('crm:contract:update')")
    public CommonResult<Boolean> updateContract(@Valid @RequestBody CrmContractSaveReqVO updateReqVO) {
        contractService.updateContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete contract")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:contract:delete')")
    public CommonResult<Boolean> deleteContract(@RequestParam("id") Long id) {
        contractService.deleteContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the contract")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:contract:query')")
    public CommonResult<CrmContractRespVO> getContract(@RequestParam("id") Long id) {
        // 1. Query Contract
        CrmContractDO contract = contractService.getContract(id);
        if (contract == null) {
            return success(null);
        }

        // 2. Splicing contract information
        List<CrmContractRespVO> respVOList = buildContractDetailList(singletonList(contract));
        return success(respVOList.get(0));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the contract page")
    @PreAuthorize("@ss.hasPermission('crm:contract:query')")
    public CommonResult<PageResult<CrmContractRespVO>> getContractPage(@Valid CrmContractPageReqVO pageVO) {
        PageResult<CrmContractDO> pageResult = contractService.getContractPage(pageVO, getLoginUserId());
        return success(BeanUtils.toBean(pageResult, CrmContractRespVO.class).setList(buildContractDetailList(pageResult.getList())));
    }

    @GetMapping("/page-by-customer")
    @Operation(summary = "Get the contract page，Based on specified customers")
    public CommonResult<PageResult<CrmContractRespVO>> getContractPageByCustomer(@Valid CrmContractPageReqVO pageVO) {
        Assert.notNull(pageVO.getCustomerId(), "Customer number cannot be empty");
        PageResult<CrmContractDO> pageResult = contractService.getContractPageByCustomerId(pageVO);
        return success(BeanUtils.toBean(pageResult, CrmContractRespVO.class).setList(buildContractDetailList(pageResult.getList())));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export Contract Excel")
    @PreAuthorize("@ss.hasPermission('crm:contract:export')")
    @OperateLog(type = EXPORT)
    public void exportContractExcel(@Valid CrmContractPageReqVO exportReqVO,
                                    HttpServletResponse response) throws IOException {
        PageResult<CrmContractDO> pageResult = contractService.getContractPage(exportReqVO, getLoginUserId());
        // Export Excel
        ExcelUtils.write(response, "Contract.xls", "Data", CrmContractRespVO.class,
                BeanUtils.toBean(pageResult.getList(), CrmContractRespVO.class));
    }

    @PutMapping("/transfer")
    @Operation(summary = "Contract transfer")
    @PreAuthorize("@ss.hasPermission('crm:contract:update')")
    public CommonResult<Boolean> transferContract(@Valid @RequestBody CrmContractTransferReqVO reqVO) {
        contractService.transferContract(reqVO, getLoginUserId());
        return success(true);
    }

    @PutMapping("/submit")
    @Operation(summary = "Submit contract for approval")
    @PreAuthorize("@ss.hasPermission('crm:contract:update')")
    public CommonResult<Boolean> submitContract(@RequestParam("id") Long id) {
        contractService.submitContract(id, getLoginUserId());
        return success(true);
    }

    /**
     * Build detailed contract results
     *
     * @param contractList Original contract information
     * @return Detailed contract results
     */
    private List<CrmContractRespVO> buildContractDetailList(List<CrmContractDO> contractList) {
        if (CollUtil.isEmpty(contractList)) {
            return Collections.emptyList();
        }
        // 1. Get customer list
        List<CrmCustomerDO> customerList = customerService.getCustomerList(
                convertSet(contractList, CrmContractDO::getCustomerId));
        // 2. Get the creator、List of responsible persons
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(convertListByFlatMap(contractList,
                contact -> Stream.of(NumberUtils.parseLong(contact.getCreator()), contact.getOwnerUserId())));
        // 3. Get contacts
        Map<Long, CrmContactDO> contactMap = convertMap(contactService.getContactListByIds(convertSet(contractList,
                CrmContractDO::getContactId)), CrmContactDO::getId);
        // 4. Get business opportunities
        Map<Long, CrmBusinessDO> businessMap = convertMap(businessService.getBusinessList(convertSet(contractList,
                CrmContractDO::getBusinessId)), CrmBusinessDO::getId);
        // 5. Get the products associated with the contract
        Map<Long, CrmContractProductDO> contractProductMap = null;
        List<CrmProductDO> productList = null;
        if (contractList.size() == 1) {
            List<CrmContractProductDO> contractProductList = contractService.getContractProductListByContractId(contractList.get(0).getId());
            contractProductMap = convertMap(contractProductList, CrmContractProductDO::getProductId);
            productList = productService.getProductListByIds(convertSet(contractProductList, CrmContractProductDO::getProductId));
        }
        return CrmContractConvert.INSTANCE.convertList(contractList, userMap, customerList, contactMap, businessMap, contractProductMap, productList);
    }

}
