package cn.econets.blossom.module.pay.api.refund;

import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundRespDTO;

import javax.validation.Valid;

/**
 * Refund slip API Interface
 *
 *
 */
public interface PayRefundApi {

    /**
     * Create a refund order
     *
     * @param reqDTO Create request
     * @return Refund order number
     */
    Long createRefund(@Valid PayRefundCreateReqDTO reqDTO);

    /**
     * Get a refund slip
     *
     * @param id Refund order number
     * @return Refund slip
     */
    PayRefundRespDTO getRefund(Long id);

}
