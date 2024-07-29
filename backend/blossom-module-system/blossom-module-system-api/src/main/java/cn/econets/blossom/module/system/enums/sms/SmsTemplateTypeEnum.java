package cn.econets.blossom.module.system.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS template type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum SmsTemplateTypeEnum {

    VERIFICATION_CODE(1), // Verification code
    NOTICE(2), // Notification
    PROMOTION(3), // Marketing
    ;

    /**
     * Type
     */
    private final int type;

}
