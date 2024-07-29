package cn.econets.blossom.module.system.controller.admin.tenant;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackagePageReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackageSaveReqVO;
import cn.econets.blossom.module.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import cn.econets.blossom.module.system.dal.dataobject.tenant.TenantPackageDO;
import cn.econets.blossom.module.system.service.tenant.TenantPackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Tenant Package")
@RestController
@RequestMapping("/system/tenant-package")
@Validated
public class TenantPackageController {

    @Resource
    private TenantPackageService tenantPackageService;

    @PostMapping("/create")
    @Operation(summary = "Create tenant package")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:create')")
    public CommonResult<Long> createTenantPackage(@Valid @RequestBody TenantPackageSaveReqVO createReqVO) {
        return success(tenantPackageService.createTenantPackage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update tenant package")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:update')")
    public CommonResult<Boolean> updateTenantPackage(@Valid @RequestBody TenantPackageSaveReqVO updateReqVO) {
        tenantPackageService.updateTenantPackage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete tenant package")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('system:tenant-package:delete')")
    public CommonResult<Boolean> deleteTenantPackage(@RequestParam("id") Long id) {
        tenantPackageService.deleteTenantPackage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get tenant package")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:query')")
    public CommonResult<TenantPackageRespVO> getTenantPackage(@RequestParam("id") Long id) {
        TenantPackageDO tenantPackage = tenantPackageService.getTenantPackage(id);
        TenantPackageRespVO tenantPackageRespVO = BeanUtils.toBean(tenantPackage, TenantPackageRespVO.class);
        tenantPackageRespVO.setMenuIds(tenantPackageService.getMenuIdsByTenantPackageId(tenantPackage.getId()));
        return success(tenantPackageRespVO);
    }

    @GetMapping("/page")
    @Operation(summary = "Get tenant package page")
    @PreAuthorize("@ss.hasPermission('system:tenant-package:query')")
    public CommonResult<PageResult<TenantPackageRespVO>> getTenantPackagePage(@Valid TenantPackagePageReqVO pageVO) {
        PageResult<TenantPackageDO> pageResult = tenantPackageService.getTenantPackagePage(pageVO);
        return success(BeanUtils.toBean(pageResult, TenantPackageRespVO.class));
    }

    @GetMapping({"/get-simple-list", "simple-list"})
    @Operation(summary = "Get a simplified list of tenant package information", description = "Only include enabled tenant packages，Mainly used for front-end drop-down options")
    public CommonResult<List<TenantPackageSimpleRespVO>> getTenantPackageList() {
        List<TenantPackageDO> list = tenantPackageService.getTenantPackageListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(BeanUtils.toBean(list, TenantPackageSimpleRespVO.class));
    }

}
