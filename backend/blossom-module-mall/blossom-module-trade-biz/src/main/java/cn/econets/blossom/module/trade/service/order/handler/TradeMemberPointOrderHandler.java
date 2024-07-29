package cn.econets.blossom.module.trade.service.order.handler;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.module.member.api.level.MemberLevelApi;
import cn.econets.blossom.module.member.api.point.MemberPointApi;
import cn.econets.blossom.module.member.enums.MemberExperienceBizTypeEnum;
import cn.econets.blossom.module.member.enums.point.MemberPointBizTypeEnum;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderItemDO;
import cn.econets.blossom.module.trade.service.aftersale.AfterSaleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.getSumValue;

/**
 * Member Points、Level {@link TradeOrderHandler} Implementation class
 *
 */
@Component
public class TradeMemberPointOrderHandler implements TradeOrderHandler {

    @Resource
    private MemberPointApi memberPointApi;
    @Resource
    private MemberLevelApi memberLevelApi;

    @Resource
    private AfterSaleService afterSaleService;

    @Override
    public void afterOrderCreate(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // Deduct user points（Order discount）。Reasons for not making a pre-deduction，It is because of the deduction of points，Need to record related business
        reducePoint(order.getUserId(), order.getUsePoint(), MemberPointBizTypeEnum.ORDER_USE, order.getId());
    }

    @Override
    public void afterPayOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // Increase user points（Free gift for order）
        addPoint(order.getUserId(), order.getGivePoint(), MemberPointBizTypeEnum.ORDER_GIVE,
                order.getId());

        // Increase user experience
        memberLevelApi.addExperience(order.getUserId(), order.getPayPrice(),
                MemberExperienceBizTypeEnum.ORDER_GIVE.getType(), String.valueOf(order.getId()));
    }

    @Override
    public void afterCancelOrder(TradeOrderDO order, List<TradeOrderItemDO> orderItems) {
        // After-sales order items，Already in afterCancelOrderItem Rollback Inventory，So there is no need to roll back repeatedly
        orderItems = filterOrderItemListByNoneAfterSale(orderItems);
        if (CollUtil.isEmpty(orderItems)) {
            return;
        }

        // Increase（Rollback）User Points（Order discount）
        Integer usePoint = getSumValue(orderItems, TradeOrderItemDO::getUsePoint, Integer::sum);
        addPoint(order.getUserId(), usePoint, MemberPointBizTypeEnum.ORDER_USE_CANCEL,
                order.getId());

        // The following returns，Needs support，That is, experience afterPayOrder Process
        if (!order.getPayStatus()) {
            return;
        }
        // Deduction（Rollback）Points（Free gift for order）
        Integer givePoint = getSumValue(orderItems, TradeOrderItemDO::getGivePoint, Integer::sum);
        reducePoint(order.getUserId(), givePoint, MemberPointBizTypeEnum.ORDER_GIVE_CANCEL,
                order.getId());
        // Deduction（Rollback）User Experience
        int payPrice = order.getPayPrice() - order.getRefundPrice();
        memberLevelApi.addExperience(order.getUserId(), payPrice,
                MemberExperienceBizTypeEnum.ORDER_GIVE_CANCEL.getType(), String.valueOf(order.getId()));
    }

    @Override
    public void afterCancelOrderItem(TradeOrderDO order, TradeOrderItemDO orderItem) {
        // Deduction（Rollback）Points（Free gift for order）
        reducePoint(order.getUserId(), orderItem.getGivePoint(), MemberPointBizTypeEnum.ORDER_GIVE_CANCEL_ITEM,
                orderItem.getId());
        // Increase（Rollback）Points（Order discount）
        addPoint(order.getUserId(), orderItem.getUsePoint(), MemberPointBizTypeEnum.ORDER_USE_CANCEL_ITEM,
                orderItem.getId());

        // Deduction（Rollback）User Experience
        AfterSaleDO afterSale = afterSaleService.getAfterSale(orderItem.getAfterSaleId());
        memberLevelApi.reduceExperience(order.getUserId(), afterSale.getRefundPrice(),
                MemberExperienceBizTypeEnum.ORDER_GIVE_CANCEL_ITEM.getType(), String.valueOf(orderItem.getId()));
    }

    /**
     * Add user points
     * <p>
     * Currently after payment is successful，A points record will be created。
     * <p>
     * There are two other approaches in the industry，You can adjust it according to your business：
     * 1. After confirming receipt，Just create the points record
     * 2. Payment or When the order is successfully placed，Create points record（Freeze），Confirm receipt and unfreeze or n The Queen Thaws
     *
     * @param userId  User ID
     * @param point   Increase the number of points
     * @param bizType Business Number
     * @param bizId   Business Number
     */
    protected void addPoint(Long userId, Integer point, MemberPointBizTypeEnum bizType, Long bizId) {
        if (point != null && point > 0) {
            memberPointApi.addPoint(userId, point, bizType.getType(), String.valueOf(bizId));
        }
    }

    protected void reducePoint(Long userId, Integer point, MemberPointBizTypeEnum bizType, Long bizId) {
        if (point != null && point > 0) {
            memberPointApi.reducePoint(userId, point, bizType.getType(), String.valueOf(bizId));
        }
    }

}
