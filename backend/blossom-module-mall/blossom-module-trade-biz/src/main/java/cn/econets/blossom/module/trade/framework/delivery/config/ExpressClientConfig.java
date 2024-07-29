package cn.econets.blossom.module.trade.framework.delivery.config;

import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClientFactory;
import cn.econets.blossom.module.trade.framework.delivery.core.client.impl.ExpressClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Express client configuration class：
 *
 * 1. Express Client Factory {@link ExpressClientFactory}
 * 2. Default express client implementation {@link ExpressClient}
 *
 */
@Configuration(proxyBeanMethods = false)
public class ExpressClientConfig {

    @Bean
    public ExpressClientFactory expressClientFactory(TradeExpressProperties tradeExpressProperties,
                                                     RestTemplate restTemplate) {
        return new ExpressClientFactoryImpl(tradeExpressProperties, restTemplate);
    }

    @Bean
    public ExpressClient defaultExpressClient(ExpressClientFactory expressClientFactory) {
        return expressClientFactory.getDefaultExpressClient();
    }

}
