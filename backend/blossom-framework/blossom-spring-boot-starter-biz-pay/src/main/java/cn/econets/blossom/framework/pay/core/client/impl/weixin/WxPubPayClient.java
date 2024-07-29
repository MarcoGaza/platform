package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
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

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.invalidParamException;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;

/**
 * WeChat Pay（Public Account）of PayClient Implementation class
 *
 * Document：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_1.shtml">JSAPI Place an order</>
 *
 */
@Slf4j
public class WxPubPayClient extends AbstractWxPayClient {

    public WxPubPayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_PUB.getCode(), config);
    }

    protected WxPubPayClient(Long channelId, String channelCode, WxPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.JSAPI);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV2(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderRequest Object
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(reqDTO)
                .setOpenid(getOpenid(reqDTO));
        // Execute request
        WxPayMpOrderResult response = client.createOrder(request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.APP.getMode(), toJsonString(response),
                reqDTO.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV3(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Build WxPayUnifiedOrderRequest Object
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(reqDTO)
                .setPayer(new WxPayUnifiedOrderV3Request.Payer().setOpenid(getOpenid(reqDTO)));
        // Execute request
        WxPayUnifiedOrderV3Result.JsapiResult response = client.createOrderV3(TradeTypeEnum.JSAPI, request);

        // Conversion result
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.APP.getMode(), toJsonString(response),
                reqDTO.getOutTradeNo(), response);
    }

    // ========== Various tools and methods ==========

    static String getOpenid(PayOrderUnifiedReqDTO reqDTO) {
        String openid = MapUtil.getStr(reqDTO.getChannelExtras(), "openid");
        if (StrUtil.isEmpty(openid)) {
            throw invalidParamException("Payment Request openid Cannot be empty！");
        }
        return openid;
    }

}
