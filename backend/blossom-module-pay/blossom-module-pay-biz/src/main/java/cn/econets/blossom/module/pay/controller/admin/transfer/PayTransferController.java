package cn.econets.blossom.module.pay.controller.admin.transfer;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.transfer.vo.*;
import cn.econets.blossom.module.pay.convert.transfer.PayTransferConvert;
import cn.econets.blossom.module.pay.dal.dataobject.transfer.PayTransferDO;
import cn.econets.blossom.module.pay.service.transfer.PayTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;

@Tag(name = "Management Backend - Transfer slip")
@RestController
@RequestMapping("/pay/transfer")
@Validated
public class PayTransferController {

    @Resource
    private PayTransferService payTransferService;

    @PostMapping("/create")
    @Operation(summary = "Create a transfer order，Initiate transfer")
    @PreAuthorize("@ss.hasPermission('pay:transfer:create')")
    public CommonResult<PayTransferCreateRespVO> createPayTransfer(@Valid @RequestBody PayTransferCreateReqVO reqVO) {
        PayTransferDO payTransfer = payTransferService.createTransfer(reqVO, getClientIP());
        return success(new PayTransferCreateRespVO().setId(payTransfer.getId()).setStatus(payTransfer.getStatus()));
    }

    @GetMapping("/get")
    @Operation(summary = "Get transfer order")
    @PreAuthorize("@ss.hasPermission('pay:transfer:query')")
    public CommonResult<PayTransferRespVO> getTransfer(@RequestParam("id") Long id) {
        return success(PayTransferConvert.INSTANCE.convert(payTransferService.getTransfer(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the transfer order page")
    @PreAuthorize("@ss.hasPermission('pay:transfer:query')")
    public CommonResult<PageResult<PayTransferPageItemRespVO>> getTransferPage(@Valid PayTransferPageReqVO pageVO) {
        PageResult<PayTransferDO> pageResult = payTransferService.getTransferPage(pageVO);
        return success(PayTransferConvert.INSTANCE.convertPage(pageResult));
    }
}
