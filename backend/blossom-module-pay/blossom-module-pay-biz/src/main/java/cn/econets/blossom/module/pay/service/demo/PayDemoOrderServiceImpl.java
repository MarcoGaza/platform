package cn.econets.blossom.module.pay.service.demo;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.api.order.PayOrderApi;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderRespDTO;
import cn.econets.blossom.module.pay.api.refund.PayRefundApi;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundRespDTO;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import cn.econets.blossom.module.pay.dal.mysql.demo.PayDemoOrderMapper;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.util.ObjectUtil.notEqual;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.addTime;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.module.pay.enums.ErrorCodeConstants.*;

/**
 * Sample Order Service Implementation class
 *
 *
 */
@Service
@Validated
@Slf4j
public class PayDemoOrderServiceImpl implements PayDemoOrderService {

    /**
     * Accessed application number
     *
     * From [Payment Management -> Application Information] Add
     */
    private static final Long PAY_APP_ID = 7L;

    /**
     * Product Information Map
     *
     * key：Product Number
     * value：[Product Name、Product price]
     */
    private final Map<Long, Object[]> spuNames = new HashMap<>();

    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private PayRefundApi payRefundApi;

    @Resource
    private PayDemoOrderMapper payDemoOrderMapper;

    public PayDemoOrderServiceImpl() {
        spuNames.put(1L, new Object[]{"Huawei mobile phone", 1});
        spuNames.put(2L, new Object[]{"Xiaomi TV", 10});
        spuNames.put(3L, new Object[]{"Apple Watch", 100});
        spuNames.put(4L, new Object[]{"ASUS laptop", 1000});
        spuNames.put(5L, new Object[]{"NIO", 200000});
    }

    @Override
    public Long createDemoOrder(Long userId, PayDemoOrderCreateReqVO createReqVO) {
        // 1.1 Get the product
        Object[] spu = spuNames.get(createReqVO.getSpuId());
        Assert.notNull(spu, "Products({}) Does not exist", createReqVO.getSpuId());
        String spuName = (String) spu[0];
        Integer price = (Integer) spu[1];
        // 1.2 Insert demo Order
        PayDemoOrderDO demoOrder = new PayDemoOrderDO().setUserId(userId)
                .setSpuId(createReqVO.getSpuId()).setSpuName(spuName)
                .setPrice(price).setPayStatus(false).setRefundPrice(0);
        payDemoOrderMapper.insert(demoOrder);

        // 2.1 Create payment order
        Long payOrderId = payOrderApi.createOrder(new PayOrderCreateReqDTO()
                .setAppId(PAY_APP_ID).setUserIp(getClientIP()) // Payment Application
                .setMerchantOrderId(demoOrder.getId().toString()) // Business order number
                .setSubject(spuName).setBody("").setPrice(price) // Price information
                .setExpireTime(addTime(Duration.ofHours(2L)))); // Payment expiration date
        // 2.2 Update payment order to demo Order
        payDemoOrderMapper.updateById(new PayDemoOrderDO().setId(demoOrder.getId())
                .setPayOrderId(payOrderId));
        // Return
        return demoOrder.getId();
    }

    @Override
    public PayDemoOrderDO getDemoOrder(Long id) {
        return payDemoOrderMapper.selectById(id);
    }

