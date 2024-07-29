package cn.econets.blossom.module.trade.framework.aftersale.config;

import cn.econets.blossom.module.trade.framework.aftersale.core.aop.AfterSaleLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO Change to aftersale Be better；
/**
 * trade Module afterSaleLog Component Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class AfterSaleLogConfiguration {

    @Bean
    public AfterSaleLogAspect afterSaleLogAspect() {
        return new AfterSaleLogAspect();
    }

}
