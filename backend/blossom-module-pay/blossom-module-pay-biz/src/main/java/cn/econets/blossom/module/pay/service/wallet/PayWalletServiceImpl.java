package cn.econets.blossom.module.pay.service.wallet;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import cn.econets.blossom.module.pay.dal.mysql.wallet.PayWalletMapper;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import cn.econets.blossom.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum.PAYMENT;
import static cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum.PAYMENT_REFUND;

/**
 * Wallet Service Implementation class
 *
 *
 */
@Service
@Slf4j
public class PayWalletServiceImpl implements  PayWalletService {

    @Resource
    private PayWalletMapper walletMapper;
    @Resource
    private PayWalletTransactionService walletTransactionService;
    @Resource
    @Lazy
    private PayOrderService orderService;
    @Resource
    @Lazy
    private PayRefundService refundService;

    @Override
    public PayWalletDO getOrCreateWallet(Long userId, Integer userType) {
        PayWalletDO wallet = walletMapper.selectByUserIdAndType(userId, userType);
        if (wallet == null) {
            wallet = new PayWalletDO().setUserId(userId).setUserType(userType)
                    .setBalance(0).setTotalExpense(0).setTotalRecharge(0);
            wallet.setCreateTime(LocalDateTime.now());
            walletMapper.insert(wallet);
        }
        return wallet;
    }

    @Override
    public PayWalletDO getWallet(Long walletId) {
        return walletMapper.selectById(walletId);
    }

    @Override
    public PageResult<PayWalletDO> getWalletPage(Integer userType,PayWalletPageReqVO pageReqVO) {
        return walletMapper.selectPage(userType, pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayWalletTransactionDO orderPay(Long userId, Integer userType, String outTradeNo, Integer price) {
        // 1. Judge whether the payment transaction extension order exists
        PayOrderExtensionDO orderExtension = orderService.getOrderExtensionByNo(outTradeNo);
        if (orderExtension == null) {
            throw exception(PAY_ORDER_EXTENSION_NOT_FOUND);
        }
        PayWalletDO wallet = getOrCreateWallet(userId, userType);
        // 2. Deduct balance
        return reduceWalletBalance(wallet.getId(), orderExtension.getOrderId(), PAYMENT, price);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayWalletTransactionDO orderRefund(String outRefundNo, Integer refundPrice, String reason) {
        // 1.1 Judge whether a refund order exists
        PayRefundDO payRefund = refundService.getRefundByNo(outRefundNo);
        if (payRefund == null) {
            throw exception(REFUND_NOT_FOUND);
        }
        // 1.2 Check whether a refund is available
        Long walletId = validateWalletCanRefund(payRefund.getId(), payRefund.getChannelOrderNo());
        PayWalletDO wallet = walletMapper.selectById(walletId);
        Assert.notNull(wallet, "Wallet {} Does not exist", walletId);

        // 2. Increase balance
        return addWalletBalance(walletId, String.valueOf(payRefund.getId()), PAYMENT_REFUND, refundPrice);
    }

    /**
     * Check whether a refund is possible
     *
     * @param refundId Payment Refund Order id
     * @param walletPayNo Wallet Payment no
     */
    private Long validateWalletCanRefund(Long refundId, String walletPayNo) {
        // 1. Verify wallet payment transaction exists
        PayWalletTransactionDO walletTransaction = walletTransactionService.getWalletTransactionByNo(walletPayNo);
        if (walletTransaction == null) {
            throw exception(WALLET_TRANSACTION_NOT_FOUND);
        }
        // 2. Check if refund exists
        PayWalletTransactionDO refundTransaction = walletTransactionService.getWalletTransaction(
                String.valueOf(refundId), PAYMENT_REFUND);
        if (refundTransaction != null) {
            throw exception(WALLET_REFUND_EXIST);
        }
        return walletTransaction.getWalletId();
    }

    @Override
    public PayWalletTransactionDO reduceWalletBalance(Long walletId, Long bizId,
                                                      PayWalletBizTypeEnum bizType, Integer price) {
        // 1. Get wallet
        PayWalletDO payWallet = getWallet(walletId);
        if (payWallet == null) {
            log.error("[reduceWalletBalance]，User wallet({})Does not exist.", walletId);
            throw exception(WALLET_NOT_FOUND);
        }

        // 2.1 Deduct balance
        int updateCounts;
        switch (bizType) {
            case PAYMENT: {
                updateCounts = walletMapper.updateWhenConsumption(payWallet.getId(), price);
                break;
            }
            case RECHARGE_REFUND: {
                updateCounts = walletMapper.updateWhenRechargeRefund(payWallet.getId(), price);
                break;
            }
            default: {
                // TODO Other types to be implemented
                throw new UnsupportedOperationException("To be implemented");
            }
        }
        if (updateCounts == 0) {
            throw exception(WALLET_BALANCE_NOT_ENOUGH);
        }
        // 2.2 Generate wallet transaction
        Integer afterBalance = payWallet.getBalance() - price;
        WalletTransactionCreateReqBO bo = new WalletTransactionCreateReqBO().setWalletId(payWallet.getId())
                .setPrice(-price).setBalance(afterBalance).setBizId(String.valueOf(bizId))
                .setBizType(bizType.getType()).setTitle(bizType.getDescription());
        return walletTransactionService.createWalletTransaction(bo);
    }

    @Override
    public PayWalletTransactionDO addWalletBalance(Long walletId, String bizId,
                                                   PayWalletBizTypeEnum bizType, Integer price) {
        // 1.1 Get wallet
        PayWalletDO payWallet = getWallet(walletId);
        if (payWallet == null) {
            log.error("[addWalletBalance]，User wallet({})Does not exist.", walletId);
            throw exception(WALLET_NOT_FOUND);
        }
        // 1.2 Update wallet balance
        switch (bizType) {
            case PAYMENT_REFUND: { // Refund Update
                walletMapper.updateWhenConsumptionRefund(payWallet.getId(), price);
                break;
            }
            case RECHARGE: { // Recharge Update
                walletMapper.updateWhenRecharge(payWallet.getId(), price);
                break;
            }
            default: {
                // TODO Other types to be implemented
                throw new UnsupportedOperationException("To be implemented");
            }
        }

        // 2. Generate wallet transaction
        WalletTransactionCreateReqBO transactionCreateReqBO = new WalletTransactionCreateReqBO()
                .setWalletId(payWallet.getId()).setPrice(price).setBalance(payWallet.getBalance() + price)
                .setBizId(bizId).setBizType(bizType.getType()).setTitle(bizType.getDescription());
        return walletTransactionService.createWalletTransaction(transactionCreateReqBO);
    }

    @Override
    public void freezePrice(Long id, Integer price) {
        int updateCounts = walletMapper.freezePrice(id, price);
        if (updateCounts == 0) {
            throw exception(WALLET_BALANCE_NOT_ENOUGH);
        }
    }

    @Override
    public void unfreezePrice(Long id, Integer price) {
        int updateCounts = walletMapper.unFreezePrice(id, price);
        if (updateCounts == 0) {
            throw exception(WALLET_FREEZE_PRICE_NOT_ENOUGH);
        }
    }

}
