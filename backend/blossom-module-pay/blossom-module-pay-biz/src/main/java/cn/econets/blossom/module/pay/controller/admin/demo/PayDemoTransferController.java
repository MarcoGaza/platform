package cn.econets.blossom.module.pay.controller.admin.demo;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.pay.api.notify.dto.PayTransferNotifyReqDTO;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferRespVO;
import cn.econets.blossom.module.pay.convert.demo.PayDemoTransferConvert;
import cn.econets.blossom.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import cn.econets.blossom.module.pay.service.demo.PayDemoTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Sample transfer slip")
@RestController
@RequestMapping("/pay/demo-transfer")
@Validated
public class PayDemoTransferController {
    @Resource
    private PayDemoTransferService demoTransferService;

    @PostMapping("/create")
    @Operation(summary = "Create a sample transfer order")
    public CommonResult<Long> createDemoTransfer(@Valid @RequestBody PayDemoTransferCreateReqVO createReqVO) {
        return success(demoTransferService.createDemoTransfer(createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "Get sample transfer order paging")
    public CommonResult<PageResult<PayDemoTransferRespVO>> getDemoTransferPage(@Valid PageParam pageVO) {
        PageResult<PayDemoTransferDO> pageResult = demoTransferService.getDemoTransferPage(pageVO);
        return success(PayDemoTransferConvert.INSTANCE.convertPage(pageResult));
    }

    @PostMapping("/update-status")
    @Operation(summary = "Update the transfer status of the sample transfer order") // By pay-module Transfer Service，Make callback
    @PermitAll // No need to log in，Safety by PayDemoTransferService Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateDemoTransferStatus(@RequestBody PayTransferNotifyReqDTO notifyReqDTO) {
        demoTransferService.updateDemoTransferStatus(Long.valueOf(notifyReqDTO.getMerchantTransferId()),
                notifyReqDTO.getPayTransferId());
        return success(true);
    }
}
