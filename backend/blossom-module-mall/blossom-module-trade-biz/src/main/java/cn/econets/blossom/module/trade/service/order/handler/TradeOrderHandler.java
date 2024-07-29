package cn.econets.blossom.module.trade.service.order.handler;

import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderItemAfterSaleStatusEnum;

import java.util.List;

/**
 * Order activity special logic processor handler Interface
 * Provide order life cycle hook interface；Before order creation、After order is created、After order payment、Order Cancelled
 *
 */
public interface TradeOrderHandler {

    /**
     * Before order creation
     *
     * @param order Order
     * @param orderItems Order Item
     */
    default void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {}

    /**
     * After order creation
     *
     * @param order Order
     * @param orderItems Order Item
     */
    default void afterOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {}

    /**
     * After paying the order
     *
     * @param order Order
     * @param orderItems Order Item
     */
    default void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {}

    /**
     * After order cancellation
     *
     * @param order Order
     * @param orderItems Order Item
     */
    default void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {}

    /**
     * After the order item is cancelled
     *
     * @param order Order
     * @param orderItem Order Item
     */
    default void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {}

    /**
     * Before order delivery
     *
     * @param order Order
     */
    default void beforeDeliveryOrder(TradeOrderDO order) {}

    // ========== Public methods ==========

    /**
     * Filter“Not sold yet”List of order items
     *
     * @param orderItems Line Item List
     * @return Filtered line item list
     */
    default List<TradeOrderItemDO> filterOrderItemListByNoneAfterSale(List<TradeOrderItemDO> orderItems) {
        return CollectionUtils.filterList(orderItems,
                item -> TradeOrderItemAfterSaleStatusEnum.isNone(item.getAfterSaleStatus()));
    }

}
