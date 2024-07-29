package cn.econets.blossom.module.system.enums.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Login result enumeration class
 */
@Getter
@AllArgsConstructor
public enum LoginResultEnum {

    SUCCESS(0), // Success
    BAD_CREDENTIALS(10), // The account or password is incorrect
    USER_DISABLED(20), // User is disabled
    CAPTCHA_NOT_FOUND(30), // The image verification code does not exist
    CAPTCHA_CODE_ERROR(31), // The image verification code is incorrect

    ;

    /**
     * Results
     */
    private final Integer result;

}
