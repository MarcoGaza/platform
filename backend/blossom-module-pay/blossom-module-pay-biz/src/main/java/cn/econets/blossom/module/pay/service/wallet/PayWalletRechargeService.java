package cn.econets.blossom.module.pay.service.wallet;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeCreateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargeDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;

/**
 * Wallet Recharge Service Interface
 *
 *
 */
public interface PayWalletRechargeService {

    /**
     * Create wallet recharge record（Initiate recharge）
     *
     * @param userId      User id
     * @param userType    User Type
     * @param createReqVO Wallet recharge request VO
     * @param userIp  UserIp
     * @return Wallet recharge record
     */
    PayWalletRechargeDO createWalletRecharge(Long userId, Integer userType, String userIp,
                                             AppPayWalletRechargeCreateReqVO createReqVO);

    /**
     * Get wallet recharge record page
     *
     * @param userId User Number
     * @param userType User Type
     * @param pageReqVO Pagination request
     * @param payStatus Do you want to pay?
     * @return Wallet recharge record paging
     */
    PageResult<PayWalletRechargeDO> getWalletRechargePackagePage(Long userId, Integer userType,
                                                                 PageParam pageReqVO, Boolean payStatus);

    /**
     * Update wallet recharge successfully
     *
     * @param id         Wallet recharge record id
     * @param payOrderId Payment order id
     */
    void updateWalletRechargerPaid(Long id, Long payOrderId);

    /**
     * Initiate wallet recharge refund
     *
     * @param id     Wallet recharge number
     * @param userIp User ip Address
     */
    void refundWalletRecharge(Long id, String userIp);

    /**
     * Update wallet recharge record to refunded
     *
     * @param id          Wallet Recharge id
     * @param payRefundId Refund slipid
     */
    void updateWalletRechargeRefunded(Long id, Long payRefundId);

}
