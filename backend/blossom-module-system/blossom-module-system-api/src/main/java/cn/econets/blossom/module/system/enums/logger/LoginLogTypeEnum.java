package cn.econets.blossom.module.system.enums.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Login log type enumeration
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN_USERNAME(100), // Log in using your account
    LOGIN_SOCIAL(101), // Use social login
    LOGIN_MOBILE(103), // Log in using your mobile phone
    LOGIN_SMS(104), // Log in using SMS

    LOGOUT_SELF(200),  // Log out automatically
    LOGOUT_DELETE(202), // Force exit
    ;

    /**
     * Log Type
     */
    private final Integer type;

}
