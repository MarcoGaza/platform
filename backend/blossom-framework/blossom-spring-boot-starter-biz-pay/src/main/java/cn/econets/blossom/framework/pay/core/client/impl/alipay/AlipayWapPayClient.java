package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.hutool.http.Method;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Alipay【Wap Website】of PayClient Implementation class
 *
 * Document：<a href="https://opendocs.alipay.com/apis/api_1/alipay.trade.wap.pay">Mobile website payment interface</a>
 *
 */
@Slf4j
public class AlipayWapPayClient extends AbstractAlipayPayClient {

    public AlipayWapPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_WAP.getCode(), config);
    }

    @Override
    public PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Build AlipayTradeWapPayModel Request
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        // ① Common parameters
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getPrice()));
        model.setProductCode("QUICK_WAP_PAY"); // Sales product code. Currently Wap Only supports in payment scenario QUICK_WAP_PAY
        // ② Personalized parameters【None】
        // ③ Alipay Wap There is only one display for payment：URL
        String displayMode = PayOrderDisplayModeEnum.URL.getMode();

        // 1.2 Build AlipayTradeWapPayRequest Request
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());
        model.setQuitUrl(reqDTO.getReturnUrl());

        // 2.1 Execute request
        AlipayTradeWapPayResponse response = client.pageExecute(request, Method.GET.name());
        // 2.2 Processing results
        if (!response.isSuccess()) {
            return buildClosedPayOrderRespDTO(reqDTO, response);
        }
        return PayOrderRespDTO.waitingOf(displayMode, response.getBody(),
                reqDTO.getOutTradeNo(), response);
    }
}
