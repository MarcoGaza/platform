package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.promotion.api.combination.CombinationRecordApi;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateRespDTO;
import cn.econets.blossom.module.trade.convert.order.TradeOrderConvert;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import cn.econets.blossom.module.trade.service.order.TradeOrderQueryService;
import cn.econets.blossom.module.trade.service.order.TradeOrderUpdateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.ORDER_CREATE_FAIL_EXIST_UNPAID;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.ORDER_DELIVERY_FAIL_COMBINATION_RECORD_STATUS_NOT_SUCCESS;

/**
 * Group order {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeCombinationOrderHandler implements TradeOrderHandler {

    @Resource
    private TradeOrderUpdateService orderUpdateService;
    @Resource
    private TradeOrderQueryService orderQueryService;

    @Resource
    private CombinationRecordApi combinationRecordApi;

    @Override
    public void beforeOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // If it is not a group order, then end
        if (!TradeOrderTypeEnum.isCombination(order.getType())) {
            return;
        }
        Assert.isTrue(orderItems.size() == 1, "When buying in a group，Only one product can be selected");

        // 1. Check whether the restrictions on group buying activities are met
        TradeOrderItemDO item = orderItems.get(0);
        combinationRecordApi.validateCombinationRecord(order.getUserId(), order.getCombinationActivityId(),
                order.getCombinationHeadId(), item.getSkuId(), item.getCount());

        // 2. Check whether the user has any unpaid group purchase orders，Avoid placing multiple orders for one group purchase
        TradeOrderDO activityOrder = orderQueryService.getOrderByUserIdAndStatusAndCombination(
                order.getUserId(), order.getCombinationActivityId(), TradeOrderStatusEnum.UNPAID.getStatus());
        if (activityOrder != null) {
            throw exception(ORDER_CREATE_FAIL_EXIST_UNPAID);
        }
    }

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // 1.If it is not a group order, then end
        if (!TradeOrderTypeEnum.isCombination(order.getType())) {
            return;
        }
        Assert.isTrue(orderItems.size() == 1, "When buying in a group，Only one product can be selected");

        // 2. Create a group purchase record
        TradeOrderItemDO item = orderItems.get(0);
        CombinationRecordCreateRespDTO combinationRecord = combinationRecordApi.createCombinationRecord(
                TradeOrderConvert.INSTANCE.convert(order, item));

        // 3. Update group purchase related information to the order。Why do several fields need to be updated?？
        // The reason is：If you are the group leader when creating an order combinationHeadId It is for null of，The group leader number is set when the group purchase record is created after the order is placed。
        orderUpdateService.updateOrderCombinationInfo(order.getId(), order.getCombinationActivityId(),
                combinationRecord.getCombinationRecordId(), combinationRecord.getCombinationHeadId());
    }

    @Override
    public void beforeDeliveryOrder(TradeOrderDO order) {
        if (!TradeOrderTypeEnum.isCombination(order.getType())) {
            return;
        }
        // Check whether the order grouping is successful
        if (!combinationRecordApi.isCombinationRecordSuccess(order.getUserId(), order.getId())) {
            throw exception(ORDER_DELIVERY_FAIL_COMBINATION_RECORD_STATUS_NOT_SUCCESS);
        }
    }

}

