package cn.econets.blossom.module.system.controller.admin.permission;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuRespVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuSaveVO;
import cn.econets.blossom.module.system.controller.admin.permission.vo.menu.MenuSimpleRespVO;
import cn.econets.blossom.module.system.dal.dataobject.permission.MenuDO;
import cn.econets.blossom.module.system.service.permission.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Menu")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/create")
    @Operation(summary = "Create menu")
    @PreAuthorize("@ss.hasPermission('system:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody MenuSaveVO createReqVO) {
        Long menuId = menuService.createMenu(createReqVO);
        return success(menuId);
    }

    @PutMapping("/update")
    @Operation(summary = "Modify menu")
    @PreAuthorize("@ss.hasPermission('system:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuSaveVO updateReqVO) {
        menuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete menu")
    @Parameter(name = "id", description = "Role number", required= true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "Get menu list", description = "Used for【Menu Management】Interface")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<MenuRespVO>> getMenuList(MenuListReqVO reqVO) {
        List<MenuDO> list = menuService.getMenuList(reqVO);
        list.sort(Comparator.comparing(MenuDO::getSort));
        return success(BeanUtils.toBean(list, MenuRespVO.class));
    }

    @GetMapping({"/list-all-simple", "simple-list"})
    @Operation(summary = "Get the menu simplified information list", description = "Only include opened menus，Used for【Role Assignment Menu】Function options。" +
            "In a multi-tenant scenario，Only menus that are available in the tenant's package will be returned")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenuList() {
        List<MenuDO> list = menuService.getMenuListByTenant(
                new MenuListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        list.sort(Comparator.comparing(MenuDO::getSort));
        return success(BeanUtils.toBean(list, MenuSimpleRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "Get menu information")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<MenuRespVO> getMenu(Long id) {
        MenuDO menu = menuService.getMenu(id);
        return success(BeanUtils.toBean(menu, MenuRespVO.class));
    }

}
