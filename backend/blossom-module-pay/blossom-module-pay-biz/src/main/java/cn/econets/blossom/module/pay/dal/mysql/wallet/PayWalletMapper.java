package cn.econets.blossom.module.pay.dal.mysql.wallet;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet.PayWalletPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayWalletMapper extends BaseMapperX<PayWalletDO> {

    default PayWalletDO selectByUserIdAndType(Long userId, Integer userType) {
        return selectOne(PayWalletDO::getUserId, userId,
                PayWalletDO::getUserType, userType);
    }

    default PageResult<PayWalletDO> selectPage(Integer userType, PayWalletPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PayWalletDO>()
                .inIfPresent(PayWalletDO::getUserId, reqVO.getUserIds())
                .eqIfPresent(PayWalletDO::getUserType, userType)
                .betweenIfPresent(PayWalletDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PayWalletDO::getId));
    }

    /**
     * When a consumer refunds， Update wallet
     *
     * @param id Wallet id
     * @param price Consumption amount
     */
    default int updateWhenConsumptionRefund(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" balance = balance + " + price
                        + ", total_expense = total_expense - " + price)
                .eq(PayWalletDO::getId, id);
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * When consuming， Update wallet
     *
     * @param price Consumption amount
     * @param id Wallet id
     */
    default int updateWhenConsumption(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" balance = balance - " + price
                        + ", total_expense = total_expense + " + price)
                .eq(PayWalletDO::getId, id)
                .ge(PayWalletDO::getBalance, price); // cas Logic
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * When recharging，Update wallet
     *
     * @param id Wallet id
     * @param price Wallet amount
     */
    default int updateWhenRecharge(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" balance = balance + " + price
                        + ", total_recharge = total_recharge + " + price)
                .eq(PayWalletDO::getId, id);
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * Freeze part of the wallet balance
     *
     * @param id Wallet id
     * @param price Frozen amount
     */
    default int freezePrice(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" balance = balance - " + price
                        + ", freeze_price = freeze_price + " + price)
                .eq(PayWalletDO::getId, id)
                .ge(PayWalletDO::getBalance, price); // cas Logic
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * Unfreeze wallet balance
     *
     * @param id Wallet id
     * @param price Unfrozen amount
     */
    default int unFreezePrice(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" balance = balance + " + price
                        + ", freeze_price = freeze_price - " + price)
                .eq(PayWalletDO::getId, id)
                .ge(PayWalletDO::getFreezePrice, price); // cas Logic
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * When recharge is refunded, Update wallet
     *
     * @param id Wallet id
     * @param price Refund amount
     */
    default  int updateWhenRechargeRefund(Long id, Integer price){
        LambdaUpdateWrapper<PayWalletDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<PayWalletDO>()
                .setSql(" freeze_price = freeze_price - " + price
                        + ", total_recharge = total_recharge - " + price)
                .eq(PayWalletDO::getId, id)
                .ge(PayWalletDO::getFreezePrice, price)
                .ge(PayWalletDO::getTotalRecharge, price);// cas Logic
        return update(null, lambdaUpdateWrapper);
    }


}




