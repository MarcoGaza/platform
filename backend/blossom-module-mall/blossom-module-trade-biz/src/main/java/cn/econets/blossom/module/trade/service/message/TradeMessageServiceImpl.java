package cn.econets.blossom.module.trade.service.message;

import cn.econets.blossom.module.system.api.notify.NotifyMessageSendApi;
import cn.econets.blossom.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.econets.blossom.module.trade.enums.MessageTemplateConstants;
import cn.econets.blossom.module.trade.service.message.bo.TradeOrderMessageWhenDeliveryOrderReqBO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Trade Message service Implementation class
 *
 */
@Service
@Validated
public class TradeMessageServiceImpl implements TradeMessageService {

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Override
    public void sendMessageWhenDeliveryOrder(TradeOrderMessageWhenDeliveryOrderReqBO reqBO) {
        if (true) {
            return;
        }
        // 1、Construct message
        Map<String, Object> msgMap = new HashMap<>(2);
        msgMap.put("orderId", reqBO.getOrderId());
        msgMap.put("deliveryMessage", reqBO.getMessage());
        // TODO Look at the template
        // 2、Send internal message
        notifyMessageSendApi.sendSingleMessageToMember(
                new NotifySendSingleToUserReqDTO()
                        .setUserId(reqBO.getUserId())
                        .setTemplateCode(MessageTemplateConstants.ORDER_DELIVERY)
                        .setTemplateParams(msgMap));
    }

}
