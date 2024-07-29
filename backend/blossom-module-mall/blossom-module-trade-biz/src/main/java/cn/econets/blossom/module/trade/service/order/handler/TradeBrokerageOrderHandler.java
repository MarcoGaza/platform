package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.trade.convert.order.TradeOrderConvert;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageRecordService;
import cn.econets.blossom.module.trade.service.brokerage.BrokerageUserService;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageAddReqBO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertList;

/**
 * Order distribution {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeBrokerageOrderHandler implements TradeOrderHandler {

    @Resource
    private MemberUserApi memberUserApi;
    @Resource
    private ProductSpuApi productSpuApi;
    @Resource
    private ProductSkuApi productSkuApi;

    @Resource
    private BrokerageRecordService brokerageRecordService;
    @Resource
    private BrokerageUserService brokerageUserService;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // Set order promoter
        BrokerageUserDO brokerageUser = brokerageUserService.getBrokerageUser(order.getUserId());
        if (brokerageUser != null && brokerageUser.getBindUserId() != null) {
            order.setBrokerageUserId(brokerageUser.getBindUserId());
        }
    }

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        if (order.getBrokerageUserId() == null) {
            return;
        }
        addBrokerage(order.getUserId(), orderItems);
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // If it is an unpaid order，No distribution results will be generated，So direct return
        if (!order.getPayStatus()) {
            return;
        }
        if (order.getBrokerageUserId() == null) {
            return;
        }

        // After-sales order items，Already in afterCancelOrderItem Rollback Inventory，So there is no need to roll back repeatedly
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }
        orderItems.forEach(orderItem -> afterCancelOrderItem(order, orderItem));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        if (order.getBrokerageUserId() == null) {
            return;
        }
        cancelBrokerage(order.getBrokerageUserId(), orderItem.getOrderId());
    }

    /**
     * Create distribution record
     * <p>
     * Currently after payment is successful，A distribution record will be created。
     * <p>
     * There are two other approaches in the industry，You can adjust it according to your business：
     * 1. After confirming receipt，Just create distribution record
     * 2. Payment or When the order is successfully placed，Create distribution record（Freeze），Confirm receipt and unfreeze or n The Queen Thaws
     *
     * @param userId  User Number
     * @param orderItems Order Item
     */
    protected void addBrokerage(Long userId, List<TradeOrderItemDO> orderItems) {
        MemberUserRespDTO user = memberUserApi.getUser(userId);
        Assert.notNull(user);
        ProductSpuRespDTO spu = productSpuApi.getSpu(orderItems.get(0).getSpuId());
        Assert.notNull(spu);
        ProductSkuRespDTO sku = productSkuApi.getSku(orderItems.get(0).getSkuId());

        // Every order item，Will generate distribution records
        List<BrokerageAddReqBO> addList = convertList(orderItems,
                item -> TradeOrderConvert.INSTANCE.convert(user, item, spu, sku));
        brokerageRecordService.addBrokerage(userId, BrokerageRecordBizTypeEnum.ORDER, addList);
    }

    protected void cancelBrokerage(Long userId, Long orderItemId) {
        brokerageRecordService.cancelBrokerage(userId, BrokerageRecordBizTypeEnum.ORDER, String.valueOf(orderItemId));
    }

}
