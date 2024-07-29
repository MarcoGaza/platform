package cn.econets.blossom.framework.pay.core.client.impl.alipay;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.impl.AbstractPayClient;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderStatusRespEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConfig;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.*;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static cn.econets.blossom.framework.pay.core.client.impl.alipay.AlipayPayClientConfig.MODE_CERTIFICATE;

/**
 * Alipay abstract class，Realize the unified interface of Alipay、and partially implemented（Refund）
 *
 */
@Slf4j
public abstract class AbstractAlipayPayClient extends AbstractPayClient<AlipayPayClientConfig> {

    @Getter // Only for single test scenarios
    protected DefaultAlipayClient client;

    public AbstractAlipayPayClient(Long channelId, String channelCode, AlipayPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    @SneakyThrows
    protected void doInit() {
        AlipayConfig alipayConfig = new AlipayConfig();
        BeanUtil.copyProperties(config, alipayConfig, false);
        this.client = new DefaultAlipayClient(alipayConfig);
    }

    // ============ Payment related ==========

    /**
     * Construct payment closed {@link PayOrderRespDTO} Object
     *
     * @return Payment closed {@link PayOrderRespDTO} Object
     */
    protected PayOrderRespDTO buildClosedPayOrderRespDTO(PayOrderUnifiedReqDTO reqDTO, AlipayResponse response) {
        Assert.isFalse(response.isSuccess());
        return PayOrderRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                reqDTO.getOutTradeNo(), response);
    }

    @Override
    public PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        // 1. Verify callback data
        Map<String, String> bodyObj = HttpUtil.decodeParamMap(body, StandardCharsets.UTF_8);
        AlipaySignature.rsaCheckV1(bodyObj, config.getAlipayPublicKey(),
                StandardCharsets.UTF_8.name(), config.getSignType());

