package cn.econets.blossom.framework.pay.config;

import cn.econets.blossom.framework.pay.core.client.PayClientFactory;
import cn.econets.blossom.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Payment Configuration Class
 *
 */
@AutoConfiguration
public class PayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
