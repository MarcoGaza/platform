package cn.econets.blossom.module.trade.framework.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

/**
 * Configuration items for transaction orders
 *
 */
@ConfigurationProperties(prefix = "application.trade.order")
@Data
@Validated
public class TradeOrderProperties {

    /**
     * Application number
     */
    @NotNull(message = "Application number cannot be empty")
    private Long appId;

    /**
     * Payment timeout
     */
    @NotNull(message = "The payment timeout cannot be empty")
    private Duration payExpireTime;

    /**
     * Receipt timeout
     */
    @NotNull(message = "The delivery timeout cannot be empty")
    private Duration receiveExpireTime;

    /**
     * Comment timeout
     */
    @NotNull(message = "Comment timeout cannot be empty")
    private Duration commentExpireTime;

}
