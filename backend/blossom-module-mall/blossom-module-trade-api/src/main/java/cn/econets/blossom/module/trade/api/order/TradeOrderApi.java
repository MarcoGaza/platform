package cn.econets.blossom.module.trade.api.order;

import cn.econets.blossom.module.trade.api.order.dto.TradeOrderRespDTO;

import java.util.Collection;
import java.util.List;

/**
 * Order API Interface
 *
 */
public interface TradeOrderApi {

    /**
     * Get order list
     *
     * @param ids Order number array
     * @return Order List
     */
    List<TradeOrderRespDTO> getOrderList(Collection<Long> ids);

    /**
     * Get order
     *
     * @param id Order Number
     * @return Order
     */
    TradeOrderRespDTO getOrder(Long id);

    // TODO Needs optimization；
    /**
     * Cancel payment order
     *
     * @param userId  User Number
     * @param orderId Order Number
     */
    void cancelPaidOrder(Long userId, Long orderId);

}
