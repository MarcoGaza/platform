package cn.econets.blossom.module.mp.framework.mp.core;

import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.service.handler.menu.MenuHandler;
import cn.econets.blossom.module.mp.service.handler.message.MessageReceiveHandler;
import cn.econets.blossom.module.mp.service.handler.message.MessageAutoReplyHandler;
import cn.econets.blossom.module.mp.service.handler.other.KfSessionHandler;
import cn.econets.blossom.module.mp.service.handler.other.NullHandler;
import cn.econets.blossom.module.mp.service.handler.other.ScanHandler;
import cn.econets.blossom.module.mp.service.handler.other.StoreCheckNotifyHandler;
import cn.econets.blossom.module.mp.service.handler.user.LocationHandler;
import cn.econets.blossom.module.mp.service.handler.user.SubscribeHandler;
import cn.econets.blossom.module.mp.service.handler.user.UnsubscribeHandler;
import com.binarywang.spring.starter.wxjava.mp.properties.WxMpProperties;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;

import java.util.List;
import java.util.Map;

/**
 * Default {@link MpServiceFactory} Implementation class
 *
 *
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultMpServiceFactory implements MpServiceFactory {

    /**
     * WeChat appId with WxMpService Mapping
     */
    private volatile Map<String, WxMpService> appId2MpServices;
    /**
     * Public Account id with WxMpService Mapping
     */
    private volatile Map<Long, WxMpService> id2MpServices;
    /**
     * WeChat appId with WxMpMessageRouter Mapping
     */
    private volatile Map<String, WxMpMessageRouter> mpMessageRouters;

    private final RedisTemplateWxRedisOps redisTemplateWxRedisOps;
    private final WxMpProperties mpProperties;

    // ========== Various Handler ==========

    private final MessageReceiveHandler messageReceiveHandler;
    private final KfSessionHandler kfSessionHandler;
    private final StoreCheckNotifyHandler storeCheckNotifyHandler;
    private final MenuHandler menuHandler;
    private final NullHandler nullHandler;
    private final SubscribeHandler subscribeHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final LocationHandler locationHandler;
    private final ScanHandler scanHandler;
    private final MessageAutoReplyHandler messageAutoReplyHandler;

    @Override
    public void init(List<MpAccountDO> list) {
        Map<String, WxMpService> appId2MpServices = Maps.newHashMap();
        Map<Long, WxMpService> id2MpServices = Maps.newHashMap();
        Map<String, WxMpMessageRouter> mpMessageRouters = Maps.newHashMap();
        // Processing list
        list.forEach(account -> {
            // Build WxMpService Object
            WxMpService mpService = buildMpService(account);
            appId2MpServices.put(account.getAppId(), mpService);
            id2MpServices.put(account.getId(), mpService);
            // Build WxMpMessageRouter Object
            WxMpMessageRouter mpMessageRouter = buildMpMessageRouter(mpService);
            mpMessageRouters.put(account.getAppId(), mpMessageRouter);
        });

        // Set to cache
        this.appId2MpServices = appId2MpServices;
        this.id2MpServices = id2MpServices;
        this.mpMessageRouters = mpMessageRouters;
    }

    @Override
    public WxMpService getMpService(Long id) {
        return id2MpServices.get(id);
    }

    @Override
    public WxMpService getMpService(String appId) {
        return appId2MpServices.get(appId);
    }

    @Override
    public WxMpMessageRouter getMpMessageRouter(String appId) {
        return mpMessageRouters.get(appId);
    }

    private WxMpService buildMpService(MpAccountDO account) {
        // First step，Create WxMpRedisConfigImpl Object
        WxMpRedisConfigImpl configStorage = new WxMpRedisConfigImpl(
                redisTemplateWxRedisOps, mpProperties.getConfigStorage().getKeyPrefix());
        configStorage.setAppId(account.getAppId());
        configStorage.setSecret(account.getAppSecret());
        configStorage.setToken(account.getToken());
        configStorage.setAesKey(account.getAesKey());

        // Step 2，Create WxMpService Object
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }

    private WxMpMessageRouter buildMpMessageRouter(WxMpService mpService) {
        WxMpMessageRouter router = new WxMpMessageRouter(mpService);
        // Log of all events（Asynchronous execution）
        router.rule().handler(messageReceiveHandler).next();

        // Receive customer service session management events
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(kfSessionHandler).end();
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(kfSessionHandler)
                .end();
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(kfSessionHandler).end();

        // Store review event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxMpEventConstants.POI_CHECK_NOTIFY)
                .handler(storeCheckNotifyHandler).end();

        // Custom menu event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(menuHandler).end();

        // Click menu to connect event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(nullHandler).end();

        // Follow the event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler)
                .end();

        // Unfollow event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler).end();

        // Report geographic location events
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.LOCATION).handler(locationHandler)
                .end();

        // Receive location information
        router.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION)
                .handler(locationHandler).end();

        // Scan code event
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN).handler(scanHandler).end();

        // Default
        router.rule().async(false).handler(messageAutoReplyHandler).end();
        return router;
    }

}
