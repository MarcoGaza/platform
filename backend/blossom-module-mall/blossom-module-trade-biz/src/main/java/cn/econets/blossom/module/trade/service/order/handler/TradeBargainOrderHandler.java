package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.promotion.api.bargain.BargainActivityApi;
import cn.econets.blossom.module.promotion.api.bargain.BargainRecordApi;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Bargaining order {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeBargainOrderHandler implements TradeOrderHandler {

    @Resource
    private BargainActivityApi bargainActivityApi;
    @Resource
    private BargainRecordApi bargainRecordApi;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (!TradeOrderTypeEnum.isBargain(order.getType())) {
            return;
        }
        // Check it clearly
        Assert.isTrue(orderItems.size() == 1, "Bargaining，Only one product can be selected");

        // Deduct inventory from bargaining activities
        bargainActivityApi.updateBargainActivityStock(order.getBargainActivityId(),
                -orderItems.get(0).getCount());
    }

    @Override
    public void afterOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (!TradeOrderTypeEnum.isBargain(order.getType())) {
            return;
        }
        // Check it clearly
        Assert.isTrue(orderItems.size() == 1, "Bargaining，Only one product can be selected");

        // Record the order number corresponding to the bargaining record
        bargainRecordApi.updateBargainRecordOrderId(order.getBargainRecordId(), order.getId());
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (!TradeOrderTypeEnum.isBargain(order.getType())) {
            return;
        }
        // Clearly verify
        Assert.isTrue(orderItems.size() == 1, "Bargaining，Only one product can be selected");

        // After-sales order items，Already in afterCancelOrderItem Rollback Inventory，So there is no need to roll back repeatedly
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        afterCancelOrderItem(order, orderItems.get(0));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        if (!TradeOrderTypeEnum.isBargain(order.getType())) {
            return;
        }
        // Restore（Increase）Bargaining inventory
        bargainActivityApi.updateBargainActivityStock(order.getBargainActivityId(), orderItem.getCount());
    }

}
