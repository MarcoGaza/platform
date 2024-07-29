package cn.econets.blossom.module.pay.controller.admin.wallet;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.econets.blossom.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import cn.econets.blossom.module.pay.service.wallet.PayWalletRechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;

@Tag(name = "Management Backend - Wallet Recharge")
@RestController
@RequestMapping("/pay/wallet-recharge")
@Validated
@Slf4j
public class PayWalletRechargeController {

    @Resource
    private PayWalletRechargeService walletRechargeService;

    @PostMapping("/update-paid")
    @Operation(summary = "Update wallet recharge to recharged") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    @PermitAll // No need to log in， Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateWalletRechargerPaid(@Valid @RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        walletRechargeService.updateWalletRechargerPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }

    // TODO ：Initiate a refund，Yes post Operation；
    @GetMapping("/refund")
    @Operation(summary = "Initiate wallet recharge refund")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<Boolean> refundWalletRecharge(@RequestParam("id") Long id) {
        walletRechargeService.refundWalletRecharge(id, getClientIP());
        return success(true);
    }

    @PostMapping("/update-refunded")
    @Operation(summary = "Update wallet recharge to be refunded") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    @PermitAll // No need to log in， Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateWalletRechargeRefunded(@RequestBody PayRefundNotifyReqDTO notifyReqDTO) {
        walletRechargeService.updateWalletRechargeRefunded(
                Long.valueOf(notifyReqDTO.getMerchantOrderId()), notifyReqDTO.getPayRefundId());
        return success(true);
    }

}
