package cn.econets.blossom.module.pay.service.wallet;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.pay.core.enums.refund.PayRefundStatusRespEnum;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeCreateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargeDO;
import cn.econets.blossom.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import cn.econets.blossom.module.pay.dal.mysql.wallet.PayWalletRechargeMapper;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import cn.econets.blossom.module.pay.service.refund.PayRefundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import static cn.hutool.core.util.ObjectUtil.notEqual;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.addTime;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.module.pay.convert.wallet.PayWalletRechargeConvert.INSTANCE;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum.*;

/**
 * Wallet Recharge Service Implementation class
 *
 *
 */
@Service
@Slf4j
public class PayWalletRechargeServiceImpl implements PayWalletRechargeService {

    /**
     * TODO Put it in payconfig
     */
    private static final Long WALLET_PAY_APP_ID = 8L;

    private static final String WALLET_RECHARGE_ORDER_SUBJECT = "Wallet balance recharge";

    @Resource
    private PayWalletRechargeMapper walletRechargeMapper;
    @Resource
    private PayWalletService payWalletService;
    @Resource
    private PayOrderService payOrderService;
    @Resource
    private PayRefundService payRefundService;
    @Resource
    private PayWalletRechargePackageService payWalletRechargePackageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayWalletRechargeDO createWalletRecharge(Long userId, Integer userType, String userIp,
                                                    AppPayWalletRechargeCreateReqVO reqVO) {
        // 1.1 Calculate recharge amount
        int payPrice;
        int bonusPrice = 0;
        if (Objects.nonNull(reqVO.getPackageId())) {
            PayWalletRechargePackageDO rechargePackage = payWalletRechargePackageService.validWalletRechargePackage(reqVO.getPackageId());
            payPrice = rechargePackage.getPayPrice();
            bonusPrice = rechargePackage.getBonusPrice();
        } else {
            payPrice = reqVO.getPayPrice();
        }
        // 1.2 Insert recharge record
        PayWalletDO wallet = payWalletService.getOrCreateWallet(userId, userType);
        PayWalletRechargeDO recharge = INSTANCE.convert(wallet.getId(), payPrice, bonusPrice, reqVO.getPackageId());
        walletRechargeMapper.insert(recharge);

        // 2.1 Create payment order
        Long payOrderId = payOrderService.createOrder(new PayOrderCreateReqDTO()
                .setAppId(WALLET_PAY_APP_ID).setUserIp(userIp)
                .setMerchantOrderId(recharge.getId().toString()) // Business order number
                .setSubject(WALLET_RECHARGE_ORDER_SUBJECT).setBody("")
                .setPrice(recharge.getPayPrice())
                .setExpireTime(addTime(Duration.ofHours(2L)))); // TODO Payment timeout
        // 2.2 Update payment orders in wallet recharge records
        walletRechargeMapper.updateById(new PayWalletRechargeDO().setId(recharge.getId()).setPayOrderId(payOrderId));
        recharge.setPayOrderId(payOrderId);
        return recharge;
    }

