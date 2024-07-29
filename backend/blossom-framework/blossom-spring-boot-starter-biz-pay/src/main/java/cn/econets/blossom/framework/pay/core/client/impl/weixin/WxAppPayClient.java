package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;

import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;

/**
 * WeChat Pay【App Payment】of PayClient Implementation class
 *
 * Document：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/open/pay/chapter2_5_3.shtml">App Payment</a>
 *
 * // TODO Not tested in detail，Because I don't have any money App
 */
@Slf4j
public class WxAppPayClient extends AbstractWxPayClient {

    public WxAppPayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_APP.getCode(), config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.APP);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV2(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderRequest Object
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(reqDTO);
        // Execute request
        WxPayMpOrderResult response = client.createOrder(request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.APP.getMode(), toJsonString(response),
                reqDTO.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV3(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderV3Request Object
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(reqDTO);
        // Execute request
        WxPayUnifiedOrderV3Result.AppResult response = client.createOrderV3(TradeTypeEnum.APP, request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.APP.getMode(), toJsonString(response),
                reqDTO.getOutTradeNo(), response);
    }

}
