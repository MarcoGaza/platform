package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.econets.blossom.framework.pay.core.client.impl.alipay.AlipayPayClientConfig.MODE_CERTIFICATE;

/**
 * Alipay【Barcode payment】of PayClient Implementation class
 *
 * Document：<a href="https://opendocs.alipay.com/open/194/105072">Pay in person</a>
 *
 */
@Slf4j
public class AlipayBarPayClient extends AbstractAlipayPayClient {

    public AlipayBarPayClient(Long channelId, AlipayPayClientConfig config) {
        super(channelId, PayChannelEnum.ALIPAY_BAR.getCode(), config);
    }

    @Override
    public PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws AlipayApiException {
        String authCode = MapUtil.getStr(reqDTO.getChannelExtras(), "auth_code");
        if (StrUtil.isEmpty(authCode)) {
            throw exception0(BAD_REQUEST.getCode(), "The barcode cannot be empty");
        }

        // 1.1 Build AlipayTradePayModel Request
        AlipayTradePayModel model = new AlipayTradePayModel();
        // ① Common parameters
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setSubject(reqDTO.getSubject());
        model.setBody(reqDTO.getBody());
        model.setTotalAmount(formatAmount(reqDTO.getPrice()));
        model.setScene("bar_code"); // Pay in person with barcode payment scenario
        // ② Personalized parameters
        model.setAuthCode(authCode);
        // ③ Alipay barcode payment only has one display
        String displayMode = PayOrderDisplayModeEnum.BAR_CODE.getMode();

        // 1.2 Build AlipayTradePayRequest Request
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        request.setReturnUrl(reqDTO.getReturnUrl());

        // 2.1 Execute request
        AlipayTradePayResponse response;
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
        if ("10000".equals(response.getCode())) { // Password-free payment
            LocalDateTime successTime = LocalDateTimeUtil.of(response.getGmtPayment());
            return PayOrderRespDTO.successOf(response.getTradeNo(), response.getBuyerUserId(), successTime,
                            response.getOutTradeNo(), response)
                    .setDisplayMode(displayMode).setDisplayContent("");
        }
        // Large amount payment，Requires user to enter password，So return waiting。At this time，The front end usually performs polling
        return PayOrderRespDTO.waitingOf(displayMode, "",
                reqDTO.getOutTradeNo(), response);
    }
}
