package cn.econets.blossom.module.pay.service.order;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderExportReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderPageReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitReqVO;
import cn.econets.blossom.module.pay.controller.admin.order.vo.PayOrderSubmitRespVO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderExtensionDO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Payment order Service Interface
 *
 *
 */
public interface PayOrderService {

    /**
     * Get payment order
     *
     * @param id Number
     * @return Payment order
     */
    PayOrderDO getOrder(Long id);

    /**
     * Get payment order
     *
     * @param appId           Application number
     * @param merchantOrderId Merchant order number
     * @return Payment order
     */
    PayOrderDO getOrder(Long appId, String merchantOrderId);

    /**
     * Get payment order list
     *
     * @param ids Number array
     * @return Payment order list
     */
    List<PayOrderDO> getOrderList(Collection<Long> ids);

    /**
     * Get the order quantity of the specified application
     *
     * @param appId Application number
     * @return Order Quantity
     */
    Long getOrderCountByAppId(Long appId);

    /**
     * Get payment order paging
     *
     * @param pageReqVO Paged query
     * @return Payment order paging
     */
    PageResult<PayOrderDO> getOrderPage(PayOrderPageReqVO pageReqVO);

    /**
     * Get payment order list, Used for Excel Export
     *
     * @param exportReqVO Query conditions
     * @return Payment order list
     */
    List<PayOrderDO> getOrderList(PayOrderExportReqVO exportReqVO);

    /**
     * Create payment slip
     *
     * @param reqDTO Create request
     * @return Payment order number
     */
    Long createOrder(@Valid PayOrderCreateReqDTO reqDTO);

    /**
     * Submit payment
     * At this time，Will initiate a payment channel call
     *
     * @param reqVO  Submit a request
     * @param userIp Submit IP
     * @return Submit result
     */
    PayOrderSubmitRespVO submitOrder(@Valid PayOrderSubmitReqVO reqVO,
                                     @NotEmpty(message = "Submit IP Cannot be empty") String userIp);

    /**
     * Notify payment order success
     *
     * @param channelId Channel Number
     * @param notify    Notification
     */
    void notifyOrder(Long channelId, PayOrderRespDTO notify);

    /**
     * Update the refund amount of the payment order
     *
     * @param id              Number
     * @param incrRefundPrice Increased refund amount
     */
    void updateOrderRefundPrice(Long id, Integer incrRefundPrice);

    /**
     * Update payment order price
     *
     * @param id Payment order number
     * @param payPrice   Payment Order Price
     */
    void updatePayOrderPrice(Long id, Integer payPrice);

    /**
     * Get payment order
     *
     * @param id Number
     * @return Payment order
     */
    PayOrderExtensionDO getOrderExtension(Long id);

    /**
     * Get payment order
     *
     * @param no Payment order no
     * @return Payment order
     */
    PayOrderExtensionDO getOrderExtensionByNo(String no);

    /**
     * Synchronize order payment status
     *
     * @param minCreateTime Minimum creation time
     * @return Synchronize to the paid order quantity
     */
    int syncOrder(LocalDateTime minCreateTime);

    /**
     * Expired orders，The status has been changed to closed
     *
     * @return Number of expired orders
     */
    int expireOrder();

}
