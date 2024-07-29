package cn.econets.blossom.module.pay.service.wallet;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;

/**
 * Wallet Service Interface
 *
 *
 */
public interface PayWalletService {

    /**
     * Get wallet information
     * <p>
     * If it does not exist，Create a wallet。Since the user will not create a wallet when registering
     *
     * @param userId   User Number
     * @param userType User Type
     */
    PayWalletDO getOrCreateWallet(Long userId, Integer userType);

    /**
     * Get wallet information
     *
     * @param walletId Wallet id
     */
    PayWalletDO getWallet(Long walletId);

    /**
     * Get member wallet page
     *
     * @param pageReqVO Paged query
     * @return Member wallet paging
     */
    PageResult<PayWalletDO> getWalletPage(Integer userType, PayWalletPageReqVO pageReqVO);

    /**
     * Wallet order payment
     *
     * @param userId     User id
     * @param userType   User Type
     * @param outTradeNo External order number
     * @param price      Amount
     */
    PayWalletTransactionDO orderPay(Long userId, Integer userType, String outTradeNo, Integer price);

    /**
     * Wallet order payment refund
     *
     * @param outRefundNo External refund number
     * @param refundPrice Refund amount
     * @param reason      Refund reason
     */
    PayWalletTransactionDO orderRefund(String outRefundNo, Integer refundPrice, String reason);

    /**
     * Deduct wallet balance
     *
     * @param walletId Wallet id
     * @param bizId    Business Relationship id
     * @param bizType  Business related categories
     * @param price    Deduction amount
     * @return Wallet Flow
     */
    PayWalletTransactionDO reduceWalletBalance(Long walletId, Long bizId,
                                               PayWalletBizTypeEnum bizType, Integer price);

    /**
     * Increase wallet balance
     *
     * @param walletId Wallet id
     * @param bizId    Business Relationship id
     * @param bizType  Business related categories
     * @param price    Increase amount
     * @return Wallet Flow
     */
    PayWalletTransactionDO addWalletBalance(Long walletId, String bizId,
                                            PayWalletBizTypeEnum bizType, Integer price);

    /**
     * Freeze part of the wallet balance
     *
     * @param id    Wallet Number
     * @param price Frozen amount
     */
    void freezePrice(Long id, Integer price);

    /**
     * Unfreeze wallet balance
     *
     * @param id    Wallet Number
     * @param price Unfrozen amount
     */
    void unfreezePrice(Long id, Integer price);

}
