package cn.econets.blossom.module.pay.controller.admin.wallet;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackagePageReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageRespVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageUpdateReqVO;
import cn.econets.blossom.module.pay.convert.wallet.PayWalletRechargePackageConvert;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import cn.econets.blossom.module.pay.service.wallet.PayWalletRechargePackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;


@Tag(name = "Management Backend - Wallet Recharge Package")
@RestController
@RequestMapping("/pay/wallet-recharge-package")
@Validated
public class PayWalletRechargePackageController {

    @Resource
    private PayWalletRechargePackageService walletRechargePackageService;

    @PostMapping("/create")
    @Operation(summary = "Create a wallet recharge package")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:create')")
    public CommonResult<Long> createWalletRechargePackage(@Valid @RequestBody WalletRechargePackageCreateReqVO createReqVO) {
        return success(walletRechargePackageService.createWalletRechargePackage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update wallet recharge package")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:update')")
    public CommonResult<Boolean> updateWalletRechargePackage(@Valid @RequestBody WalletRechargePackageUpdateReqVO updateReqVO) {
        walletRechargePackageService.updateWalletRechargePackage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete wallet recharge package")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:delete')")
    public CommonResult<Boolean> deleteWalletRechargePackage(@RequestParam("id") Long id) {
        walletRechargePackageService.deleteWalletRechargePackage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get wallet recharge package")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:query')")
    public CommonResult<WalletRechargePackageRespVO> getWalletRechargePackage(@RequestParam("id") Long id) {
        PayWalletRechargePackageDO walletRechargePackage = walletRechargePackageService.getWalletRechargePackage(id);
        return success(PayWalletRechargePackageConvert.INSTANCE.convert(walletRechargePackage));
    }

    @GetMapping("/page")
    @Operation(summary = "Get wallet recharge package page")
    @PreAuthorize("@ss.hasPermission('pay:wallet-recharge-package:query')")
    public CommonResult<PageResult<WalletRechargePackageRespVO>> getWalletRechargePackagePage(@Valid WalletRechargePackagePageReqVO pageVO) {
        PageResult<PayWalletRechargePackageDO> pageResult = walletRechargePackageService.getWalletRechargePackagePage(pageVO);
        return success(PayWalletRechargePackageConvert.INSTANCE.convertPage(pageResult));
    }

}
