package cn.econets.blossom.module.crm.controller.admin.customer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.*;
import cn.econets.blossom.module.crm.convert.customer.CrmCustomerConvert;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerPoolConfigService;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.pojo.PageParam.PAGE_SIZE_NONE;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.CUSTOMER_POOL_CONFIG_NOT_EXISTS_OR_DISABLED;

@Tag(name = "Management Backend - CRM Customer")
@RestController
@RequestMapping("/crm/customer")
@Validated
public class CrmCustomerController {

    @Resource
    private CrmCustomerService customerService;
    @Resource
    private CrmCustomerPoolConfigService customerPoolConfigService;
    @Resource
    private DeptApi deptApi;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create a customer")
    @PreAuthorize("@ss.hasPermission('crm:customer:create')")
    public CommonResult<Long> createCustomer(@Valid @RequestBody CrmCustomerSaveReqVO createReqVO) {
        return success(customerService.createCustomer(createReqVO, getLoginUserId()));
    }

    @PutMapping("/update")
    @Operation(summary = "Update Customer")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> updateCustomer(@Valid @RequestBody CrmCustomerSaveReqVO updateReqVO) {
        customerService.updateCustomer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete customer")
    @Parameter(name = "id", description = "Customer Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer:delete')")
    public CommonResult<Boolean> deleteCustomer(@RequestParam("id") Long id) {
        customerService.deleteCustomer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get customers")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<CrmCustomerRespVO> getCustomer(@RequestParam("id") Long id) {
        // 1. Get Customer
        CrmCustomerDO customer = customerService.getCustomer(id);
        if (customer == null) {
            return success(null);
        }
        // 2. Splicing data
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                Collections.asSet(Long.valueOf(customer.getCreator()), customer.getOwnerUserId()));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        return success(CrmCustomerConvert.INSTANCE.convert(customer, userMap, deptMap));
    }

