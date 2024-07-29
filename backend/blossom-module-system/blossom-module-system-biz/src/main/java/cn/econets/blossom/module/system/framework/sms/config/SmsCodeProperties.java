package cn.econets.blossom.module.system.framework.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.Duration;

@ConfigurationProperties(prefix = "application.sms-code")
@Validated
@Data
public class SmsCodeProperties {

    /**
     * Expiration time
     */
    @NotNull(message = "The expiration time cannot be empty")
    private Duration expireTimes;
    /**
     * SMS sending frequency
     */
    @NotNull(message = "SMS sending frequency cannot be empty")
    private Duration sendFrequency;
    /**
     * Maximum number of daily shipments
     */
    @NotNull(message = "The maximum number of daily sends cannot be empty")
    private Integer sendMaximumQuantityPerDay;
    /**
     * Minimum verification code value
     */
    @NotNull(message = "The minimum value of the verification code cannot be empty")
    private Integer beginCode;
    /**
     * Verification code maximum value
     */
    @NotNull(message = "The maximum value of the verification code cannot be empty")
    private Integer endCode;

}
