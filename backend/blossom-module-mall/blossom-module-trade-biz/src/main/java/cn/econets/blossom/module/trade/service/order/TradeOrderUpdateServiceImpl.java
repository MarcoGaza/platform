package cn.econets.blossom.module.trade.service.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.module.member.api.address.MemberAddressApi;
import cn.econets.blossom.module.member.api.address.dto.MemberAddressRespDTO;
import cn.econets.blossom.module.pay.api.order.PayOrderApi;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderRespDTO;
import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import cn.econets.blossom.module.product.api.comment.ProductCommentApi;
import cn.econets.blossom.module.product.api.comment.dto.ProductCommentCreateReqDTO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderDeliveryReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderRemarkReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderUpdateAddressReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderUpdatePriceReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderSettlementReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderSettlementRespVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemCommentCreateReqVO;
import cn.econets.blossom.module.trade.convert.order.TradeOrderConvert;
import cn.econets.blossom.module.trade.dal.dataobject.cart.CartDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.dal.mysql.order.TradeOrderItemMapper;
import cn.econets.blossom.module.trade.dal.mysql.order.TradeOrderMapper;
import cn.econets.blossom.module.trade.dal.redis.no.TradeNoRedisDAO;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import cn.econets.blossom.module.trade.enums.order.*;
import cn.econets.blossom.module.trade.framework.order.config.TradeOrderProperties;
import cn.econets.blossom.module.trade.framework.order.core.annotations.TradeOrderLog;
import cn.econets.blossom.module.trade.framework.order.core.utils.TradeOrderLogUtils;
import cn.econets.blossom.module.trade.service.cart.CartService;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressService;
import cn.econets.blossom.module.trade.service.message.TradeMessageService;
import cn.econets.blossom.module.trade.service.message.bo.TradeOrderMessageWhenDeliveryOrderReqBO;
import cn.econets.blossom.module.trade.service.order.handler.TradeOrderHandler;
import cn.econets.blossom.module.trade.service.price.TradePriceService;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils.minusTime;
import static cn.econets.blossom.framework.common.util.servlet.ServletUtils.getClientIP;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getTerminal;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * Transaction Order【Write】Service Implementation class
 *
 */
@Service
@Slf4j
public class TradeOrderUpdateServiceImpl implements TradeOrderUpdateService {

    @Resource
    private TradeOrderMapper tradeOrderMapper;
    @Resource
    private TradeOrderItemMapper tradeOrderItemMapper;
    @Resource
    private TradeNoRedisDAO tradeNoRedisDAO;

    @Resource
    private List<TradeOrderHandler> tradeOrderHandlers;

    @Resource
    private CartService cartService;
    @Resource
    private TradePriceService tradePriceService;
    @Resource
    private DeliveryExpressService deliveryExpressService;
    @Resource
    private TradeMessageService tradeMessageService;

    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private MemberAddressApi addressApi;
    @Resource
    private ProductCommentApi productCommentApi;

    @Resource
    private TradeOrderProperties tradeOrderProperties;

    // =================== Order ===================

    @Override
    public AppTradeOrderSettlementRespVO settlementOrder(Long userId, AppTradeOrderSettlementReqVO settlementReqVO) {
        // 1. Get the delivery address
        MemberAddressRespDTO address = getAddress(userId, settlementReqVO.getAddressId());
        if (address != null) {
            settlementReqVO.setAddressId(address.getId());
        }

        // 2. Calculate price
        TradePriceCalculateRespBO calculateRespBO = calculatePrice(userId, settlementReqVO);

        // 3. Splicing returns
        return TradeOrderConvert.INSTANCE.convert(calculateRespBO, address);
    }

    /**
     * Get user address
     *
     * @param userId    User Number
     * @param addressId Address number
     * @return Address
     */
    private MemberAddressRespDTO getAddress(Long userId, Long addressId) {
        if (addressId != null) {
            return addressApi.getAddress(addressId, userId);
        }
        return addressApi.getDefaultAddress(userId);
    }

