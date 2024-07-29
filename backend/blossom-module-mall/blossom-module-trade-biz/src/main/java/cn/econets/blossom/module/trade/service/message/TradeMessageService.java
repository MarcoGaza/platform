package cn.econets.blossom.module.trade.service.message;

import cn.econets.blossom.module.trade.service.message.bo.TradeOrderMessageWhenDeliveryOrderReqBO;

/**
 * Trade Message service Interface
 *
 */
public interface TradeMessageService {

    /**
     * Send notification when order is shipped
     *
     * @param reqBO Send message
     */
    void sendMessageWhenDeliveryOrder(TradeOrderMessageWhenDeliveryOrderReqBO reqBO);

}
