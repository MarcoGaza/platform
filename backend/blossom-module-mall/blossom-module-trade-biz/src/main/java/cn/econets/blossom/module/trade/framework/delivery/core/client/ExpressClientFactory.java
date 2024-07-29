package cn.econets.blossom.module.trade.framework.delivery.core.client;

import cn.econets.blossom.module.trade.framework.delivery.core.enums.ExpressClientEnum;

/**
 * Express client factory interface：Used to create and cache express clients
 *
 */
public interface ExpressClientFactory {

    /**
     * Get the default express client
     */
    ExpressClient getDefaultExpressClient();

    /**
     * Get the express client through enumeration，If it does not exist，Create a corresponding express client
     *
     * @param clientEnum Express client enumeration
     */
    ExpressClient getOrCreateExpressClient(ExpressClientEnum clientEnum);

}
