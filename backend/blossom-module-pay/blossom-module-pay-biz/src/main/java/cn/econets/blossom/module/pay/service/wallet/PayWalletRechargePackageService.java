package cn.econets.blossom.module.pay.service.wallet;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackagePageReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageUpdateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Wallet Recharge Package Service Interface
 *
 *
 */
public interface PayWalletRechargePackageService {

    /**
     * Get wallet recharge package
     * @param packageId Recharge package number
     */
    PayWalletRechargePackageDO getWalletRechargePackage(Long packageId);

    /**
     * Check the validity of the wallet recharge package, Throw if invalid ServiceException Abnormal
     *
     * @param packageId Recharge package number
     */
    PayWalletRechargePackageDO validWalletRechargePackage(Long packageId);

    /**
     * Create a recharge package
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createWalletRechargePackage(@Valid WalletRechargePackageCreateReqVO createReqVO);

    /**
     * Update recharge package
     *
     * @param updateReqVO Update information
     */
    void updateWalletRechargePackage(@Valid WalletRechargePackageUpdateReqVO updateReqVO);

    /**
     * Delete recharge package
     *
     * @param id Number
     */
    void deleteWalletRechargePackage(Long id);

    /**
     * Get the recharge package page
     *
     * @param pageReqVO Paged query
     * @return Recharge Package Page
     */
    PageResult<PayWalletRechargePackageDO> getWalletRechargePackagePage(WalletRechargePackagePageReqVO pageReqVO);

    /**
     * Get the recharge package list
     *
     * @param status Status
     * @return Recharge package list
     */
    List<PayWalletRechargePackageDO> getWalletRechargePackageList(Integer status);

}
