package cn.econets.blossom.module.pay.service.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderStatusRespEnum;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderExportReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import cn.econets.blossom.module.pay.convert.order.PayOrderConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import cn.econets.blossom.module.pay.dal.mysql.order.PayOrderExtensionMapper;
import cn.econets.blossom.module.pay.dal.mysql.order.PayOrderMapper;
import cn.econets.blossom.module.pay.dal.redis.no.PayNoRedisDAO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyTypeEnum;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.pay.framework.pay.config.PayProperties;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import cn.econets.blossom.module.pay.service.notify.PayNotifyService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Payment order Service Implementation class
 *
 *
 */
@Service
@Validated
@Slf4j
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    private PayProperties payProperties;

    @Resource
    private PayOrderMapper orderMapper;
    @Resource
    private PayOrderExtensionMapper orderExtensionMapper;
    @Resource
    private PayNoRedisDAO noRedisDAO;

    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;
    @Resource
    private PayNotifyService notifyService;

    @Override
    public PayOrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public PayOrderDO getOrder(Long appId, String merchantOrderId) {
        return orderMapper.selectByAppIdAndMerchantOrderId(appId, merchantOrderId);
    }

    @Override
    public List<PayOrderDO> getOrderList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return orderMapper.selectBatchIds(ids);
    }

    @Override
    public Long getOrderCountByAppId(Long appId) {
        return orderMapper.selectCountByAppId(appId);
    }

    @Override
    public PageResult<PayOrderDO> getOrderPage(PayOrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayOrderDO> getOrderList(PayOrderExportReqVO exportReqVO) {
        return orderMapper.selectList(exportReqVO);
    }

    @Override
    public Long createOrder(PayOrderCreateReqDTO reqDTO) {
        // Verification App
        PayAppDO app = appService.validPayApp(reqDTO.getAppId());

        // Check whether the corresponding payment transaction order already exists。If yes，Return directly
        PayOrderDO order = orderMapper.selectByAppIdAndMerchantOrderId(
                reqDTO.getAppId(), reqDTO.getMerchantOrderId());
        if (order != null) {
            log.warn("[createOrder][appId({}) merchantOrderId({}) The corresponding payment slip already exists({})]", order.getAppId(),
                    order.getMerchantOrderId(), toJsonString(order)); // In theory，This will not happen
            return order.getId();
        }

        // Create a payment transaction form
        order = PayOrderConvert.INSTANCE.convert(reqDTO).setAppId(app.getId())
                // Merchant related fields
                .setNotifyUrl(app.getOrderNotifyUrl())
                // Order related fields
                .setStatus(PayOrderStatusEnum.WAITING.getStatus())
                // Refund related fields
                .setRefundPrice(0);
        orderMapper.insert(order);
        return order.getId();
    }

    @Override // Attention，Transaction annotations cannot be added here，Avoid failure in calling payment channels，Will PayOrderExtensionDO Rolled back
    public PayOrderSubmitRespVO submitOrder(PayOrderSubmitReqVO reqVO, String userIp) {
        // 1.1 Get PayOrderDO ，And verify whether it exists
        PayOrderDO order = validateOrderCanSubmit(reqVO.getId());
        // 1.32 Check whether the payment channel is valid
        PayChannelDO channel = validateChannelCanSubmit(order.getAppId(), reqVO.getChannelCode());
        PayClient client = channelService.getPayClient(channel.getId());

        // 2. Insert PayOrderExtensionDO
        String no = noRedisDAO.generate(payProperties.getOrderNoPrefix());
        PayOrderExtensionDO orderExtension = PayOrderConvert.INSTANCE.convert(reqVO, userIp)
                .setOrderId(order.getId()).setNo(no)
                .setChannelId(channel.getId()).setChannelCode(channel.getCode())
                .setStatus(PayOrderStatusEnum.WAITING.getStatus());
        orderExtensionMapper.insert(orderExtension);

        // 3. Calling the three-party interface
        PayOrderUnifiedReqDTO unifiedOrderReqDTO = PayOrderConvert.INSTANCE.convert2(reqVO, userIp)
                // Merchant-related fields
                .setOutTradeNo(orderExtension.getNo()) // Attention，Here is used PayOrderExtensionDO.no Properties！
                .setSubject(order.getSubject()).setBody(order.getBody())
                .setNotifyUrl(genChannelOrderNotifyUrl(channel))
                .setReturnUrl(reqVO.getReturnUrl())
                // Order related fields
                .setPrice(order.getPrice()).setExpireTime(order.getExpireTime());
        PayOrderRespDTO unifiedOrderResp = client.unifiedOrder(unifiedOrderReqDTO);

        // 4. If the direct payment call is successful，Directly update the payment order status to success。For example：Payment code payment，Password-free payment，Just verify the payment is successful
        if (unifiedOrderResp != null) {
            getSelf().notifyOrder(channel, unifiedOrderResp);
            // If there is a channel error code，Throws a business exception，Prompt the user
            if (StrUtil.isNotEmpty(unifiedOrderResp.getChannelErrorCode())) {
                throw exception(PAY_ORDER_SUBMIT_CHANNEL_ERROR, unifiedOrderResp.getChannelErrorCode(),
                        unifiedOrderResp.getChannelErrorMsg());
            }
            // The latest status needs to be read here
            order = orderMapper.selectById(order.getId());
        }
        return PayOrderConvert.INSTANCE.convert(order, unifiedOrderResp);
    }

    private PayOrderDO validateOrderCanSubmit(Long id) {
        PayOrderDO order = orderMapper.selectById(id);
        if (order == null) { // Does it exist?
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        if (PayOrderStatusEnum.isSuccess(order.getStatus())) { // Verification Status，Payment found
            throw exception(PAY_ORDER_STATUS_IS_SUCCESS);
        }
        if (!PayOrderStatusEnum.WAITING.getStatus().equals(order.getStatus())) { // Verification Status，Must be pending payment
            throw exception(PAY_ORDER_STATUS_IS_NOT_WAITING);
        }
        if (LocalDateTimeUtils.beforeNow(order.getExpireTime())) { // Check if it is expired
            throw exception(PAY_ORDER_IS_EXPIRED);
        }

        // 【Important】Check whether the payment extension order has been paid，Just no callback、Or the data is abnormal
        validateOrderActuallyPaid(id);
        return order;
    }

    /**
     * Verify that the payment order has actually been paid
     *
     * @param id Payment number
     */
    @VisibleForTesting
    void validateOrderActuallyPaid(Long id) {
        List<PayOrderExtensionDO> orderExtensions = orderExtensionMapper.selectListByOrderId(id);
        orderExtensions.forEach(orderExtension -> {
            // Situation 1：Verify database orderExtension Has it been paid?
            if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) {
                log.warn("[validateOrderCanSubmit][order({}) of extension({}) Paid，Maybe the data is inconsistent]",
                        id, orderExtension.getId());
                throw exception(PAY_ORDER_EXTENSION_IS_PAID);
            }
            // Case 2：Calling the three-party interface，Query payment order status，Has it been paid?
            PayClient payClient = channelService.getPayClient(orderExtension.getChannelId());
            if (payClient == null) {
                log.error("[validateOrderCanSubmit][Channel Number({}) Cannot find the corresponding payment client]", orderExtension.getChannelId());
                return;
            }
            PayOrderRespDTO respDTO = payClient.getOrder(orderExtension.getNo());
            if (respDTO != null && PayOrderStatusRespEnum.isSuccess(respDTO.getStatus())) {
                log.warn("[validateOrderCanSubmit][order({}) of PayOrderRespDTO({}) Paid，Maybe callback delay]",
                        id, toJsonString(respDTO));
                throw exception(PAY_ORDER_EXTENSION_IS_PAID);
            }
        });
    }

    private PayChannelDO validateChannelCanSubmit(Long appId, String channelCode) {
        // Verification App
        appService.validPayApp(appId);
        // Check whether the payment channel is valid
        PayChannelDO channel = channelService.validPayChannel(appId, channelCode);
        PayClient client = channelService.getPayClient(channel.getId());
        if (client == null) {
            log.error("[validatePayChannelCanSubmit][Channel Number({}) Cannot find the corresponding payment client]", channel.getId());
            throw exception(CHANNEL_NOT_FOUND);
        }
        return channel;
    }

    /**
     * According to the payment channel code，Generate callback address for payment channel
     *
     * @param channel Payment channel
     * @return Payment channel callback address  Configuration address + "/" + channel id
     */
    private String genChannelOrderNotifyUrl(PayChannelDO channel) {
        return payProperties.getOrderNotifyUrl() + "/" + channel.getId();
    }

    @Override
    public void notifyOrder(Long channelId, PayOrderRespDTO notify) {
        // Check whether the payment channel is valid
        PayChannelDO channel = channelService.validPayChannel(channelId);
        // Update payment order to paid
        TenantUtils.execute(channel.getTenantId(), () -> getSelf().notifyOrder(channel, notify));
    }

    /**
     * Notify and update the payment result of the order
     *
     * @param channel Payment channel
     * @param notify  Notice
     */
    @Transactional(rollbackFor = Exception.class)
    // Attention，If the method is called from within a method，Need to pass getSelf().notifyPayOrder(channel, notify) Call，Otherwise the transaction will not take effect
    public void notifyOrder(PayChannelDO channel, PayOrderRespDTO notify) {
        // Situation 1：Payment successful callback
        if (PayOrderStatusRespEnum.isSuccess(notify.getStatus())) {
            notifyOrderSuccess(channel, notify);
            return;
        }
        // Case 2：Payment failed callback
        if (PayOrderStatusRespEnum.isClosed(notify.getStatus())) {
            notifyOrderClosed(channel, notify);
        }
        // Case 3：WAITING：No processing required
        // Case 4：REFUND：Process through refund callback
    }

    private void notifyOrderSuccess(PayChannelDO channel, PayOrderRespDTO notify) {
        // 1. Update PayOrderExtensionDO Payment successful
        PayOrderExtensionDO orderExtension = updateOrderSuccess(notify);
        // 2. Update PayOrderDO Payment successful
        Boolean paid = updateOrderSuccess(channel, orderExtension, notify);
        if (paid) { // If the callback has been successful before，Return directly，No need to record payment notification records repeatedly；For example：Payment platform repeated callback
            return;
        }

        // 3. Insert payment notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.ORDER.getType(),
                orderExtension.getOrderId());
    }

    /**
     * Update PayOrderExtensionDO Payment successful
     *
     * @param notify Notice
     * @return PayOrderExtensionDO Object
     */
    private PayOrderExtensionDO updateOrderSuccess(PayOrderRespDTO notify) {
        // 1. Query PayOrderExtensionDO
        PayOrderExtensionDO orderExtension = orderExtensionMapper.selectByNo(notify.getOutTradeNo());
        if (orderExtension == null) {
            throw exception(PAY_ORDER_EXTENSION_NOT_FOUND);
        }
        if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) { // If it is already successful，Return directly，No need to update repeatedly
            log.info("[updateOrderExtensionSuccess][orderExtension({}) Already paid，No update required]", orderExtension.getId());
            return orderExtension;
        }
        if (ObjectUtil.notEqual(orderExtension.getStatus(), PayOrderStatusEnum.WAITING.getStatus())) { // Verification Status，Must be pending payment
            throw exception(PAY_ORDER_EXTENSION_STATUS_IS_NOT_WAITING);
        }

        // 2. Update PayOrderExtensionDO
        int updateCounts = orderExtensionMapper.updateByIdAndStatus(orderExtension.getId(), orderExtension.getStatus(),
                PayOrderExtensionDO.builder().status(PayOrderStatusEnum.SUCCESS.getStatus()).channelNotifyData(toJsonString(notify)).build());
        if (updateCounts == 0) { // Verification Status，Must be pending payment
            throw exception(PAY_ORDER_EXTENSION_STATUS_IS_NOT_WAITING);
        }
        log.info("[updateOrderExtensionSuccess][orderExtension({}) Updated to paid]", orderExtension.getId());
        return orderExtension;
    }

    /**
     * Update PayOrderDO Payment successful
     *
     * @param channel        Payment channel
     * @param orderExtension Payment extension form
     * @param notify         Notification callback
     * @return Has the callback been successfully completed before?
     */
    private Boolean updateOrderSuccess(PayChannelDO channel, PayOrderExtensionDO orderExtension,
                                       PayOrderRespDTO notify) {
        // 1. Judgement PayOrderDO Whether payment is pending
        PayOrderDO order = orderMapper.selectById(orderExtension.getOrderId());
        if (order == null) {
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        if (PayOrderStatusEnum.isSuccess(order.getStatus()) // If it is already successful，Return directly，No need to update repeatedly
                && Objects.equals(order.getExtensionId(), orderExtension.getId())) {
            log.info("[updateOrderExtensionSuccess][order({}) Already paid，No update required]", order.getId());
            return true;
        }
        if (!PayOrderStatusEnum.WAITING.getStatus().equals(order.getStatus())) { // Verification status，Must be pending payment
            throw exception(PAY_ORDER_STATUS_IS_NOT_WAITING);
        }

        // 2. Update PayOrderDO
        int updateCounts = orderMapper.updateByIdAndStatus(order.getId(), PayOrderStatusEnum.WAITING.getStatus(),
                PayOrderDO.builder().status(PayOrderStatusEnum.SUCCESS.getStatus())
                        .channelId(channel.getId()).channelCode(channel.getCode())
                        .successTime(notify.getSuccessTime()).extensionId(orderExtension.getId()).no(orderExtension.getNo())
                        .channelOrderNo(notify.getChannelOrderNo()).channelUserId(notify.getChannelUserId())
                        .channelFeeRate(channel.getFeeRate())
                        .channelFeePrice(MoneyUtils.calculateRatePrice(order.getPrice(), channel.getFeeRate()))
                        .build());
        if (updateCounts == 0) { // Verification status，Must be pending payment
            throw exception(PAY_ORDER_STATUS_IS_NOT_WAITING);
        }
        log.info("[updateOrderExtensionSuccess][order({}) Updated to paid]", order.getId());
        return false;
    }

    private void notifyOrderClosed(PayChannelDO channel, PayOrderRespDTO notify) {
        updateOrderExtensionClosed(channel, notify);
    }

    private void updateOrderExtensionClosed(PayChannelDO channel, PayOrderRespDTO notify) {
        // 1. Query PayOrderExtensionDO
        PayOrderExtensionDO orderExtension = orderExtensionMapper.selectByNo(notify.getOutTradeNo());
        if (orderExtension == null) {
            throw exception(PAY_ORDER_EXTENSION_NOT_FOUND);
        }
        if (PayOrderStatusEnum.isClosed(orderExtension.getStatus())) { // If it is already closed，Return directly，No need to update repeatedly
            log.info("[updateOrderExtensionClosed][orderExtension({}) Payment is already closed，No update required]", orderExtension.getId());
            return;
        }
        // Usually it will show payment success first，Then pay and close，All scenarios are closed due to full refund。This situation，We do not update payment extension orders，Only through the refund process，Update payment order
        if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) {
            log.info("[updateOrderExtensionClosed][orderExtension({}) Yes, paid，No need to update, payment closed]", orderExtension.getId());
            return;
        }
        if (ObjectUtil.notEqual(orderExtension.getStatus(), PayOrderStatusEnum.WAITING.getStatus())) { // Verification Status，Must be pending payment
            throw exception(PAY_ORDER_EXTENSION_STATUS_IS_NOT_WAITING);
        }

        // 2. Update PayOrderExtensionDO
        int updateCounts = orderExtensionMapper.updateByIdAndStatus(orderExtension.getId(), orderExtension.getStatus(),
                PayOrderExtensionDO.builder().status(PayOrderStatusEnum.CLOSED.getStatus()).channelNotifyData(toJsonString(notify))
                        .channelErrorCode(notify.getChannelErrorCode()).channelErrorMsg(notify.getChannelErrorMsg()).build());
        if (updateCounts == 0) { // Verification Status，Must be pending payment
            throw exception(PAY_ORDER_EXTENSION_STATUS_IS_NOT_WAITING);
        }
        log.info("[updateOrderExtensionClosed][orderExtension({}) Update to payment closed]", orderExtension.getId());
    }

    @Override
    public void updateOrderRefundPrice(Long id, Integer incrRefundPrice) {
        PayOrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        if (!PayOrderStatusEnum.isSuccessOrRefund(order.getStatus())) {
            throw exception(PAY_ORDER_REFUND_FAIL_STATUS_ERROR);
        }
        if (order.getRefundPrice() + incrRefundPrice > order.getPrice()) {
            throw exception(REFUND_PRICE_EXCEED);
        }

        // Update order
        PayOrderDO updateObj = new PayOrderDO()
                .setRefundPrice(order.getRefundPrice() + incrRefundPrice)
                .setStatus(PayOrderStatusEnum.REFUND.getStatus());
        int updateCount = orderMapper.updateByIdAndStatus(id, order.getStatus(), updateObj);
        if (updateCount == 0) {
            throw exception(PAY_ORDER_REFUND_FAIL_STATUS_ERROR);
        }
    }

    @Override
    public void updatePayOrderPrice(Long id, Integer payPrice) {
        PayOrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(PayOrderStatusEnum.WAITING.getStatus(), order.getStatus())) {
            throw exception(PAY_ORDER_STATUS_IS_NOT_WAITING);
        }
        if (ObjectUtil.equal(order.getPrice(), payPrice)) {
            return;
        }

        // TODO Should new Come out and update
        order.setPrice(payPrice);
        orderMapper.updateById(order);
    }

    @Override
    public PayOrderExtensionDO getOrderExtension(Long id) {
        return orderExtensionMapper.selectById(id);
    }

    @Override
    public PayOrderExtensionDO getOrderExtensionByNo(String no) {
        return orderExtensionMapper.selectByNo(no);
    }

    @Override
    public int syncOrder(LocalDateTime minCreateTime) {
        // 1. Query the orders to be paid before the specified creation time
        List<PayOrderExtensionDO> orderExtensions = orderExtensionMapper.selectListByStatusAndCreateTimeLe(
                PayOrderStatusEnum.WAITING.getStatus(), minCreateTime);
        if (CollUtil.isEmpty(orderExtensions)) {
            return 0;
        }
        // 2. Traversal execution
        int count = 0;
        for (PayOrderExtensionDO orderExtension : orderExtensions) {
            count += syncOrder(orderExtension) ? 1 : 0;
        }
        return count;
    }

    /**
     * Synchronize a single payment extension form
     *
     * @param orderExtension Payment extension form
     * @return Has it been paid?
     */
    private boolean syncOrder(PayOrderExtensionDO orderExtension) {
        try {
            // 1.1 Query payment order information
            PayClient payClient = channelService.getPayClient(orderExtension.getChannelId());
            if (payClient == null) {
                log.error("[syncOrder][Channel Number({}) Cannot find the corresponding payment client]", orderExtension.getChannelId());
                return false;
            }
            PayOrderRespDTO respDTO = payClient.getOrder(orderExtension.getNo());
            // 1.2 Callback payment result
            notifyOrder(orderExtension.getChannelId(), respDTO);

            // 2. If it has been paid，Then return true
            return PayOrderStatusRespEnum.isSuccess(respDTO.getStatus());
        } catch (Throwable e) {
            log.error("[syncOrder][orderExtension({}) Synchronous payment status is abnormal]", orderExtension.getId(), e);
            return false;
        }
    }

    @Override
    public int expireOrder() {
        // 1. Query expired orders to be paid
        List<PayOrderDO> orders = orderMapper.selectListByStatusAndExpireTimeLt(
                PayOrderStatusEnum.WAITING.getStatus(), LocalDateTime.now());
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }

        // 2. Traversal execution
        int count = 0;
        for (PayOrderDO order : orders) {
            count += expireOrder(order) ? 1 : 0;
        }
        return count;
    }

    /**
     * Synchronize a single payment order
     *
     * @param order Payment slip
     * @return Is it expired?
     */
    private boolean expireOrder(PayOrderDO order) {
        try {
            // 1. Need to process the associated payment extension order first，Avoid incorrect overdue payment or Refunded orders
            List<PayOrderExtensionDO> orderExtensions = orderExtensionMapper.selectListByOrderId(order.getId());
            for (PayOrderExtensionDO orderExtension : orderExtensions) {
                if (PayOrderStatusEnum.isClosed(orderExtension.getStatus())) {
                    continue;
                }
                // Situation 1：Verify database orderExtension Has it been paid?
                if (PayOrderStatusEnum.isSuccess(orderExtension.getStatus())) {
                    log.error("[expireOrder][order({}) of extension({}) Paid，Maybe the data is inconsistent]",
                            order.getId(), orderExtension.getId());
                    return false;
                }
                // Case 2：Calling the three-party interface，Query payment order status，Has it been paid?/Refunded
                PayClient payClient = channelService.getPayClient(orderExtension.getChannelId());
                if (payClient == null) {
                    log.error("[expireOrder][Channel Number({}) Cannot find the corresponding payment client]", orderExtension.getChannelId());
                    return false;
                }
                PayOrderRespDTO respDTO = payClient.getOrder(orderExtension.getNo());
                if (PayOrderStatusRespEnum.isRefund(respDTO.getStatus())) {
                    // Additional explanation：In theory，Should be WAITING => SUCCESS => REFUND Status，If directly WAITING => REFUND Status，It means the process is lost in the middle
                    // At this time，Human intervention required，Manually complete the data，Keep WAITING => SUCCESS => REFUND Process
                    log.error("[expireOrder][extension({}) of PayOrderRespDTO({}) Refunded，Maybe callback delay]",
                            orderExtension.getId(), toJsonString(respDTO));
                    return false;
                }
                if (PayOrderStatusRespEnum.isSuccess(respDTO.getStatus())) {
                    notifyOrder(orderExtension.getChannelId(), respDTO);
                    return false;
                }
                // Back-up logic：Update the payment extension form to closed
                PayOrderExtensionDO updateObj = new PayOrderExtensionDO().setStatus(PayOrderStatusEnum.CLOSED.getStatus())
                        .setChannelNotifyData(toJsonString(respDTO));
                if (orderExtensionMapper.updateByIdAndStatus(orderExtension.getId(), PayOrderStatusEnum.WAITING.getStatus(),
                        updateObj) == 0) {
                    log.error("[expireOrder][extension({}) Update to payment closure failure]", orderExtension.getId());
                    return false;
                }
                log.info("[expireOrder][extension({}) Updated to payment closed successfully]", orderExtension.getId());
            }

            // 2. None of the above situations，You can safely update to closed
            PayOrderDO updateObj = new PayOrderDO().setStatus(PayOrderStatusEnum.CLOSED.getStatus());
            if (orderMapper.updateByIdAndStatus(order.getId(), order.getStatus(), updateObj) == 0) {
                log.error("[expireOrder][order({}) Update to payment closure failure]", order.getId());
                return false;
            }
            log.info("[expireOrder][order({}) Update to payment closure failure]", order.getId());
            return true;
        } catch (Throwable e) {
            log.error("[expireOrder][order({}) Expired order exception]", order.getId(), e);
            return false;
        }
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private PayOrderServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
