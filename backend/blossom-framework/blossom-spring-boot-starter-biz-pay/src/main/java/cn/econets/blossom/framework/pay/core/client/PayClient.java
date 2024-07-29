package cn.econets.blossom.framework.pay.core.client;

import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import cn.econets.blossom.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;

import java.util.Map;

/**
 * Payment Client，Used to connect to various payment channels SDK，Realize payment initiation、Refund and other functions
 *
 */
public interface PayClient {

    /**
     * Get channel number
     *
     * @return Channel Number
     */
    Long getId();

    // ============ Payment related ==========

    /**
     * Call payment channel，Unified order
     *
     * @param reqDTO Order information
     * @return Payment order information
     */
    PayOrderRespDTO unifiedOrder(PayOrderUnifiedReqDTO reqDTO);

    /**
     * Analysis order Callback data
     *
     * @param params HTTP Callback interface content type for application/x-www-form-urlencoded All parameters
     * @param body HTTP Callback interface request body
     * @return Payment order information
     */
    PayOrderRespDTO parseOrderNotify(Map<String, String> params, String body);

    /**
     * Get payment order information
     *
     * @param outTradeNo External order number
     * @return Payment order information
     */
    PayOrderRespDTO getOrder(String outTradeNo);

    // ============ Refund related ==========

    /**
     * Call payment channel，Make a refund
     *
     * @param reqDTO  Uniform refund request information
     * @return Refund Information
     */
    PayRefundRespDTO unifiedRefund(PayRefundUnifiedReqDTO reqDTO);

    /**
     * Analysis refund Callback data
     *
     * @param params HTTP Callback interface content type for application/x-www-form-urlencoded All parameters
     * @param body HTTP Callback interface request body
     * @return Payment order information
     */
    PayRefundRespDTO parseRefundNotify(Map<String, String> params, String body);

    /**
     * Get refund order information
     *
     * @param outTradeNo External order number
     * @param outRefundNo External refund number
     * @return Refund order information
     */
    PayRefundRespDTO getRefund(String outTradeNo, String outRefundNo);

    /**
     * Call channel，Make a transfer
     *
     * @param reqDTO Uniform transfer request information
     * @return Transfer information
     */
    PayTransferRespDTO unifiedTransfer(PayTransferUnifiedReqDTO reqDTO);

    /**
     * Get transfer order information
     *
     * @param outTradeNo External order number
     * @param type Transfer Type
     * @return Transfer information
     */
    PayTransferRespDTO getTransfer(String outTradeNo, PayTransferTypeEnum type);
}
