package cn.econets.blossom.module.trade.framework.delivery.config;

import cn.econets.blossom.module.trade.framework.delivery.core.enums.ExpressClientEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

// TODO ：Should it be stored in the database in the future?？Consider saas Multi-tenant，Different tenants use different configurations？
/**
 * Configuration items for transaction freight express delivery
 *
 */
@Component
@ConfigurationProperties(prefix = "application.trade.express")
@Data
@Validated
public class TradeExpressProperties {

    /**
     * Express Delivery Client
     *
     * Not provided by default，Need to remind users to configure a courier service provider。
     */
    private ExpressClientEnum client = ExpressClientEnum.NOT_PROVIDE;

    /**
     * ExpressBird Configuration
     */
    @Valid
    private KdNiaoConfig kdNiao;
    /**
     * Express Delivery 100 Configuration
     */
    @Valid
    private Kd100Config kd100;

    /**
     * ExpressBird configuration project
     */
    @Data
    public static class KdNiaoConfig {

        /**
         * Expressbird user ID
         */
        @NotEmpty(message = "Expressbird user ID Configuration items cannot be empty")
        private String businessId;
        /**
         * ExpressBird API Key
         */
        @NotEmpty(message = "ExpressBird Api Key Configuration items cannot be empty")
        private String apiKey;

    }

    /**
     * Express Delivery 100 Configuration item
     */
    @Data
    public static class Kd100Config {

        /**
         * Express Delivery 100 Authorization code
         */
        @NotEmpty(message = "Express Delivery 100 The authorization code configuration item cannot be empty")
        private String customer;
        /**
         * Express Delivery 100 Authorization key
         */
        @NotEmpty(message = "Express Delivery 100 Authorization Key Configuration items cannot be empty")
        private String key;

    }

}