    @Override
    public PageResult<PayDemoOrderDO> getDemoOrderPage(PageParam pageReqVO) {
        return payDemoOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateDemoOrderPaid(Long id, Long payOrderId) {
        // Verify and obtain payment order（Available for payment）
        PayOrderRespDTO payOrder = validateDemoOrderCanPaid(id, payOrderId);

        // Update PayDemoOrderDO Status is paid
        int updateCount = payDemoOrderMapper.updateByIdAndPayed(id, false,
                new PayDemoOrderDO().setPayStatus(true).setPayTime(LocalDateTime.now())
                        .setPayChannelCode(payOrder.getChannelCode()));
        if (updateCount == 0) {
            throw exception(DEMO_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
    }

    /**
     * Verify that the transaction order meets the conditions for being paid
     *
     * 1. Transaction order not paid
     * 2. The payment order has been paid
     *
     * @param id Transaction order number
     * @param payOrderId Payment order number
     * @return Transaction Order
     */
    private PayOrderRespDTO validateDemoOrderCanPaid(Long id, Long payOrderId) {
        // 1.1 Check if the order exists
        PayDemoOrderDO order = payDemoOrderMapper.selectById(id);
        if (order == null) {
            throw exception(DEMO_ORDER_NOT_FOUND);
        }
        // 1.2 Verification order not paid
        if (order.getPayStatus()) {
            log.error("[validateDemoOrderCanPaid][order({}) Not in payment state，Please process！order The data is：{}]",
                    id, toJsonString(order));
            throw exception(DEMO_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
        // 1.3 Check payment order matching
        if (notEqual(order.getPayOrderId(), payOrderId)) { // Payment order number
            log.error("[validateDemoOrderCanPaid][order({}) Payment order does not match({})，Please process！order The data is：{}]",
                    id, payOrderId, toJsonString(order));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        // 2.1 Check if the payment slip exists
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId);
        if (payOrder == null) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) Does not exist，Please process！]", id, payOrderId);
            throw exception(PAY_ORDER_NOT_FOUND);
        }
        // 2.2 Verify that the payment order has been paid
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) Not paid，Please process！payOrder The data is：{}]",
                    id, payOrderId, toJsonString(payOrder));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // 2.3 Verify payment amount is consistent
        if (notEqual(payOrder.getPrice(), order.getPrice())) {
            log.error("[validateDemoOrderCanPaid][order({}) payOrder({}) Payment amount does not match，Please process！order The data is：{}，payOrder The data is：{}]",
                    id, payOrderId, toJsonString(order), toJsonString(payOrder));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH);
        }
        // 2.4 Check payment order matching（Secondary）
        if (notEqual(payOrder.getMerchantOrderId(), id.toString())) {
            log.error("[validateDemoOrderCanPaid][order({}) Payment order does not match({})，Please process！payOrder The data is：{}]",
                    id, payOrderId, toJsonString(payOrder));
            throw exception(DEMO_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }
        return payOrder;
    }

    @Override
    public void refundDemoOrder(Long id, String userIp) {
        // 1. Check whether the order can be refunded
        PayDemoOrderDO order = validateDemoOrderCanRefund(id);

        // 2.1 Generate a refund order number
        // Generally speaking，When the user initiates a refund，A separate after-sales rights protection table will be inserted，Then use this table id As refundId
        // We are a simple one here demo，So there is no after-sales rights protection form，Use the order directly id + "-refund" Come and demonstrate
        String refundId = order.getId() + "-refund";
        // 2.2 Create a refund order
        Long payRefundId = payRefundApi.createRefund(new PayRefundCreateReqDTO()
                .setAppId(PAY_APP_ID).setUserIp(getClientIP()) // Payment Application
                .setMerchantOrderId(String.valueOf(order.getId())) // Payment order number
                .setMerchantRefundId(refundId)
                .setReason("Want to refund money").setPrice(order.getPrice()));// Price information
        // 2.3 Update refund order to demo Order
        payDemoOrderMapper.updateById(new PayDemoOrderDO().setId(id)
                .setPayRefundId(payRefundId).setRefundPrice(order.getPrice()));
    }

    private PayDemoOrderDO validateDemoOrderCanRefund(Long id) {
        // Check if the order exists
        PayDemoOrderDO order = payDemoOrderMapper.selectById(id);
        if (order == null) {
            throw exception(DEMO_ORDER_NOT_FOUND);
        }
        // Check whether the order has been paid
        if (!order.getPayStatus()) {
            throw exception(DEMO_ORDER_REFUND_FAIL_NOT_PAID);
        }
        // Check if the order has been refunded
        if (order.getPayRefundId() != null) {
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUNDED);
        }
        return order;
    }

    @Override
    public void updateDemoOrderRefunded(Long id, Long payRefundId) {
        // 1. Verify and obtain refund order（Refundable）
        PayRefundRespDTO payRefund = validateDemoOrderCanRefunded(id, payRefundId);
        // 2.2 Update refund order to demo Order
        payDemoOrderMapper.updateById(new PayDemoOrderDO().setId(id)
                .setRefundTime(payRefund.getSuccessTime()));
    }

    private PayRefundRespDTO validateDemoOrderCanRefunded(Long id, Long payRefundId) {
        // 1.1 Verify sample order
        PayDemoOrderDO order = payDemoOrderMapper.selectById(id);
        if (order == null) {
            throw exception(DEMO_ORDER_NOT_FOUND);
        }
        // 1.2 Verify refund order matching
        if (Objects.equals(order.getPayRefundId(), payRefundId)) {
            log.error("[validateDemoOrderCanRefunded][order({}) Refund order does not match({})，Please process！order The data is：{}]",
                    id, payRefundId, toJsonString(order));
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_ORDER_ID_ERROR);
        }

        // 2.1 Verify refund order
        PayRefundRespDTO payRefund = payRefundApi.getRefund(payRefundId);
        if (payRefund == null) {
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_NOT_FOUND);
        }
        // 2.2
        if (!PayRefundStatusEnum.isSuccess(payRefund.getStatus())) {
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_NOT_SUCCESS);
        }
        // 2.3 Verify that the refund amount is consistent
        if (notEqual(payRefund.getRefundPrice(), order.getPrice())) {
            log.error("[validateDemoOrderCanRefunded][order({}) payRefund({}) Refund amount does not match，Please process！order The data is：{}，payRefund The data is：{}]",
                    id, payRefundId, toJsonString(order), toJsonString(payRefund));
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_PRICE_NOT_MATCH);
        }
        // 2.4 Verify refund order matching（Secondary）
        if (notEqual(payRefund.getMerchantOrderId(), id.toString())) {
            log.error("[validateDemoOrderCanRefunded][order({}) Refund order does not match({})，Please process！payRefund The data is：{}]",
                    id, payRefundId, toJsonString(payRefund));
            throw exception(DEMO_ORDER_REFUND_FAIL_REFUND_ORDER_ID_ERROR);
        }
        return payRefund;
    }

}
