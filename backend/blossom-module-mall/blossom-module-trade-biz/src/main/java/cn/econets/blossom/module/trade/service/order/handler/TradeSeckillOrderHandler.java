package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.promotion.api.seckill.SeckillActivityApi;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Second-selling orders {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeSeckillOrderHandler implements TradeOrderHandler {

    @Resource
    private SeckillActivityApi seckillActivityApi;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (!TradeOrderTypeEnum.isSeckill(order.getType())) {
            return;
        }
        // Check it clearly
        Assert.isTrue(orderItems.size() == 1, "Second kill，Only one product can be selected");

        // Deduct inventory from flash sales
        seckillActivityApi.updateSeckillStockDecr(order.getSeckillActivityId(),
                orderItems.get(0).getSkuId(), orderItems.get(0).getCount());
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (!TradeOrderTypeEnum.isSeckill(order.getType())) {
            return;
        }
        // Clearly verify
        Assert.isTrue(orderItems.size() == 1, "Second kill，Only one product can be selected");

        // After-sales order items，Already in afterCancelOrderItem Rollback Inventory，So there is no need to roll back repeatedly
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        afterCancelOrderItem(order, orderItems.get(0));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        if (!TradeOrderTypeEnum.isSeckill(order.getType())) {
            return;
        }
        // Restore inventory for flash sales
        seckillActivityApi.updateSeckillStockIncr(order.getSeckillActivityId(),
                orderItem.getSkuId(), orderItem.getCount());
    }

}
