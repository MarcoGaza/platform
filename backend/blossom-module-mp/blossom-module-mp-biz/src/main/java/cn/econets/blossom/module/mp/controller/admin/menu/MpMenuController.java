package cn.econets.blossom.module.mp.controller.admin.menu;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.mp.controller.admin.menu.vo.MpMenuRespVO;
import cn.econets.blossom.module.mp.controller.admin.menu.vo.MpMenuSaveReqVO;
import cn.econets.blossom.module.mp.convert.menu.MpMenuConvert;
import cn.econets.blossom.module.mp.dal.dataobject.menu.MpMenuDO;
import cn.econets.blossom.module.mp.service.menu.MpMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Official Account Menu")
@RestController
@RequestMapping("/mp/menu")
@Validated
public class MpMenuController {

    @Resource
    private MpMenuService mpMenuService;

    @PostMapping("/save")
    @Operation(summary = "Save the public account menu")
    @PreAuthorize("@ss.hasPermission('mp:menu:save')")
    public CommonResult<Boolean> saveMenu(@Valid @RequestBody MpMenuSaveReqVO createReqVO) {
        mpMenuService.saveMenu(createReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the public account menu")
    @Parameter(name = "accountId", description = "The public account number", required = true, example = "10")
    @PreAuthorize("@ss.hasPermission('mp:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("accountId") Long accountId) {
        mpMenuService.deleteMenuByAccountId(accountId);
        return success(true);
    }

    @GetMapping("/list")
    @Operation(summary = "Get the public account menu list")
    @Parameter(name = "accountId", description = "The public account number", required = true, example = "10")
    @PreAuthorize("@ss.hasPermission('mp:menu:query')")
    public CommonResult<List<MpMenuRespVO>> getMenuList(@RequestParam("accountId") Long accountId) {
        List<MpMenuDO> list = mpMenuService.getMenuListByAccountId(accountId);
        return success(MpMenuConvert.INSTANCE.convertList(list));
    }

}