    @GetMapping("/page")
    @Operation(summary = "Get customer paging")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<CrmCustomerRespVO>> getCustomerPage(@Valid CrmCustomerPageReqVO pageVO) {
        // 1. Query customer paging
        PageResult<CrmCustomerDO> pageResult = customerService.getCustomerPage(pageVO, getLoginUserId());
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 2. Splicing data
        Map<Long, Long> poolDayMap = Boolean.TRUE.equals(pageVO.getPool()) ? null :
                getPoolDayMap(pageResult.getList()); // Customer Interface，Need to check the time until entering the high seas
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSetByFlatMap(pageResult.getList(), user -> Stream.of(Long.parseLong(user.getCreator()), user.getOwnerUserId())));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        return success(CrmCustomerConvert.INSTANCE.convertPage(pageResult, userMap, deptMap, poolDayMap));
    }

    @GetMapping("/put-in-pool-remind-page")
    @Operation(summary = "Get the page of customers waiting to enter the high seas")
    @PreAuthorize("@ss.hasPermission('crm:customer:query')")
    public CommonResult<PageResult<CrmCustomerRespVO>> getPutInPoolRemindCustomerPage(@Valid CrmCustomerPageReqVO pageVO) {
        // Get high seas configuration TODO @dbh52：Merge to getPutInPoolRemindCustomerPage It will be more suitable；
        CrmCustomerPoolConfigDO poolConfigDO = customerPoolConfigService.getCustomerPoolConfig();
        if (ObjUtil.isNull(poolConfigDO)
                || Boolean.FALSE.equals(poolConfigDO.getEnabled())
                || Boolean.FALSE.equals(poolConfigDO.getNotifyEnabled())
        ) { // TODO @dbh52：This bracket，No line break in general，In java Here；
            throw exception(CUSTOMER_POOL_CONFIG_NOT_EXISTS_OR_DISABLED);
        }

        // 1. Query customer paging
        PageResult<CrmCustomerDO> pageResult = customerService.getPutInPoolRemindCustomerPage(pageVO, poolConfigDO, getLoginUserId());
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 2. Splicing data
        // TODO @Merge getCustomerPage Japanese getPutInPoolRemindCustomerPage Post-processing；
        Map<Long, Long> poolDayMap = getPoolDayMap(pageResult.getList()); // Customer Interface，Need to check the time until entering the high seas
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSetByFlatMap(pageResult.getList(), user -> Stream.of(Long.parseLong(user.getCreator()), user.getOwnerUserId())));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));
        return success(CrmCustomerConvert.INSTANCE.convertPage(pageResult, userMap, deptMap, poolDayMap));
    }

    /**
     * Get the time to enter the high seas
     *
     * @param customerList Customer List
     * @return Map<key Customer Number, value Time to enter the high seas>
     */
    private Map<Long, Long> getPoolDayMap(List<CrmCustomerDO> customerList) {
        CrmCustomerPoolConfigDO poolConfig = customerPoolConfigService.getCustomerPoolConfig();
        if (poolConfig == null || !poolConfig.getEnabled()) {
            return MapUtil.empty();
        }
        return convertMap(customerList, CrmCustomerDO::getId, customer -> {
            // 1.1 Number of days unsold items were placed on the high seas
            long dealExpireDay = 0;
            if (!customer.getDealStatus()) {
                dealExpireDay = poolConfig.getDealExpireDays() - LocalDateTimeUtils.between(customer.getCreateTime());
            }
            // 1.2 Number of days released into the high seas without follow-up
            LocalDateTime lastTime = ObjUtil.defaultIfNull(customer.getContactLastTime(), customer.getCreateTime());
            long contactExpireDay = poolConfig.getContactExpireDays() - LocalDateTimeUtils.between(lastTime);
            if (contactExpireDay < 0) {
                contactExpireDay = 0;
            }
            // 2. Return the minimum number of days
            return Math.min(dealExpireDay, contactExpireDay);
        });
    }

    @GetMapping(value = "/list-all-simple")
    @Operation(summary = "Get a simplified list of customer information", description = "Only include clients with read permissions，Mainly used for front-end drop-down options")
    public CommonResult<List<CrmCustomerRespVO>> getSimpleDeptList() {
        CrmCustomerPageReqVO reqVO = new CrmCustomerPageReqVO();
        reqVO.setPageSize(PAGE_SIZE_NONE); // No paging
        List<CrmCustomerDO> list = customerService.getCustomerPage(reqVO, getLoginUserId()).getList();
        return success(convertList(list, customer -> // Return only id、name Simplify fields
                new CrmCustomerRespVO().setId(customer.getId()).setName(customer.getName())));
    }

    // TODO @puhui999：Export to the high seas，The front end can take over
    @GetMapping("/export-excel")
    @Operation(summary = "Export customers Excel")
    @PreAuthorize("@ss.hasPermission('crm:customer:export')")
    @OperateLog(type = EXPORT)
    public void exportCustomerExcel(@Valid CrmCustomerPageReqVO pageVO,
                                    HttpServletResponse response) throws IOException {
        pageVO.setPageSize(PAGE_SIZE_NONE); // No paging
        List<CrmCustomerDO> list = customerService.getCustomerPage(pageVO, getLoginUserId()).getList();
        // Export Excel
        ExcelUtils.write(response, "Customer.xls", "Data", CrmCustomerRespVO.class,
                BeanUtils.toBean(list, CrmCustomerRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "Get the import customer template")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // Manually create an export demo
        List<CrmCustomerImportExcelVO> list = Arrays.asList(
                CrmCustomerImportExcelVO.builder().name("Taro Road").industryId(1).level(1).source(1).mobile("15601691300").telephone("")
                        .website("https://doc.iocoder.cn/").qq("").wechat("").email("yunai@iocoder.cn").description("").remark("")
                        .areaId(null).detailAddress("").build(),
                CrmCustomerImportExcelVO.builder().name("Source code").industryId(1).level(1).source(1).mobile("15601691300").telephone("")
                        .website("https://doc.iocoder.cn/").qq("").wechat("").email("yunai@iocoder.cn").description("").remark("")
                        .areaId(null).detailAddress("").build()
        );
        // Output
        ExcelUtils.write(response, "Customer import template.xls", "Customer List", CrmCustomerImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "Import customers")
    @PreAuthorize("@ss.hasPermission('system:customer:import')")
    public CommonResult<CrmCustomerImportRespVO> importExcel(@Valid @RequestBody CrmCustomerImportReqVO importReqVO)
            throws Exception {
        List<CrmCustomerImportExcelVO> list = ExcelUtils.read(importReqVO.getFile(), CrmCustomerImportExcelVO.class);
        return success(customerService.importCustomerList(list, importReqVO));
    }

    @PutMapping("/transfer")
    @Operation(summary = "Transfer customer")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> transferCustomer(@Valid @RequestBody CrmCustomerTransferReqVO reqVO) {
        customerService.transferCustomer(reqVO, getLoginUserId());
        return success(true);
    }

    @PutMapping("/lock")
    @Operation(summary = "Lock/Unlock customer")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> lockCustomer(@Valid @RequestBody CrmCustomerLockReqVO lockReqVO) {
        customerService.lockCustomer(lockReqVO, getLoginUserId());
        return success(true);
    }

    // ==================== High Seas Operations ====================

    @PutMapping("/put-pool")
    @Operation(summary = "Data is placed in the high seas")
    @Parameter(name = "id", description = "Customer Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer:update')")
    public CommonResult<Boolean> putCustomerPool(@RequestParam("id") Long id) {
        customerService.putCustomerPool(id);
        return success(true);
    }

    @PutMapping("/receive")
    @Operation(summary = "Get high seas customers")
    @Parameter(name = "ids", description = "Number array", required = true, example = "1,2,3")
    @PreAuthorize("@ss.hasPermission('crm:customer:receive')")
    public CommonResult<Boolean> receiveCustomer(@RequestParam(value = "ids") List<Long> ids) {
        customerService.receiveCustomer(ids, getLoginUserId(), Boolean.TRUE);
        return success(true);
    }

    @PutMapping("/distribute")
    @Operation(summary = "Assign the high seas to the corresponding person in charge")
    @PreAuthorize("@ss.hasPermission('crm:customer:distribute')")
    public CommonResult<Boolean> distributeCustomer(@Valid @RequestBody CrmCustomerDistributeReqVO distributeReqVO) {
        customerService.receiveCustomer(distributeReqVO.getIds(), distributeReqVO.getOwnerUserId(), Boolean.FALSE);
        return success(true);
    }

}
