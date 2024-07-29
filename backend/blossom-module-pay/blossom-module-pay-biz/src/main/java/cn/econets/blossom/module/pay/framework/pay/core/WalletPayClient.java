package cn.econets.blossom.module.pay.framework.pay.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.impl.AbstractPayClient;
import cn.econets.blossom.framework.pay.core.client.impl.NonePayClientConfig;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.pay.core.enums.refund.PayRefundStatusRespEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import cn.econets.blossom.module.pay.service.wallet.PayWalletService;
import cn.econets.blossom.module.pay.service.wallet.PayWalletTransactionService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.PAY_ORDER_EXTENSION_NOT_FOUND;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.REFUND_NOT_FOUND;

/**
 * Payed by wallet PayClient Implementation class
 *
 *
 */
@Slf4j
public class WalletPayClient extends AbstractPayClient<NonePayClientConfig> {

    public static final String USER_ID_KEY = "user_id";
    public static final String USER_TYPE_KEY = "user_type";

    private PayWalletService wallService;
    private PayWalletTransactionService walletTransactionService;
    private PayOrderService orderService;
    private PayRefundService refundService;

    public WalletPayClient(Long channelId,  NonePayClientConfig config) {
        super(channelId, PayChannelEnum.WALLET.getCode(), config);
    }

    @Override
    protected void doInit() {
        if (wallService == null) {
            wallService = SpringUtil.getBean(PayWalletService.class);
        }
        if (walletTransactionService == null) {
            walletTransactionService = SpringUtil.getBean(PayWalletTransactionService.class);
        }
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        try {
            Long userId = MapUtil.getLong(reqDTO.getChannelExtras(), USER_ID_KEY);
            Integer userType = MapUtil.getInt(reqDTO.getChannelExtras(), USER_TYPE_KEY);
            Assert.notNull(userId, "User id Cannot be empty");
            Assert.notNull(userType, "User type cannot be empty");
            PayWalletTransactionDO transaction = wallService.orderPay(userId, userType, reqDTO.getOutTradeNo(),
                    reqDTO.getPrice());
            return PayOrderRespDTO.successOf(transaction.getNo(), transaction.getCreator(),
                    transaction.getCreateTime(),
                    reqDTO.getOutTradeNo(), transaction);
        } catch (Throwable ex) {
            log.error("[doUnifiedOrder] Failed", ex);
            Integer errorCode = INTERNAL_SERVER_ERROR.getCode();
            String errorMsg = INTERNAL_SERVER_ERROR.getMsg();
            if (ex instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) ex;
                errorCode = serviceException.getCode();
                errorMsg = serviceException.getMessage();
            }
            return PayOrderRespDTO.closedOf(String.valueOf(errorCode), errorMsg,
                    reqDTO.getOutTradeNo(), "");
        }
    }

    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("No payment callback for wallet payment");
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) {
        if (orderService == null) {
            orderService = SpringUtil.getBean(PayOrderService.class);
        }
        PayOrderExtensionDO orderExtension = orderService.getOrderExtensionByNo(outTradeNo);
        // The payment transaction extension form does not exist， Return to closed state
        if (orderExtension == null) {
            return PayOrderRespDTO.closedOf(String.valueOf(PAY_ORDER_EXTENSION_NOT_FOUND.getCode()),
                    PAY_ORDER_EXTENSION_NOT_FOUND.getMsg(), outTradeNo, "");
        }
        // Closed state
        if (PayOrderStatusEnum.isClosed(orderExtension.getStatus())) {
            return PayOrderRespDTO.closedOf(orderExtension.getChannelErrorCode(),
                    orderExtension.getChannelErrorMsg(), outTradeNo, "");
        }
        // Successful status
        if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) {
            PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransaction(
                    String.valueOf(orderExtension.getOrderId()), PayWalletBizTypeEnum.PAYMENT);
            Assert.notNull(walletTransaction, "Payment slip {} Wallet flow cannot be empty", outTradeNo);
            return PayOrderRespDTO.successOf(walletTransaction.getNo(), walletTransaction.getCreator(),
                    walletTransaction.getCreateTime(), outTradeNo, walletTransaction);
        }
        // Other states are invalid
        log.error("[doGetOrder] Payment slip {} The status is incorrect", outTradeNo);
        throw new IllegalStateException(String.format("Payment slip[%s] Incorrect status", outTradeNo));
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        try {
            PayWalletTransactionDO payWalletTransaction = wallService.orderRefund(reqDTO.getOutRefundNo(),
                    reqDTO.getRefundPrice(), reqDTO.getReason());
            return PayRefundRespDTO.successOf(payWalletTransaction.getNo(), payWalletTransaction.getCreateTime(),
                    reqDTO.getOutRefundNo(), payWalletTransaction);
        } catch (Throwable ex) {
            log.error("[doUnifiedRefund] Failed", ex);
            Integer errorCode = INTERNAL_SERVER_ERROR.getCode();
            String errorMsg = INTERNAL_SERVER_ERROR.getMsg();
            if (ex instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) ex;
                errorCode =  serviceException.getCode();
                errorMsg = serviceException.getMessage();
            }
            return PayRefundRespDTO.failureOf(String.valueOf(errorCode), errorMsg,
                    reqDTO.getOutRefundNo(), "");
        }
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) {
        throw new UnsupportedOperationException("No refund callback for wallet payment");
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) {
        if (refundService == null) {
            refundService = SpringUtil.getBean(PayRefundService.class);
        }
        PayRefundDO payRefund = refundService.getRefundByNo(outRefundNo);
        // The payment refund order does not exist， Return refund failure status
        if (payRefund == null) {
            return PayRefundRespDTO.failureOf(String.valueOf(REFUND_NOT_FOUND), REFUND_NOT_FOUND.getMsg(),
                    outRefundNo, "");
        }
        // Refund failed
        if (PayRefundStatusRespEnum.isFailure(payRefund.getStatus())) {
            return PayRefundRespDTO.failureOf(payRefund.getChannelErrorCode(), payRefund.getChannelErrorMsg(),
                    outRefundNo, "");
        }
        // Refund successful
        if (PayRefundStatusRespEnum.isSuccess(payRefund.getStatus())) {
            PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransaction(
                    String.valueOf(payRefund.getId()), PayWalletBizTypeEnum.PAYMENT_REFUND);
            Assert.notNull(walletTransaction, "Payment Refund Order {} Wallet flow cannot be empty", outRefundNo);
            return PayRefundRespDTO.successOf(walletTransaction.getNo(), walletTransaction.getCreateTime(),
                    outRefundNo, walletTransaction);
        }
        // Other states are invalid
        log.error("[doGetRefund] Payment Refund Order {} The status is incorrect", outRefundNo);
        throw new IllegalStateException(String.format("Payment Refund Order[%s] Incorrect status", outRefundNo));
    }

    @Override
    public PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) {
        throw new UnsupportedOperationException("To be implemented");
    }

}
