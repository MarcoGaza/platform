package cn.econets.blossom.framework.pay.core.client;

import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;

/**
 * Factory interface of payment client
 *
 */
public interface PayClientFactory {

    /**
     * Get payment client
     *
     * @param channelId Channel Number
     * @return Payment Client
     */
     PayClient getPayClient(Long channelId);

    /**
     * Create a payment client
     *
     * @param channelId Channel Number
     * @param channelCode Channel Code
     * @param config Payment Configuration
     */
    <Config extends PayClientConfig> void createOrUpdatePayClient(Long channelId, String channelCode,
                                                                  Config config);

    /**
     * Register payment client Class，For implementation in modules PayClient
     *
     * @param channel Enumeration of payment channel codes
     * @param payClientClass Payment Client class
     */
    void registerPayClientClass(PayChannelEnum channel, Class<?> payClientClass);

}