    /**
     * Calculate order price
     *
     * @param userId          User Number
     * @param settlementReqVO Settlement information
     * @return Order Price
     */
    private TradePriceCalculateRespBO calculatePrice(Long userId, AppTradeOrderSettlementReqVO settlementReqVO) {
        // 1. If from shopping cart，Get the items in the shopping cart
        List<CartDO> cartList = cartService.getCartList(userId,
                convertSet(settlementReqVO.getItems(), AppTradeOrderSettlementReqVO.Item::getCartId));

        // 2. Calculate price
        TradePriceCalculateReqBO calculateReqBO = TradeOrderConvert.INSTANCE.convert(userId, settlementReqVO, cartList);
        calculateReqBO.getItems().forEach(item -> Assert.isTrue(item.getSelected(), // Defensive Programming，Make sure they are all selected
                "Products({}) Not set to selected", item.getSkuId()));
        return tradePriceService.calculatePrice(calculateReqBO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_CREATE)
    public TradeOrderDO createOrder(Long userId, AppTradeOrderCreateReqVO createReqVO) {
        // 1.1 Price calculation
        TradePriceCalculateRespBO calculateRespBO = calculatePrice(userId, createReqVO);
        // 1.2 Build order
        TradeOrderDO order = buildTradeOrder(userId, createReqVO, calculateRespBO);
        List<TradeOrderItemDO> orderItems = buildTradeOrderItems(order, calculateRespBO);

        // 2. Logic before order creation
        tradeOrderHandlers.forEach(handler -> handler.beforeOrderCreate(order, orderItems));

        // 3. Save order
        tradeOrderMapper.insert(order);
        orderItems.forEach(orderItem -> orderItem.setOrderId(order.getId()));
        tradeOrderItemMapper.insertBatch(orderItems);

        // 4. Logic after order creation
        afterCreateTradeOrder(order, orderItems, createReqVO);
        return order;
    }

    private TradeOrderDO buildTradeOrder(Long userId, AppTradeOrderCreateReqVO createReqVO,
                                         TradePriceCalculateRespBO calculateRespBO) {
        TradeOrderDO order = TradeOrderConvert.INSTANCE.convert(userId, createReqVO, calculateRespBO);
        order.setType(calculateRespBO.getType());
        order.setNo(tradeNoRedisDAO.generate(TradeNoRedisDAO.TRADE_ORDER_NO_PREFIX));
        order.setStatus(TradeOrderStatusEnum.UNPAID.getStatus());
        order.setRefundStatus(TradeOrderRefundStatusEnum.NONE.getStatus());
        order.setProductCount(getSumValue(calculateRespBO.getItems(), TradePriceCalculateRespBO.OrderItem::getCount, Integer::sum));
        order.setUserIp(getClientIP()).setTerminal(getTerminal());
        // Payment + Refund information
        order.setAdjustPrice(0).setPayStatus(false);
        order.setRefundStatus(TradeOrderRefundStatusEnum.NONE.getStatus()).setRefundPrice(0);
        // Logistics Information
        order.setDeliveryType(createReqVO.getDeliveryType());
        if (Objects.equals(createReqVO.getDeliveryType(), DeliveryTypeEnum.EXPRESS.getType())) {
            MemberAddressRespDTO address = addressApi.getAddress(createReqVO.getAddressId(), userId);
            Assert.notNull(address, "Address({}) Cannot be empty", createReqVO.getAddressId()); // When calculating price，Already calculated
            order.setReceiverName(address.getName()).setReceiverMobile(address.getMobile())
                    .setReceiverAreaId(address.getAreaId()).setReceiverDetailAddress(address.getDetailAddress());
        } else if (Objects.equals(createReqVO.getDeliveryType(), DeliveryTypeEnum.PICK_UP.getType())) {
            order.setReceiverName(createReqVO.getReceiverName()).setReceiverMobile(createReqVO.getReceiverMobile());
            order.setPickUpVerifyCode(RandomUtil.randomNumbers(8)); // A random verification code，Length is 8 position
        }
        return order;
    }

    private List<TradeOrderItemDO> buildTradeOrderItems(TradeOrderDO tradeOrderDO,
                                                        TradePriceCalculateRespBO calculateRespBO) {
        return TradeOrderConvert.INSTANCE.convertList(tradeOrderDO, calculateRespBO);
    }

    /**
     * After order creation，Execute post logic
     * <p>
     * For example：Coupon deduction、Points deduction、Creation of payment orders, etc.
     *
     * @param order       Order
     * @param orderItems  Order Item
     * @param createReqVO Create order request
     */
    private void afterCreateTradeOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems,
                                       AppTradeOrderCreateReqVO createReqVO) {
        // 1. Execute order creation postprocessor
        tradeOrderHandlers.forEach(handler -> handler.afterOrderCreate(order, orderItems));

        // 2. Delete shopping cart items
        Set<Long> cartIds = convertSet(createReqVO.getItems(), AppTradeOrderSettlementReqVO.Item::getCartId);
        if (CollUtil.isNotEmpty(cartIds)) {
            cartService.deleteCart(order.getUserId(), cartIds);
        }

        // 3. Generate prepayment
        createPayOrder(order, orderItems);

        // 4. Insert order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), null, order.getStatus());

        // TODO LeeYan9: You can think about it, Marketing discount records of orders, Where should it be recorded?, Discuss on WeChat!
    }

