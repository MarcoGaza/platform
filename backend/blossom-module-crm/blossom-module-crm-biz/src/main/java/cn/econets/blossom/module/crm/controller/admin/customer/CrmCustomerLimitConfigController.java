package cn.econets.blossom.module.crm.controller.admin.customer;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigRespVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigSaveReqVO;
import cn.econets.blossom.module.crm.convert.customer.CrmCustomerLimitConfigConvert;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerLimitConfigDO;
import cn.econets.blossom.module.crm.service.customer.CrmCustomerLimitConfigService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSetByFlatMap;

@Tag(name = "Management Backend - CRM Customer restriction configuration")
@RestController
@RequestMapping("/crm/customer-limit-config")
@Validated
public class CrmCustomerLimitConfigController {

    @Resource
    private CrmCustomerLimitConfigService customerLimitConfigService;

    @Resource
    private DeptApi deptApi;
    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "Create customer restriction configuration")
    @PreAuthorize("@ss.hasPermission('crm:customer-limit-config:create')")
    public CommonResult<Long> createCustomerLimitConfig(@Valid @RequestBody CrmCustomerLimitConfigSaveReqVO createReqVO) {
        return success(customerLimitConfigService.createCustomerLimitConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update customer restriction configuration")
    @PreAuthorize("@ss.hasPermission('crm:customer-limit-config:update')")
    public CommonResult<Boolean> updateCustomerLimitConfig(@Valid @RequestBody CrmCustomerLimitConfigSaveReqVO updateReqVO) {
        customerLimitConfigService.updateCustomerLimitConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete customer restriction configuration")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('crm:customer-limit-config:delete')")
    public CommonResult<Boolean> deleteCustomerLimitConfig(@RequestParam("id") Long id) {
        customerLimitConfigService.deleteCustomerLimitConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get client restriction configuration")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:customer-limit-config:query')")
    public CommonResult<CrmCustomerLimitConfigRespVO> getCustomerLimitConfig(@RequestParam("id") Long id) {
        CrmCustomerLimitConfigDO customerLimitConfig = customerLimitConfigService.getCustomerLimitConfig(id);
        // Splicing data
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(customerLimitConfig.getUserIds());
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(customerLimitConfig.getDeptIds());
        return success(CrmCustomerLimitConfigConvert.INSTANCE.convert(customerLimitConfig, userMap, deptMap));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the customer restriction configuration page")
    @PreAuthorize("@ss.hasPermission('crm:customer-limit-config:query')")
    public CommonResult<PageResult<CrmCustomerLimitConfigRespVO>> getCustomerLimitConfigPage(@Valid CrmCustomerLimitConfigPageReqVO pageVO) {
        PageResult<CrmCustomerLimitConfigDO> pageResult = customerLimitConfigService.getCustomerLimitConfigPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }
        // Splicing data
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSetByFlatMap(pageResult.getList(), CrmCustomerLimitConfigDO::getUserIds, Collection::stream));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(
                convertSetByFlatMap(pageResult.getList(), CrmCustomerLimitConfigDO::getDeptIds, Collection::stream));
        return success(CrmCustomerLimitConfigConvert.INSTANCE.convertPage(pageResult, userMap, deptMap));
    }

}
