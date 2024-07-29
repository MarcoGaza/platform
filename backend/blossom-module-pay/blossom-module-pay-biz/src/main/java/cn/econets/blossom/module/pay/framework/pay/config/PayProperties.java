package cn.econets.blossom.module.pay.framework.pay.config;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "application.pay")
@Validated
@Data
public class PayProperties {

    private static final String ORDER_NO_PREFIX = "P";
    private static final String REFUND_NO_PREFIX = "R";

    /**
     * Payment callback address
     *
     * In fact，Corresponding PayNotifyController of notifyOrder Method URL
     *
     * Callback order：Payment channel（Alipay payment、WeChat Pay） => blossom-module-pay of orderNotifyUrl Address => Business PayAppDO.orderNotifyUrl Address
     */
    @NotEmpty(message = "The payment callback address cannot be empty")
    @URL(message = "The format of the payment callback address must be URL")
    private String orderNotifyUrl;

    /**
     * Refund callback address
     *
     * In fact，Corresponding PayNotifyController of notifyRefund Method URL
     *
     * Callback order：Payment channel（Alipay payment、WeChat Pay） => blossom-module-pay of refundNotifyUrl Address => Business PayAppDO.notifyRefundUrl Address
     */
    @NotEmpty(message = "The payment callback address cannot be empty")
    @URL(message = "The format of the payment callback address must be URL")
    private String refundNotifyUrl;

    /**
     * Payment order no Prefix
     */
    @NotEmpty(message = "Payment order no The prefix cannot be empty")
    private String orderNoPrefix = ORDER_NO_PREFIX;

    /**
     * Refund order no Prefix
     */
    @NotEmpty(message = "Refund order no The prefix cannot be empty")
    private String refundNoPrefix = REFUND_NO_PREFIX;

}
