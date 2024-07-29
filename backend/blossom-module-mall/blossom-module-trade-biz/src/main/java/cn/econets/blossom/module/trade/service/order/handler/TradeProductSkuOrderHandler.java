package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.trade.convert.order.TradeOrderConvert;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Products SKU In stock {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeProductSkuOrderHandler implements TradeOrderHandler {

    @Resource
    private ProductSkuApi productSkuApi;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        productSkuApi.updateSkuStock(TradeOrderConvert.INSTANCE.convertNegative(orderItems));
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // After-sales order items，Already in afterCancelOrderItem Rollback Inventory，So there is no need to roll back repeatedly
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        productSkuApi.updateSkuStock(TradeOrderConvert.INSTANCE.convert(orderItems));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        productSkuApi.updateSkuStock(TradeOrderConvert.INSTANCE.convert(singletonList(orderItem)));
    }

}
