package cn.econets.blossom.framework.pay.core.client.impl.weixin;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.invalidParamException;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;

/**
 * WeChat Pay【Payment code payment】of PayClient Implementation class
 *
 * Document：<a href="https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1">Payment code payment</a>
 *
 */
@Slf4j
public class WxBarPayClient extends AbstractWxPayClient {

    /**
     * WeChat payment code expiration time
     */
    private static final Duration AUTH_CODE_EXPIRE = Duration.ofMinutes(3);

    public WxBarPayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_BAR.getCode(), config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.MICROPAY);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV2(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // Since the payment code needs to be polled continuously，So the payment needs to be completed in a shorter time
        LocalDateTime expireTime = LocalDateTimeUtils.addTime(AUTH_CODE_EXPIRE);
        if (expireTime.isAfter(reqDTO.getExpireTime())) {
            expireTime = reqDTO.getExpireTime();
        }
        // Build WxPayMicropayRequest Object
        WxPayMicropayRequest request = WxPayMicropayRequest.newBuilder()
                .outTradeNo(reqDTO.getOutTradeNo())
                .body(reqDTO.getSubject())
                .detail(reqDTO.getBody())
                .totalFee(reqDTO.getPrice()) // Unit points
                .timeExpire(formatDateV2(expireTime))
                .spbillCreateIp(reqDTO.getUserIp())
                .authCode(getAuthCode(reqDTO))
                .build();
        // Execute request，Retry until failure（Expired），Or success
        WxPayException lastWxPayException = null;
        for (int i = 1; i < Byte.MAX_VALUE; i++) {
            try {
                WxPayMicropayResult response = client.micropay(request);
                // Payment successful，For example：1）The user entered a password；2）Users can pay without password
                return PayOrderRespDTO.successOf(response.getTransactionId(), response.getOpenid(), parseDateV2(response.getTimeEnd()),
                        response.getOutTradeNo(), response)
                        .setDisplayMode(PayOrderDisplayModeEnum.BAR_CODE.getMode());
            } catch (WxPayException ex) {
                lastWxPayException = ex;
                // If you don't meet this 3 Any kind，Throw directly WxPayException Abnormal，Not only need to be processed
                // 1. SYSTEMERROR：Interface returns an error：Please call the scanned order result query immediatelyAPI，Query the current order status，And decide the next step based on the order status。
                // 2. USERPAYING：User paying，Password required：Waiting 5 seconds，Then call the scanned order result query API，Query the different status of the current order，Decide on the next step。
                // 3. BANKERROR：Bank system abnormality：Please call the scanned order result query immediately API，Query the different status of the current order，Decide on the next step。
                if (!StrUtil.equalsAny(ex.getErrCode(), "SYSTEMERROR", "USERPAYING", "BANKERROR")) {
                    throw ex;
                }
                // Waiting 5 seconds，Continue to the next round of re-initiating payment
                log.info("[doUnifiedOrderV2][Initiate WeChat Bar Payment({})Failed，Wait for the next round of retry，Request({})，Response({})]", i,
                        toJsonString(request), ex.getMessage());
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
            }
        }
        throw lastWxPayException;
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV3(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        return doUnifiedOrderV2(reqDTO);
    }

    // ========== Various tools and methods ==========

    static String getAuthCode(PayOrderUnifiedReqDTO reqDTO) {
        String authCode = MapUtil.getStr(reqDTO.getChannelExtras(), "authCode");
        if (StrUtil.isEmpty(authCode)) {
            throw invalidParamException("Payment Request authCode Cannot be empty！");
        }
        return authCode;
    }

}
