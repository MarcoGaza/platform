package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;

/**
 * WeChat Pay【Native QR code】of PayClient Implementation class
 *
 * Document：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_4_1.shtml">Native Place an order</a>
 *
 */
@Slf4j
public class WxNativePayClient extends AbstractWxPayClient {

    public WxNativePayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_NATIVE.getCode(), config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.NATIVE);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV2(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderRequest Object
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(reqDTO);
        // Execute request
        WxPayNativeOrderResult response = client.createOrder(request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.QR_CODE.getMode(), response.getCodeUrl(),
                reqDTO.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV3(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderV3Request Object
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(reqDTO);
        // Execute request
        String response = client.createOrderV3(TradeTypeEnum.NATIVE, request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.QR_CODE.getMode(), response,
                reqDTO.getOutTradeNo(), response);
    }

}
