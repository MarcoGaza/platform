package cn.econets.blossom.module.mp.service.handler.user;

import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.message.MpAutoReplyService;
import cn.econets.blossom.module.mp.service.user.MpUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Event handler of interest
 *
 *
 */
@Component
@Slf4j
public class SubscribeHandler implements WxMpMessageHandler {

    @Resource
    private MpUserService mpUserService;
    @Resource
    private MpAutoReplyService mpAutoReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService weixinService, WxSessionManager sessionManager) throws WxErrorException {
        // First step，From the public account platform，Get fan information
        log.info("[handle][Fans({}) Follow]", wxMessage.getFromUser());
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = weixinService.getUserService().userInfo(wxMessage.getFromUser());
        } catch (WxErrorException e) {
            log.error("[handle][Fans({})] Failed to obtain fan information！", wxMessage.getFromUser(), e);
        }

        // Step 2，Save fans information
        mpUserService.saveUser(MpContextHolder.getAppId(), wxMpUser);

        // Step 3，Reply to the welcome message
        return mpAutoReplyService.replyForSubscribe(MpContextHolder.getAppId(), wxMessage);
    }

}
