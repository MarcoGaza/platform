package cn.econets.blossom.module.trade.service.aftersale;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.module.pay.api.refund.PayRefundApi;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSaleDisagreeReqVO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSalePageReqVO;
import cn.econets.blossom.module.trade.controller.admin.aftersale.vo.AfterSaleRefuseReqVO;
import cn.econets.blossom.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.aftersale.vo.AppAfterSaleDeliveryReqVO;
import cn.econets.blossom.module.trade.convert.aftersale.AfterSaleConvert;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.dal.mysql.aftersale.AfterSaleMapper;
import cn.econets.blossom.module.trade.dal.redis.no.TradeNoRedisDAO;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleOperateTypeEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleStatusEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleTypeEnum;
import cn.econets.blossom.module.trade.enums.aftersale.AfterSaleWayEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderItemAfterSaleStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.framework.aftersale.core.annotations.AfterSaleLog;
import cn.econets.blossom.module.trade.framework.aftersale.core.utils.AfterSaleLogUtils;
import cn.econets.blossom.module.trade.framework.order.config.TradeOrderProperties;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressService;
import cn.econets.blossom.module.trade.service.order.TradeOrderQueryService;
import cn.econets.blossom.module.trade.service.order.TradeOrderUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * After-sales order Service Implementation class
 *
 */
@Slf4j
@Service
@Validated
public class AfterSaleServiceImpl implements AfterSaleService {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private DeliveryExpressService deliveryExpressService;

    @Resource
    private AfterSaleMapper tradeAfterSaleMapper;
    @Resource
    private TradeNoRedisDAO tradeNoRedisDAO;

    @Resource
    private PayRefundApi payRefundApi;

    @Resource
    private TradeOrderProperties tradeOrderProperties;

    @Override
    public PageResult<AfterSaleDO> getAfterSalePage(AfterSalePageReqVO pageReqVO) {
        return tradeAfterSaleMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<AfterSaleDO> getAfterSalePage(Long userId, PageParam pageParam) {
        return tradeAfterSaleMapper.selectPage(userId, pageParam);
    }

    @Override
    public AfterSaleDO getAfterSale(Long userId, Long id) {
        return tradeAfterSaleMapper.selectByIdAndUserId(id, userId);
    }

    @Override
    public AfterSaleDO getAfterSale(Long id) {
        return tradeAfterSaleMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_CREATE)
    public Long createAfterSale(Long userId, AppAfterSaleCreateReqVO createReqVO) {
        // First step，Pre-check
        TradeOrderItemDO tradeOrderItem = validateOrderItemApplicable(userId, createReqVO);

        // Step 2，Store after-sales orders
        AfterSaleDO afterSale = createAfterSale(createReqVO, tradeOrderItem);
        return afterSale.getId();
    }

    /**
     * Check whether the transaction order item can apply for after-sales service
     *
     * @param userId      User Number
     * @param createReqVO After-sales creation information
     * @return Transaction order item
     */
    private TradeOrderItemDO validateOrderItemApplicable(Long userId, AppAfterSaleCreateReqVO createReqVO) {
        // Verify order item exists
        TradeOrderItemDO orderItem = tradeOrderQueryService.getOrderItem(userId, createReqVO.getOrderItemId());
        if (orderItem == null) {
            throw exception(ORDER_ITEM_NOT_FOUND);
        }
        // After-sales service has been applied for，No more after-sales applications are allowed
        if (!TradeOrderItemAfterSaleStatusEnum.isNone(orderItem.getAfterSaleStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_ITEM_APPLIED);
        }
        // Amount of refund requested，Cannot exceed the price of the product
        if (createReqVO.getRefundPrice() > orderItem.getPayPrice()) {
            throw exception(AFTER_SALE_CREATE_FAIL_REFUND_PRICE_ERROR);
        }

        // Verify order exists
        TradeOrderDO order = tradeOrderQueryService.getOrder(userId, orderItem.getOrderId());
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // TODO Exceeding a certain time，After-sales service is not allowed
        // Cancelled，Unable to initiate after-sales service
        if (TradeOrderStatusEnum.isCanceled(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_CANCELED);
        }
        // Not paid，Unable to initiate after-sales service
        if (!TradeOrderStatusEnum.havePaid(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_PAID);
        }
        // If yes【Return and Refund】Situation，Additional verification is required to determine whether the shipment is made
        if (createReqVO.getWay().equals(AfterSaleWayEnum.RETURN_AND_REFUND.getWay())
                && !TradeOrderStatusEnum.haveDelivered(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_DELIVERED);
        }
        return orderItem;
    }

    private AfterSaleDO createAfterSale(AppAfterSaleCreateReqVO createReqVO,
                                        TradeOrderItemDO orderItem) {
        // Create after-sales order
        AfterSaleDO afterSale = AfterSaleConvert.INSTANCE.convert(createReqVO, orderItem);
        afterSale.setNo(tradeNoRedisDAO.generate(TradeNoRedisDAO.AFTER_SALE_NO_PREFIX));
        afterSale.setStatus(AfterSaleStatusEnum.APPLY.getStatus());
        // Mark whether it is pre-sale or after-sale
        TradeOrderDO order = tradeOrderQueryService.getOrder(orderItem.getUserId(), orderItem.getOrderId());
        afterSale.setOrderNo(order.getNo()); // Record orderNo Order flow，Convenient for subsequent retrieval
        afterSale.setType(TradeOrderStatusEnum.isCompleted(order.getStatus())
                ? AfterSaleTypeEnum.AFTER_SALE.getType() : AfterSaleTypeEnum.IN_SALE.getType());
        tradeAfterSaleMapper.insert(afterSale);

        // Update the after-sales status of the transaction order item
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCreate(orderItem.getId(), afterSale.getId());

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), null,
                AfterSaleStatusEnum.APPLY.getStatus());

