package cn.econets.blossom.module.pay.controller.admin.demo;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.econets.blossom.module.pay.api.notify.dto.PayRefundNotifyReqDTO;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import cn.econets.blossom.module.pay.convert.demo.PayDemoOrderConvert;
import cn.econets.blossom.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import cn.econets.blossom.module.pay.service.demo.PayDemoOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Sample Order")
@RestController
@RequestMapping("/pay/demo-order")
@Validated
public class PayDemoOrderController {

    @Resource
    private PayDemoOrderService payDemoOrderService;

    @PostMapping("/create")
    @Operation(summary = "Create a sample order")
    public CommonResult<Long> createDemoOrder(@Valid @RequestBody PayDemoOrderCreateReqVO createReqVO) {
        return success(payDemoOrderService.createDemoOrder(getLoginUserId(), createReqVO));
    }

    @GetMapping("/page")
    @Operation(summary = "Get sample order paging")
    public CommonResult<PageResult<PayDemoOrderRespVO>> getDemoOrderPage(@Valid PageParam pageVO) {
        PageResult<PayDemoOrderDO> pageResult = payDemoOrderService.getDemoOrderPage(pageVO);
        return success(PayDemoOrderConvert.INSTANCE.convertPage(pageResult));
    }

    @PostMapping("/update-paid")
    @Operation(summary = "Update the sample order to be paid") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    @PermitAll // No need to log in，Safety by PayDemoOrderService Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateDemoOrderPaid(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        payDemoOrderService.updateDemoOrderPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }

    @PutMapping("/refund")
    @Operation(summary = "Initiate a refund for the sample order")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<Boolean> refundDemoOrder(@RequestParam("id") Long id) {
        payDemoOrderService.refundDemoOrder(id, getClientIP());
        return success(true);
    }

    @PostMapping("/update-refunded")
    @Operation(summary = "Update the example order to be refunded") // By pay-module Payment Service，Make callback，Visible PayNotifyJob
    @PermitAll // No need to log in，Safety by PayDemoOrderService Internal verification implementation
    @OperateLog(enable = false) // Disable operation log，Because there is no operator
    public CommonResult<Boolean> updateDemoOrderRefunded(@RequestBody PayRefundNotifyReqDTO notifyReqDTO) {
        payDemoOrderService.updateDemoOrderRefunded(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayRefundId());
        return success(true);
    }

}
