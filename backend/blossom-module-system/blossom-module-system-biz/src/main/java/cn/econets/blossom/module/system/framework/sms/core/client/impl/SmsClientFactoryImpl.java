package cn.econets.blossom.module.system.framework.sms.core.client.impl;

import cn.econets.blossom.module.system.framework.sms.core.client.SmsClient;
import cn.econets.blossom.module.system.framework.sms.core.client.SmsClientFactory;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.aliyun.AliyunSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.debug.DebugDingTalkSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.client.impl.tencent.TencentSmsClient;
import cn.econets.blossom.module.system.framework.sms.core.enums.SmsChannelEnum;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * SMS client factory interface
 *
 */
@Validated
@Slf4j
public class SmsClientFactoryImpl implements SmsClientFactory {

    /**
     * SMS client Map
     * key：Channel Number，Use {@link SmsChannelProperties#getId()}
     */
    private final ConcurrentMap<Long, AbstractSmsClient> channelIdClients = new ConcurrentHashMap<>();

    /**
     * SMS client Map
     * key：Channel Code，Use {@link SmsChannelProperties#getCode()} ()}
     *
     * Attention，In some scenarios，Need to get a client of a certain channel type，So you need to use it。
     * For example，Parse SMS reception results，is relatively universal，No need to use a certain channel number {@link #channelIdClients}
     */
    private final ConcurrentMap<String, AbstractSmsClient> channelCodeClients = new ConcurrentHashMap<>();

    public SmsClientFactoryImpl() {
        // Initialization channelCodeClients Collection
        Arrays.stream(SmsChannelEnum.values()).forEach(channel -> {
            // Create an empty SmsChannelProperties Object
            SmsChannelProperties properties = new SmsChannelProperties();
            properties.setCode(channel.getCode());
            properties.setApiKey("default default");
            properties.setApiSecret("default");
            // Create Sms Client
            AbstractSmsClient smsClient = createSmsClient(properties);
            channelCodeClients.put(channel.getCode(), smsClient);
        });
    }

    @Override
    public SmsClient getSmsClient(Long channelId) {
        return channelIdClients.get(channelId);
    }

    @Override
    public SmsClient getSmsClient(String channelCode) {
        return channelCodeClients.get(channelCode);
    }

    @Override
    public void createOrUpdateSmsClient(SmsChannelProperties properties) {
        AbstractSmsClient client = channelIdClients.get(properties.getId());
        if (client == null) {
            client = this.createSmsClient(properties);
            client.init();
            channelIdClients.put(client.getId(), client);
        } else {
            client.refresh(properties);
        }
    }

    private AbstractSmsClient createSmsClient(SmsChannelProperties properties) {
        SmsChannelEnum channelEnum = SmsChannelEnum.getByCode(properties.getCode());
        Assert.notNull(channelEnum, String.format("Channel Type(%s) Empty", channelEnum));
        // Create client
        switch (channelEnum) {
            case ALIYUN: return new AliyunSmsClient(properties);
            case DEBUG_DING_TALK: return new DebugDingTalkSmsClient(properties);
            case TENCENT: return new TencentSmsClient(properties);
        }
        // Create failed，Error log + Throws an exception
        log.error("[createSmsClient][Configuration({}) No suitable client implementation found]", properties);
        throw new IllegalArgumentException(String.format("Configuration(%s) No suitable client implementation found", properties));
    }

}
