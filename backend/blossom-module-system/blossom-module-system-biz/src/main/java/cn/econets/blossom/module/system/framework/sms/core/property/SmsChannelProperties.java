package cn.econets.blossom.module.system.framework.sms.core.property;

import cn.econets.blossom.module.system.framework.sms.core.enums.SmsChannelEnum;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * SMS channel configuration class
 *
 */
@Data
@Validated
public class SmsChannelProperties {

    /**
     * Channel Number
     */
    @NotNull(message = "SMS channel ID Cannot be empty")
    private Long id;
    /**
     * SMS signature
     */
    @NotEmpty(message = "SMS signature cannot be empty")
    private String signature;
    /**
     * Channel Code
     *
     * Enumeration {@link SmsChannelEnum}
     */
    @NotEmpty(message = "Channel code cannot be empty")
    private String code;
    /**
     * SMS API Account
     */
    @NotEmpty(message = "SMS API The account cannot be empty")
    private String apiKey;
    /**
     * SMS API Key
     */
    @NotEmpty(message = "SMS API The key cannot be empty")
    private String apiSecret;
    /**
     * SMS sending callback URL
     */
    private String callbackUrl;

}