        // TODO Send after-sales message
        return afterSale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_AGREE_APPLY)
    public void agreeAfterSale(Long userId, Long id) {
        // Verify the existence of after-sales order，Status not approved
        AfterSaleDO afterSale = validateAfterSaleAuditable(id);

        // Update the status of after-sales order
        // Situation 1：Refund：Mark as WAIT_REFUND Status。After the refund is successfully initiated，In the mark COMPLETE Status
        // Situation 2：Return and Refund：Need to wait for the user to return the product，Can initiate a refund
        Integer newStatus = afterSale.getWay().equals(AfterSaleWayEnum.REFUND.getWay()) ?
                AfterSaleStatusEnum.WAIT_REFUND.getStatus() : AfterSaleStatusEnum.SELLER_AGREE.getStatus();
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.APPLY.getStatus(), new AfterSaleDO()
                .setStatus(newStatus).setAuditUserId(userId).setAuditTime(LocalDateTime.now()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(), newStatus);

        // TODO Send after-sales message
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_DISAGREE_APPLY)
    public void disagreeAfterSale(Long userId, AfterSaleDisagreeReqVO auditReqVO) {
        // Verify the existence of after-sales order，Status not approved
        AfterSaleDO afterSale = validateAfterSaleAuditable(auditReqVO.getId());

        // Update the status of after-sales order
        Integer newStatus = AfterSaleStatusEnum.SELLER_DISAGREE.getStatus();
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.APPLY.getStatus(), new AfterSaleDO()
                .setStatus(newStatus).setAuditUserId(userId).setAuditTime(LocalDateTime.now())
                .setAuditReason(auditReqVO.getAuditReason()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(), newStatus);

        // TODO Send after-sales message

        // Update the after-sales status of the transaction order item to【Not applied for】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    /**
     * Check whether the after-sales order is approved（Agree to after-sales service、Refuse after-sales service）
     *
     * @param id After-sales number
     * @return After-sales order
     */
    private AfterSaleDO validateAfterSaleAuditable(Long id) {
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.APPLY.getStatus())) {
            throw exception(AFTER_SALE_AUDIT_FAIL_STATUS_NOT_APPLY);
        }
        return afterSale;
    }

    private void updateAfterSaleStatus(Long id, Integer status, AfterSaleDO updateObj) {
        int updateCount = tradeAfterSaleMapper.updateByIdAndStatus(id, status, updateObj);
        if (updateCount == 0) {
            throw exception(AFTER_SALE_UPDATE_STATUS_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_DELIVERY)
    public void deliveryAfterSale(Long userId, AppAfterSaleDeliveryReqVO deliveryReqVO) {
        // Verify the existence of after-sales order，Status: Not returned
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(deliveryReqVO.getId());
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.SELLER_AGREE.getStatus())) {
            throw exception(AFTER_SALE_DELIVERY_FAIL_STATUS_NOT_SELLER_AGREE);
        }
        DeliveryExpressDO express = deliveryExpressService.validateDeliveryExpress(deliveryReqVO.getLogisticsId());

        // Update the logistics information of the after-sales order
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.SELLER_AGREE.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())
                .setLogisticsId(deliveryReqVO.getLogisticsId()).setLogisticsNo(deliveryReqVO.getLogisticsNo())
                .setDeliveryTime(LocalDateTime.now()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(),
                MapUtil.<String, Object>builder().put("deliveryName", express.getName())
                        .put("logisticsNo", deliveryReqVO.getLogisticsNo()).build());

        // TODO Send after-sales message
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_AGREE_RECEIVE)
    public void receiveAfterSale(Long userId, Long id) {
        // Verify the existence of after-sales order，and the status is returned
        AfterSaleDO afterSale = validateAfterSaleReceivable(id);

        // Update the status of after-sales order
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.WAIT_REFUND.getStatus()).setReceiveTime(LocalDateTime.now()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.WAIT_REFUND.getStatus());

        // TODO Send after-sales message
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_DISAGREE_RECEIVE)
    public void refuseAfterSale(Long userId, AfterSaleRefuseReqVO refuseReqVO) {
        // Verify the existence of after-sales order，and the status is returned
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(refuseReqVO.getId());
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CONFIRM_FAIL_STATUS_NOT_BUYER_DELIVERY);
        }

        // Update the status of after-sales order
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.SELLER_REFUSE.getStatus()).setReceiveTime(LocalDateTime.now())
                .setReceiveReason(refuseReqVO.getRefuseMemo()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.SELLER_REFUSE.getStatus(),
                MapUtil.of("reason", refuseReqVO.getRefuseMemo()));

        // TODO Send after-sales message

        // Update the after-sales status of the transaction order item to【Not applied for】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    /**
     * Check whether the after-sales order is receivable，The buyer has shipped the goods
     *
     * @param id After-sales number
     * @return After-sales order
     */
    private AfterSaleDO validateAfterSaleReceivable(Long id) {
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CONFIRM_FAIL_STATUS_NOT_BUYER_DELIVERY);
        }
        return afterSale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_REFUND)
    public void refundAfterSale(Long userId, String userIp, Long id) {
        // Check the status of after-sales order，and the status is pending refund
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.WAIT_REFUND.getStatus())) {
            throw exception(AFTER_SALE_REFUND_FAIL_STATUS_NOT_WAIT_REFUND);
        }

        // Initiate a refund order。Attention，Need to be done after transaction is committed，Initiate again，Avoid repeated initiation
        createPayRefund(userIp, afterSale);

        // Update the status of the after-sales order to【Completed】
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.WAIT_REFUND.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.COMPLETE.getStatus()).setRefundTime(LocalDateTime.now()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.COMPLETE.getStatus());

        // TODO Send after-sales message

        // Update the after-sales status of the transaction order item to【Completed】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleSuccess(afterSale.getOrderItemId(), afterSale.getRefundPrice());
    }

    private void createPayRefund(String userIp, AfterSaleDO afterSale) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                // Create a refund order
                PayRefundCreateReqDTO createReqDTO = AfterSaleConvert.INSTANCE.convert(userIp, afterSale, tradeOrderProperties)
                        .setReason(StrUtil.format("Refund【{}】", afterSale.getSpuName()));
                Long payRefundId = payRefundApi.createRefund(createReqDTO);
                // Update the refund number of the after-sales order
                tradeAfterSaleMapper.updateById(new AfterSaleDO().setId(afterSale.getId()).setPayRefundId(payRefundId));
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_CANCEL)
    public void cancelAfterSale(Long userId, Long id) {
        // Check the status of after-sales order，and the status is pending refund
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (!ObjectUtils.equalsAny(afterSale.getStatus(), AfterSaleStatusEnum.APPLY.getStatus(),
                AfterSaleStatusEnum.SELLER_AGREE.getStatus(),
                AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CANCEL_FAIL_STATUS_NOT_APPLY_OR_AGREE_OR_BUYER_DELIVERY);
        }

        // Update the status of the after-sales order to【Cancelled】
        updateAfterSaleStatus(afterSale.getId(), afterSale.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.BUYER_CANCEL.getStatus()));

        // Record after-sales log
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.BUYER_CANCEL.getStatus());

        // TODO Send after-sales message

        // Update the after-sales status of the transaction order item to【Not applied for】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    @Override
    public Long getApplyingAfterSaleCount(Long userId) {
        return tradeAfterSaleMapper.selectCountByUserIdAndStatus(userId, AfterSaleStatusEnum.APPLYING_STATUSES);
    }

}
