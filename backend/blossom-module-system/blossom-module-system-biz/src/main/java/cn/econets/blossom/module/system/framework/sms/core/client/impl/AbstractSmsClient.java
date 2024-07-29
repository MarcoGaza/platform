package cn.econets.blossom.module.system.framework.sms.core.client.impl;

import cn.econets.blossom.module.system.framework.sms.core.client.SmsClient;
import cn.econets.blossom.module.system.framework.sms.core.property.SmsChannelProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class of SMS client，Provide template method，Reduce redundant code in subclasses
 *
 */
@Slf4j
public abstract class AbstractSmsClient implements SmsClient {

    /**
     * SMS channel configuration
     */
    protected volatile SmsChannelProperties properties;

    public AbstractSmsClient(SmsChannelProperties properties) {
        this.properties = properties;
    }

    /**
     * Initialization
     */
    public final void init() {
        doInit();
        log.debug("[init][Configuration({}) Initialization completed]", properties);
    }

    /**
     * Custom initialization
     */
    protected abstract void doInit();

    public final void refresh(SmsChannelProperties properties) {
        // Judge whether to update
        if (properties.equals(this.properties)) {
            return;
        }
        log.info("[refresh][Configuration({})Changes have occurred，Reinitialize]", properties);
        // Initialization
        this.init();
    }

    @Override
    public Long getId() {
        return properties.getId();
    }

}
