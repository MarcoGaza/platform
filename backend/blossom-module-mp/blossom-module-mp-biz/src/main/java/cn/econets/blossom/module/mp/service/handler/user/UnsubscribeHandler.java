package cn.econets.blossom.module.mp.service.handler.user;

import cn.econets.blossom.module.mp.framework.mp.core.context.MpContextHolder;
import cn.econets.blossom.module.mp.service.user.MpUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Unfollow event handler
 *
 *
 */
@Component
@Slf4j
public class UnsubscribeHandler implements WxMpMessageHandler {

    @Resource
    @Lazy // Delayed loading，Solve the problem of circular dependencies
    private MpUserService mpUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("[handle][Fans({}) Unfollow]", wxMessage.getFromUser());
        mpUserService.updateUserUnsubscribe(MpContextHolder.getAppId(), wxMessage.getFromUser());
        return null;
    }

}
