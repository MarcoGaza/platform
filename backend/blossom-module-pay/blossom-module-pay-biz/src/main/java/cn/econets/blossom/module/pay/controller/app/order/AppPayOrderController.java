package cn.econets.blossom.module.pay.controller.app.order;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderRespVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import cn.econets.blossom.module.pay.controller.app.order.vo.AppPayOrderSubmitReqVO;
import cn.econets.blossom.module.pay.controller.app.order.vo.AppPayOrderSubmitRespVO;
import cn.econets.blossom.module.pay.convert.order.PayOrderConvert;
import cn.econets.blossom.module.pay.framework.pay.core.WalletPayClient;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;
import java.util.Objects;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserType;

@Tag(name = "User APP - Payment order")
@RestController
@RequestMapping("/pay/order")
@Validated
@Slf4j
public class AppPayOrderController {

    @Resource
    private PayOrderService payOrderService;

    // TODO Temporary demo，Technical Proofing。
    @GetMapping("/get")
    @Operation(summary = "Get payment order")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    public CommonResult<PayOrderRespVO> getOrder(@RequestParam("id") Long id) {
        return success(PayOrderConvert.INSTANCE.convert(payOrderService.getOrder(id)));
    }

    @PostMapping("/submit")
    @Operation(summary = "Submit payment order")
    public CommonResult<AppPayOrderSubmitRespVO> submitPayOrder(@RequestBody AppPayOrderSubmitReqVO reqVO) {
        // 1. Wallet payment matters，Requires additional transmission user_id Japanese user_type
        if (Objects.equals(reqVO.getChannelCode(), PayChannelEnum.WALLET.getCode())) {
            Map<String, String> channelExtras = reqVO.getChannelExtras() == null ?
                    Maps.newHashMapWithExpectedSize(2) : reqVO.getChannelExtras();
            channelExtras.put(WalletPayClient.USER_ID_KEY, String.valueOf(getLoginUserId()));
            channelExtras.put(WalletPayClient.USER_TYPE_KEY, String.valueOf(getLoginUserType()));
            reqVO.setChannelExtras(channelExtras);
        }

        // 2. Submit payment
        PayOrderSubmitRespVO respVO = payOrderService.submitOrder(reqVO, getClientIP());
        return success(PayOrderConvert.INSTANCE.convert3(respVO));
    }

}
