package cn.econets.blossom.framework.pay.core.client.impl;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.exception.PayException;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;

/**
 * Abstract class of payment client，Provide template method，Reduce redundant code in subclasses
 *
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {

    /**
     * Channel Number
     */
    private final Long channelId;
    /**
     * Channel Code
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final String channelCode;
    /**
     * Payment Configuration
     */
    protected Config config;

    public AbstractPayClient(Long channelId, String channelCode, Config config) {
        this.channelId = channelId;
        this.channelCode = channelCode;
        this.config = config;
    }

    /**
     * Initialization
     */
    public final void init() {
        doInit();
        log.debug("[init][Client({}) Initialization completed]", getId());
    }

    /**
     * Custom initialization
     */
    protected abstract void doInit();

    public final void refresh(Config config) {
        // Judge whether to update
        if (config.equals(this.config)) {
            return;
        }
        log.info("[refresh][Client({})Changes have occurred，Reinitialize]", getId());
        this.config = config;
        // Initialization
        this.init();
    }

    @Override
    public Long getId() {
        return channelId;
    }

    // ============ Payment related ==========

    @Override
    public final PayOrderRespDTO unifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        ValidationUtils.validate(reqDTO);
        // Execute unified order
        PayOrderRespDTO resp;
        try {
            resp = doUnifiedOrder(reqDTO);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            // System abnormality，Then package it into PayException Exception thrown
            log.error("[unifiedOrder][Client({}) request({}) Abnormal payment initiated]",
                    getId(), toJsonString(reqDTO), ex);
            throw buildPayException(ex);
        }
        return resp;
    }

    protected abstract PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO)
            throws Throwable;

    @Override
    public final PayOrderRespDTO parseOrderNotify(Map<String, String> params, String body) {
        try {
            return doParseOrderNotify(params, body);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            log.error("[parseOrderNotify][Client({}) params({}) body({}) Parsing failed]",
                    getId(), params, body, ex);
            throw buildPayException(ex);
        }
    }

    protected abstract PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body)
            throws Throwable;

    @Override
    public final PayOrderRespDTO getOrder(String outTradeNo) {
        try {
            return doGetOrder(outTradeNo);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            log.error("[getOrder][Client({}) outTradeNo({}) Check payment order exception]",
                    getId(), outTradeNo, ex);
            throw buildPayException(ex);
        }
    }

    protected abstract PayOrderRespDTO doGetOrder(String outTradeNo)
            throws Throwable;

    // ============ Refund related ==========

    @Override
    public final PayRefundRespDTO unifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        ValidationUtils.validate(reqDTO);
        // Implement unified refund
        PayRefundRespDTO resp;
        try {
            resp = doUnifiedRefund(reqDTO);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            // System abnormality，Then package it into PayException Exception thrown
            log.error("[unifiedRefund][Client({}) request({}) Abnormal refund initiated]",
                    getId(), toJsonString(reqDTO), ex);
            throw buildPayException(ex);
        }
        return resp;
    }

    protected abstract PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) throws Throwable;

    @Override
    public final PayRefundRespDTO parseRefundNotify(Map<String, String> params, String body) {
        try {
            return doParseRefundNotify(params, body);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            log.error("[parseRefundNotify][Client({}) params({}) body({}) Parsing failed]",
                    getId(), params, body, ex);
            throw buildPayException(ex);
        }
    }

    protected abstract PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body)
            throws Throwable;

    @Override
    public final PayRefundRespDTO getRefund(String outTradeNo, String outRefundNo) {
        try {
            return doGetRefund(outTradeNo, outRefundNo);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            log.error("[getRefund][Client({}) outTradeNo({}) outRefundNo({}) Query refund order exception]",
                    getId(), outTradeNo, outRefundNo, ex);
            throw buildPayException(ex);
        }
    }

    protected abstract PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo)
            throws Throwable;

    @Override
    public final PayTransferRespDTO unifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        validatePayTransferReqDTO(reqDTO);
        PayTransferRespDTO resp;
        try {
            resp = doUnifiedTransfer(reqDTO);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            // System abnormality，Then package it into PayException Exception thrown
            log.error("[unifiedTransfer][Client({}) request({}) Abnormal transfer initiated]",
                    getId(), toJsonString(reqDTO), ex);
            throw buildPayException(ex);
        }
        return resp;
    }
    private void validatePayTransferReqDTO(PayTransferUnifiedReqDTO reqDTO) {
        PayTransferTypeEnum transferType = PayTransferTypeEnum.typeOf(reqDTO.getType());
        switch (transferType) {
            case ALIPAY_BALANCE: {
                ValidationUtils.validate(reqDTO,  PayTransferTypeEnum.Alipay.class);
                break;
            }
            case WX_BALANCE: {
                ValidationUtils.validate(reqDTO, PayTransferTypeEnum.WxPay.class);
                break;
            }
            default: {
                throw exception(NOT_IMPLEMENTED);
            }
        }
    }

    @Override
    public final PayTransferRespDTO getTransfer(String outTradeNo, PayTransferTypeEnum type) {
        try {
            return doGetTransfer(outTradeNo, type);
        } catch (ServiceException ex) { // Business exception，All implementation classes have been translated，So just throw it out directly
            throw ex;
        } catch (Throwable ex) {
            log.error("[getTransfer][Client({}) outTradeNo({}) type({}) Query transfer order exception]",
                    getId(), outTradeNo, type, ex);
            throw buildPayException(ex);
        }
    }

    protected abstract PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO)
            throws Throwable;

    protected abstract PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type)
            throws Throwable;

    // ========== Various tools and methods ==========

    private PayException buildPayException(Throwable ex) {
        if (ex instanceof PayException) {
            return (PayException) ex;
        }
        throw new PayException(ex);
    }

}
