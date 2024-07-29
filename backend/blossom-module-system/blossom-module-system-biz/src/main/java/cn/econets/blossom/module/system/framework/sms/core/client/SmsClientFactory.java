package cn.econets.blossom.module.system.framework.sms.core.client;


import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;

/**
 * SMS client factory interface
 *
 */
public interface SmsClientFactory {

    /**
     * Get SMS Client
     *
     * @param channelId Channel Number
     * @return SMS Client
     */
    SmsClient getSmsClient(Long channelId);

    /**
     * Get SMS Client
     *
     * @param channelCode Channel Code
     * @return SMS Client
     */
    SmsClient getSmsClient(String channelCode);

    /**
     * Create SMS Client
     *
     * @param properties Configuration object
     */
    void createOrUpdateSmsClient(SmsChannelProperties properties);

}