        // 2. Analysis of order status
        // Additional instructions：Alipay will not only callback when payment is successful，When various trigger payment order data changes，Will make callbacks，So here status The analysis will be more complicated
        Integer status = parseStatus(bodyObj.get("trade_status"));
        // Special Logic: Alipay has no refund status，So，If there is a refund amount，We believe the refund was successful
        if (MapUtil.getDouble(bodyObj, "refund_fee", 0D) > 0) {
            status = PayOrderStatusRespEnum.REFUND.getStatus();
        }
        Assert.notNull(status, (Supplier<Throwable>) () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) of trade_status Incorrect", body));
        });
        return PayOrderRespDTO.of(status, bodyObj.get("trade_no"), bodyObj.get("seller_id"), parseTime(params.get("gmt_payment")),
                bodyObj.get("out_trade_no"), body);
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) throws Throwable {
        // 1.1 Build AlipayTradeRefundModel Request
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        // 1.2 Build AlipayTradeQueryRequest Request
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            // Certificate Mode
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) { // Unsuccessful，For example, the order does not exist
            return PayOrderRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                    outTradeNo, response);
        }
        // 2.2 Analysis of order status
        Integer status = parseStatus(response.getTradeStatus());
        Assert.notNull(status, () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) of trade_status Incorrect", response.getBody()));
        });
        return PayOrderRespDTO.of(status, response.getTradeNo(), response.getBuyerUserId(), LocalDateTimeUtil.of(response.getSendPayDate()),
                outTradeNo, response);
    }

    private static Integer parseStatus(String tradeStatus) {
        return Objects.equals("WAIT_BUYER_PAY", tradeStatus) ? PayOrderStatusRespEnum.WAITING.getStatus()
                : ObjectUtils.equalsAny(tradeStatus, "TRADE_FINISHED", "TRADE_SUCCESS") ? PayOrderStatusRespEnum.SUCCESS.getStatus()
                : Objects.equals("TRADE_CLOSED", tradeStatus) ? PayOrderStatusRespEnum.CLOSED.getStatus() : null;
    }

    // ============ Refund related ==========

    /**
     * Alipay unified refund interface alipay.trade.refund
     *
     * @param reqDTO Refund Request request DTO
     * @return Refund Request Response
     */
    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Build AlipayTradeRefundModel Request
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(reqDTO.getOutTradeNo());
        model.setOutRequestNo(reqDTO.getOutRefundNo());
        model.setRefundAmount(formatAmount(reqDTO.getRefundPrice()));
        model.setRefundReason(reqDTO.getReason());
        // 1.2 Build AlipayTradePayRequest Request
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);

        // 2.1 Execute request
        AlipayTradeRefundResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) {  // Certificate Mode
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // When it appears ACQ.SYSTEM_ERROR, Refund may succeed or fail。 Return WAIT Status. Follow-up job Will poll
            if (ObjectUtils.equalsAny(response.getSubCode(), "ACQ.SYSTEM_ERROR", "SYSTEM_ERROR")) {
                return PayRefundRespDTO.waitingOf(null, reqDTO.getOutRefundNo(), response);
            }
            return PayRefundRespDTO.failureOf(response.getSubCode(), response.getSubMsg(), reqDTO.getOutRefundNo(), response);
        }
        // 2.2 Create return result
        // Alipay only needs to call the refund to return success，The refund is considered successful，No callback required。Specific details can be seen parseNotify Method description。
        // In addition，Alipay does not have a refund order number，So no need to set
        return PayRefundRespDTO.successOf(null, LocalDateTimeUtil.of(response.getGmtRefundPay()),
                reqDTO.getOutRefundNo(), response);
    }

    @Override
    public PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) {
        // Additional instructions：When refunding via Alipay，No callback，This is different from WeChat Pay。And，Refund is divided into partial refunds、and full refund。
        // ① Partial Refund：There will be a callback，But it calls back the synchronous callback of the order status，Not a callback for a refund order
        // ② Full refund：Wap Payment has synchronous callback of order status，But PC/Scan the code but no result
        // So，Here during parsing，Even if the order status is synchronized due to refund，We also ignore and do nothing“Refund synchronization”，It is a callback of the order。
        // In fact，Alipay refund as long as it is successfully initiated，The refund can be considered successful，No need to wait for callback。
        throw new UnsupportedOperationException("Alipay no refund callback");
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) throws AlipayApiException {
        // 1.1 Build AlipayTradeFastpayRefundQueryModel Request
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(outTradeNo);
        model.setOutRequestNo(outRefundNo);
        model.setQueryOptions(Collections.singletonList("gmt_refund_pay"));
        // 1.2 Build AlipayTradeFastpayRefundQueryRequest Request
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);

        // 2.1 Execute request
        AlipayTradeFastpayRefundQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) { // Certificate Mode
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // Clearly does not exist，It should be a failure，Can be closed
            if (ObjectUtils.equalsAny(response.getSubCode(), "TRADE_NOT_EXIST", "ACQ.TRADE_NOT_EXIST")) {
                return PayRefundRespDTO.failureOf(outRefundNo, response);
            }
            // May exist“ACQ.SYSTEM_ERROR”System errors, etc.，So return WAIT Continue waiting
            return PayRefundRespDTO.waitingOf(null, outRefundNo, response);
        }
        // 2.2 Create return result
        if (Objects.equals(response.getRefundStatus(), "REFUND_SUCCESS")) {
            return PayRefundRespDTO.successOf(null, LocalDateTimeUtil.of(response.getGmtRefundPay()),
                    outRefundNo, response);
        }
        return PayRefundRespDTO.waitingOf(null, outRefundNo, response);
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) throws AlipayApiException {
        // 1.1 Verify public key type Must use public key certificate mode
        if (!Objects.equals(config.getMode(), MODE_CERTIFICATE)) {
            throw exception0(ERROR_CONFIGURATION.getCode(), "Alipay single transfer must use the public key certificate mode");
        }
        // 1.2 Build AlipayFundTransUniTransferModel
        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        // ① Common parameters
        model.setTransAmount(formatAmount(reqDTO.getPrice())); // Transfer amount
        model.setOrderTitle(reqDTO.getSubject());               // Title of the transfer business，Used to be displayed in the bill of Alipay users。
        model.setOutBizNo(reqDTO.getOutTransferNo());
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");    // Sales product code。Single non-password transfer is fixed at TRANS_ACCOUNT_NO_PWD
        model.setBizScene("DIRECT_TRANSFER");           // Business scenario Single non-password transfer is fixed at DIRECT_TRANSFER
        if (reqDTO.getChannelExtras() != null) {
            model.setBusinessParams(JsonUtils.toJsonString(reqDTO.getChannelExtras()));
        }
        // ② Personalized parameters
        Participant payeeInfo = new Participant();
        PayTransferTypeEnum transferType = PayTransferTypeEnum.typeOf(reqDTO.getType());
        switch (transferType) {
            // TODO ：Does it not need to be passed? transferType Parameters？Because it should be clear that it is Alipay？
            // 。 Should we consider transferring the money to a bank card?。So spread transferType But I don't know how to test the transfer to a bank card??
            case ALIPAY_BALANCE: {
                payeeInfo.setIdentityType("ALIPAY_LOGON_ID");
                payeeInfo.setIdentity(reqDTO.getAlipayLogonId()); // Alipay login number
                payeeInfo.setName(reqDTO.getUserName()); // Alipay account name
                model.setPayeeInfo(payeeInfo);
                break;
            }
            case BANK_CARD: {
                payeeInfo.setIdentityType("BANKCARD_ACCOUNT");
                // TODO To be implemented
                throw exception(NOT_IMPLEMENTED);
            }
            default: {
                throw exception0(BAD_REQUEST.getCode(), "Incorrect transfer type: {}", transferType);
            }
        }
        // 1.3 Build AlipayFundTransUniTransferRequest
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizModel(model);
        // Execute request
        AlipayFundTransUniTransferResponse response = client.certificateExecute(request);
        // Processing results
        if (!response.isSuccess()) {
            // When it appears SYSTEM_ERROR, The transfer may succeed or fail。 Return WAIT Status. Follow-up job Will poll，or the same outBizNo Re-initiate transfer
            // Discovery outBizNo Same The two request parameters are the same. will return "PAYMENT_INFO_INCONSISTENCY", I don't know what the problem is. Return temporarily WAIT. Follow-upjob Will poll
            if (ObjectUtils.equalsAny(response.getSubCode(),"PAYMENT_INFO_INCONSISTENCY", "SYSTEM_ERROR", "ACQ.SYSTEM_ERROR")) {
                return PayTransferRespDTO.waitingOf(null, reqDTO.getOutTransferNo(), response);
            }
            return PayTransferRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                    reqDTO.getOutTransferNo(), response);
        } else {
            if (ObjectUtils.equalsAny(response.getStatus(), "REFUND", "FAIL")) { // Transfer to bank card will appear "REFUND" "FAIL"
                return PayTransferRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                        reqDTO.getOutTransferNo(), response);
            }
            if (Objects.equals(response.getStatus(), "DEALING")) { // Transfer to bank card will appear "DEALING"  Processing
                return PayTransferRespDTO.dealingOf(response.getOrderId(), reqDTO.getOutTransferNo(), response);
            }
            return PayTransferRespDTO.successOf(response.getOrderId(), parseTime(response.getTransDate()),
                    response.getOutBizNo(), response);
        }

    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) throws Throwable {
        // 1.1 Build AlipayFundTransCommonQueryModel
        AlipayFundTransCommonQueryModel model = new AlipayFundTransCommonQueryModel();
        model.setProductCode(type == PayTransferTypeEnum.BANK_CARD ? "TRANS_BANKCARD_NO_PWD" : "TRANS_ACCOUNT_NO_PWD");
        model.setBizScene("DIRECT_TRANSFER"); //Business scenario
        model.setOutBizNo(outTradeNo);
        // 1.2 Build AlipayFundTransCommonQueryRequest
        AlipayFundTransCommonQueryRequest request = new AlipayFundTransCommonQueryRequest();
        request.setBizModel(model);

        // 2.1 Execute request
        AlipayFundTransCommonQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_CERTIFICATE)) { // Certificate Mode
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        // 2.2 Processing return results
        if (response.isSuccess()) {
            if (ObjectUtils.equalsAny(response.getStatus(), "REFUND", "FAIL")) { // Transfer to bank card will appear "REFUND" "FAIL"
                return PayTransferRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                        outTradeNo, response);
            }
            if (Objects.equals(response.getStatus(), "DEALING")) { // Transfer to bank card will appear "DEALING" Processing
                return PayTransferRespDTO.dealingOf(response.getOrderId(), outTradeNo, response);
            }
            return PayTransferRespDTO.successOf(response.getOrderId(), parseTime(response.getPayDate()),
                    response.getOutBizNo(), response);
        } else {
            // When it appears SYSTEM_ERROR, The transfer may succeed or fail。 Return WAIT Status. Follow-up job Will poll, or the same outBizNo Re-initiate transfer
            // When it appears ORDER_NOT_EXIST Maybe the transfer is still being processed,It may also be that the transfer process failed. Return WAIT Status. Follow-up job Will poll, or the same outBizNo Re-initiate transfer
            if (ObjectUtils.equalsAny(response.getSubCode(), "ORDER_NOT_EXIST", "SYSTEM_ERROR", "ACQ.SYSTEM_ERROR")) {
                return PayTransferRespDTO.waitingOf(null, outTradeNo, response);
            }
            return PayTransferRespDTO.closedOf(response.getSubCode(), response.getSubMsg(),
                    outTradeNo, response);
        }
    }

    // ========== Various tools and methods ==========

    protected String formatAmount(Integer amount) {
        return String.valueOf(amount / 100.0);
    }

    protected String formatTime(LocalDateTime time) {
        return LocalDateTimeUtil.format(time, NORM_DATETIME_FORMATTER);
    }

    protected LocalDateTime parseTime(String str) {
        return LocalDateTimeUtil.parse(str, NORM_DATETIME_FORMATTER);
    }

}
