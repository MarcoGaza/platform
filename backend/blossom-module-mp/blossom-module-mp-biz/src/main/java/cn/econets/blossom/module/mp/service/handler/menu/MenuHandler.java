package cn.econets.blossom.module.mp.service.handler.menu;

import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.menu.MpMenuService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMenuService;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType;

/**
 * Event handler for custom menu
 *
 * Logic：When fans click on the menu，Trigger the corresponding reply
 *
 *
 */
@Component
public class MenuHandler implements WxMpMessageHandler {

    @Resource
    private MpMenuService mpMenuService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService weixinService, WxSessionManager sessionManager) {
        return mpMenuService.reply(MpContextHolder.getAppId(), wxMessage.getEventKey(), wxMessage.getFromUser());
    }

}
