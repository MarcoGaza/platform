package cn.econets.blossom.module.crm.controller.admin.permission;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.permission.vo.CrmPermissionCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.permission.vo.CrmPermissionRespVO;
import cn.econets.blossom.module.crm.controller.admin.permission.vo.CrmPermissionUpdateReqVO;
import cn.econets.blossom.module.crm.convert.permission.CrmPermissionConvert;
import cn.econets.blossom.module.crm.dal.dataobject.permission.CrmPermissionDO;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.PostApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.dept.dto.PostRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Stream;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - CRM Data permissions")
@RestController
@RequestMapping("/crm/permission")
@Validated
public class CrmPermissionController {

    @Resource
    private CrmPermissionService permissionService;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    private PostApi postApi;

    @PostMapping("/create")
    @Operation(summary = "Create data permissions")
    @PreAuthorize("@ss.hasPermission('crm:permission:create')")
    @CrmPermission(bizTypeValue = "#reqVO.bizType", bizId = "#reqVO.bizId", level = CrmPermissionLevelEnum.OWNER)
    public CommonResult<Boolean> addPermission(@Valid @RequestBody CrmPermissionCreateReqVO reqVO) {
        permissionService.createPermission(BeanUtils.toBean(reqVO, CrmPermissionCreateReqBO.class));
        return success(true);
    }

    @PutMapping("/update")
    @Operation(summary = "Edit data permissions")
    @PreAuthorize("@ss.hasPermission('crm:permission:update')")
    @CrmPermission(bizTypeValue = "#updateReqVO.bizType", bizId = "#updateReqVO.bizId"
            , level = CrmPermissionLevelEnum.OWNER)
    public CommonResult<Boolean> updatePermission(@Valid @RequestBody CrmPermissionUpdateReqVO updateReqVO) {
        permissionService.updatePermission(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete data permission")
    @Parameter(name = "ids", description = "Data permission number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:permission:delete')")
    public CommonResult<Boolean> deletePermission(@RequestParam("ids") Collection<Long> ids) {
        permissionService.deletePermissionBatch(ids, getLoginUserId());
        return success(true);
    }

    @DeleteMapping("/delete-self")
    @Operation(summary = "Delete your own data permissions")
    @Parameter(name = "id", description = "Data permission number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('crm:permission:delete')")
    public CommonResult<Boolean> deleteSelfPermission(@RequestParam("id") Long id) {
        permissionService.deleteSelfPermission(id, getLoginUserId());
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "Get data permission list")
    @Parameters({
            @Parameter(name = "bizType", description = "CRM Type", required = true, example = "2"),
            @Parameter(name = "bizId", description = "CRM Type data number", required = true, example = "1024")
    })
    @PreAuthorize("@ss.hasPermission('crm:permission:query')")
    public CommonResult<List<CrmPermissionRespVO>> getPermissionList(@RequestParam("bizType") Integer bizType,
                                                                     @RequestParam("bizId") Long bizId) {
        List<CrmPermissionDO> permission = permissionService.getPermissionListByBiz(bizType, bizId);
        if (CollUtil.isEmpty(permission)) {
            return success(Collections.emptyList());
        }

        // Splicing data
        List<AdminUserRespDTO> userList = adminUserApi.getUserList(convertSet(permission, CrmPermissionDO::getUserId));
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userList, AdminUserRespDTO::getDeptId));
        Set<Long> postIds = CollectionUtils.convertSetByFlatMap(userList, AdminUserRespDTO::getPostIds,
                item -> item != null ? item.stream() : Stream.empty());
        Map<Long, PostRespDTO> postMap = postApi.getPostMap(postIds);
        return success(CrmPermissionConvert.INSTANCE.convert(permission, userList, deptMap, postMap));
    }

}
