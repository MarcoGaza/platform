package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.Method;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Alipay【PC Website】of PayClient Implementation class
 *
 * Document：<a href="https://opendocs.alipay.com/open/270/105898">Computer website payment</a>
 *
 */
@Slf4j
public class AlipayPcPayClient extends AbstractAlipayPayClient {

    public AlipayPcPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_PC.getCode(), config);
    }

    @Override
    public PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Build AlipayTradePagePayModel Request
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        // ① Common parameters
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getPrice()));
        model.setTimeExpire(formatTime(reqDTO.getExpireTime()));
        model.setProductCode("FAST_INSTANT_TRADE_PAY"); // Sales product code. Currently PC Only supports payment scenarios FAST_INSTANT_TRADE_PAY
        // ② Personalized parameters
        // If you want more personalized parameters，For reference https://www.pingxx.com/api/Payment channel extra Parameter Description.html of alipay_pc_direct Partial expansion
        model.setQrPayMode("2"); // Jump Mode - Order Code，Effects see：https://help.pingxx.com/article/1137360/
        // ③ Alipay PC There are two display modes for payment：FORM、URL
        String displayMode = ObjectUtil.defaultIfNull(reqDTO.getDisplayMode(),
                PayOrderDisplayModeEnum.URL.getMode());

        // 1.2 Build AlipayTradePagePayRequest Request
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 Execute request
        AlipayTradePagePayResponse response;
        if (Objects.equals(displayMode, PayOrderDisplayModeEnum.FORM.getMode())) {
            response = client.pageExecute(request, Method.POST.name()); // Special use required POST Request
        } else {
            response = client.pageExecute(request, Method.GET.name());
        }
        // 2.2 Processing results
        if (!response.isSuccess()) {
            return buildClosedPayOrderRespDTO(reqDTO, response);
        }
        return PayOrderRespDTO.waitingOf(displayMode, response.getBody(),
                reqDTO.getOutTradeNo(), response);
    }
}
