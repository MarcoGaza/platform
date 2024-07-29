package cn.econets.blossom.module.mp.service.handler.user;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.message.MpAutoReplyService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Event handler for reporting geographic location
 *
 * Trigger operation：Open WeChat public account -> Click + Number -> Select「Voice」
 *
 * Logic：When fans upload their location，Can also trigger automatic reply
 *
 *
 */
@Component
@Slf4j
public class LocationHandler implements WxMpMessageHandler {

    @Resource
    private MpAutoReplyService mpAutoReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) {
        // Defensive Programming：Must be LOCATION Message
        if (ObjectUtil.notEqual(wxMessage.getMsgType(), WxConsts.XmlMsgType.LOCATION)) {
            return null;
        }
        log.info("[handle][Report geographic location，Latitude({})、Longitude({})、Accuracy({})", wxMessage.getLatitude(),
                wxMessage.getLongitude(), wxMessage.getPrecision());

        // Automatic reply
        return mpAutoReplyService.replyForMessage(MpContextHolder.getAppId(), wxMessage);
    }

}
