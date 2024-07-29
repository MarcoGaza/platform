package cn.econets.blossom.module.pay.service.refund;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.pay.core.client.PayClient;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.refund.PayRefundStatusRespEnum;
import cn.econets.blossom.framework.tenant.core.util.TenantUtils;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.refund.vo.PayRefundExportReqVO;
import cn.econets.blossom.module.pay.controller.admin.refund.vo.PayRefundPageReqVO;
import cn.econets.blossom.module.pay.convert.refund.PayRefundConvert;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.channel.PayChannelDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.dal.mysql.refund.PayRefundMapper;
import cn.econets.blossom.module.pay.dal.redis.no.PayNoRedisDAO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyTypeEnum;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import cn.econets.blossom.module.pay.framework.pay.config.PayProperties;
import cn.econets.blossom.module.pay.service.app.PayAppService;
import cn.econets.blossom.module.pay.service.channel.PayChannelService;
import cn.econets.blossom.module.pay.service.notify.PayNotifyService;
import cn.econets.blossom.module.pay.service.order.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Refund order Service Implementation class
 *
 *
 */
@Service
@Slf4j
@Validated
public class PayRefundServiceImpl implements PayRefundService {

    @Resource
    private PayProperties payProperties;

    @Resource
    private PayRefundMapper refundMapper;
    @Resource
    private PayNoRedisDAO noRedisDAO;

    @Resource
    private PayOrderService orderService;
    @Resource
    private PayAppService appService;
    @Resource
    private PayChannelService channelService;
    @Resource
    private PayNotifyService notifyService;

    @Override
    public PayRefundDO getRefund(Long id) {
        return refundMapper.selectById(id);
    }

    @Override
    public PayRefundDO getRefundByNo(String no) {
        return refundMapper.selectByNo(no);
    }

    @Override
    public Long getRefundCountByAppId(Long appId) {
        return refundMapper.selectCountByAppId(appId);
    }

