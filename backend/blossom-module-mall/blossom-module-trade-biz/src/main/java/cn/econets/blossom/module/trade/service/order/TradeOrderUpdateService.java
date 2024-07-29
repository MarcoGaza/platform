package cn.econets.blossom.module.trade.service.order;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderDeliveryReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderRemarkReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderUpdateAddressReqVO;
import cn.econets.blossom.module.trade.controller.admin.order.vo.TradeOrderUpdatePriceReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderSettlementReqVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.AppTradeOrderSettlementRespVO;
import cn.econets.blossom.module.trade.controller.app.order.vo.item.AppTradeOrderItemCommentCreateReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderDO;

import javax.validation.constraints.NotNull;

/**
 * Transaction Order【Write】Service Interface
 *
 */
public interface TradeOrderUpdateService {

    // =================== Order ===================

    /**
     * Get order settlement information
     *
     * @param userId          Logged in user
     * @param settlementReqVO Order settlement request
     * @return Order settlement result
     */
    AppTradeOrderSettlementRespVO settlementOrder(Long userId, AppTradeOrderSettlementReqVO settlementReqVO);

    /**
     * 【Member】Create a trading order
     *
     * @param userId      Logged in user
     * @param createReqVO Create a trading order request model
     * @return Transaction order
     */
    TradeOrderDO createOrder(Long userId, AppTradeOrderCreateReqVO createReqVO);

    /**
     * Update transaction order has been paid
     *
     * @param id         Transaction order number
     * @param payOrderId Payment order number
     */
    void updateOrderPaid(Long id, Long payOrderId);

    /**
     * 【Administrator】Shipping transaction order
     *
     * @param deliveryReqVO Shipping Request
     */
    void deliveryOrder(TradeOrderDeliveryReqVO deliveryReqVO);

    /**
     * 【Member】Receiving transaction order
     *
     * @param userId User Number
     * @param id     Order number
     */
    void receiveOrderByMember(Long userId, Long id);

    /**
     * 【System】Automatic receipt transaction order
     *
     * @return Quantity received
     */
    int receiveOrderBySystem();

    /**
     * 【Member】Cancel transaction order
     *
     * @param userId User Number
     * @param id     Order number
     */
    void cancelOrderByMember(Long userId, Long id);

    /**
     * 【System】Automatically cancel the order
     *
     * @return Cancel quantity
     */
    int cancelOrderBySystem();

    /**
     * 【Member】Delete order
     *
     * @param userId User Number
     * @param id     Order number
     */
    void deleteOrder(Long userId, Long id);

    /**
     * 【Administrator】Transaction Order Notes
     *
     * @param reqVO Request
     */
    void updateOrderRemark(TradeOrderRemarkReqVO reqVO);

    /**
     * 【Administrator】Adjust price
     *
     * @param reqVO Request
     */
    void updateOrderPrice(TradeOrderUpdatePriceReqVO reqVO);

    /**
     * 【Administrator】Adjust address
     *
     * @param reqVO Request
     */
    void updateOrderAddress(TradeOrderUpdateAddressReqVO reqVO);

    /**
     * 【Administrator】Cancel order
     *
     * @param id Order number
     */
    void pickUpOrderByAdmin(Long id);

    /**
     * 【Administrator】Cancel order
     *
     * @param pickUpVerifyCode Self-collection verification code
     */
    void pickUpOrderByAdmin(String pickUpVerifyCode);

    /**
     * 【Administrator】According to the self-collection verification code，Query order
     *
     * @param pickUpVerifyCode Self-collection verification code
     */
    TradeOrderDO getByPickUpVerifyCode(String pickUpVerifyCode);

    // =================== Order Item ===================

    /**
     * After the after-sales application，Update the after-sales status of the transaction order item
     *
     * @param id          Transaction order item number
     * @param afterSaleId After-sales order number
     */
    void updateOrderItemWhenAfterSaleCreate(@NotNull Long id, @NotNull Long afterSaleId);

    /**
     * When after-sales service is completed，Update the after-sales status of the transaction order item
     *
     * @param id          Transaction order item number
     * @param refundPrice Refund amount
     */
    void updateOrderItemWhenAfterSaleSuccess(@NotNull Long id, @NotNull Integer refundPrice);

    /**
     * When the after-sales cancellation（User canceled、Administrator rejected、Administrator refuses to accept the goods）After，Update the after-sales status of the transaction order item
     *
     * @param id Transaction order item number
     */
    void updateOrderItemWhenAfterSaleCancel(@NotNull Long id);

    /**
     * 【Member】Comments for creating line items
     *
     * @param userId      User Number
     * @param createReqVO Create request
     * @return Get evaluation id
     */
    Long createOrderItemCommentByMember(Long userId, AppTradeOrderItemCommentCreateReqVO createReqVO);

    /**
     * 【System】Comments for creating line items
     *
     * @return Number of commented orders
     */
    int createOrderItemCommentBySystem();

    /**
     * Update group purchase related information to the order
     *
     * @param orderId             Order number
     * @param activityId          Group buying activity number
     * @param combinationRecordId Group buying record number
     * @param headId              Team Leader Number
     */
    void updateOrderCombinationInfo(Long orderId, Long activityId, Long combinationRecordId, Long headId);

    // TODO Group buy cancelled，Don't adjust this interface；
    /**
     * Cancel payment order
     *
     * @param userId  User Number
     * @param orderId Order number
     */
    void cancelPaidOrder(Long userId, Long orderId);

}
