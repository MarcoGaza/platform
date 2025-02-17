package cn.econets.blossom.module.pay.service.wallet;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackagePageReqVO;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageUpdateReqVO;
import cn.econets.blossom.module.pay.convert.wallet.PayWalletRechargePackageConvert;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import cn.econets.blossom.module.pay.dal.mysql.wallet.PayWalletRechargePackageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Wallet Recharge Package Service Implementation class
 *
 *
 */
@Service
public class PayWalletRechargePackageServiceImpl implements PayWalletRechargePackageService {

    @Resource
    private PayWalletRechargePackageMapper walletRechargePackageMapper;

    @Override
    public PayWalletRechargePackageDO getWalletRechargePackage(Long packageId) {
        return walletRechargePackageMapper.selectById(packageId);
    }

    @Override
    public PayWalletRechargePackageDO validWalletRechargePackage(Long packageId) {
        PayWalletRechargePackageDO rechargePackageDO = walletRechargePackageMapper.selectById(packageId);
        if (rechargePackageDO == null) {
            throw exception(WALLET_RECHARGE_PACKAGE_NOT_FOUND);
        }
        if (CommonStatusEnum.DISABLE.getStatus().equals(rechargePackageDO.getStatus())) {
            throw exception(WALLET_RECHARGE_PACKAGE_IS_DISABLE);
        }
        return rechargePackageDO;
    }

    @Override
    public Long createWalletRechargePackage(WalletRechargePackageCreateReqVO createReqVO) {
        // Check if the package name is unique
        validateRechargePackageNameUnique(null, createReqVO.getName());

        // Insert
        PayWalletRechargePackageDO walletRechargePackage = PayWalletRechargePackageConvert.INSTANCE.convert(createReqVO);
        walletRechargePackageMapper.insert(walletRechargePackage);
        // Return
        return walletRechargePackage.getId();
    }

    @Override
    public void updateWalletRechargePackage(WalletRechargePackageUpdateReqVO updateReqVO) {
        // Check existence
        validateWalletRechargePackageExists(updateReqVO.getId());
        // Check if the package name is unique
        validateRechargePackageNameUnique(updateReqVO.getId(), updateReqVO.getName());

        // Update
        PayWalletRechargePackageDO updateObj = PayWalletRechargePackageConvert.INSTANCE.convert(updateReqVO);
        walletRechargePackageMapper.updateById(updateObj);
    }

    private void validateRechargePackageNameUnique(Long id, String name) {
        if (StrUtil.isBlank(name)) {
            return;
        }
        PayWalletRechargePackageDO rechargePackage = walletRechargePackageMapper.selectByName(name);
        if (rechargePackage == null) {
            return ;
        }
        if (id == null) {
            throw exception(WALLET_RECHARGE_PACKAGE_NAME_EXISTS);
        }
        if (!id.equals(rechargePackage.getId())) {
            throw exception(WALLET_RECHARGE_PACKAGE_NAME_EXISTS);
        }
    }

    @Override
    public void deleteWalletRechargePackage(Long id) {
        // Check existence
        validateWalletRechargePackageExists(id);
        // Delete
        walletRechargePackageMapper.deleteById(id);
    }

    private void validateWalletRechargePackageExists(Long id) {
        if (walletRechargePackageMapper.selectById(id) == null) {
            throw exception(WALLET_RECHARGE_PACKAGE_NOT_FOUND);
        }
    }

    @Override
    public PageResult<PayWalletRechargePackageDO> getWalletRechargePackagePage(WalletRechargePackagePageReqVO pageReqVO) {
        return walletRechargePackageMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayWalletRechargePackageDO> getWalletRechargePackageList(Integer status) {
        return walletRechargePackageMapper.selectListByStatus(status);
    }

}