    private void createPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // Create payment order，For subsequent payment
        PayOrderCreateReqDTO payOrderCreateReqDTO = TradeOrderConvert.INSTANCE.convert(
                order, orderItems, tradeOrderProperties);
        Long payOrderId = payOrderApi.createOrder(payOrderCreateReqDTO);

        // Update to the transaction list
        tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId()).setPayOrderId(payOrderId));
        order.setPayOrderId(payOrderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_PAY)
    public void updateOrderPaid(Long id, Long payOrderId) {
        // 1. Verify and obtain transaction orders（Available for payment）
        KeyValue<TradeOrderDO, PayOrderRespDTO> orderResult = validateOrderPayable(id, payOrderId);
        TradeOrderDO order = orderResult.getKey();
        PayOrderRespDTO payOrder = orderResult.getValue();

        // 2. Update TradeOrderDO Status is paid，Waiting for delivery
        int updateCount = tradeOrderMapper.updateByIdAndStatus(id, order.getStatus(),
                new TradeOrderDO().setStatus(TradeOrderStatusEnum.UNDELIVERED.getStatus()).setPayStatus(true)
                        .setPayTime(LocalDateTime.now()).setPayChannelCode(payOrder.getChannelCode()));
        if (updateCount == 0) {
            throw exception(ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }

        // 3. Execute TradeOrderHandler Post-processing
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(id);
        tradeOrderHandlers.forEach(handler -> handler.afterPayOrder(order, orderItems));

        // 4. Record order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), TradeOrderStatusEnum.UNDELIVERED.getStatus());
        TradeOrderLogUtils.setUserInfo(order.getUserId(), UserTypeEnum.MEMBER.getValue());
    }

    /**
     * Verify that the transaction order meets the conditions for being paid
     * <p>
     * 1. Transaction order not paid
     * 2. The payment order has been paid
     *
     * @param id         Transaction order number
     * @param payOrderId Payment order number
     * @return Transaction Order
     */
    private KeyValue<TradeOrderDO, PayOrderRespDTO> validateOrderPayable(Long id, Long payOrderId) {
        // Check if the order exists
        TradeOrderDO order = validateOrderExists(id);
        // Verification order not paid
        if (!TradeOrderStatusEnum.isUnpaid(order.getStatus()) || order.getPayStatus()) {
            log.error("[validateOrderPaid][order({}) Not in payment state，Please process！order The data is：{}]",
                    id, JsonUtils.toJsonString(order));
            throw exception(ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
        // Check payment order matching
        if (ObjectUtil.notEqual(order.getPayOrderId(), payOrderId)) { // Payment order number
            log.error("[validateOrderPaid][order({}) Payment order does not match({})，Please process！order The data is：{}]",
                    id, payOrderId, JsonUtils.toJsonString(order));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        // Check if the payment slip exists
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId);
        if (payOrder == null) {
            log.error("[validateOrderPaid][order({}) payOrder({}) Does not exist，Please process！]", id, payOrderId);
            throw exception(ORDER_NOT_FOUND);
        }
        // Verify that the payment order has been paid
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validateOrderPaid][order({}) payOrder({}) Not paid，Please process！payOrder The data is：{}]",
                    id, payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // Verify payment amount is consistent
        if (ObjectUtil.notEqual(payOrder.getPrice(), order.getPayPrice())) {
            log.error("[validateOrderPaid][order({}) payOrder({}) Payment amount does not match，Please process！order The data is：{}，payOrder The data is：{}]",
                    id, payOrderId, JsonUtils.toJsonString(order), JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH);
        }
        // Check payment order matching（Secondary）
        if (ObjectUtil.notEqual(payOrder.getMerchantOrderId(), id.toString())) {
            log.error("[validateOrderPaid][order({}) Payment order does not match({})，Please process！payOrder The data is：{}]",
                    id, payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }
        return new KeyValue<>(order, payOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_DELIVERY)
    public void deliveryOrder(TradeOrderDeliveryReqVO deliveryReqVO) {
        // 1.1 Verify and obtain transaction orders（Available for delivery）
        TradeOrderDO order = validateOrderDeliverable(deliveryReqVO.getId());
        // 1.2 Verification deliveryType Is it express delivery?，Only express delivery can deliver
        if (ObjectUtil.notEqual(order.getDeliveryType(), DeliveryTypeEnum.EXPRESS.getType())) {
            throw exception(ORDER_DELIVERY_FAIL_DELIVERY_TYPE_NOT_EXPRESS);
        }

        // 2. Update order to shipped
        TradeOrderDO updateOrderObj = new TradeOrderDO();
        // 2.1 Express delivery
        DeliveryExpressDO express = null;
        if (ObjectUtil.notEqual(deliveryReqVO.getLogisticsId(), TradeOrderDO.LOGISTICS_ID_NULL)) {
            express = deliveryExpressService.validateDeliveryExpress(deliveryReqVO.getLogisticsId());
            updateOrderObj.setLogisticsId(deliveryReqVO.getLogisticsId()).setLogisticsNo(deliveryReqVO.getLogisticsNo());
        } else {
            // 2.2 No need to ship
            updateOrderObj.setLogisticsId(0L).setLogisticsNo("");
        }
        // Perform update
        updateOrderObj.setStatus(TradeOrderStatusEnum.DELIVERED.getStatus()).setDeliveryTime(LocalDateTime.now());
        int updateCount = tradeOrderMapper.updateByIdAndStatus(order.getId(), order.getStatus(), updateOrderObj);
        if (updateCount == 0) {
            throw exception(ORDER_DELIVERY_FAIL_STATUS_NOT_UNDELIVERED);
        }

        // 3. Record order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), TradeOrderStatusEnum.DELIVERED.getStatus(),
                MapUtil.<String, Object>builder().put("deliveryName", express != null ? express.getName() : "")
                        .put("logisticsNo", express != null ? deliveryReqVO.getLogisticsNo() : "").build());

        // 4. Send internal message
        tradeMessageService.sendMessageWhenDeliveryOrder(new TradeOrderMessageWhenDeliveryOrderReqBO()
                .setOrderId(order.getId()).setUserId(order.getUserId()).setMessage(null));
    }

    /**
     * Verify that the transaction order meets the conditions for being shipped
     * <p>
     * 1. Transaction order not shipped
     *
     * @param id Transaction order number
     * @return Transaction Order
     */
    private TradeOrderDO validateOrderDeliverable(Long id) {
        TradeOrderDO order = validateOrderExists(id);
        // 1. Check if the order has not been shipped
        if (ObjectUtil.notEqual(TradeOrderRefundStatusEnum.NONE.getStatus(), order.getRefundStatus())) {
            throw exception(ORDER_DELIVERY_FAIL_REFUND_STATUS_NOT_NONE);
        }

        // 2. Execute TradeOrderHandler Pre-processing
        tradeOrderHandlers.forEach(handler -> handler.beforeDeliveryOrder(order));
        return order;
    }

    @NotNull
    private TradeOrderDO validateOrderExists(Long id) {
        // Check if the order exists
        TradeOrderDO order = tradeOrderMapper.selectById(id);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_RECEIVE)
    public void receiveOrderByMember(Long userId, Long id) {
        // Verify and obtain transaction orders（Can receive goods）
        TradeOrderDO order = validateOrderReceivable(userId, id);

        // Receipt order
        receiveOrder0(order);
    }

    @Override
    public int receiveOrderBySystem() {
        // 1. Query expired orders to be paid
        LocalDateTime expireTime = minusTime(tradeOrderProperties.getReceiveExpireTime());
        List<TradeOrderDO> orders = tradeOrderMapper.selectListByStatusAndDeliveryTimeLt(
                TradeOrderStatusEnum.DELIVERED.getStatus(), expireTime);
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }

        // 2. Traversal execution，Cancel one by one
        int count = 0;
        for (TradeOrderDO order : orders) {
            try {
                getSelf().receiveOrderBySystem(order);
                count++;
            } catch (Throwable e) {
                log.error("[receiveOrderBySystem][order({}) Automatic delivery order exception]", order.getId(), e);
            }
        }
        return count;
    }

    /**
     * Automatically receive a single order
     *
     * @param order Order
     */
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.SYSTEM_RECEIVE)
    public void receiveOrderBySystem(TradeOrderDO order) {
        receiveOrder0(order);
    }

    /**
     * Core implementation of receiving orders
     *
     * @param order Order
     */
    private void receiveOrder0(TradeOrderDO order) {
        // Update TradeOrderDO The status is completed
        int updateCount = tradeOrderMapper.updateByIdAndStatus(order.getId(), order.getStatus(),
                new TradeOrderDO().setStatus(TradeOrderStatusEnum.COMPLETED.getStatus()).setReceiveTime(LocalDateTime.now()));
        if (updateCount == 0) {
            throw exception(ORDER_RECEIVE_FAIL_STATUS_NOT_DELIVERED);
        }

        // Insert order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), TradeOrderStatusEnum.COMPLETED.getStatus());
    }

    /**
     * Verify that the transaction order meets the conditions for selling goods
     * <p>
     * 1. Transaction order awaiting receipt
     *
     * @param userId User Number
     * @param id     Transaction order number
     * @return Transaction Order
     */
    private TradeOrderDO validateOrderReceivable(Long userId, Long id) {
        // Check if the order exists
        TradeOrderDO order = tradeOrderMapper.selectByIdAndUserId(id, userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // Check whether the order is in the waiting-for-receipt state
        if (!TradeOrderStatusEnum.isDelivered(order.getStatus())) {
            throw exception(ORDER_RECEIVE_FAIL_STATUS_NOT_DELIVERED);
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_CANCEL)
    public void cancelOrderByMember(Long userId, Long id) {
        // 1.1 Check existence
        TradeOrderDO order = tradeOrderMapper.selectOrderByIdAndUserId(id, userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // 1.2 Verification status
        if (ObjectUtil.notEqual(order.getStatus(), TradeOrderStatusEnum.UNPAID.getStatus())) {
            throw exception(ORDER_CANCEL_FAIL_STATUS_NOT_UNPAID);
        }

        // 2. Cancel order
        cancelOrder0(order, TradeOrderCancelTypeEnum.MEMBER_CANCEL);
    }

    @Override
    public int cancelOrderBySystem() {
        // 1. Query expired orders to be paid
        LocalDateTime expireTime = minusTime(tradeOrderProperties.getPayExpireTime());
        List<TradeOrderDO> orders = tradeOrderMapper.selectListByStatusAndCreateTimeLt(
                TradeOrderStatusEnum.UNPAID.getStatus(), expireTime);
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }

        // 2. Traversal execution，Cancel one by one
        int count = 0;
        for (TradeOrderDO order : orders) {
            try {
                getSelf().cancelOrderBySystem(order);
                count++;
            } catch (Throwable e) {
                log.error("[cancelOrderBySystem][order({}) Expired order exception]", order.getId(), e);
            }
        }
        return count;
    }

    /**
     * Automatically cancel a single order
     *
     * @param order Order
     */
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.SYSTEM_CANCEL)
    public void cancelOrderBySystem(TradeOrderDO order) {
        cancelOrder0(order, TradeOrderCancelTypeEnum.PAY_TIMEOUT);
    }

    /**
     * Core implementation of canceling an order
     *
     * @param order      Order
     * @param cancelType Cancel type
     */
    private void cancelOrder0(TradeOrderDO order, TradeOrderCancelTypeEnum cancelType) {
        // 1. Update TradeOrderDO The status is canceled
        int updateCount = tradeOrderMapper.updateByIdAndStatus(order.getId(), order.getStatus(),
                new TradeOrderDO().setStatus(TradeOrderStatusEnum.CANCELED.getStatus())
                        .setCancelType(cancelType.getType()).setCancelTime(LocalDateTime.now()));
        if (updateCount == 0) {
            throw exception(ORDER_CANCEL_FAIL_STATUS_NOT_UNPAID);
        }

        // 2. Execute TradeOrderHandler Post-processing
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(order.getId());
        tradeOrderHandlers.forEach(handler -> handler.afterCancelOrder(order, orderItems));

        // 3. Add order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), TradeOrderStatusEnum.CANCELED.getStatus());
    }

    /**
     * If the amount is fully refunded，Cancel the order
     * If there is still unrefundable amount，No need to cancel the order
     *
     * @param order       Order
     * @param refundPrice Refund amount
     */
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_CANCEL_AFTER_SALE)
    public void cancelOrderByAfterSale(TradeOrderDO order, Integer refundPrice) {
        // 1. Update order
        if (refundPrice < order.getPayPrice()) {
            return;
        }
        tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId())
                .setStatus(TradeOrderStatusEnum.CANCELED.getStatus())
                .setCancelType(TradeOrderCancelTypeEnum.AFTER_SALE_CLOSE.getType()).setCancelTime(LocalDateTime.now()));

        // 2. Execute TradeOrderHandler Post-processing
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(order.getId());
        tradeOrderHandlers.forEach(handler -> handler.afterCancelOrder(order, orderItems));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_DELETE)
    public void deleteOrder(Long userId, Long id) {
        // 1.1 Check existence
        TradeOrderDO order = tradeOrderMapper.selectOrderByIdAndUserId(id, userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // 1.2 Verification status
        if (ObjectUtil.notEqual(order.getStatus(), TradeOrderStatusEnum.CANCELED.getStatus())) {
            throw exception(ORDER_DELETE_FAIL_STATUS_NOT_CANCEL);
        }
        // 2. Delete order
        tradeOrderMapper.deleteById(id);

        // 3. Record log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), order.getStatus());
    }

    @Override
    public void updateOrderRemark(TradeOrderRemarkReqVO reqVO) {
        // Verify and obtain transaction orders
        validateOrderExists(reqVO.getId());

        // Update
        TradeOrderDO order = TradeOrderConvert.INSTANCE.convert(reqVO);
        tradeOrderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_UPDATE_PRICE)
    public void updateOrderPrice(TradeOrderUpdatePriceReqVO reqVO) {
        // 1.1 Verify transaction order
        TradeOrderDO order = validateOrderExists(reqVO.getId());
        if (order.getPayStatus()) {
            throw exception(ORDER_UPDATE_PRICE_FAIL_PAID);
        }
        // 1.2 Check whether the price adjustment amount has changed
        if (order.getAdjustPrice() > 0) {
            throw exception(ORDER_UPDATE_PRICE_FAIL_ALREADY);
        }
        // 1.3 The payment price cannot be 0
        int newPayPrice = order.getPayPrice() + order.getAdjustPrice();
        if (newPayPrice <= 0) {
            throw exception(ORDER_UPDATE_PRICE_FAIL_PRICE_ERROR);
        }

        // 2. Update order
        tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId())
                .setAdjustPrice(reqVO.getAdjustPrice()).setPayPrice(newPayPrice));

        // 3. Update TradeOrderItem，Need to do adjustPrice Sharing
        List<TradeOrderItemDO> orderOrderItems = tradeOrderItemMapper.selectListByOrderId(order.getId());
        List<Integer> dividePrices = TradePriceCalculatorHelper.dividePrice2(orderOrderItems, newPayPrice);
        List<TradeOrderItemDO> updateItems = new ArrayList<>();
        for (int i = 0; i < orderOrderItems.size(); i++) {
            TradeOrderItemDO item = orderOrderItems.get(i);
            updateItems.add(new TradeOrderItemDO().setId(item.getId()).setAdjustPrice(dividePrices.get(i))
                    .setPayPrice(item.getPayPrice() + dividePrices.get(i)));
        }
        tradeOrderItemMapper.updateBatch(updateItems);

        // 4. Update payment order
        payOrderApi.updatePayOrderPrice(order.getPayOrderId(), newPayPrice);

        // 5. Record order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), order.getStatus(),
                MapUtil.<String, Object>builder().put("oldPayPrice", MoneyUtils.fenToYuanStr(order.getPayPrice()))
                        .put("newPayPrice", MoneyUtils.fenToYuanStr(newPayPrice)).build());
    }

    @Override
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_UPDATE_ADDRESS)
    public void updateOrderAddress(TradeOrderUpdateAddressReqVO reqVO) {
        // Verify transaction order
        TradeOrderDO order = validateOrderExists(reqVO.getId());
        // Only waiting for delivery，Only then can you modify the order delivery address；
        if (!TradeOrderStatusEnum.isUndelivered(order.getStatus())) {
            throw exception(ORDER_UPDATE_ADDRESS_FAIL_STATUS_NOT_DELIVERED);
        }

        // Update
        tradeOrderMapper.updateById(TradeOrderConvert.INSTANCE.convert(reqVO));

        // Record order log
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), order.getStatus());
    }

    @Override
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_PICK_UP_RECEIVE)
    public void pickUpOrderByAdmin(Long id) {
        getSelf().pickUpOrder(tradeOrderMapper.selectById(id));
    }

    @Override
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.ADMIN_PICK_UP_RECEIVE)
    public void pickUpOrderByAdmin(String pickUpVerifyCode) {
        getSelf().pickUpOrder(tradeOrderMapper.selectOneByPickUpVerifyCode(pickUpVerifyCode));
    }

    @Override
    public TradeOrderDO getByPickUpVerifyCode(String pickUpVerifyCode) {
        return tradeOrderMapper.selectOneByPickUpVerifyCode(pickUpVerifyCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void pickUpOrder(TradeOrderDO order) {
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        if (ObjUtil.notEqual(DeliveryTypeEnum.PICK_UP.getType(), order.getDeliveryType())) {
            throw exception(ORDER_RECEIVE_FAIL_DELIVERY_TYPE_NOT_PICK_UP);
        }
        receiveOrder0(order);
    }

    // =================== Order Item ===================

    @Override
    public void updateOrderItemWhenAfterSaleCreate(Long id, Long afterSaleId) {
        // Update order items
        updateOrderItemAfterSaleStatus(id, TradeOrderItemAfterSaleStatusEnum.NONE.getStatus(),
                TradeOrderItemAfterSaleStatusEnum.APPLY.getStatus(), afterSaleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderItemWhenAfterSaleSuccess(Long id, Integer refundPrice) {
        // 1.1 Update order items
        updateOrderItemAfterSaleStatus(id, TradeOrderItemAfterSaleStatusEnum.APPLY.getStatus(),
                TradeOrderItemAfterSaleStatusEnum.SUCCESS.getStatus(), null);
        // 1.2 Execute TradeOrderHandler Post-processing
        TradeOrderItemDO orderItem = tradeOrderItemMapper.selectById(id);
        TradeOrderDO order = tradeOrderMapper.selectById(orderItem.getOrderId());
        tradeOrderHandlers.forEach(handler -> handler.afterCancelOrderItem(order, orderItem));

        // 2.1 Update the refund amount of the order、Points
        Integer orderRefundPrice = order.getRefundPrice() + refundPrice;
        Integer orderRefundPoint = order.getRefundPoint() + orderItem.getUsePoint();
        Integer refundStatus = isAllOrderItemAfterSaleSuccess(order.getId()) ?
                TradeOrderRefundStatusEnum.ALL.getStatus() // If all after-sales are successful，You need to cancel the order
                : TradeOrderRefundStatusEnum.PART.getStatus();
        tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId())
                .setRefundStatus(refundStatus)
                .setRefundPrice(orderRefundPrice).setRefundPoint(orderRefundPoint));
        // 2.2 If all refunds are made，Cancel the order
        getSelf().cancelOrderByAfterSale(order, orderRefundPrice);
    }

    @Override
    public void updateOrderItemWhenAfterSaleCancel(Long id) {
        // Update order items
        updateOrderItemAfterSaleStatus(id, TradeOrderItemAfterSaleStatusEnum.APPLY.getStatus(),
                TradeOrderItemAfterSaleStatusEnum.NONE.getStatus(), null);
    }

    private void updateOrderItemAfterSaleStatus(Long id, Integer oldAfterSaleStatus, Integer newAfterSaleStatus,
                                                Long afterSaleId) {
        // Update order items
        int updateCount = tradeOrderItemMapper.updateAfterSaleStatus(id, oldAfterSaleStatus, newAfterSaleStatus, afterSaleId);
        if (updateCount <= 0) {
            throw exception(ORDER_ITEM_UPDATE_AFTER_SALE_STATUS_FAIL);
        }

    }

    /**
     * Judge all order items of a specified order，Are all after-sales services successful?
     *
     * @param id Order number
     * @return Are all after-sales services successful?
     */
    private boolean isAllOrderItemAfterSaleSuccess(Long id) {
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(id);
        return orderItems.stream().allMatch(orderItem -> Objects.equals(orderItem.getAfterSaleStatus(),
                TradeOrderItemAfterSaleStatusEnum.SUCCESS.getStatus()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.MEMBER_COMMENT)
    public Long createOrderItemCommentByMember(Long userId, AppTradeOrderItemCommentCreateReqVO createReqVO) {
        // 1.1 Pass order items first ID，Check if the order item exists
        TradeOrderItemDO orderItem = tradeOrderItemMapper.selectByIdAndUserId(createReqVO.getOrderItemId(), userId);
        if (orderItem == null) {
            throw exception(ORDER_ITEM_NOT_FOUND);
        }
        // 1.2 Check order status
        TradeOrderDO order = tradeOrderMapper.selectOrderByIdAndUserId(orderItem.getOrderId(), userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(order.getStatus(), TradeOrderStatusEnum.COMPLETED.getStatus())) {
            throw exception(ORDER_COMMENT_FAIL_STATUS_NOT_COMPLETED);
        }
        if (ObjectUtil.notEqual(order.getCommentStatus(), Boolean.FALSE)) {
            throw exception(ORDER_COMMENT_STATUS_NOT_FALSE);
        }

        // 2. Create a review
        Long commentId = createOrderItemComment0(orderItem, createReqVO);

        // 3. If all line items are commented on，Update the order evaluation status
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderId(order.getId());
        if (!anyMatch(orderItems, item -> Objects.equals(item.getCommentStatus(), Boolean.FALSE))) {
            tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId()).setCommentStatus(Boolean.TRUE)
                    .setFinishTime(LocalDateTime.now()));
            // Add order log。Attention：Only after all line items have been evaluated，will increase
            TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), order.getStatus());
        }
        return commentId;
    }

    @Override
    public int createOrderItemCommentBySystem() {
        // 1. Query expired orders to be paid
        LocalDateTime expireTime = minusTime(tradeOrderProperties.getCommentExpireTime());
        List<TradeOrderDO> orders = tradeOrderMapper.selectListByStatusAndReceiveTimeLt(
                TradeOrderStatusEnum.COMPLETED.getStatus(), expireTime, false);
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }

        // 2. Traversal execution，Cancel one by one
        int count = 0;
        for (TradeOrderDO order : orders) {
            try {
                getSelf().createOrderItemCommentBySystemBySystem(order);
                count++;
            } catch (Throwable e) {
                log.error("[createOrderItemCommentBySystem][order({}) Expired order exception]", order.getId(), e);
            }
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderCombinationInfo(Long orderId, Long activityId, Long combinationRecordId, Long headId) {
        tradeOrderMapper.updateById(
                new TradeOrderDO().setId(orderId).setCombinationActivityId(activityId)
                        .setCombinationRecordId(combinationRecordId).setCombinationHeadId(headId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPaidOrder(Long userId, Long orderId) {
        // TODO This implementation needs to be optimized；
        TradeOrderDO order = tradeOrderMapper.selectOrderByIdAndUserId(orderId, userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        cancelOrder0(order, TradeOrderCancelTypeEnum.MEMBER_CANCEL);
    }

    /**
     * Create a comment for a single order
     *
     * @param order Order
     */
    @Transactional(rollbackFor = Exception.class)
    @TradeOrderLog(operateType = TradeOrderOperateTypeEnum.SYSTEM_COMMENT)
    public void createOrderItemCommentBySystemBySystem(TradeOrderDO order) {
        // 1. Query uncommented order items
        List<TradeOrderItemDO> orderItems = tradeOrderItemMapper.selectListByOrderIdAndCommentStatus(
                order.getId(), Boolean.FALSE);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }

        // 2. Comments one by one
        for (TradeOrderItemDO orderItem : orderItems) {
            // 2.1 Create a review
            AppTradeOrderItemCommentCreateReqVO commentCreateReqVO = new AppTradeOrderItemCommentCreateReqVO()
                    .setOrderItemId(orderItem.getId()).setAnonymous(false).setContent("")
                    .setBenefitScores(5).setDescriptionScores(5);
            createOrderItemComment0(orderItem, commentCreateReqVO);

            // 2.2 Update line item evaluation status
            tradeOrderItemMapper.updateById(new TradeOrderItemDO().setId(orderItem.getId()).setCommentStatus(Boolean.TRUE));
        }

        // 3. All line items have comments，Update order evaluation status
        tradeOrderMapper.updateById(new TradeOrderDO().setId(order.getId()).setCommentStatus(Boolean.TRUE)
                .setFinishTime(LocalDateTime.now()));
        // Add order log。Attention：Only after all line items have been evaluated，will increase
        TradeOrderLogUtils.setOrderInfo(order.getId(), order.getStatus(), order.getStatus());
    }

    /**
     * Core implementation for creating comments for order items
     *
     * @param orderItem   Order Item
     * @param createReqVO Comment content
     * @return Comment number
     */
    private Long createOrderItemComment0(TradeOrderItemDO orderItem, AppTradeOrderItemCommentCreateReqVO createReqVO) {
        // 1. Create a review
        ProductCommentCreateReqDTO productCommentCreateReqDTO = TradeOrderConvert.INSTANCE.convert04(createReqVO, orderItem);
        Long commentId = productCommentApi.createComment(productCommentCreateReqDTO);

        // 2. Update line item evaluation status
        tradeOrderItemMapper.updateById(new TradeOrderItemDO().setId(orderItem.getId()).setCommentStatus(Boolean.TRUE));
        return commentId;
    }

    // =================== Marketing related operations ===================

    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private TradeOrderUpdateServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
