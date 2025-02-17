package cn.econets.blossom.module.trade.framework.delivery.core.client.impl;

import cn.hutool.core.lang.Assert;
import cn.econets.blossom.module.trade.framework.delivery.config.TradeExpressProperties;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.impl.kd100.Kd100ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.impl.kdniao.KdNiaoExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.enums.ExpressClientEnum;
import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClientFactory;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Express client factory implementation class
 *
 */
@AllArgsConstructor
public class ExpressClientFactoryImpl implements ExpressClientFactory {

    private final Map<ExpressClientEnum, ExpressClient> clientMap = new ConcurrentHashMap<>(8);

    private final TradeExpressProperties tradeExpressProperties;
    private final RestTemplate restTemplate;

    @Override
    public ExpressClient getDefaultExpressClient() {
        ExpressClient defaultClient = getOrCreateExpressClient(tradeExpressProperties.getClient());
        Assert.notNull("The default express client cannot be empty");
        return defaultClient;
    }

    @Override
    public ExpressClient getOrCreateExpressClient(ExpressClientEnum clientEnum) {
        return clientMap.computeIfAbsent(clientEnum,
                client -> createExpressClient(client, tradeExpressProperties));
    }

    private ExpressClient createExpressClient(ExpressClientEnum queryProviderEnum,
                                                TradeExpressProperties tradeExpressProperties) {
        switch (queryProviderEnum) {
            case NOT_PROVIDE:
                return new NoProvideExpressClient();
            case KD_NIAO:
                return new KdNiaoExpressClient(restTemplate, tradeExpressProperties.getKdNiao());
            case KD_100:
                return new Kd100ExpressClient(restTemplate, tradeExpressProperties.getKd100());
        }
        return null;
    }
}