    @Override
    public PageResult<PayWalletRechargeDO> getWalletRechargePackagePage(Long userId, Integer userType,
                                                                               PageParam pageReqVO, Boolean payStatus) {
        PayWalletDO wallet = payWalletService.getOrCreateWallet(userId, userType);
        return walletRechargeMapper.selectPage(pageReqVO, wallet.getId(), payStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWalletRechargerPaid(Long id, Long payOrderId) {
        // 1.1 Get wallet recharge records
        PayWalletRechargeDO walletRecharge = walletRechargeMapper.selectById(id);
        if (walletRecharge == null) {
            log.error("[updateWalletRechargerPaid][Wallet recharge record does not exist，Wallet recharge record id({})]", id);
            throw exception(WALLET_RECHARGE_NOT_FOUND);
        }
        // 1.2 Check whether the wallet recharge can be paid
        PayOrderDO payOrderDO = validateWalletRechargerCanPaid(walletRecharge, payOrderId);

        // 2. Update the payment status of wallet recharge
        int updateCount = walletRechargeMapper.updateByIdAndPaid(id, false,
                new PayWalletRechargeDO().setId(id).setPayStatus(true).setPayTime(LocalDateTime.now())
                        .setPayChannelCode(payOrderDO.getChannelCode()));
        if (updateCount == 0) {
            throw exception(WALLET_RECHARGE_UPDATE_PAID_STATUS_NOT_UNPAID);
        }

        // 3. Update wallet balance
        // TODO ：In this case，Will the deposit be withdrawn in the future?，Withdraw cash too。Similar to first charge 100，Send 110；Then withdraw cash 110；
        // TODO You need to add a cashable balance in your wallet
        payWalletService.addWalletBalance(walletRecharge.getWalletId(), String.valueOf(id),
                PayWalletBizTypeEnum.RECHARGE, walletRecharge.getTotalPrice());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundWalletRecharge(Long id, String userIp) {
        // 1.1 Get wallet recharge records
        PayWalletRechargeDO walletRecharge = walletRechargeMapper.selectById(id);
        if (walletRecharge == null) {
            log.error("[refundWalletRecharge][Wallet recharge record does not exist，Wallet recharge record id({})]", id);
            throw exception(WALLET_RECHARGE_NOT_FOUND);
        }
        // 1.2 Check whether a refund can be initiated for wallet recharge
        PayWalletDO wallet = validateWalletRechargeCanRefund(walletRecharge);

        // 2. Freeze the refund balance，For the time being, only the gift balance will be refunded
        payWalletService.freezePrice(wallet.getId(), walletRecharge.getTotalPrice());

        // 3. Create a refund order
        String walletRechargeId = String.valueOf(id);
        String refundId = walletRechargeId + "-refund";
        Long payRefundId = payRefundService.createPayRefund(new PayRefundCreateReqDTO()
                .setAppId(WALLET_PAY_APP_ID).setUserIp(userIp)
                .setMerchantOrderId(walletRechargeId)
                .setMerchantRefundId(refundId)
                .setReason("Want to refund money").setPrice(walletRecharge.getPayPrice()));

        // 4. Update recharge record refund number
        // TODO Generally, this type of new construction update Object，The suggestion is，The first one set id Properties，It is easy to know to update it
        walletRechargeMapper.updateById(new PayWalletRechargeDO().setPayRefundId(payRefundId)
                .setRefundStatus(WAITING.getStatus()).setId(walletRecharge.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWalletRechargeRefunded(Long id, Long payRefundId) {
        // 1.1 Get wallet recharge records
        PayWalletRechargeDO walletRecharge = walletRechargeMapper.selectById(id);
        if (walletRecharge == null) {
            log.error("[updateWalletRechargerPaid][Wallet recharge record does not exist，Wallet recharge record id({})]", id);
            throw exception(WALLET_RECHARGE_NOT_FOUND);
        }
        // 1.2 Check whether wallet recharge can be updated and refunded
        PayRefundDO payRefund = validateWalletRechargeCanRefunded(walletRecharge, payRefundId);

        PayWalletRechargeDO updateObj = new PayWalletRechargeDO().setId(id);
        // Refund successful
        if (PayRefundStatusEnum.isSuccess(payRefund.getStatus())) {
            // 2.1 Update wallet balance
            payWalletService.reduceWalletBalance(walletRecharge.getWalletId(), id,
                    PayWalletBizTypeEnum.RECHARGE_REFUND, walletRecharge.getTotalPrice());

            updateObj.setRefundStatus(SUCCESS.getStatus()).setRefundTime(payRefund.getSuccessTime())
                    .setRefundTotalPrice(walletRecharge.getTotalPrice()).setRefundPayPrice(walletRecharge.getPayPrice())
                    .setRefundBonusPrice(walletRecharge.getBonusPrice());
        }
        // Refund failed
        if (PayRefundStatusRespEnum.isFailure(payRefund.getStatus())) {
            // 2.2 Unfreeze balance
            payWalletService.unfreezePrice(walletRecharge.getWalletId(), walletRecharge.getTotalPrice());

            updateObj.setRefundStatus(FAILURE.getStatus());
        }
        // 3. Update the refund field for wallet recharge
        walletRechargeMapper.updateByIdAndRefunded(id, WAITING.getStatus(), updateObj);
    }

    private PayRefundDO validateWalletRechargeCanRefunded(PayWalletRechargeDO walletRecharge, Long payRefundId) {
        // 1. Verify refund order matching
        if (notEqual(walletRecharge.getPayRefundId(), payRefundId)) {
            log.error("[validateWalletRechargeCanRefunded][Wallet Recharge({}) Refund order does not match({})，Please process！The data of wallet recharge is：{}]",
                    walletRecharge.getId(), payRefundId, toJsonString(walletRecharge));
            throw exception(WALLET_RECHARGE_REFUND_FAIL_REFUND_ORDER_ID_ERROR);
        }

        // 2.1 Verify refund order
        PayRefundDO payRefund = payRefundService.getRefund(payRefundId);
        if (payRefund == null) {
            log.error("[validateWalletRechargeCanRefunded][payRefund({})Does not exist]", payRefundId);
            throw exception(WALLET_RECHARGE_REFUND_FAIL_REFUND_NOT_FOUND);
        }
        // 2.2 Verify the refund amount is consistent
        if (notEqual(payRefund.getRefundPrice(), walletRecharge.getPayPrice())) {
            log.error("[validateWalletRechargeCanRefunded][Wallet({}) payRefund({}) Refund amount does not match，Please process！Wallet data is：{}，payRefund The data is：{}]",
                    walletRecharge.getId(), payRefundId, toJsonString(walletRecharge), toJsonString(payRefund));
            throw exception(WALLET_RECHARGE_REFUND_FAIL_REFUND_PRICE_NOT_MATCH);
        }
        // 2.3 Check whether the refund order and merchant order match
        if (notEqual(payRefund.getMerchantOrderId(), walletRecharge.getId().toString())) {
            log.error("[validateWalletRechargeCanRefunded][Wallet({}) Refund order does not match({})，Please process！payRefund The data is：{}]",
                    walletRecharge.getId(), payRefundId, toJsonString(payRefund));
            throw exception(WALLET_RECHARGE_REFUND_FAIL_REFUND_ORDER_ID_ERROR);
        }
        return payRefund;
    }

    private PayWalletDO validateWalletRechargeCanRefund(PayWalletRechargeDO walletRecharge) {
        // Check whether the recharge order is paid
        if (!walletRecharge.getPayStatus()) {
            throw exception(WALLET_RECHARGE_REFUND_FAIL_NOT_PAID);
        }
        // Check whether the recharge order has been refunded
        if (walletRecharge.getPayRefundId() != null) {
            throw exception(WALLET_RECHARGE_REFUND_FAIL_REFUNDED);
        }
        // Check if the wallet balance is sufficient
        PayWalletDO wallet = payWalletService.getWallet(walletRecharge.getWalletId());
        Assert.notNull(wallet, "User wallet({}) Does not exist", wallet.getId());
        if (wallet.getBalance() < walletRecharge.getTotalPrice()) {
            throw exception(WALLET_RECHARGE_REFUND_BALANCE_NOT_ENOUGH);
        }
        // TODO Need to consider，Amount of gift，Will it cause the withdrawal to exceed；
        return wallet;
    }

    private PayOrderDO validateWalletRechargerCanPaid(PayWalletRechargeDO walletRecharge, Long payOrderId) {
        // 1.1 Check the payment status of the recharge record
        if (walletRecharge.getPayStatus()) {
            log.error("[validateWalletRechargerCanPaid][Wallet({}) Not in unpaid state!  Wallet data is：{}]",
                    walletRecharge.getId(), toJsonString(walletRecharge));
            throw exception(WALLET_RECHARGE_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
        // 1.2 Check payment order matching
        if (notEqual(walletRecharge.getPayOrderId(), payOrderId)) { // Payment order number
            log.error("[validateWalletRechargerCanPaid][Wallet({}) Payment order does not match({})，Please process！ Wallet data is：{}]",
                    walletRecharge.getId(), payOrderId, toJsonString(walletRecharge));
            throw exception(WALLET_RECHARGE_UPDATE_PAID_PAY_ORDER_ID_ERROR);
        }

        // 2.1 Check if the payment slip exists
        PayOrderDO payOrder = payOrderService.getOrder(payOrderId);
        if (payOrder == null) {
            log.error("[validateWalletRechargerCanPaid][Wallet({}) payOrder({}) Does not exist，Please process！]",
                    walletRecharge.getId(), payOrderId);
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        // 2.2 Verify that the payment order has been paid
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validateWalletRechargerCanPaid][Wallet({}) payOrder({}) Not paid，Please process！payOrder The data is：{}]",
                    walletRecharge.getId(), payOrderId, toJsonString(payOrder));
            throw exception(WALLET_RECHARGE_UPDATE_PAID_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // 2.3 Verify payment amount is consistent
        if (notEqual(payOrder.getPrice(), walletRecharge.getPayPrice())) {
            log.error("[validateDemoOrderCanPaid][Wallet({}) payOrder({}) Payment amount does not match，Please process！Wallet The data is：{}，payOrder The data is：{}]",
                    walletRecharge.getId(), payOrderId, toJsonString(walletRecharge), toJsonString(payOrder));
            throw exception(WALLET_RECHARGE_UPDATE_PAID_PAY_PRICE_NOT_MATCH);
        }
        // 2.4 Verify merchant order matching of payment order
        if (notEqual(payOrder.getMerchantOrderId(), walletRecharge.getId().toString())) {
            log.error("[validateDemoOrderCanPaid][Wallet({}) Payment order does not match({})，Please process！payOrder The data is：{}]",
                    walletRecharge.getId(), payOrderId, toJsonString(payOrder));
            throw exception(WALLET_RECHARGE_UPDATE_PAID_PAY_ORDER_ID_ERROR);
        }
        return payOrder;
    }

}
