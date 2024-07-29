package cn.econets.blossom.module.mp.controller.admin.account;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.mp.controller.admin.account.vo.*;
import cn.econets.blossom.module.mp.convert.account.MpAccountConvert;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.service.account.MpAccountService;
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

@Tag(name = "Management Backend - Public Account")
@RestController
@RequestMapping("/mp/account")
@Validated
public class MpAccountController {

    @Resource
    private MpAccountService mpAccountService;

    @PostMapping("/create")
    @Operation(summary = "Create a public account")
    @PreAuthorize("@ss.hasPermission('mp:account:create')")
    public CommonResult<Long> createAccount(@Valid @RequestBody MpAccountCreateReqVO createReqVO) {
        return success(mpAccountService.createAccount(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update the public account")
    @PreAuthorize("@ss.hasPermission('mp:account:update')")
    public CommonResult<Boolean> updateAccount(@Valid @RequestBody MpAccountUpdateReqVO updateReqVO) {
        mpAccountService.updateAccount(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the public account")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:account:delete')")
    public CommonResult<Boolean> deleteAccount(@RequestParam("id") Long id) {
        mpAccountService.deleteAccount(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the public account")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mp:account:query')")
    public CommonResult<MpAccountRespVO> getAccount(@RequestParam("id") Long id) {
        MpAccountDO wxAccount = mpAccountService.getAccount(id);
        return success(MpAccountConvert.INSTANCE.convert(wxAccount));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the public account paging")
    @PreAuthorize("@ss.hasPermission('mp:account:query')")
    public CommonResult<PageResult<MpAccountRespVO>> getAccountPage(@Valid MpAccountPageReqVO pageVO) {
        PageResult<MpAccountDO> pageResult = mpAccountService.getAccountPage(pageVO);
        return success(MpAccountConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of public account information")
    @PreAuthorize("@ss.hasPermission('mp:account:query')")
    public CommonResult<List<MpAccountSimpleRespVO>> getSimpleAccounts() {
        List<MpAccountDO> list = mpAccountService.getAccountList();
        return success(MpAccountConvert.INSTANCE.convertList02(list));
    }

    @PutMapping("/generate-qr-code")
    @Operation(summary = "Generate a public account QR code")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:account:qr-code')")
    public CommonResult<Boolean> generateAccountQrCode(@RequestParam("id") Long id) {
        mpAccountService.generateAccountQrCode(id);
        return success(true);
    }

    @PutMapping("/clear-quota")
    @Operation(summary = "Clear public account API Quota")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('mp:account:clear-quota')")
    public CommonResult<Boolean> clearAccountQuota(@RequestParam("id") Long id) {
        mpAccountService.clearAccountQuota(id);
        return success(true);
    }

}
