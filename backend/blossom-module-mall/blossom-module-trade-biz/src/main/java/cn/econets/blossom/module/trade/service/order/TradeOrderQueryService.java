package cn.econets.blossom.module.trade.service.order;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderPageReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderPageReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.singleton;

/**
 * Transaction Order【Read】 Service Interface
 *
 */
public interface TradeOrderQueryService {

    // =================== Order ===================

    /**
     * Get the transaction order with the specified number
     *
     * @param id Transaction order number
     * @return Transaction Order
     */
    TradeOrderDO getOrder(Long id);

    /**
     * Get the specified user，Specified transaction order
     *
     * @param userId User Number
     * @param id     Transaction order number
     * @return Transaction Order
     */
    TradeOrderDO getOrder(Long userId, Long id);

    /**
     * Get the specified user，Specified activities，Trading orders of specified status
     *
     * @param userId     User Number
     * @param combinationActivityId Activity number
     * @param status     Order Status
     * @return Transaction Order
     */
    TradeOrderDO getOrderByUserIdAndStatusAndCombination(Long userId, Long combinationActivityId, Integer status);

    /**
     * Get order list
     *
     * @param ids Order number array
     * @return Order List
     */
    List<TradeOrderDO> getOrderList(Collection<Long> ids);

    /**
     * 【Administrator】Get transaction order paging
     *
     * @param reqVO Pagination request
     * @return Transaction Order
     */
    PageResult<TradeOrderDO> getOrderPage(TradeOrderPageReqVO reqVO);

    /**
     * Get order statistics
     *
     * @param reqVO Request parameters
     * @return Order Statistics
     */
    TradeOrderSummaryRespVO getOrderSummary(TradeOrderPageReqVO reqVO);

    /**
     * 【Member】Get transaction order paging
     *
     * @param userId User Number
     * @param reqVO  Pagination request
     * @return Transaction Order
     */
    PageResult<TradeOrderDO> getOrderPage(Long userId, AppTradeOrderPageReqVO reqVO);

    /**
     * 【Member】Get the number of transaction orders
     *
     * @param userId       User Number
     * @param status       Order Status。If empty，No filtering is performed
     * @param commonStatus Evaluation status。If empty，No filtering is performed
     * @return Order quantity
     */
    Long getOrderCount(Long userId, Integer status, Boolean commonStatus);

    /**
     * 【Front Desk】Get the logistics track of the order
     *
     * @param id     Order number
     * @param userId User Number
     * @return Logistics track array
     */
    List<ExpressTrackRespDTO> getExpressTrackList(Long id, Long userId);

    /**
     * 【Backstage】Get the logistics track of the order
     *
     * @param id Order number
     * @return Logistics track array
     */
    List<ExpressTrackRespDTO> getExpressTrackList(Long id);

    /**
     * 【Member】Under the specified flash sale event，The number of items purchased by the user
     *
     * @param userId     User Number
     * @param activityId Activity number
     * @return Number of flash sale products
     */
    int getSeckillProductCount(Long userId, Long activityId);

    // =================== Order Item ===================

    /**
     * Get the specified user，Specified transaction order item
     *
     * @param userId User Number
     * @param itemId Transaction order item number
     * @return Transaction order item
     */
    TradeOrderItemDO getOrderItem(Long userId, Long itemId);

    /**
     * Get transaction order items
     *
     * @param id Transaction order item number itemId
     * @return Transaction order item
     */
    TradeOrderItemDO getOrderItem(Long id);

    /**
     * According to the transaction order number，Query transaction order items
     *
     * @param orderId Transaction order number
     * @return Transaction order item array
     */
    default List<TradeOrderItemDO> getOrderItemListByOrderId(Long orderId) {
        return getOrderItemListByOrderId(singleton(orderId));
    }

    /**
     * Based on the transaction order number array，Query transaction order items
     *
     * @param orderIds Transaction order number array
     * @return Transaction order item array
     */
    List<TradeOrderItemDO> getOrderItemListByOrderId(Collection<Long> orderIds);

}
