package cn.econets.blossom.module.mp.service.handler.message;

import cn.econets.blossom.module.mp.dal.dataobject.message.MpAutoReplyDO;
import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.message.MpAutoReplyService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Event handler for automatic reply messages
 *
 *
 */
@Component
@Slf4j
public class MessageAutoReplyHandler implements WxMpMessageHandler {

    @Resource
    private MpAutoReplyService mpAutoReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService weixinService, WxSessionManager sessionManager) {
        // Only process messages of specified types
        if (!MpAutoReplyDO.REQUEST_MESSAGE_TYPE.contains(wxMessage.getMsgType())) {
            return null;
        }

        // Auto reply
        return mpAutoReplyService.replyForMessage(MpContextHolder.getAppId(), wxMessage);
    }

}
