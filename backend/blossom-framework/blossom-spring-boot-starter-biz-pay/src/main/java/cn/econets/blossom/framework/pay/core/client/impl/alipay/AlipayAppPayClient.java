package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Alipay【App Payment】of PayClient Implementation class
 *
 * Document：<a href="https://opendocs.alipay.com/open/02e7gq">App Payment</a>
 *
 * // TODO Not tested in detail，Because I don't have any money App
 *
 */
@Slf4j
public class AlipayAppPayClient extends AbstractAlipayPayClient {

    public AlipayAppPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_APP.getCode(), config);
    }

    @Override
    public PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Build AlipayTradeAppPayModel Request
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // ① Common parameters
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody() + "test");
        model.setTotalAmount(formatAmount(reqDTO.getPrice()));
        model.setTimeExpire(formatTime(reqDTO.getExpireTime()));
        model.setProductCode("QUICK_MSECURITY_PAY"); // Sales product code：Wireless fast payment products
        // ② Personalized parameters【None】
        // ③ Only one display is available for Alipay scan code payment
        String displayMode = PayOrderDisplayModeEnum.APP.getMode();

        // 1.2 Build AlipayTradePrecreateRequest Request
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 Execute request
        AlipayTradeAppPayResponse response = client.sdkExecute(request);
        // 2.2 Processing results
        if (!response.isSuccess()) {
            return buildClosedPayOrderRespDTO(reqDTO, response);
        }
        return PayOrderRespDTO.waitingOf(displayMode, response.getBody(),
                reqDTO.getOutTradeNo(), response);
    }
}
