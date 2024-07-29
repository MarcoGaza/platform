package cn.econets.blossom.module.pay.api.order;

import cn.econets.blossom.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.econets.blossom.module.pay.api.order.dto.PayOrderRespDTO;

import javax.validation.Valid;

/**
 * Payment slip API Interface
 *
 */
public interface PayOrderApi {

    /**
     * Create payment order
     *
     * @param reqDTO Create request
     * @return Payment order number
     */
    Long createOrder(@Valid PayOrderCreateReqDTO reqDTO);

    /**
     * Get payment slip
     *
     * @param id Payment order number
     * @return Payment slip
     */
    PayOrderRespDTO getOrder(Long id);

    /**
     * Update payment order price
     *
     * @param id Payment order number
     * @param payPrice   Payment order price
     */
    void updatePayOrderPrice(Long id, Integer payPrice);

}