    @Override
    public PageResult<PayRefundDO> getRefundPage(PayRefundPageReqVO pageReqVO) {
        return refundMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PayRefundDO> getRefundList(PayRefundExportReqVO exportReqVO) {
        return refundMapper.selectList(exportReqVO);
    }

    @Override
    public Long createPayRefund(PayRefundCreateReqDTO reqDTO) {
        // 1.1 Verification App
        PayAppDO app = appService.validPayApp(reqDTO.getAppId());
        // 1.2 Verify payment order
        PayOrderDO order = validatePayOrderCanRefund(reqDTO);
        // 1.3 Check whether the payment channel is valid
        PayChannelDO channel = channelService.validPayChannel(order.getChannelId());
        PayClient client = channelService.getPayClient(channel.getId());
        if (client == null) {
            log.error("[refund][Channel Number({}) Cannot find the corresponding payment client]", channel.getId());
            throw exception(CHANNEL_NOT_FOUND);
        }
        // 1.4 Check whether the refund order already exists
        PayRefundDO refund = refundMapper.selectByAppIdAndMerchantRefundId(
                app.getId(), reqDTO.getMerchantRefundId());
        if (refund != null) {
            throw exception(REFUND_EXISTS);
        }

        // 2.1 Insert refund slip
        String no = noRedisDAO.generate(payProperties.getRefundNoPrefix());
        refund = PayRefundConvert.INSTANCE.convert(reqDTO)
                .setNo(no).setOrderId(order.getId()).setOrderNo(order.getNo())
                .setChannelId(order.getChannelId()).setChannelCode(order.getChannelCode())
                // Merchant-related fields
                .setNotifyUrl(app.getRefundNotifyUrl())
                // Channel related fields
                .setChannelOrderNo(order.getChannelOrderNo())
                // Refund related fields
                .setStatus(PayRefundStatusEnum.WAITING.getStatus())
                .setPayPrice(order.getPrice()).setRefundPrice(reqDTO.getPrice());
        refundMapper.insert(refund);
        try {
            // 2.2 Submit a refund request to the channel
            PayRefundUnifiedReqDTO unifiedReqDTO = new PayRefundUnifiedReqDTO()
                    .setPayPrice(order.getPrice())
                    .setRefundPrice(reqDTO.getPrice())
                    .setOutTradeNo(order.getNo())
                    .setOutRefundNo(refund.getNo())
                    .setNotifyUrl(genChannelRefundNotifyUrl(channel))
                    .setReason(reqDTO.getReason());
            PayRefundRespDTO refundRespDTO = client.unifiedRefund(unifiedReqDTO);
            // 2.3 Processing refund returns
            getSelf().notifyRefund(channel, refundRespDTO);
        } catch (Throwable e) {
            // Attention：Only print exceptions here，No throw。
            // The reason is：Although an exception occurred when calling the payment channel for refund（Network request timed out），Actual refund successful。This result，Subsequent refund callback、Or you can get the refund polling compensation。
            // Final，Under abnormal circumstances，The payment center will asynchronously call back the refund callback interface of the business，Provide refund results
            log.error("[createPayRefund][Refund id({}) requestDTO({}) Exception occurred]",
                    refund.getId(), reqDTO, e);
        }

        // Return refund number
        return refund.getId();
    }

    /**
     * Check whether the payment order can be refunded
     *
     * @param reqDTO Refund application information
     * @return Payment order
     */
    private PayOrderDO validatePayOrderCanRefund(PayRefundCreateReqDTO reqDTO) {
        PayOrderDO order = orderService.getOrder(reqDTO.getAppId(), reqDTO.getMerchantOrderId());
        if (order == null) {
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        // Verification status，Must be paid、or refunded
        if (!PayOrderStatusEnum.isSuccessOrRefund(order.getStatus())) {
            throw exception(PAY_ORDER_REFUND_FAIL_STATUS_ERROR);
        }

        // Verification amount，The refund amount cannot be greater than the original amount
        if (reqDTO.getPrice() + order.getRefundPrice() > order.getPrice()){
            throw exception(REFUND_PRICE_EXCEED);
        }
        // Are there any orders being refunded?
        if (refundMapper.selectCountByAppIdAndOrderId(reqDTO.getAppId(), order.getId(),
                PayRefundStatusEnum.WAITING.getStatus()) > 0) {
            throw exception(REFUND_HAS_REFUNDING);
        }
        return order;
    }

    /**
     * According to the payment channel code，Generate callback address for payment channel
     *
     * @param channel Payment channel
     * @return Payment channel callback address  Configuration address + "/" + channel id
     */
    private String genChannelRefundNotifyUrl(PayChannelDO channel) {
        return payProperties.getRefundNotifyUrl() + "/" + channel.getId();
    }

    @Override
    public void notifyRefund(Long channelId, PayRefundRespDTO notify) {
        // Check whether the payment channel is valid
        PayChannelDO channel = channelService.validPayChannel(channelId);
        // Update refund order
        TenantUtils.execute(channel.getTenantId(), () -> getSelf().notifyRefund(channel, notify));
    }

    /**
     * Notify and update the refund result of the order
     *
     * @param channel Payment channel
     * @param notify Notice
     */
    @Transactional(rollbackFor = Exception.class)  // Attention，If the method is called from within a method，Need to pass getSelf().notifyRefund(channel, notify) Call，Otherwise the transaction will not take effect
    public void notifyRefund(PayChannelDO channel, PayRefundRespDTO notify) {
        // Situation 1：Refund successful
        if (PayRefundStatusRespEnum.isSuccess(notify.getStatus())) {
            notifyRefundSuccess(channel, notify);
            return;
        }
        // Case 2：Refund failed
        if (PayRefundStatusRespEnum.isFailure(notify.getStatus())) {
            notifyRefundFailure(channel, notify);
        }
    }

    private void notifyRefundSuccess(PayChannelDO channel, PayRefundRespDTO notify) {
        // 1.1 Query PayRefundDO
        PayRefundDO refund = refundMapper.selectByAppIdAndNo(
                channel.getAppId(), notify.getOutRefundNo());
        if (refund == null) {
            throw exception(REFUND_NOT_FOUND);
        }
        if (PayRefundStatusEnum.isSuccess(refund.getStatus())) { // If it is already successful，Return directly，No need to update repeatedly
            log.info("[notifyRefundSuccess][Refund order({}) The refund has been successful，No update required]", refund.getId());
            return;
        }
        if (!PayRefundStatusEnum.WAITING.getStatus().equals(refund.getStatus())) {
            throw exception(REFUND_STATUS_IS_NOT_WAITING);
        }
        // 1.2 Update PayRefundDO
        PayRefundDO updateRefundObj = new PayRefundDO()
                .setSuccessTime(notify.getSuccessTime())
                .setChannelRefundNo(notify.getChannelRefundNo())
                .setStatus(PayRefundStatusEnum.SUCCESS.getStatus())
                .setChannelNotifyData(toJsonString(notify));
        int updateCounts = refundMapper.updateByIdAndStatus(refund.getId(), refund.getStatus(), updateRefundObj);
        if (updateCounts == 0) { // Verification status，Must be in waiting state
            throw exception(REFUND_STATUS_IS_NOT_WAITING);
        }
        log.info("[notifyRefundSuccess][Refund order({}) Updated to refund successful]", refund.getId());

        // 2. Update order
        orderService.updateOrderRefundPrice(refund.getOrderId(), refund.getRefundPrice());

        // 3. Insert refund notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.REFUND.getType(),
                refund.getId());
    }

    private void notifyRefundFailure(PayChannelDO channel, PayRefundRespDTO notify) {
        // 1.1 Query PayRefundDO
        PayRefundDO refund = refundMapper.selectByAppIdAndNo(
                channel.getAppId(), notify.getOutRefundNo());
        if (refund == null) {
            throw exception(REFUND_NOT_FOUND);
        }
        if (PayRefundStatusEnum.isFailure(refund.getStatus())) { // If it is already successful，Return directly，No need to update repeatedly
            log.info("[notifyRefundSuccess][Refund order({}) Refund is closed，No update required]", refund.getId());
            return;
        }
        if (!PayRefundStatusEnum.WAITING.getStatus().equals(refund.getStatus())) {
            throw exception(REFUND_STATUS_IS_NOT_WAITING);
        }
        // 1.2 Update PayRefundDO
        PayRefundDO updateRefundObj = new PayRefundDO()
                .setChannelRefundNo(notify.getChannelRefundNo())
                .setStatus(PayRefundStatusEnum.FAILURE.getStatus())
                .setChannelNotifyData(toJsonString(notify))
                .setChannelErrorCode(notify.getChannelErrorCode()).setChannelErrorMsg(notify.getChannelErrorMsg());
        int updateCounts = refundMapper.updateByIdAndStatus(refund.getId(), refund.getStatus(), updateRefundObj);
        if (updateCounts == 0) { // Verification status，Must be in waiting state
            throw exception(REFUND_STATUS_IS_NOT_WAITING);
        }
        log.info("[notifyRefundFailure][Refund order({}) Updated to refund failed]", refund.getId());

        // 2. Insert refund notification record
        notifyService.createPayNotifyTask(PayNotifyTypeEnum.REFUND.getType(),
                refund.getId());
    }

    @Override
    public int syncRefund() {
        // 1. Query the orders to be refunded within the specified creation time
        List<PayRefundDO> refunds = refundMapper.selectListByStatus(PayRefundStatusEnum.WAITING.getStatus());
        if (CollUtil.isEmpty(refunds)) {
            return 0;
        }
        // 2. Traversal execution
        int count = 0;
        for (PayRefundDO refund : refunds) {
            count += syncRefund(refund) ? 1 : 0;
        }
        return count;
    }

    /**
     * Synchronize a single refund order
     *
     * @param refund Refund order
     * @return Whether to synchronize
     */
    private boolean syncRefund(PayRefundDO refund) {
        try {
            // 1.1 Query refund order information
            PayClient payClient = channelService.getPayClient(refund.getChannelId());
            if (payClient == null) {
                log.error("[syncRefund][Channel Number({}) Cannot find the corresponding payment client]", refund.getChannelId());
                return false;
            }
            PayRefundRespDTO respDTO = payClient.getRefund(refund.getOrderNo(), refund.getNo());
            // 1.2 Call back refund result
            notifyRefund(refund.getChannelId(), respDTO);

            // 2. If synchronized to，Then return true
            return PayRefundStatusEnum.isSuccess(respDTO.getStatus())
                    || PayRefundStatusEnum.isFailure(respDTO.getStatus());
        } catch (Throwable e) {
            log.error("[syncRefund][refund({}) Synchronous refund status is abnormal]", refund.getId(), e);
            return false;
        }
    }

    /**
     * Get its own proxy object，Solved AOP Effectiveness issue
     *
     * @return Myself
     */
    private PayRefundServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
