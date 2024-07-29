package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static cn.econets.blossom.framework.pay.core.client.impl.alipay.AlipayPayClientConfig.MODE_CERTIFICATE;

/**
 * Alipay【Scan code to pay】of PayClient Implementation class
 *
 * Document：<a href="https://opendocs.alipay.com/apis/02890k">Scan code to pay</a>
 *
 */
@Slf4j
public class AlipayQrPayClient extends AbstractAlipayPayClient {

    public AlipayQrPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_QR.getCode(), config);
    }

    @Override
    public PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Build AlipayTradePrecreateModel Request
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        // ① Common parameters
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getPrice()));
        model.setProductCode("FACE_TO_FACE_PAYMENT"); // Sales product code. Currently only supports scanning payment scenarios FACE_TO_FACE_PAYMENT
        // ② Personalized parameters【None】
        // ③ Only one display is available for Alipay scan code payment，Considering that the front end may want to scan the QR code，Open the phone
        String displayMode = PayOrderDisplayModeEnum.QR_CODE.getMode();

        // 1.2 Build AlipayTradePrecreateRequest Request
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 Execute request
        AlipayTradePrecreateResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            // Certificate Mode
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        // 2.2 Processing results
        if (!response.isSuccess()) {
            return buildClosedPayOrderRespDTO(reqDTO, response);
        }
        return PayOrderRespDTO.waitingOf(displayMode, response.getQrCode(),
                reqDTO.getOutTradeNo(), response);
    }
}
