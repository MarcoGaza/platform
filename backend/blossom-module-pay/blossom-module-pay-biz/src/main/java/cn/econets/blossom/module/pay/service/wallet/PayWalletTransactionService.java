package cn.econets.blossom.module.pay.service.wallet;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.transaction.PayWalletTransactionPageReqVO;
import cn.econets.blossom.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionPageReqVO;
import cn.econets.blossom.module.pay.controller.app.wallet.vo.transaction.AppPayWalletTransactionSummaryRespVO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletTransactionDO;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.econets.blossom.module.pay.service.wallet.bo.WalletTransactionCreateReqBO;

import javax.validation.Valid;

import java.time.LocalDateTime;

/**
 * Wallet balance flow Service Interface
 *
 *
 */
public interface PayWalletTransactionService {

    /**
     * Query wallet balance and transaction paging
     *
     * @param userId   User Number
     * @param userType User Type
     * @param pageVO   Pagination query parameters
     */
    PageResult<PayWalletTransactionDO> getWalletTransactionPage(Long userId, Integer userType,
                                                                AppPayWalletTransactionPageReqVO pageVO);

    /**
     * Query wallet balance and transaction paging
     *
     * @param pageVO   Pagination query parameters
     */
    PageResult<PayWalletTransactionDO> getWalletTransactionPage(PayWalletTransactionPageReqVO pageVO);

    /**
     * Add wallet balance flow
     *
     * @param bo Create wallet transaction bo
     * @return New wallet do
     */
    PayWalletTransactionDO createWalletTransaction(@Valid WalletTransactionCreateReqBO bo);

    /**
     * According to no，Get the remaining balance of the wallet
     *
     * @param no Serial number
     */
    PayWalletTransactionDO getWalletTransactionByNo(String no);

    /**
     * Get wallet transaction
     *
     * @param bizId Business Number
     * @param type  Business Type
     * @return Wallet Flow
     */
    PayWalletTransactionDO getWalletTransaction(String bizId, PayWalletBizTypeEnum type);

    /**
     * Get wallet transaction statistics
     *
     * @param userId User Number
     * @param userType User Type
     * @param createTime Time period
     * @return Wallet Transaction Statistics
     */
    AppPayWalletTransactionSummaryRespVO getWalletTransactionSummary(Long userId, Integer userType,
                                                                     LocalDateTime[] createTime);

}
